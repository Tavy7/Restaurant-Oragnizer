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
import ro.unibuc.myapplication.MainActivity;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.R;

public class ItemCRUD extends Fragment implements OnItemClickListener {
    protected RestaurantDatabase db;
    public static final String bundleKey = "213321";

    public ItemCRUD() {
        super(R.layout.fragment_item_crud);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setTitle("Item CRUD");
        db = RestaurantDatabase.getInstance(view.getContext());

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
                AddItemFragment addItemFragment = new AddItemFragment();

                FragmentTransaction fragmentTransaction = ((MainActivity)view.getContext()).
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.MainFragment, addItemFragment)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onItemClick(Item item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(bundleKey, item);

        AddItemFragment addItemFragment = new AddItemFragment();
        addItemFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.MainFragment, addItemFragment)
                .addToBackStack(null);
        fragmentTransaction.commit();

    }
}
