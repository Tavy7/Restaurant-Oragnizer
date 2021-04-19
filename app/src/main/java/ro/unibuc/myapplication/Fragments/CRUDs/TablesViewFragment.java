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

import ro.unibuc.myapplication.Adapters.TableAdapter;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Fragments.OnItemClickListener;
import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Models.Table;
import ro.unibuc.myapplication.R;

public class TablesViewFragment extends Fragment implements OnItemClickListener {
    protected static final String bundleKey = "1233212";

    public TablesViewFragment() { super(R.layout.fragment_view_tables); };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((EmployeeActivity)requireActivity()).setTitle("Table CRUD");

        // Create recycler view
        RecyclerView tableRecyclerView = (RecyclerView)view.findViewById(R.id.table_crud_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.VERTICAL, false);

        // Set layout
        tableRecyclerView.setLayoutManager(layoutManager);
        tableRecyclerView.setItemAnimator(new DefaultItemAnimator());

        RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
        // Get the data
        List<Table> tableList = db.tableDAO().getAllTables();
        TableAdapter tableAdapter = new TableAdapter((ArrayList<Table>) tableList, this);
        tableRecyclerView.setAdapter(tableAdapter);

        final Button addTableBtn = view.findViewById(R.id.addTableBtn);
        addTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize fragment
                CRUD_Table tableCRUD = new CRUD_Table();

                FragmentTransaction fragmentTransaction = ((EmployeeActivity)view.getContext()).
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.EmployeeMainFragment, tableCRUD)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onItemClick(Table table) {
        // Save item that user clicked on and pass to next fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable(bundleKey, table);

        CRUD_Table tableCRUD = new CRUD_Table();
        tableCRUD.setArguments(bundle);

        FragmentTransaction fragmentTransaction = requireActivity().
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.EmployeeMainFragment, tableCRUD)
                .addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static String getBundleKey() {
        return bundleKey;
    }
}
