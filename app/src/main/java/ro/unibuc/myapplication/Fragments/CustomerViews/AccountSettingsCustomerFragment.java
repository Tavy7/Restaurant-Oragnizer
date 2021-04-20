package ro.unibuc.myapplication.Fragments.CustomerViews;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import ro.unibuc.myapplication.AccountActivity;
import ro.unibuc.myapplication.R;

public class AccountSettingsCustomerFragment extends Fragment {
    Button logoutBtn;

    public AccountSettingsCustomerFragment() { super(R.layout.fragment_customer_account_settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logoutBtn = view.findViewById(R.id.customerLogoutBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logout
                SharedPreferences sp = AccountActivity.getSharedPreferencesInstance(requireContext());
                sp.edit().clear().apply();
                Toast.makeText(view.getContext(), "deleted", Toast.LENGTH_SHORT).show();

                FirebaseAuth.getInstance().signOut();
                requireActivity().finish();
            }
        });
    }
}
