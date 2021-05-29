package ro.unibuc.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.unibuc.myapplication.Fragments.OnItemClickListener;
import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.Models.Schedule;
import ro.unibuc.myapplication.R;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{
    protected List<Schedule> schList;
    protected static OnItemClickListener itemClickListener;

    public ScheduleAdapter(List<Schedule> schList, OnItemClickListener itemClickListener) {
        this.schList = schList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder.bind(schList.get(position));
    }

    @Override
    public int getItemCount() {
        return schList.size();
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected static TextView scheduleProgram;
        protected static TextView employeesOnSchedule;
        protected static RelativeLayout layout;
        private static View view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            scheduleProgram = itemView.findViewById(R.id.scheduleDescription);
            employeesOnSchedule = itemView.findViewById(R.id.employeesOnSchedule);
            layout = itemView.findViewById(R.id.schedulesContainer);
            view = itemView;

        }

        public static void bind(Schedule schedule) {
            String date = schedule.getDate();
            String startTime = schedule.getShift_start().toString();
            String endTime = schedule.getShift_end().toString();

            String schProgram = date + " Start: " + startTime + " End: " + endTime;
            scheduleProgram.setText(schProgram);

            StringBuilder empOnSchStr = new StringBuilder();
            List<Employee> empsOnShift = schedule.getEmployees_on_shift();
            for(Employee empOnShift : empsOnShift){
                String empName = empOnShift.getName();
                String empRole = empOnShift.getRole();

                empOnSchStr.append(empName + " " + empRole + "\n");
            }

            // Delete \n
            empOnSchStr.deleteCharAt(empOnSchStr.length() - 1);
            empOnSchStr.deleteCharAt(empOnSchStr.length() - 2);

            employeesOnSchedule.setText(empOnSchStr.toString());

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(schedule);
                }
            });
        }
    }
}
