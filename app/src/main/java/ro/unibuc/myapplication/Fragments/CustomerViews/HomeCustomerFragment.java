package ro.unibuc.myapplication.Fragments.CustomerViews;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import ro.unibuc.myapplication.AccountActivity;
import ro.unibuc.myapplication.CustomerActivity;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Fragments.OccupiedTableFragment;
import ro.unibuc.myapplication.Models.Customer;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.R;

public class HomeCustomerFragment extends Fragment {
    Button orderNow;

    public HomeCustomerFragment() { super(R.layout.fragment_customer_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RestaurantDatabase db = RestaurantDatabase.getInstance(requireContext());
        // GET USER
        String username = AccountActivity.getCurrentUsername();
        Customer customer = db.customerDAO().getCustomerByName(username);

        if (customer == null){
            Toast.makeText(requireContext(), username, Toast.LENGTH_SHORT).show();
            return;
        }

        List<Order> orderList = db.orderDAO().getUsersOrders(customer.getUid());
        if (orderList != null && orderList.size() > 0){
            Order order = orderList.get(orderList.size() - 1);
            if (!order.isOrderFinished()){
                Bundle bundle = new Bundle();
                bundle.putParcelable(OccupiedTableFragment.getBundleKey(), order);
                CustomerActivity.getNavController().navigate(R.id.orderReadFragment, bundle);
            }
        }

        orderNow = view.findViewById(R.id.orderNow);
        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerActivity.getNavController().navigate(R.id.CRUD_Order2);
            }
        });
    }
}
