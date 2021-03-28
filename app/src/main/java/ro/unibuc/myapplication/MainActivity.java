package ro.unibuc.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // Returns list with last 3 days and next 7
    protected void setCalendarNavBar()
    {
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

        RecyclerView dateRecyclerView = (RecyclerView)findViewById(R.id.DateNavBar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                MainActivity.this, LinearLayoutManager.HORIZONTAL, false
        );
        dateRecyclerView.setLayoutManager(layoutManager);
        dateRecyclerView.setItemAnimator(new DefaultItemAnimator());

        DateNavAdapter dateNavAdapter = new DateNavAdapter(MainActivity.this, navBarModel);
        dateRecyclerView.setAdapter(dateNavAdapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCalendarNavBar();
    }
}