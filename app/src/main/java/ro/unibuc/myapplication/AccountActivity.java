package ro.unibuc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import ro.unibuc.myapplication.Fragments.LoginFragment;

public class AccountActivity extends AppCompatActivity {

    private static SharedPreferences sharedPreferences = null;
    private static final String SHARED_PREF_NAME = "AppPref";
    private static final String SPKEY_NAME = "username";
    private GoogleSignInAccount googleAccount;

    public static synchronized SharedPreferences getSharedPreferencesInstance(Context context) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        return sharedPreferences;
    }

    private void gotoMainActivity(){
        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Get google account
        googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleAccount != null){
            gotoMainActivity();
        }

        if (googleAccount == null){
            Toast.makeText(this, "heu", Toast.LENGTH_SHORT).show();
        }

        sharedPreferences = getSharedPreferencesInstance(getApplicationContext());
        // Get shared preferences
        String name = sharedPreferences.getString(SPKEY_NAME, null);

        if (name != null){
            // If user is logged, change to main activity
            gotoMainActivity();
        }
        else {
            // else goto login
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.LoginFragment, LoginFragment.class, null)
                    .commit();
        }
    }
}