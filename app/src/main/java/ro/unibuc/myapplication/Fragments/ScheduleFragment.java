package ro.unibuc.myapplication.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ro.unibuc.myapplication.R;

public class ScheduleFragment extends Fragment {
    public ScheduleFragment(){
        super(R.layout.fragment_schedule);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView) view.findViewById(R.id.ScheduleText)).setText("ets");
        Bundle bundle = this.getArguments();
        if (bundle != null){
            String date = bundle.getString(CalendarFragment.DayDate);
            ((TextView) view.findViewById(R.id.ScheduleText)).setText(date);
        }
    }
}
