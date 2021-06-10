package ro.unibuc.myapplication.Fragments.EmployeeViews;

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

import com.google.firebase.auth.FirebaseAuth;

import ro.unibuc.myapplication.AccountActivity;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.Models.Passwords;
import ro.unibuc.myapplication.R;

public class AccountSettingsEmployeeFragment extends Fragment {
    EditText name;
    EditText password;
    TextView role;
    Button saveBtn;
    Button logoutBtn;
    SharedPreferences.Editor editor;

    public AccountSettingsEmployeeFragment() { super(R.layout.fragment_employee_account_settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.empName);
        password = view.findViewById(R.id.empPass);
        role = view.findViewById(R.id.empRole);
        saveBtn = view.findViewById(R.id.empSaveChanges);
        logoutBtn = view.findViewById(R.id.empLogOut);
        editor = (SharedPreferences.Editor)
                AccountActivity.getSharedPreferencesInstance(requireContext()).edit();

        Bundle bundle = getArguments();
        if (bundle != null) {
            Employee employee = bundle.getParcelable(EmployeeActivity.EMPLOYEE_KEY);
            if (employee != null) {
                name.setText(employee.getName());
                role.setText(employee.getRole());

                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nameVal = name.getText().toString();
                        String passwordVal = password.getText().toString();

                        editor.putString(AccountActivity.SPKEY_NAME, nameVal);
                        AccountActivity.setCurrentUsername(nameVal);

                        Employee newEmp = new Employee(nameVal, employee.getRole());
                        newEmp.setPassword(employee.getPassword());
                        newEmp.setUid(employee.getUid());
                        if (!passwordVal.equals("") && passwordVal.length() > 5){
                            Passwords passwords = new Passwords(passwordVal, newEmp.getName());
                            String newPass = passwords.calculateHash();
                            newEmp.setPassword(newPass);
                        }


                        RestaurantDatabase.getInstance(requireContext()).employeeDAO().insertEmployee(newEmp);

                    }
                });
            }
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logout
                editor.clear().apply();
                Toast.makeText(view.getContext(), "Logged out", Toast.LENGTH_SHORT).show();

                FirebaseAuth.getInstance().signOut();
                requireActivity().finish();
            }
        });
    }
}
