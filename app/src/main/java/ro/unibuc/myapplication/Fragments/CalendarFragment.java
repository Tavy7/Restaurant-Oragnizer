package ro.unibuc.myapplication.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import ro.unibuc.myapplication.Adapters.DateNavAdapter;
import ro.unibuc.myapplication.Models.DateNavBarModel;
import ro.unibuc.myapplication.R;

public class CalendarFragment extends Fragment implements OnItemClickListener{
    public static final String DayDate = "dayDate";

    public CalendarFragment(){
        super(R.layout.fragment_calendar);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create recycler view
        RecyclerView dateRecyclerView = (RecyclerView)view.findViewById(R.id.DateNavBar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false
        );

        // Set layout
        dateRecyclerView.setLayoutManager(layoutManager);
        dateRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Get the data
        ArrayList<DateNavBarModel> navBarModel = getCalendarNavBarArray();
        DateNavAdapter dateNavAdapter = new DateNavAdapter(this, navBarModel);
        dateRecyclerView.setAdapter(dateNavAdapter);

        // Initialize secondary fragment
        getActivity().getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.SchFragmentContainer, ScheduleFragment.class, null)
                .commit();
    }

    // Get last 3 days and next 7 dates
    protected ArrayList<DateNavBarModel> getCalendarNavBarArray() {
        Calendar calendar = Calendar.getInstance();
        // We go back 3 days from today
        calendar.add(Calendar.DATE, -3);
        ArrayList<DateNavBarModel> navBarModel = new ArrayList<>();

        // Add every date 10 days from now
        for (Integer i = 0; i < 10; i++){
            String date = String.valueOf(calendar.get(Calendar.DATE));

            if (i == 2) date = "Yesterday";
            if (i == 3) date = "Today";
            if (i == 4) date = "Tomorrow";

            DateNavBarModel dateModel = new DateNavBarModel(date);
            navBarModel.add(dateModel);

            calendar.add(Calendar.DATE, 1);
        }

        return navBarModel;
    }

    @Override
    public void onItemClick(DateNavBarModel item){
        Toast.makeText(getContext(), item.getDayDate(), Toast.LENGTH_LONG).show();

        Bundle bundle = new Bundle();
        bundle.putString(DayDate, String.valueOf(item.getDayDate()));

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.SchFragmentContainer, fragment)
                .addToBackStack(null);

        fragmentTransaction.commit();
    }
}

