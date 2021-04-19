package ro.unibuc.myapplication.Fragments.CRUDs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.unibuc.myapplication.Adapters.OrderAdapter;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Fragments.OnItemClickListener;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.R;

public class OrdersViewFragment extends Fragment implements OnItemClickListener {
    protected static final String bundleKey = "432ket2a";
    public OrdersViewFragment() { super(R.layout.fragment_view_orders);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((EmployeeActivity)requireActivity()).setTitle("Order CRUD");

        // Create recycler view
        RecyclerView itemRecyclerView = (RecyclerView)view.findViewById(R.id.ordersRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.VERTICAL, false
        );

        // Set layout
        itemRecyclerView.setLayoutManager(layoutManager);
        itemRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Get the data
        RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
        List<Order> orderList = db.orderDAO().getAllOrders();
        OrderAdapter orderAdapter = new OrderAdapter(orderList, this);
        itemRecyclerView.setAdapter(orderAdapter);

        final Button addItemBtn = view.findViewById(R.id.addOrderBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize fragment
                NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.EmployeeMainFragment);
                NavController navCo = navHostFragment.getNavController();

                navCo.navigate(R.id.CRUD_Order);
            }
        });
    }

    @Override
    public void onItemClick(Order order) {
        // Save item that user clicked on and pass to next fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable(bundleKey, order);

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.EmployeeMainFragment);
        NavController navCo = navHostFragment.getNavController();

        navCo.navigate(R.id.CRUD_Order, bundle);
    }

    public static String getBundleKey() {
        return bundleKey;
    }
}
