package ro.unibuc.myapplication.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.R;

public class ItemSelectionAdapter extends RecyclerView.Adapter<ItemSelectionAdapter.ViewHolder> {
    protected List<Item> itemList;

    public ItemSelectionAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Item item = itemList.get(position);

        holder.title.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.price.setText(String.valueOf(item.getPrice()));

        // Item box onclick
        holder.view.setBackgroundColor(item.isSelected() ? Color.CYAN : Color.WHITE);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setSelected(!item.isSelected());
                holder.view.setBackgroundColor(item.isSelected() ? Color.CYAN : Color.WHITE);
            }
        });

        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qnt = Integer.parseInt(holder.itemQnt.getText().toString());
                qnt += 1;
                holder.itemQnt.setText(String.valueOf(qnt));
                item.setQuantity(qnt);
            }
        });

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qnt = Integer.parseInt(holder.itemQnt.getText().toString());
                qnt -= 1;
                holder.itemQnt.setText(String.valueOf(qnt));
                item.setQuantity(qnt);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Todo add buttons for quantity control
        private TextView title;
        private TextView description;
        private TextView price;
        private View view;
        private TextView itemQnt;
        private Button plusBtn;
        private Button minusBtn;

        public ViewHolder(View view) {
            super(view);
            title = itemView.findViewById(R.id.itemTitle);
            description = itemView.findViewById(R.id.itemDescription);
            price = itemView.findViewById(R.id.itemPrice);

            itemQnt = itemView.findViewById(R.id.itemQnt);
            // Initialize item qnt with 1 everytime
            itemQnt.setText("1");
            plusBtn = itemView.findViewById(R.id.plusBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);

            this.view = view;
        }
    }
}
