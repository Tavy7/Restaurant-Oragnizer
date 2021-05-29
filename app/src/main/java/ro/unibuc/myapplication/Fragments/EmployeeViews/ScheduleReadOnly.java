package ro.unibuc.myapplication.Fragments.EmployeeViews;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ro.unibuc.myapplication.R;

public class ScheduleReadOnly extends Fragment {

    public ScheduleReadOnly() {
        super(R.layout.fragment_readonly_schedule);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
