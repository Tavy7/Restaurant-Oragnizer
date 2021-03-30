package ro.unibuc.myapplication.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to emp CRUD fragment
            }
        });

        final TextView itemBtn = view.findViewById(R.id.AdminItemBtn);
        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to item CRUD fragment
            }
        });

        final TextView orderBtn = view.findViewById(R.id.AdminOrdersBtn);
        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to btn CRUD fragment
            }
        });

        final TextView scheduleBtn = view.findViewById(R.id.AdminSchBtn);
        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to schedule CRUD fragment
            }
        });

        final TextView tableBtn = view.findViewById(R.id.AdminTableBtn);
        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to table CRUD fragment
            }
        });

        final TextView userBtn = view.findViewById(R.id.AdminUsersBtn);
        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to user CRUD fragment
            }
        });
    }
}
