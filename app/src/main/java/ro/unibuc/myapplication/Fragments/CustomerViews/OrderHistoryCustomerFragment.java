package ro.unibuc.myapplication.Fragments.CustomerViews;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.unibuc.myapplication.AccountActivity;
import ro.unibuc.myapplication.Adapters.OrderAdapter;
import ro.unibuc.myapplication.CustomerActivity;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Fragments.OccupiedTableFragment;
import ro.unibuc.myapplication.Fragments.OnItemClickListener;
import ro.unibuc.myapplication.Models.Customer;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.R;

public class OrderHistoryCustomerFragment extends Fragment implements OnItemClickListener {
    TextView pageTitle;
    RecyclerView ordersRecycler;

    public OrderHistoryCustomerFragment() { super(R.layout.fragment_customer_order_history);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageTitle = view.findViewById(R.id.yourOrders);
        ordersRecycler = view.findViewById(R.id.customerOrdersRecycler);

        LinearLayoutManager manager = new LinearLayoutManager(requireContext());

        // Set layout
        ordersRecycler.setLayoutManager(manager);
        ordersRecycler.setItemAnimator(new DefaultItemAnimator());

        RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());

        // GET USER
        String username = AccountActivity.getCurrentUsername();
        Customer customer = db.customerDAO().getCustomerByName(username);

        if (customer == null){
            Toast.makeText(requireContext(), username, Toast.LENGTH_SHORT).show();
            return;
        }

        pageTitle.setText(String.format("Hello %s", customer.getName()));

        // Get the data
        List<Order> orderList = db.orderDAO().getUsersOrders(customer.getUid());
        OrderAdapter orderAdapter = new OrderAdapter(orderList, this);
        ordersRecycler.setAdapter(orderAdapter);
    }

    @Override
    public void onItemClick(Order order) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(OccupiedTableFragment.getBundleKey(), order);
        CustomerActivity.getNavController().navigate(R.id.orderReadFragment, bundle);
    }
}
