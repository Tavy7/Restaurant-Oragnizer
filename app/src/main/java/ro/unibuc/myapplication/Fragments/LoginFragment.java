package ro.unibuc.myapplication.Fragments;

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
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.myapplication.AccountActivity;
import ro.unibuc.myapplication.CustomerActivity;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Models.Customer;
import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.Models.Passwords;
import ro.unibuc.myapplication.R;

public class LoginFragment extends Fragment {
    EditText username, password;
    Button loginButton;
    TextView registerText;
    Button googleLoginBtn;

    private static final String ADMIN = "admin";
    private static final String DEFAULT_EMP_PASSWORD = "123emp";
    private static GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;

    private static final String SPKEY_NAME = "username";
    private static final String SPKEY_PASS = "password";
    private FirebaseAuth mAuth;
    private static final String spDefaultUsername = "no_username_found";

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(R.string.login);

        username = view.findViewById(R.id.loginUsername);
        password = view.findViewById(R.id.loginPassword);
        loginButton = view.findViewById(R.id.registerButton);
        registerText = view.findViewById(R.id.registerText);
        googleLoginBtn = view.findViewById(R.id.googleLoginBtn);

        // If user clicks on login button
        SharedPreferences sharedPreferences = AccountActivity.getSharedPreferencesInstance(requireContext());

        // Get a list of employees
        List<Employee> employeeList = RestaurantDatabase.getInstance(requireContext()).
                employeeDAO().getAllEmployees();

        loginButton.setOnClickListener(new View.OnClickListener() {
            // Checks if input credentials are equal to
            // ones stored in shared preferences
            @Override
            public void onClick(View v) {
                String usernameVal = username.getText().toString();
                String passwordVal = password.getText().toString();

                Passwords hashPass = new Passwords(passwordVal, usernameVal);
                String hashedPassword = hashPass.calculateHash();

                StringBuilder adminPassB = new StringBuilder();
                adminPassB.append(ADMIN);
                String adminPass = adminPassB.reverse().toString();

                // If user and pass are admin credentials
                if (usernameVal.equals(ADMIN) && passwordVal.equals(adminPass)){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SPKEY_NAME, usernameVal);
                    editor.apply();
                    AccountActivity.setCurrentUsername(usernameVal);
                    AccountActivity.setCurrentUserType(AccountActivity.AT_EMP);
                    gotoMainActivity();
                    requireActivity().finish();
                    return;
                }

                for (Employee emp : employeeList){
                    boolean ok = emp.getName().equals(usernameVal);
                    if (ok) {
                        String passwprd = emp.getPassword();
                        boolean passOK = hashedPassword.equals(passwprd) || passwordVal.equals(DEFAULT_EMP_PASSWORD);
                        if (passOK){

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(SPKEY_NAME, usernameVal);
                            editor.apply();
                            AccountActivity.setCurrentUsername(usernameVal);
                            AccountActivity.setCurrentUserType(AccountActivity.AT_EMP);
                            // If username belogns to an employee
                            // go to emp acitvity
                            gotoMainActivity();
                            requireActivity().finish();
                            return;
                        }
                    }
                }

                // Verify credentials
                String spName = sharedPreferences.getString(SPKEY_NAME, spDefaultUsername);
                // If input username equals db username
                if (usernameVal.equals(spName)){

                    String pass = sharedPreferences.getString(SPKEY_PASS, "-20");
                    if (pass.equals(hashedPassword)){
                        AccountActivity.setCurrentUsername(usernameVal);
                        AccountActivity.setCurrentUserType(AccountActivity.AT_CUS);
                        // Anonymous customer login
                        Toast.makeText(getContext(), "Login succesful!", Toast.LENGTH_SHORT).show();
                        gotoMainActivity();
                    }
                }

                RestaurantDatabase db = RestaurantDatabase.getInstance(requireContext());
                Customer customer = db.customerDAO().getCustomerByName(usernameVal);
                Employee employee = db.employeeDAO().getEmployeeByName(usernameVal);

                // Calculate password hash
                String dbPassword = null;
                if (customer != null){
                    dbPassword = customer.getPassword();
                    AccountActivity.setCurrentUserType(AccountActivity.AT_CUS);
                }

                if (employee != null){
                    dbPassword = employee.getPassword();
                    AccountActivity.setCurrentUserType(AccountActivity.AT_EMP);
                }

                if (dbPassword != null && dbPassword.compareTo(hashedPassword) == 0){
                    if (spName.compareTo(spDefaultUsername) == 0){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(usernameVal, SPKEY_NAME);
                        AccountActivity.setCurrentUsername(usernameVal);

                        Toast.makeText(getContext(), usernameVal, Toast.LENGTH_SHORT).show();
                        editor.apply();
                        editor.commit();
                    }

                    gotoMainActivity();
                    return;
                }

                Toast.makeText(getContext(), "Wrong credentials!", Toast.LENGTH_SHORT).show();
            }
        });

        // If users click on register we switch to register fragment
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate(R.id.registerFragment);
            }
        });

        // Google login
        mAuth = FirebaseAuth.getInstance();
        createRequest();
        // If user click on google button
        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call google's sign in method
                signIn();
            }
        });
    }

    public void gotoMainActivity(){
        // End account activity
        try{
            int userType = AccountActivity.getCurrentUserType();

            Intent intent = null;
            if (userType == AccountActivity.AT_EMP){
                // User is an employee
                intent = new Intent(getContext(), EmployeeActivity.class);
            }
            else if (userType == AccountActivity.AT_CUS || userType == 0){
                // User is customer or not found
                intent = new Intent(getContext(), CustomerActivity.class);
            }

            // Goto main activity
            startActivity(intent);

            AccountActivity aa = ((AccountActivity)(requireActivity()));
            aa.finish();
        }
        catch (java.lang.ClassCastException e){
            // Context is from another activity
        }
    }

    private void createRequest(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(requireContext(), "Authentification error.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Google login succesful!", Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            // Store username in shared prefs
                            SharedPreferences sp = AccountActivity.getSharedPreferencesInstance(requireContext());
                            SharedPreferences.Editor editor = sp.edit();
                            Toast.makeText(requireContext(), user.getDisplayName(), Toast.LENGTH_SHORT).show();

                            editor.putString(AccountActivity.SPKEY_NAME, user.getDisplayName());

                            AccountActivity.setCurrentUsername(user.getDisplayName());
                            AccountActivity.setCurrentUserType(AccountActivity.AT_CUS);

                            editor.apply();
                            editor.commit();

                            Customer customer = new Customer(user.getDisplayName(), "",
                                    new ArrayList<Order>(), user.getEmail());
                            RestaurantDatabase.getInstance(requireContext()).customerDAO().insertCustomer(customer);

                            gotoMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Login error!", Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                    }
                });
    }

    public static GoogleSignInClient getmGoogleSignInClient() {
        return mGoogleSignInClient;
    }
}
