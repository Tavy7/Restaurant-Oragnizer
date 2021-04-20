package ro.unibuc.myapplication.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ro.unibuc.myapplication.R;

public class ScheduleFragment extends Fragment {
    TextView scheduleText;
    public ScheduleFragment(){
        super(R.layout.fragment_schedule);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scheduleText = view.findViewById(R.id.ScheduleText);
        scheduleText.setText("No schedules for this day.");

        Bundle bundle = this.getArguments();

        if (bundle != null){
            String date = bundle.getString(CalendarFragment.DayDate);
            scheduleText.setVisibility(View.GONE);
        }

        // Parse date and search database for schedules in that specific day
    }
}
