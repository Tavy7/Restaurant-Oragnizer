package ro.unibuc.myapplication.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ro.unibuc.myapplication.Fragments.CRUDs.FragmentViewCustomer;
import ro.unibuc.myapplication.Fragments.CRUDs.FragmentViewEmployee;
import ro.unibuc.myapplication.Fragments.CRUDs.FragmentViewItems;
import ro.unibuc.myapplication.Fragments.CRUDs.FragmentViewOrders;
import ro.unibuc.myapplication.Fragments.CRUDs.FragmentViewSchedules;
import ro.unibuc.myapplication.Fragments.CRUDs.FragmentViewTables;
import ro.unibuc.myapplication.MainActivity;
import ro.unibuc.myapplication.R;

public class AdminFragment extends Fragment {
    public AdminFragment(){
        super(R.layout.fragment_admin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)requireActivity()).setTitle("Admin Panel");

        final TextView customerBtn = view.findViewById(R.id.AdminCustomerBtn);
        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to customer CRUD fragment
                FragmentViewCustomer viewCustomer = new FragmentViewCustomer();

                FragmentTransaction fragmentTransaction = requireActivity().
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.MainFragment, viewCustomer)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        final TextView employeeBtn = view.findViewById(R.id.AdminEmpBtn);
        employeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to emp CRUD fragment
                FragmentViewEmployee viewEmployeeFragment = new FragmentViewEmployee();

                FragmentTransaction fragmentTransaction = requireActivity().
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.MainFragment, viewEmployeeFragment)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        final TextView itemBtn = view.findViewById(R.id.AdminItemBtn);
        itemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to item CRUD fragment
                FragmentViewItems viewItemsFragmentFragment = new FragmentViewItems();

                FragmentTransaction fragmentTransaction = requireActivity().
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.MainFragment, viewItemsFragmentFragment)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        final TextView orderBtn = view.findViewById(R.id.AdminOrdersBtn);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to order CRUD fragment
                FragmentViewOrders ordersFragment = new FragmentViewOrders();

                FragmentTransaction fragmentTransaction = requireActivity().
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.MainFragment, ordersFragment)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        final TextView scheduleBtn = view.findViewById(R.id.AdminSchBtn);
        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentViewSchedules schedulesFragment = new FragmentViewSchedules();

                FragmentTransaction fragmentTransaction = requireActivity().
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.MainFragment, schedulesFragment)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        final TextView tableBtn = view.findViewById(R.id.AdminTableBtn);
        tableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentViewTables viewTablesFragmentFragment = new FragmentViewTables();

                FragmentTransaction fragmentTransaction = requireActivity().
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.MainFragment, viewTablesFragmentFragment)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}
