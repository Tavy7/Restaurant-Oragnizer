package ro.unibuc.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ro.unibuc.myapplication.Fragments.OnItemClickListener;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<Item> itemList;
    public static OnItemClickListener itemClickListener;

    public ItemAdapter(ArrayList<Item> itemList, OnItemClickListener itemClickListener) {
        this.itemList = itemList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.title.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.price.setText(String.valueOf(item.getPrice()));
        holder.itemQnt.setText(String.valueOf(item.getQuantity()));

        holder.container.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                itemClickListener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView price;
        private RelativeLayout container;
        private TextView itemQnt;
        private Button plusBtn;
        private Button minusBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            description = itemView.findViewById(R.id.itemDescription);
            price = itemView.findViewById(R.id.itemPrice);
            container = itemView.findViewById(R.id.itemContainer);

            // This adapter is not used for creating a order
            // so we hide de quantity controlls
            itemQnt = itemView.findViewById(R.id.itemQnt);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            plusBtn.setVisibility(View.INVISIBLE);
            minusBtn = itemView.findViewById(R.id.minusBtn);
            minusBtn.setVisibility(View.INVISIBLE);
        }
    }
}
