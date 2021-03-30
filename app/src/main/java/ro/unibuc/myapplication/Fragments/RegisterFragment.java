package ro.unibuc.myapplication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ro.unibuc.myapplication.AccountActivity;
import ro.unibuc.myapplication.MainActivity;
import ro.unibuc.myapplication.R;

public class RegisterFragment extends Fragment {
    EditText username, password;
    Button registerButton;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "AppPref";
    private static final String SPKEY_NAME = "username";
    private static final String SPKEY_PASS = "password";

    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Change title
        ((AccountActivity)getActivity()).setTitle(R.string.register);

        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        username = view.findViewById(R.id.loginUsername);
        password = view.findViewById(R.id.loginPassword);
        registerButton = view.findViewById(R.id.registerButton);


        // TODO Verifica daca username exista in baza de date
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Register
                String userVal = username.getText().toString();
                String passVal = password.getText().toString();

                if (userVal.length() == 0 || passVal.equals("")){
                    Toast.makeText(getContext(), "Field cannot be empty!", Toast.LENGTH_SHORT).show();

                }else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SPKEY_NAME, userVal);
                    editor.putString(SPKEY_PASS, passVal);
                    editor.apply();

                    Toast.makeText(getContext(), "User registred!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
