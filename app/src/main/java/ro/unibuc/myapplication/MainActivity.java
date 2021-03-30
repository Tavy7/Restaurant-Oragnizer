package ro.unibuc.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Fragments.CalendarFragment;
import ro.unibuc.myapplication.Fragments.TableFragment;
import ro.unibuc.myapplication.Models.Item;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertItems();
        initializeNavBar();

        if (savedInstanceState == null){
            // Start app with calendar fragment
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.MainFragment, CalendarFragment.class, null)
                    .commit();
        }
    }

    // Function that adds functionality to main navigation bar
    protected void initializeNavBar(){

        // Initialize calendar button
        CalendarFragment calendarFragment = new CalendarFragment();
        final Button calendar = (Button)findViewById(R.id.CalendarBtn);
        // Add listener to change fragment on click
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize fragment
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.MainFragment, calendarFragment)
                        .addToBackStack(null).commit();

            }
        });

        TableFragment tableFragment = new TableFragment();
        final Button tables = (Button)findViewById(R.id.TablesBtn);
        // Add listener to change fragment on click
        tables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.MainFragment, tableFragment)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        final Button account = (Button)findViewById(R.id.AccountBtn);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Account deleted", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("AppPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();

                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);

            }
        });
    }

    private void insertItems(){
        Item item1 = new Item(1, "item10210", 6.80f, "desc", 0);
        Item item2 = new Item(2, "3434", 3.80f, "desc", 0);


        RestaurantDatabase db = RestaurantDatabase.getInstance(this);
        db.itemDao().insertItem(item1);
        db.itemDao().insertItem(item2);
    }
}