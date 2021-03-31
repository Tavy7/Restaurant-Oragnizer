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
        ViewHolder.bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static TextView title;
        private static TextView description;
        private static TextView price;
        private static RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            description = itemView.findViewById(R.id.itemDescription);
            price = itemView.findViewById(R.id.itemPrice);
            container = itemView.findViewById(R.id.itemContainer);
        }
//      Useless
//        public TextView getTitle() {
//            return title;
//        }
//
//        public TextView getDescription() {
//            return description;
//        }
//
//        public TextView getPrice() {
//            return price;
//        }

        public static void bind(Item item){
            title.setText(item.getName());
            description.setText(item.getDescription());
            price.setText(Float.toString(item.getPrice()));

            container.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    itemClickListener.onItemClick(item);
                }
            });
        }
    }
}
