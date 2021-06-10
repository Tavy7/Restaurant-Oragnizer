package ro.unibuc.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Models.Customer;

public class CustomerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String CUSTOMER_KEY = "123esdsdda";
    static NavController navController;
    static BottomNavigationView nav;

    public static NavController getNavController() {
        return navController;
    }

    public static BottomNavigationView getNav() {
        return nav;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        nav = findViewById(R.id.customerNavBar);
        nav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        // Initialize navController
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.CustomerFragment);
        navController = navHostFragment.getNavController();
    }

    @Override
    public void onBackPressed(){
        navController.popBackStack();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.customerHome:
                navController.navigate(R.id.homeCustomerFragment);
                item.setChecked(true);
                break;

            case R.id.orderHistory:
                navController.navigate(R.id.orderHistoryCustomerFragment);
                item.setChecked(true);
                break;

            case R.id.accountSettings:
                SharedPreferences sp = AccountActivity.getSharedPreferencesInstance(this);
                sp.edit().clear().apply();

                FirebaseAuth.getInstance().signOut();
                String username = AccountActivity.getCurrentUsername();
                RestaurantDatabase db = RestaurantDatabase.getInstance(this);
                Customer customer = db.customerDAO().getCustomerByName(username);
                Bundle bundle = new Bundle();
                bundle.putParcelable(CUSTOMER_KEY, customer);

                navController.navigate(R.id.accountSettingsCustomerFragment, bundle);
                item.setChecked(true);
                break;
        }

        return false;
    }
}
