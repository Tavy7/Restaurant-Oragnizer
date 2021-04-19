package ro.unibuc.myapplication.Adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Fragments.CRUDs.TablesViewFragment;
import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.Models.Table;
import ro.unibuc.myapplication.R;

public class TableHomeViewAdapter extends RecyclerView.Adapter<TableHomeViewAdapter.ViewHolder> {
    protected List<Table> tableList;

    public TableHomeViewAdapter(List<Table> tableList) {
        this.tableList = tableList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_table_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Table table = tableList.get(position);

        holder.tableTitle.setText(String.valueOf(table.getQRCodeValue()));

        Drawable circleBg = holder.tableImage.getBackground();
        String green = "#7CFC00";
        holder.tableImage.setColorFilter(Color.parseColor(green), PorterDuff.Mode.SRC_ATOP);

        boolean isOcuppied = table.isOccupied();
        if (isOcuppied){
            String red = "#FF0000";
            holder.tableImage.setColorFilter(Color.parseColor(red), PorterDuff.Mode.ADD.SRC_ATOP);
            holder.tableEmployee.setVisibility(View.VISIBLE);

            int empId = table.getServingEmployeeId();
            if (empId == 0){
                holder.tableEmployee.setText("No emp");
            }
            else {
                Employee emp = RestaurantDatabase.getInstance(holder.view.getContext()).employeeDAO().getEmployeeById(empId);
                holder.tableEmployee.setText(emp.getName());
            }
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(TablesViewFragment.getBundleKey(), table);

                // Spaghetti to get Nav Controller to change fragment
                NavHostFragment navHostFragment = (NavHostFragment) ((EmployeeActivity)holder.view.getContext()).getSupportFragmentManager()
                        .findFragmentById(R.id.EmployeeMainFragment);
                NavController navCo = navHostFragment.getNavController();

                navCo.navigate(R.id.CRUD_Table, bundle);
            }
        });
    }


    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tableTitle;
        TextView tableEmployee;
        ImageView tableImage;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tableTitle = itemView.findViewById(R.id.tableHomeTitle);
            tableEmployee = itemView.findViewById(R.id.tableEmp);
            tableImage = itemView.findViewById(R.id.tableImage);
            this.view = itemView;
        }
    }
}
