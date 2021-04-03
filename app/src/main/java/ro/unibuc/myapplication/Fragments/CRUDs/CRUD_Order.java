package ro.unibuc.myapplication.Fragments.CRUDs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ro.unibuc.myapplication.Adapters.ItemSelectionAdapter;
import ro.unibuc.myapplication.Adapters.TableSelectionAdapter;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.Models.Table;
import ro.unibuc.myapplication.R;

public class CRUD_Order extends Fragment {
    protected RecyclerView selectTable;
    protected TableSelectionAdapter tableSelectionAdapter;
    protected RecyclerView selectItems;
    protected ItemSelectionAdapter itemSelectionAdapter;
    protected Button saveOrder;
    protected Button deleteOrder;

    public CRUD_Order() { super(R.layout.fragment_add_order);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectTable = view.findViewById(R.id.selectTableRecycler);
        selectItems = view.findViewById(R.id.selectItemsRecycler);
        saveOrder = view.findViewById(R.id.save_order_btn);
        deleteOrder = view.findViewById(R.id.delete_order_btn);

        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        selectTable.setHasFixedSize(true);
        selectTable.setLayoutManager(manager);

        selectItems.setHasFixedSize(true);
        manager = new LinearLayoutManager(requireContext());
        selectItems.setLayoutManager(manager);

        RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
        List<Table> tableList = db.tableDAO().getAllTables();
        List<Item> itemList = db.itemDao().getAllItems();

        tableSelectionAdapter = new TableSelectionAdapter(tableList);
        itemSelectionAdapter = new ItemSelectionAdapter(itemList);

        selectTable.setAdapter(tableSelectionAdapter);
        selectItems.setAdapter(itemSelectionAdapter);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            Order order = bundle.getParcelable(FragmentViewOrders.getBundleKey());
            assert order != null;
            buttonUpdateItem(order);
        }
        else{
            buttonInsertNewItem();
        }
    }

    // Returns the object if the data is ok
    // else returns null
    protected Order verifyDataInserted(View view){
        List<Table> tables = tableSelectionAdapter.getTableList();

        Table table = null;
        for (Table i : tables){
            if (i.isSelected()){
                table = i;
                break;
            }
        }

        if (table == null){
            Toast.makeText(view.getContext(), "Table has to be selected to complete order.",
                    Toast.LENGTH_SHORT).show();
            return null;

        }

        List<Item> itemList = itemSelectionAdapter.getItemList();
        List<Item> boughtItems = new ArrayList<>();

        for (Item itm : itemList){
            if (itm.isSelected()){
                boughtItems.add(itm);
            }
        }

        if (boughtItems.size() == 0){
            Toast.makeText(view.getContext(), "Atelast one item is required per order.",
                    Toast.LENGTH_SHORT).show();
            return null;
        }

        //Todo get account id that puts order
        RestaurantDatabase db = RestaurantDatabase.getInstance(requireContext());
        int accountId = db.employeeDAO().getAllEmployees().get(0).getUid();

        Calendar calendar = Calendar.getInstance();
        String orderDate = calendar.getTime().toString();
        Toast.makeText(view.getContext(), String.valueOf(table.getQRCodeValue()),
                Toast.LENGTH_SHORT).show();

        return new Order(boughtItems, table.getQRCodeValue(), findTotal(boughtItems), 1, orderDate);
    }

    protected void buttonInsertNewItem(){
        saveOrder.setText("Add order");
        saveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = verifyDataInserted(view);

                if (order == null) {
                    return;
                }

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                // Returns new generated id
                long newGeneratedId = db.orderDAO().insertOrder(order);
                Toast.makeText(view.getContext(),
                        "Schedule succesfully inserated!", Toast.LENGTH_SHORT).show();

                // Go back to list view
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    protected void buttonUpdateItem(Order order){

        saveOrder.setText("Edit order");
        saveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order newOrder = verifyDataInserted(view);

                if (newOrder == null) {
                    return;
                }
                // Item generated is new so it has no id
                newOrder.setOid(order.getOid());

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.orderDAO().updateOrder(newOrder);

                Toast.makeText(view.getContext(),
                        "Order succesfully updated!", Toast.LENGTH_SHORT).show();
                // Go back to list view
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Delete button is hidden by default in view
        deleteOrder.setVisibility(View.VISIBLE);
        deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.orderDAO().deleteOrder(order);
                Toast.makeText(getContext(), "Order deleted", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }


    // Function that calculates the total price
    // of the items from array list items.
    protected float findTotal(List<Item> itemList){
        float total = 0;
        for (Item item : itemList){
            float price = item.getPrice();

            int discount = item.getDiscount();
            // If price has a discount
            if (discount != 0){
                // Then we update the price
                price -= discount * price / 100;
            }
            total += price;
        }

        return total;
    }
}
