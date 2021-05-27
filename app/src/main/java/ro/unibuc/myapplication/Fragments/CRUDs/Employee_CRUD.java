package ro.unibuc.myapplication.Fragments.CRUDs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.R;

public class Employee_CRUD extends Fragment {
    protected EditText employeeName;
    protected EditText employeeRole;
    protected Button addEmp;
    protected Button deleteEmp;

    public Employee_CRUD(){ super(R.layout.fragment_add_employee); };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        employeeName = view.findViewById(R.id.addEmpName);
        employeeRole = view.findViewById(R.id.addEmpRole);
        addEmp = view.findViewById(R.id.saveEmpBtn);
        deleteEmp = view.findViewById(R.id.deleteEmpBtn);

        // If bundle is not null that means the
        // fragment was called to update an item
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Employee employee = bundle.getParcelable(EmployeesViewFragment.getBundleKey());
            buttonUpdateItem(employee);
        }
        else {
            buttonInsertNewItem();
        }
    }

    // Returns the object if the data is ok
    // else returns null
    protected Employee verifyDataInserted(View view){
        // Check if item name is not empty
        String employeeNameVal = employeeName.getText().toString();
        if (employeeNameVal == ""){
            Toast.makeText(view.getContext(),
                    "Name cannot be empty!", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Check for description
        String empRoleVal = employeeRole.getText().toString();
        if (empRoleVal.equals("")){
            Toast.makeText(view.getContext(),
                "Role cannot be empty!", Toast.LENGTH_SHORT).show();
            return null;
        }

        return new Employee(employeeNameVal, empRoleVal);
    }

    protected void buttonInsertNewItem(){
        addEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Employee employee = verifyDataInserted(view);

                if (employee == null) {
                    return;
                }

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                // Returns new generated id
                long newGeneratedId = db.employeeDAO().insertEmployee(employee);
                Toast.makeText(view.getContext(),
                        "Employee succesfully inserated!", Toast.LENGTH_SHORT).show();

                // Go back to list view
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    protected void buttonUpdateItem(Employee employee){
        employeeName.setText(employee.getName());
        employeeRole.setText(employee.getRole());

        addEmp.setText("Edit employee");
        addEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Employee newEmp = verifyDataInserted(view);

                if (newEmp == null) {
                    return;
                }
                // Item generated is new so it has no id
                newEmp.setUid(employee.getUid());

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.employeeDAO().updateEmployee(newEmp);

                Toast.makeText(view.getContext(),
                        "Employee succesfully updated!", Toast.LENGTH_SHORT).show();
                // Go back to list view
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Delete button is hidden by default in view
        deleteEmp.setVisibility(View.VISIBLE);
        deleteEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.employeeDAO().deleteEmployee(employee);
                Toast.makeText(getContext(), "Employee deleted", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
