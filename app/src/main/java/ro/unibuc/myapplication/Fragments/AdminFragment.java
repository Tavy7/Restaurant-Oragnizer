package ro.unibuc.myapplication.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ro.unibuc.myapplication.Fragments.CRUDs.ViewItemsFragment;
import ro.unibuc.myapplication.Fragments.CRUDs.ViewTablesFragment;
import ro.unibuc.myapplication.MainActivity;
import ro.unibuc.myapplication.R;

public class AdminFragment extends Fragment {
    public AdminFragment(){
        super(R.layout.fragment_admin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setTitle("Admin Panel");

        final TextView customerBtn = view.findViewById(R.id.AdminCustomerBtn);
        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to customer CRUD fragment
            }
        });

        final TextView employeeBtn = view.findViewById(R.id.AdminEmpBtn);
        employeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to emp CRUD fragment
            }
        });

        final TextView itemBtn = view.findViewById(R.id.AdminItemBtn);
        itemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewItemsFragment viewItemsFragmentFragment = new ViewItemsFragment();

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
                // go to btn CRUD fragment
            }
        });

        final TextView scheduleBtn = view.findViewById(R.id.AdminSchBtn);
        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to schedule CRUD fragment
            }
        });

        final TextView tableBtn = view.findViewById(R.id.AdminTableBtn);
        tableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewTablesFragment viewTablesFragmentFragment = new ViewTablesFragment();

                FragmentTransaction fragmentTransaction = requireActivity().
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.MainFragment, viewTablesFragmentFragment)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        final TextView userBtn = view.findViewById(R.id.AdminUsersBtn);
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to user CRUD fragment
            }
        });
    }
}
