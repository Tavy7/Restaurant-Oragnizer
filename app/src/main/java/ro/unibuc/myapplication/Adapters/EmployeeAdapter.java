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
import ro.unibuc.myapplication.R;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    protected List<Employee> empList;
    protected static OnItemClickListener itemClickListener;

    public EmployeeAdapter(List<Employee> empList, OnItemClickListener itemClickListener) {
        this.empList = empList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder.bind(empList.get(position));
    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected  static TextView empName;
        protected static TextView empRole;
        protected static RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            empName = itemView.findViewById(R.id.employeeName);
            empRole = itemView.findViewById(R.id.employeeRole);
            layout = itemView.findViewById(R.id.empContainer);

        }

        public static void bind(Employee employee){
            empName.setText(employee.getName());
            empRole.setText(employee.getRole());

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(employee);
                }
            });
        }
    }
}
