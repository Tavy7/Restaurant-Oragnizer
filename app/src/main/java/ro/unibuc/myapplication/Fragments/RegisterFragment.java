package ro.unibuc.myapplication.Fragments;

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

import java.util.ArrayList;

import ro.unibuc.myapplication.AccountActivity;
import ro.unibuc.myapplication.CustomerActivity;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Models.Customer;
import ro.unibuc.myapplication.Models.Passwords;
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
        requireActivity().setTitle(R.string.register);

        sharedPreferences = AccountActivity.getSharedPreferencesInstance(requireActivity());

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

                    Passwords hashPass = new Passwords(userVal, passVal);
                    String hashedPassword = hashPass.calculateHash();

                    editor.putString(SPKEY_PASS, hashedPassword);
                    editor.apply();
                    editor.commit();

                    // Only customers should register as employees should have
                    // account generated from database
                    Customer customer = new Customer(userVal, "", new ArrayList<>(), "");
                    RestaurantDatabase.getInstance(requireContext())
                            .customerDAO().insertCustomer(customer);

                    Toast.makeText(getContext(), "User registred!", Toast.LENGTH_SHORT).show();
                    gotoMainActivity();

                }
            }
        });
    }

    public void gotoMainActivity(){
        // End account activity
        try{
            AccountActivity aa = ((AccountActivity)(requireActivity()));

            int userType = aa.getUserType();

            Intent intent = null;
            if (userType == 1){
                // User is an employee
                intent = new Intent(getContext(), EmployeeActivity.class);
            }
            else if (userType == 2 || userType == 0){
                // User is customer or not found
                intent = new Intent(getContext(), CustomerActivity.class);
            }

            // Goto main activity
            startActivity(intent);

            aa.finish();
        }
        catch (java.lang.ClassCastException e){
            // Context is from another activity
        }
    }
}
