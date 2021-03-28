package ro.unibuc.myapplication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ro.unibuc.myapplication.AccountActivity;
import ro.unibuc.myapplication.MainActivity;
import ro.unibuc.myapplication.R;

public class LoginFragment extends Fragment {
    EditText username, password;
    Button loginButton;
    TextView registerText;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "AppPref";
    private static final String SPKEY_NAME = "username";
    private static final String SPKEY_PASS = "password";

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AccountActivity)getActivity()).setTitle(R.string.login);

        username = view.findViewById(R.id.loginUsername);
        password = view.findViewById(R.id.loginPassword);
        loginButton = view.findViewById(R.id.registerButton);
        registerText = view.findViewById(R.id.registerText);

        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Verify credentials
                String name = sharedPreferences.getString(SPKEY_NAME, "-4");
                String pass = sharedPreferences.getString(SPKEY_PASS, "-20");

                String usernameVal = username.getText().toString();
                String passwordVal = password.getText().toString();

                // If input username equals db username
                if (name.equals(usernameVal)){
                    // If in pass eq db pass
                    if (pass.equals(passwordVal)){
                        Toast.makeText(getContext(), "Login succesful!", Toast.LENGTH_SHORT).show();
                        // Goto main activity
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(getContext(), "Wrong credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // If users click on register we switch to register fragment
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AccountActivity)getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RegisterFragment fragment = new RegisterFragment();
                fragmentTransaction.replace(R.id.LoginFragment, fragment)
                        .addToBackStack(null).commit();
            }
        });
    }
}
