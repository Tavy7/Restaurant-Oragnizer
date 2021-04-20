package ro.unibuc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class AccountActivity extends AppCompatActivity {

    private static SharedPreferences sharedPreferences = null;
    private static final String SHARED_PREF_NAME = "AppPref";
    public static final String SPKEY_NAME = "username";
    private GoogleSignInAccount googleAccount;

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
        Intent intent = new Intent(AccountActivity.this, UserActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);


        // Get google account
//        googleAccount = GoogleSignIn.getLastSignedInAccount(this);
//        if (googleAccount != null){
//            gotoUserActivity();
//            finish();
//        }

        // Get shared preferences
        sharedPreferences = getSharedPreferencesInstance(this);
        String name = sharedPreferences.getString(SPKEY_NAME, null);
        if (name != null){
            // If user is logged, change to main activity
            gotoEmpActivity();
            finish();
        }
    }
}