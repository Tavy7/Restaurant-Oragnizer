package ro.unibuc.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ro.unibuc.myapplication.Fragments.CalendarFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            // Start app with schedule fragment
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.CalendarFragmentContainer, CalendarFragment.class, null)
                    .commit();
        }
    }
}