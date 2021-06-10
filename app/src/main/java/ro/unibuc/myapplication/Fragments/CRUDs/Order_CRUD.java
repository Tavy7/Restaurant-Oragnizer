package ro.unibuc.myapplication.Fragments.CRUDs;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ro.unibuc.myapplication.AccountActivity;
import ro.unibuc.myapplication.Adapters.ItemSelectionAdapter;
import ro.unibuc.myapplication.Adapters.TableSelectionAdapter;
import ro.unibuc.myapplication.CustomerActivity;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.EmployeeActivity;
import ro.unibuc.myapplication.Fragments.OccupiedTableFragment;
import ro.unibuc.myapplication.Models.Customer;
import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.Models.Table;
import ro.unibuc.myapplication.R;

import static ro.unibuc.myapplication.AccountActivity.getSharedPreferencesInstance;

public class Order_CRUD extends Fragment {
    protected TextView tableNum;
    protected RecyclerView selectTable;
    protected TableSelectionAdapter tableSelectionAdapter;
    protected RecyclerView selectItems;
    protected ItemSelectionAdapter itemSelectionAdapter;
    protected Button saveOrder;
    protected Button deleteOrder;
    protected Button scanQRBtn;
    protected Button changeViewBtn;
    protected TextView resHourInfo;
    protected TextView resDateInfo;
    protected EditText reservationDate;
    protected EditText reservationHour;

    AccountActivity aa;

