package ro.unibuc.myapplication.Fragments.CRUDs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.myapplication.Adapters.EmployeeAdapter;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Fragments.OnItemClickListener;
import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.R;

public class FragmentViewEmployee extends Fragment implements OnItemClickListener {
    protected static final String bundleKey = "abcades";

    public FragmentViewEmployee(){ super(R.layout.fragment_view_employees); };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((EmployeeActivity)requireActivity()).setTitle("Employee CRUD");

        // Create recycler view
        RecyclerView empRecyclerView = (RecyclerView)view.findViewById(R.id.emp_crud_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.VERTICAL, false
        );

        // Set layout
        empRecyclerView.setLayoutManager(layoutManager);
        empRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Get the data
        RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
        List<Employee> empList = db.employeeDAO().getAllEmployees();
        EmployeeAdapter empAdapter = new EmployeeAdapter((ArrayList<Employee>) empList, this);
        empRecyclerView.setAdapter(empAdapter);

        final Button addEmpBtn = view.findViewById(R.id.addEmpBtn);
        addEmpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize fragment
                CRUD_Employee employeeCRUDFragment = new CRUD_Employee();

                FragmentTransaction fragmentTransaction = ((EmployeeActivity)view.getContext()).
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.EmployeeMainFragment, employeeCRUDFragment)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onItemClick(Employee employee) {
        // Save item that user clicked on and pass to next fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable(bundleKey, employee);

        CRUD_Employee employeeCRUD = new CRUD_Employee();
        employeeCRUD.setArguments(bundle);

        FragmentTransaction fragmentTransaction = requireActivity().
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.EmployeeMainFragment, employeeCRUD)
                .addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static String getBundleKey() {
        return bundleKey;
    }
}
