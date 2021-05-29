package ro.unibuc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Models.Customer;
import ro.unibuc.myapplication.Models.Employee;

public class AccountActivity extends AppCompatActivity {

    private static SharedPreferences sharedPreferences = null;
    private static final String SHARED_PREF_NAME = "AppPref";
    public static final String SPKEY_NAME = "username";

    // Returns shared prefs singleton instance
    public static synchronized SharedPreferences getSharedPreferencesInstance(Context context) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        return sharedPreferences;
    }

    protected int function(String currentUserName) {

        // Search for employee username
        Employee emp = RestaurantDatabase.getInstance(this).
                employeeDAO().getEmployeeByName(currentUserName);

        if (emp != null) {
            return 1;
        }
        Customer customer = RestaurantDatabase.getInstance(this).
                customerDAO().getCustomerByName(currentUserName);

        if (customer != null) {
            return 2;
        }
        return 0;
    }

    public int getUserType(){
        // Get logged username on share prefs
        SharedPreferences sharedPreferences = getSharedPreferencesInstance(this);
        String currentUserName = sharedPreferences.getString(AccountActivity.SPKEY_NAME, null);

        return function(currentUserName);
    }

    private void gotoEmpActivity(){
        Intent intent = new Intent(AccountActivity.this, EmployeeActivity.class);
        startActivity(intent);
    }

    private void gotoUserActivity(){
        Intent intent = new Intent(AccountActivity.this, CustomerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

         //Get google account
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            gotoUserActivity();
            finish();
            return;
        }

        // Get shared preferences
        sharedPreferences = getSharedPreferencesInstance(this);
        String name = sharedPreferences.getString(SPKEY_NAME, null);
        if (name != null){
            // If user is logged, change to main activity
            //gotoEmpActivity();
            //finish();
        }
    }
}