    public Order_CRUD() { super(R.layout.fragment_add_order);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tableNum = view.findViewById(R.id.tableNumber);
        selectTable = view.findViewById(R.id.selectTableRecycler);
        selectItems = view.findViewById(R.id.orderItemsRecycler);
        saveOrder = view.findViewById(R.id.save_order_btn);
        deleteOrder = view.findViewById(R.id.delete_order_btn);
        scanQRBtn = view.findViewById(R.id.scanQRButton);
        changeViewBtn = view.findViewById(R.id.changeTABLEsELECTvIEW);

        reservationDate = view.findViewById(R.id.reservedDate);
        reservationDate.setVisibility(View.GONE);
        reservationHour = view.findViewById(R.id.reservedTime);
        reservationHour.setVisibility(View.GONE);
        resHourInfo = view.findViewById(R.id.resHourInfo);
        resDateInfo = view.findViewById(R.id.resDateInfo);
        resDateInfo.setVisibility(View.GONE);
        resHourInfo.setVisibility(View.GONE);

        aa = new AccountActivity();
        Bundle bundle = this.getArguments();

        scanQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (EmployeeActivity.class.getSimpleName().compareTo(requireActivity().getLocalClassName()) == 0){
                    EmployeeActivity.getNavController().navigate(R.id.QRScanFragment, bundle);
                }
                else
                    CustomerActivity.getNavController().navigate(R.id.QRScanFragment2, bundle);
            }
        });

        changeViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView();
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        selectTable.setHasFixedSize(true);
        selectTable.setLayoutManager(manager);

        manager = new LinearLayoutManager(requireContext());
        selectItems.setHasFixedSize(true);
        selectItems.setLayoutManager(manager);

        RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
        List<Table> tableList = db.tableDAO().getAllTables();
        tableSelectionAdapter = new TableSelectionAdapter(tableList);
        selectTable.setAdapter(tableSelectionAdapter);

        if(bundle != null) {
            Order order = bundle.getParcelable(OrdersViewFragment.getBundleKey());
            assert order != null;
            List<Item> itemList = order.getItems();
            if (!order.isOrderFinished() || itemList == null){
                itemList = db.itemDao().getAllItems();
            }

            itemSelectionAdapter = new ItemSelectionAdapter(itemList);
            selectItems.setAdapter(itemSelectionAdapter);

            buttonUpdateItem(order);
        }
        else{
            List<Item> itemList = db.itemDao().getAllItems();
            itemSelectionAdapter = new ItemSelectionAdapter(itemList);
            selectItems.setAdapter(itemSelectionAdapter);

            buttonInsertNewItem();
        }
    }

    private void changeView() {
        // Function changes visibility of two componenets
        // that have role to input table number
        // selectTable is a recycler view where user can click on table number
        // tableNum displays id from scanQR

        int visibility = selectTable.getVisibility();

        // Set those two to the other comopnent's visibility
        tableNum.setVisibility(visibility);
        scanQRBtn.setVisibility(visibility);

        // Change other component's visibility to its inverse
        int visible = View.GONE;
        if (visibility != View.VISIBLE)
            visible = View.VISIBLE;

        selectTable.setVisibility(visible);

        visibility = reservationDate.getVisibility();
        visible = View.GONE;
        if (visibility != View.VISIBLE){
            visible = View.VISIBLE;
        }

        reservationHour.setVisibility(visible);
        reservationDate.setVisibility(visible);

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

        if (tableNum.getText().equals(R.string.no_number)){
            new AlertDialog.Builder(requireContext())
                    .setTitle("No table found")
                    .setMessage("Please scan the QR code.")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return null;
        }

        RestaurantDatabase db = RestaurantDatabase.getInstance(requireContext());

        int qrNumber = 0;
        String text = tableNum.getText().toString();

        for (int i = 0; i < text.length(); i++){
            try{
                qrNumber = (qrNumber * 10) +  Integer.parseInt(String.valueOf(text.charAt(i)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // If table has not been selected from hidden menu, we try to get from the QR
        if (table == null)
            table = db.tableDAO().getTable(qrNumber);

        if (table == null) {
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

        int id = getCurrentUserId();
        Calendar calendar = Calendar.getInstance();
        String orderDate = calendar.getTime().toString();

        Order order = new Order(boughtItems, table.getQRCodeValue(), id, orderDate, false);
        order.setTotalPrice(order.findTotal(boughtItems));

        // Check for reservation
        String date = String.valueOf(reservationDate.getText());
        String hour = String.valueOf(reservationHour.getText());

        if (date.equals("") || hour.equals("")){
            return order;
        }
        // Else send notification for reservation

        return order;
    }

    protected void updateTableInDatabase(Order order){
        int tableId = order.getTableQRValue();
        RestaurantDatabase db = RestaurantDatabase.getInstance(requireContext());
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                Table table = db.tableDAO().getTable(tableId);

                // Update table in database
                table.setOccupied(true);
                table.setOrderId(order.getOid());
                table.setServingEmployeeId(order.getAccountId());

                db.tableDAO().insertTable(table);
            }
        });
        //db.orderDAO().insertOrder(order);
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

                int id = getCurrentUserId();
                order.setAccountId(id);

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        // Returns new generated id
                        int newGeneratedId = (int)(db.orderDAO().insertOrder(order));
                        order.setOid(newGeneratedId);
                    }
                });

                Toast.makeText(view.getContext(),
                        "Order succesfully inserated!", Toast.LENGTH_SHORT).show();


                updateTableInDatabase(order);

                if (EmployeeActivity.class.getSimpleName().compareTo(requireActivity().getLocalClassName()) == 0){
                    EmployeeActivity.getNavController().navigate(R.id.tableFragment);
                }
                else {
                    CustomerActivity.getNav().getMenu().getItem(0).setChecked(true);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(OccupiedTableFragment.getBundleKey(), order);
                    CustomerActivity.getNavController().navigate(R.id.orderReadFragment, bundle);
                }
            }
        });
    }

    protected void buttonUpdateItem(Order order){
        String tableIdStr = "Table " + String.valueOf(order.getTableQRValue());
        tableNum.setText(tableIdStr);

        saveOrder.setText("Send order");

        saveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Item> itemList = order.getItems();
                Order newOrder = verifyDataInserted(view);

                if (newOrder == null) {
                    return;
                }

                // Concatenate new items with old items
                // maybe could check if two are the same
                if (itemList != null) {
                    for (int i = 0; i < itemList.size(); i++) {
                        newOrder.appendItem(itemList.get(i));
                    }
                }
                // Update order total
                // this is disgusting but i am tired
                newOrder.setTotalPrice(newOrder.findTotal(newOrder.getItems()));
                newOrder.setAccountId(getCurrentUserId());

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                // Confilt strategy is REPLACE so is the same as update
                int id = (int) (db.orderDAO().insertOrder(newOrder));
                db.orderDAO().deleteOrder(order);
                Toast.makeText(requireContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
                // Item generated is new so it has no id
                newOrder.setOid(id);
                updateTableInDatabase(newOrder);
                db.orderDAO().deleteOrder(order);

                Toast.makeText(view.getContext(),
                        "Order succesfully updated!", Toast.LENGTH_SHORT).show();


                // If this is EMP activity
                if (EmployeeActivity.class.getSimpleName().compareTo(requireActivity().getLocalClassName()) == 0) {
                    EmployeeActivity.getNavController().navigate(R.id.tableFragment);
                } else {
                    // Customer activity
                    CustomerActivity.getNav().getMenu().getItem(0).setChecked(true);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(OccupiedTableFragment.getBundleKey(), newOrder);
                    CustomerActivity.getNavController().navigate(R.id.orderReadFragment, bundle);
                }
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

    protected int getCurrentUserId(){
        // Get logged username on share prefs
        SharedPreferences sharedPreferences = getSharedPreferencesInstance(requireContext());
        String currentUserName = sharedPreferences.getString(AccountActivity.SPKEY_NAME, null);

        // Search for employee username
        Employee emp = RestaurantDatabase.getInstance(requireContext()).
                employeeDAO().getEmployeeByName(currentUserName);

        int id = 0;
        if (emp != null) {
            id = emp.getUid();
        }

        Customer customer = RestaurantDatabase.getInstance(requireContext()).
                customerDAO().getCustomerByName(currentUserName);

        if (customer != null){
            id = customer.getUid();
        }

        return id;
    }
}
