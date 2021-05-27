package ro.unibuc.myapplication.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Fragments.CRUDs.OrdersViewFragment;
import ro.unibuc.myapplication.Fragments.CRUDs.TablesViewFragment;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.Models.Table;
import ro.unibuc.myapplication.R;

public class OccupiedTableFragment extends Fragment {
    TextView orderTitle;
    Button createOrder;
    final static String BundleKey = "eorx123";

    public static String getBundleKey() {
        return BundleKey;
    }

    public OccupiedTableFragment() {
        super(R.layout.occupied_table);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle("Table order");

        orderTitle = view.findViewById(R.id.OrderStatus);
        createOrder = view.findViewById(R.id.createOrderBtn);
        String createOrderText = "Create order";
        createOrder.setText(createOrderText);

        // Parse bundle
        Bundle bundle = getArguments();
        Order order = null;
        if (bundle != null){
            // If the table has an order attached
            Table table = bundle.getParcelable(TablesViewFragment.getBundleKey());
            assert table != null;

            createOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO CHANGE FRAGMENT FOR ALL USERS
                    // Goto create order
                    NavController navCo = EmployeeActivity.getNavController();

                    Order order = new Order(table.getQRCodeValue(), 1);
                    table.setOrderId(order.getOid());

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(OrdersViewFragment.getBundleKey(), order);
                    navCo.navigate(R.id.CRUD_Order, bundle);
                }
            });

            int orderId = table.getOrderId();
            if (orderId != 0) {
                // If order is found
                RestaurantDatabase db = RestaurantDatabase.getInstance(requireContext());

                order = (Order) db.orderDAO().getOrderById(orderId);
                if (order != null) {
                    String orderStatus = "Table " + String.valueOf(order.getTableQRValue())
                            + " Account " + String.valueOf(order.getAccountId())
                            + " Date " + order.getOrderDate();
                    orderTitle.setText(orderStatus);
                    createOrder.setVisibility(View.GONE);
                    Bundle bundle1 = new Bundle();
                    bundle1.putParcelable(BundleKey, order);

                    // Initialize order fragment
                    NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                            .findFragmentById(R.id.tableSOrderFragment);
                    assert navHostFragment != null;
                    NavController navCo = navHostFragment.getNavController();

                    // Set fragment show order
                    navCo.navigate(R.id.orderReadFragment, bundle1);
                }
            }
        }
    }
}
