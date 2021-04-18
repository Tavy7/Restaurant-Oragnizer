package ro.unibuc.myapplication.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import ro.unibuc.myapplication.AccountActivity;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.R;

public class TableFragment extends Fragment {
    public TableFragment(){
        super(R.layout.fragment_table);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((EmployeeActivity)getActivity()).setTitle(R.string.tables);

        RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());

        List<Item> itemList = db.itemDao().getAllItems();
        for (Item i : itemList) {
            Log.i("persons", i.getName() + " " + i.getDescription());
        }

        TextView item1 = (TextView)view.findViewById(R.id.item20);
        TextView item2 = (TextView)view.findViewById(R.id.item21);

        if (itemList.size() > 0) {
            item1.setText(itemList.get(0).getName());
            item2.setText(itemList.get(1).getName());
        }

        Button button = requireActivity().findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = AccountActivity.getSharedPreferencesInstance(requireContext());
                sp.edit().clear().commit();
                Toast.makeText(requireContext(), "deleted", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                LoginFragment.getmGoogleSignInClient().signOut();
                requireActivity().finish();
            }
        });
    }
}
