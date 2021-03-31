package ro.unibuc.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ro.unibuc.myapplication.Fragments.OnItemClickListener;
import ro.unibuc.myapplication.Models.DateNavBarModel;
import ro.unibuc.myapplication.R;

// Basically write declarations of two classes and all code appears by magic.

public class DateNavAdapter extends RecyclerView.Adapter<DateNavAdapter.ViewHolder> {
    private ArrayList<DateNavBarModel> models;
    public static OnItemClickListener itemClickListener;

    public DateNavAdapter(OnItemClickListener listener, ArrayList<DateNavBarModel> models){
        this.itemClickListener = listener;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_calendar_nav, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewHolder.bind(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Initialize variable
        private static TextView textView;
        private static ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // DayTextView is an element of date nav bar recycler view
            textView = itemView.findViewById(R.id.DayTextView);
            this.layout = itemView.findViewById(R.id.DayContainer);
        }

        public static void bind(DateNavBarModel item){
            textView.setText(item.getDayDate());

            layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    itemClickListener.onItemClick(item);
                }
            });
        }
    }
}
