package ro.unibuc.myapplication.Fragments.CustomerViews;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ro.unibuc.myapplication.Fragments.OccupiedTableFragment;
import ro.unibuc.myapplication.Models.Order;
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
        }

    }
}
