package ro.unibuc.myapplication.Fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ro.unibuc.myapplication.R;

public class FragmentSelectEmployee extends Fragment {

    public FragmentSelectEmployee() { super(R.layout.fragment_view_employees);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
