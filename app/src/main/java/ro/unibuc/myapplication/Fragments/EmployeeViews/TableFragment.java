package ro.unibuc.myapplication.Fragments.EmployeeViews;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.unibuc.myapplication.Adapters.TableHomeViewAdapter;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Models.Table;
import ro.unibuc.myapplication.R;

public class TableFragment extends Fragment {
    RecyclerView tableRecycler;
    private static final int ROW_COUNT = 3;

    public TableFragment(){
        super(R.layout.fragment_tables);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().setTitle(R.string.tables);
        Context context = requireContext();

        tableRecycler = view.findViewById(R.id.tableHomeRecycler);
        GridLayoutManager manager = new GridLayoutManager(context, ROW_COUNT);
        tableRecycler.setLayoutManager(manager);

        List<Table> tableList = RestaurantDatabase.getInstance(context).tableDAO().getAllTables();
        TableHomeViewAdapter adapter = new TableHomeViewAdapter(tableList);
        tableRecycler.setAdapter(adapter);
    }
}
