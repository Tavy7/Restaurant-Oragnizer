package ro.unibuc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    private static SharedPreferences sharedPreferences = null;
    private static final String SHARED_PREF_NAME = "AppPref";
    public static final String SPKEY_NAME = "username";
    public static final int AT_EMP = 1;
    public static final int AT_CUS = 2;

    protected static String currentUsername;
    protected static int currentUserType;

    // Returns shared prefs singleton instance
    public static synchronized SharedPreferences getSharedPreferencesInstance(Context context) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        return sharedPreferences;
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
            int userType = getCurrentUserType();

            Intent intent = null;
            if (userType == 1){
                // User is an employee
                intent = new Intent(this, EmployeeActivity.class);
            }
            else if (userType == 2 || userType == 0){
                // User is customer or not found
                intent = new Intent(this, CustomerActivity.class);
            }

            startActivity(intent);
            finish();
        }
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static void setCurrentUsername(String currentUsername) {
        AccountActivity.currentUsername = currentUsername;
    }

    public static int getCurrentUserType() {
        return currentUserType;
    }

    public static void setCurrentUserType(int currentUserType) {
        AccountActivity.currentUserType = currentUserType;
    }
}