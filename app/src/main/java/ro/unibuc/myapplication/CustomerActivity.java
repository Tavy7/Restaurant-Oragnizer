package ro.unibuc.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class CustomerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
                navController.navigate(R.id.accountSettingsCustomerFragment);
                item.setChecked(true);
                break;
        }

        return false;
    }
}
