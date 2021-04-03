package ro.unibuc.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ro.unibuc.myapplication.Fragments.OnItemClickListener;
import ro.unibuc.myapplication.Models.Table;
import ro.unibuc.myapplication.R;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {
    protected ArrayList<Table> tableList;
    public static OnItemClickListener itemClickListener;

    public TableAdapter(ArrayList<Table> tableList, OnItemClickListener itemClickListener) {
        this.tableList = tableList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_table, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder.bind(tableList.get(position));
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected static TextView tableNumber;
        protected static TextView tableStatus;
        private static RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tableNumber = itemView.findViewById(R.id.tableTitle);
            tableStatus = itemView.findViewById(R.id.tableStatus);
            container = itemView.findViewById(R.id.tableContainer);
        }

        public static void bind(Table table){
            tableNumber.setText("Table no" + String.valueOf(table.getQRCodeValue()));
            tableStatus.setText(table.isOcupiedString());

            container.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    itemClickListener.onItemClick(table);
                }
            });

        }
    }
}
