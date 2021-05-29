package ro.unibuc.myapplication.Fragments.CustomerViews;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import ro.unibuc.myapplication.Fragments.CRUDs.OrdersViewFragment;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.R;

public class HomeCustomerFragment extends Fragment {
    Button cameraQR;

    public HomeCustomerFragment() { super(R.layout.fragment_customer_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cameraQR = view.findViewById(R.id.scanQRBtn);
        cameraQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigation.findNavController(view).navigate(R.id.QRScanFragment2);
                Order order = new Order(new ArrayList<Item>(), 1, 0, null, false);
                Bundle newBundle = new Bundle();
                newBundle.putParcelable(OrdersViewFragment.getBundleKey(), order);
                Navigation.findNavController(view).navigate(R.id.CRUD_Order2, newBundle);
            }
        });
    }
}
