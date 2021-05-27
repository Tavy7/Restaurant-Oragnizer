package ro.unibuc.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class EmployeeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static NavController navController;
    BottomNavigationView nav;

    public static NavController getNavController() {
        return navController;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        if (savedInstanceState == null){
            // Initialize bottom navigation bar
            nav = findViewById(R.id.empNavBar);
            nav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

            // Initialize navController
            NavHostFragment navHostFragment =
                    (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.EmployeeMainFragment);
            navController = navHostFragment.getNavController();

            // Start app with calendar fragment ( old home ) -- using frame manager
//            getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .add(R.id.EmployeeMainFragment, CalendarFragment.class, null)
//                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.tableFragment:
                navController.navigate(R.id.tableFragment);
                item.setChecked(true);
                break;

            case R.id.calendarFragment:
                navController.navigate(R.id.calendarFragment);
                item.setChecked(true);
                break;

            case R.id.adminFragment:
                navController.navigate(R.id.adminFragment);
                item.setChecked(true);
                break;
        }

        return false;
    }
}