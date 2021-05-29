package ro.unibuc.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.unibuc.myapplication.Fragments.OnItemClickListener;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    protected List<Order> orderList;
    protected OnItemClickListener itemClickListener;

    public OrderAdapter(List<Order> orderList, OnItemClickListener itemClickListener) {
        this.orderList = orderList;
        this.itemClickListener = itemClickListener;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_order, parent, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderAdapter.ViewHolder holder, int position) {
        final Order order = orderList.get(position);

        holder.orderDetails.setText(order.getOrderDate());

        StringBuilder stringBuilder = new StringBuilder();

        for (Item i : order.getItems()){
            stringBuilder.append(i.getName() + " x" + i.getQuantity() + "\n");
        }

        holder.orderItems.setText(stringBuilder.toString());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(order);
            }
        });

        // TODO Search for username instead of id
        holder.orderEmpName.setText(String.valueOf(order.getAccountId()) + " "
            + String.valueOf(order.getTableQRValue()) + " "
            + String.valueOf(order.getOid()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Todo change with Orders from view
        protected TextView orderDetails;
        protected TextView orderItems;
        protected TextView orderEmpName;
        protected View view;

        public ViewHolder(View view) {
            super(view);
            orderDetails = view.findViewById(R.id.orderDateAndTotal);
            orderItems = view.findViewById(R.id.orderItemList);
            orderEmpName = view.findViewById(R.id.orderEmpName);
            this.view = view;
        }
    }
}

