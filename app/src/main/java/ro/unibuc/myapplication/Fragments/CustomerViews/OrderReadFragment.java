package ro.unibuc.myapplication.Fragments.CustomerViews;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ro.unibuc.myapplication.Adapters.ItemAdapter;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Fragments.CRUDs.OrdersViewFragment;
import ro.unibuc.myapplication.Fragments.OccupiedTableFragment;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.Models.Table;
import ro.unibuc.myapplication.R;

public class OrderReadFragment extends Fragment {

    TextView orderTable;
    TextView orderEmp;
    TextView totalPrice;
    Button sendOrderBtn;
    Button addItemsToOrderBtn;
    RecyclerView orderItems;

    public OrderReadFragment() {
        super(R.layout.fragment_read_order);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderTable = view.findViewById(R.id.orderTableNumber);
        orderEmp = view.findViewById(R.id.orderEmpName);
        totalPrice = view.findViewById(R.id.orderTotalPrice);
        sendOrderBtn = view.findViewById(R.id.sendOrderButton);
        addItemsToOrderBtn = view.findViewById(R.id.AddItemsToOrderBtn);
        orderItems = view.findViewById(R.id.orderItemsRecycler);

        Bundle bundle = getArguments();
        if (bundle != null){
            Order order = bundle.getParcelable(OccupiedTableFragment.getBundleKey());
            if (order != null){
                setUIElements(order);
            }
        }
    }

    private void setUIElements(Order order) {
        orderTable.setText(String.format("Table %s", String.valueOf(order.getTableQRValue())));
        orderEmp.setText(String.format("Emp %s", String.valueOf(order.getAccountId())));
        totalPrice.setText(String.format("Total: %s", order.getTotalPrice()));

        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        orderItems.setLayoutManager(manager);
        ArrayList items = (ArrayList<Item>) order.getItems();
        ItemAdapter itemAdapter = new ItemAdapter(items, null);
        orderItems.setAdapter(itemAdapter);

        addItemsToOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add more items to order
                Bundle bundle = new Bundle();
                bundle.putParcelable(OrdersViewFragment.getBundleKey(), order);

                NavController nav = EmployeeActivity.getNavController();
                nav.navigate(R.id.CRUD_Order, bundle);
            }
        });

        // If order is finished
        sendOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make payment

                order.setOrderFinished(true);
                int tableId = order.getTableQRValue();

                if (tableId != 0){
                    // Clear table
                    RestaurantDatabase db = RestaurantDatabase.getInstance(requireContext());
                    Table table = db.tableDAO().getTable(tableId);
                    table.setOccupied(false);
                    // Leave the ids of order and account to table

                    db.tableDAO().updateTable(table);
                }
                EmployeeActivity.getNavController().popBackStack();
            }
        });
    }
}
