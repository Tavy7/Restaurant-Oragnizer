package ro.unibuc.myapplication.Fragments.CRUDs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.myapplication.Adapters.ItemAdapter;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Fragments.OnItemClickListener;
import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.R;

public class FragmentViewItems extends Fragment implements OnItemClickListener {
    protected static final String bundleKey = "213321";

    public FragmentViewItems() {
        super(R.layout.fragment_view_items);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((EmployeeActivity)requireActivity()).setTitle("Item CRUD");
        RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());

        // Create recycler view
        RecyclerView itemRecyclerView = (RecyclerView)view.findViewById(R.id.item_crud_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.VERTICAL, false
        );

        // Set layout
        itemRecyclerView.setLayoutManager(layoutManager);
        itemRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Get the data
        List<Item> itemList = db.itemDao().getAllItems();
        ItemAdapter itemAdapter = new ItemAdapter((ArrayList<Item>) itemList, this);
        itemRecyclerView.setAdapter(itemAdapter);

        final Button addItemBtn = view.findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize fragment
                CRUD_Item addItemFragment = new CRUD_Item();

                FragmentTransaction fragmentTransaction = ((EmployeeActivity)view.getContext()).
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.EmployeeMainFragment, addItemFragment)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onItemClick(Item item) {
        // Save item that user clicked on and pass to next fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable(bundleKey, item);

        CRUD_Item itemCRUD = new CRUD_Item();
        itemCRUD.setArguments(bundle);

        FragmentTransaction fragmentTransaction = requireActivity().
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.EmployeeMainFragment, itemCRUD)
                .addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static String getBundleKey() {
        return bundleKey;
    }
}
