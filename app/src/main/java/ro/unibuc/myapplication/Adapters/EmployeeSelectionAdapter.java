package ro.unibuc.myapplication.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.R;

public class EmployeeSelectionAdapter extends RecyclerView.Adapter<EmployeeSelectionAdapter.ViewHolder> {
    protected List<Employee> empList;

    public EmployeeSelectionAdapter(List<Employee> empList) {
        this.empList = empList;
    }

    public List<Employee> getEmpList() {
        return empList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Employee emp = empList.get(position);
        holder.empName.setText(emp.getName());
        holder.empRole.setText(emp.getRole());
        holder.view.setBackgroundColor(emp.isSelected() ? Color.CYAN : Color.WHITE);
        holder.empName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emp.setSelected(!emp.isSelected());
                holder.view.setBackgroundColor(emp.isSelected() ? Color.CYAN : Color.WHITE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView empName;
        protected TextView empRole;
        protected View view;

        public ViewHolder(View view) {
            super(view);
            empName = view.findViewById(R.id.employeeName);
            empRole = view.findViewById(R.id.employeeRole);
            this.view = view;
        }
    }
}
