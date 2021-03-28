package ro.unibuc.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ro.unibuc.myapplication.Models.DateNavBarModel;
import ro.unibuc.myapplication.R;

// Basically write declarations of two classes and all code appears by magic.

public class DateNavAdapter extends RecyclerView.Adapter<DateNavAdapter.ViewHolder> {
    ArrayList<DateNavBarModel> models;
    Context context;

    public DateNavAdapter(Context context, ArrayList<DateNavBarModel> models){
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(models.get(position).getDayDate());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Initialize variable
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}
