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

import java.util.List;

import ro.unibuc.myapplication.Adapters.ScheduleAdapter;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Fragments.OnItemClickListener;
import ro.unibuc.myapplication.MainActivity;
import ro.unibuc.myapplication.Models.Schedule;
import ro.unibuc.myapplication.R;

public class FragmentViewSchedules extends Fragment implements OnItemClickListener {
    protected static final String bundleKey = "2ha1t5";
    public FragmentViewSchedules() {super(R.layout.fragment_view_schedules);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)requireActivity()).setTitle("Schedule CRUD");

        // Create recycler view
        RecyclerView itemRecyclerView = (RecyclerView)view.findViewById(R.id.scheduleRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.VERTICAL, false
        );

        // Set layout
        itemRecyclerView.setLayoutManager(layoutManager);
        itemRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Get the data
        RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
        List<Schedule> itemList = db.scheduleDAO().getAllSchedules();
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(itemList, this);
        itemRecyclerView.setAdapter(scheduleAdapter);

        final Button addItemBtn = view.findViewById(R.id.addScheduleBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize fragment
                CRUD_Schedule scheduleCRUD = new CRUD_Schedule();

                FragmentTransaction fragmentTransaction = ((MainActivity)view.getContext()).
                        getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.MainFragment, scheduleCRUD)
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onItemClick(Schedule schedule) {
        // Save item that user clicked on and pass to next fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable(bundleKey, schedule);

        CRUD_Schedule scheduleCRUD = new CRUD_Schedule();
        scheduleCRUD.setArguments(bundle);

        FragmentTransaction fragmentTransaction = requireActivity().
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.MainFragment, scheduleCRUD)
                .addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static String getBundleKey() {
        return bundleKey;
    }
}
