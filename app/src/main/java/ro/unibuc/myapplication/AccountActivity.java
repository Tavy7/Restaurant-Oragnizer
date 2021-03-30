package ro.unibuc.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import ro.unibuc.myapplication.Fragments.LoginFragment;

public class AccountActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "AppPref";
    private static final String SPKEY_NAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(SPKEY_NAME, null);

        if (name != null){
            // If user is logged, change to main activity
            Intent intent = new Intent(AccountActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            // goto login
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.LoginFragment, LoginFragment.class, null)
                    .commit();
        }
    }
}