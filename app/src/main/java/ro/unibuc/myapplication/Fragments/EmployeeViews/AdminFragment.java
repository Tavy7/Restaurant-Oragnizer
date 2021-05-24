package ro.unibuc.myapplication.Fragments.EmployeeViews;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;

import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Fragments.LoginFragment;
import ro.unibuc.myapplication.R;

public class AdminFragment extends Fragment {
    NavController navController;
    Button logoutBtn;
    public AdminFragment(){
        super(R.layout.fragment_admin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((EmployeeActivity)requireActivity()).setTitle("Admin Panel");

        // Initialize navController
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().
                 getSupportFragmentManager().findFragmentById(R.id.EmployeeMainFragment);
        navController = navHostFragment.getNavController();

        // Here we have CRUD for all models
        final TextView customerBtn = view.findViewById(R.id.AdminCustomerBtn);
        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to customer CRUD fragment
                navController.navigate(R.id.fragmentViewCustomer);
            }
        });

        final TextView employeeBtn = view.findViewById(R.id.AdminEmpBtn);
        employeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to emp CRUD fragment
                navController.navigate(R.id.fragmentViewEmployee);
            }
        });

        final TextView itemBtn = view.findViewById(R.id.AdminItemBtn);
        itemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to item CRUD fragment
                navController.navigate(R.id.fragmentViewItems);
            }
        });

        final TextView orderBtn = view.findViewById(R.id.AdminOrdersBtn);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to order CRUD fragment
                navController.navigate(R.id.fragmentViewOrders);
            }
        });

        final TextView scheduleBtn = view.findViewById(R.id.AdminSchBtn);
        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.fragmentViewSchedules);
            }
        });

        final TextView tableBtn = view.findViewById(R.id.AdminTableBtn);
        tableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.fragmentViewTables);
            }
        });


        logoutBtn = requireActivity().findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences sp = AccountActivity.getSharedPreferencesInstance(requireContext());
//                sp.edit().clear().commit();
                LoginFragment.getmGoogleSignInClient().signOut();
                FirebaseAuth.getInstance().signOut();
                requireActivity().finish();
            }
        });
    }
}
