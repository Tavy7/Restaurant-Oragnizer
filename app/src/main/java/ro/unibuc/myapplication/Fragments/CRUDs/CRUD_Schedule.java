package ro.unibuc.myapplication.Fragments.CRUDs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.unibuc.myapplication.Adapters.EmployeeSelectionAdapter;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.Models.Schedule;
import ro.unibuc.myapplication.R;

public class CRUD_Schedule extends Fragment {
    protected EditText schDate;
    protected EditText schStart;
    protected EditText schEnd;
    protected RecyclerView empList;
    protected Button saveSch;
    protected Button delSch;
    protected EmployeeSelectionAdapter employeeSelectionAdapter;

    public CRUD_Schedule() {super(R.layout.fragment_add_schedule);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        schDate = view.findViewById(R.id.addSchDate);
        schStart = view.findViewById(R.id.addShiftStartHour);
        schEnd = view.findViewById(R.id.addShiftEndHour);
        empList = view.findViewById(R.id.EmpListForSch);
        saveSch = view.findViewById(R.id.saveScheduleBtn);
        delSch = view.findViewById(R.id.deleteScheduleBtn);

        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        empList.setHasFixedSize(true);
        empList.setLayoutManager(manager);

        RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());


        // If bundle is not null that means the
        // fragment was called to update an item
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            // If we update we try to get list from the item
            Schedule schedule = bundle.getParcelable(FragmentViewSchedules.getBundleKey());
            assert schedule != null;
            employeeSelectionAdapter = new EmployeeSelectionAdapter(schedule.getEmployees_on_shift());
            empList.setAdapter(employeeSelectionAdapter);


            buttonUpdateItem(schedule);
        }
        else {
            Toast.makeText(view.getContext(), "bnee", Toast.LENGTH_SHORT).show();
            // If we add new schedule we get a list of all emp
            employeeSelectionAdapter = new EmployeeSelectionAdapter(
                    db.employeeDAO().getAllEmployees());
            empList.setAdapter(employeeSelectionAdapter);

            buttonInsertNewItem();
        }
    }
    // Returns the object if the data is ok
    // else returns null
    protected Schedule verifyDataInserted(View view){
        String schDateVal = schDate.getText().toString();

        if (schDateVal.equals("")){
            Toast.makeText(view.getContext(), "Invalid date", Toast.LENGTH_SHORT).show();
            return null;
        }

        String startHour = schStart.getText().toString();
        if (startHour.equals("")){
            Toast.makeText(view.getContext(), "Invalid start hour", Toast.LENGTH_SHORT).show();
            return null;
        }

        String endHour = schStart.getText().toString();
        if (endHour.equals("")){
            Toast.makeText(view.getContext(), "Invalid end hour", Toast.LENGTH_SHORT).show();
            return null;
        }

        List<Employee> employeeList = employeeSelectionAdapter.getEmpList();

        if (employeeList.size() == 0){
            Toast.makeText(view.getContext(), "Atelast one employee is required per shift",
                    Toast.LENGTH_SHORT).show();
            return null;
        }

        return new Schedule(schDateVal, startHour, endHour, employeeList);
    }

    protected void buttonInsertNewItem(){
        saveSch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Schedule schedule = verifyDataInserted(view);

                if (schedule == null) {
                    return;
                }

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                // Returns new generated id
                long newGeneratedId = db.scheduleDAO().insertSchedule(schedule);
                Toast.makeText(view.getContext(),
                        "Schedule succesfully inserated!", Toast.LENGTH_SHORT).show();

                // Go back to list view
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    protected void buttonUpdateItem(Schedule schedule){
        schDate.setText(String.valueOf(schedule.getDate()));
        schStart.setText(schedule.getShift_start());
        schEnd.setText(String.valueOf(schedule.getShift_end()));

        saveSch.setText("Edit schedule");
        saveSch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Schedule newSchedule = verifyDataInserted(view);

                if (newSchedule == null) {
                    return;
                }
                // Item generated is new so it has no id
                newSchedule.setSid(schedule.getSid());

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.scheduleDAO().updateSchedule(newSchedule);

                Toast.makeText(view.getContext(),
                        "Schedule succesfully updated!", Toast.LENGTH_SHORT).show();
                // Go back to list view
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Delete button is hidden by default in view
        delSch.setVisibility(View.VISIBLE);
        delSch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.scheduleDAO().deleteSchedule(schedule);
                Toast.makeText(getContext(), "Schedule deleted", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
