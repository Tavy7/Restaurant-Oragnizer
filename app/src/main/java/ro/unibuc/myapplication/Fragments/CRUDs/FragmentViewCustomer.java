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

import java.util.List;

import ro.unibuc.myapplication.Adapters.CustomerAdapter;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Fragments.OnItemClickListener;
import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Models.Customer;
import ro.unibuc.myapplication.R;

public class FragmentViewCustomer extends Fragment implements OnItemClickListener {
    protected static final String bundleKey = "cdxijna";

    public FragmentViewCustomer(){ super(R.layout.fragmnet_view_customers); };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((EmployeeActivity)requireActivity()).setTitle("Customer CRUD");

        // Create recycler view
        RecyclerView customerRecyclerView = (RecyclerView)view.findViewById(R.id.customer_crud_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.VERTICAL, false
        );

        // Set layout
        customerRecyclerView.setLayoutManager(layoutManager);
        customerRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Get the data
        RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
        List<Customer> empList = db.customerDAO().getAllCustomers();

        CustomerAdapter customerAdapter = new CustomerAdapter(empList, this);
        customerRecyclerView.setAdapter(customerAdapter);

        final Button addEmpBtn = view.findViewById(R.id.addCustomerBtn);
        addEmpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize fragment
                CRUD_Customer customerCrudFragment = new CRUD_Customer();

                FragmentTransaction fragmentTransaction = ((EmployeeActivity)view.getContext()).
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.EmployeeMainFragment, customerCrudFragment)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onItemClick(Customer customer) {
        // Save item that user clicked on and pass to next fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable(bundleKey, customer);

        CRUD_Customer customerCRUD = new CRUD_Customer();
        customerCRUD.setArguments(bundle);

        FragmentTransaction fragmentTransaction = requireActivity().
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.EmployeeMainFragment, customerCRUD)
                .addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static String getBundleKey() {
        return bundleKey;
    }
}
