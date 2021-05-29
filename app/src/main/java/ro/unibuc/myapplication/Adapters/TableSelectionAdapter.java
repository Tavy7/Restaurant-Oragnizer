package ro.unibuc.myapplication.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.unibuc.myapplication.Models.Table;
import ro.unibuc.myapplication.R;

public class TableSelectionAdapter extends RecyclerView.Adapter<TableSelectionAdapter.ViewHolder> {
    protected List<Table> tableList;

    public TableSelectionAdapter(List<Table> tableList) {
        this.tableList = tableList;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_table, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Table table = tableList.get(position);

        holder.tableId.setText(String.valueOf(table.getQRCodeValue()));
        holder.tableStatus.setText(table.isOcupiedString());

        holder.view.setBackgroundColor(table.isSelected() ? Color.CYAN : Color.WHITE);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table.setSelected(!table.isSelected());
                holder.view.setBackgroundColor(table.isSelected() ? Color.CYAN : Color.WHITE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView tableId;
        protected TextView tableStatus;
        protected View view;

        public ViewHolder(View view) {
            super(view);
            tableId = view.findViewById(R.id.tableTitle);
            tableStatus = view.findViewById(R.id.tableStatus);
            this.view = view;
        }
    }
}
