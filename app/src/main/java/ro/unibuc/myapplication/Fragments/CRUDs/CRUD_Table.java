package ro.unibuc.myapplication.Fragments.CRUDs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Calendar;

import ro.unibuc.myapplication.AccountActivity;
import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.Models.Table;
import ro.unibuc.myapplication.R;

import static ro.unibuc.myapplication.AccountActivity.getSharedPreferencesInstance;

public class CRUD_Table extends Fragment {
    protected EditText addTableId;
    protected Switch isOccupiedSwitch;
    protected Button addTableBtn;
    protected Button deleteTableBtn;
    protected Button takeTableBtn;
    View view;

    public CRUD_Table(){ super(R.layout.fragment_add_table); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        addTableId = view.findViewById(R.id.addTableID);
        isOccupiedSwitch = view.findViewById(R.id.isTableOccupied);
        addTableBtn = view.findViewById(R.id.saveTableBtn);
        deleteTableBtn = view.findViewById(R.id.deleteTableBtn);
        takeTableBtn = view.findViewById(R.id.empTakeTable);

        // If bundle is not null that means the
        // fragment was called to update an item
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Table table = bundle.getParcelable(TablesViewFragment.getBundleKey());
            buttonUpdateItem(table);
        }
        else {
            buttonInsertNewItem();
        }
    }

    protected Table verifyDataInserted(View view){
        String tableIdStr = addTableId.getText().toString();

        // Check is string is integer
        if (!tableIdStr.matches("[-+]?[0-9]*")) {
            return null;
        }
        int tableIdVal = Integer.parseInt(tableIdStr);
        RestaurantDatabase db = RestaurantDatabase.getInstance(requireContext());

        boolean isOcc = isOccupiedSwitch.isChecked();

        return new Table(tableIdVal, db.itemDao().getAllItems(), isOcc);
    }

    protected void buttonInsertNewItem(){
        addTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table table = verifyDataInserted(view);

                if (table == null) {
                    return;
                }

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());

                Table testTable = db.tableDAO().getTable(table.getQRCodeValue());
                if(testTable != null){
                    Toast.makeText(view.getContext(), "This ID belongs to other table!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                db.tableDAO().insertTable(table);
                Toast.makeText(view.getContext(),
                        "Table succesfully inserated!", Toast.LENGTH_SHORT).show();

                // Go back to list view
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    protected void buttonUpdateItem(Table table){
        addTableId.setText(String.valueOf(table.getQRCodeValue()));
        isOccupiedSwitch.setChecked(table.isOccupied());

        addTableBtn.setText("Edit table");
        addTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table newTable = verifyDataInserted(view);

                if (newTable == null) {
                    return;
                }

                int oldId = table.getQRCodeValue();
                int newId = newTable.getQRCodeValue();

                if (oldId != newId){
                    Toast.makeText(getContext(), "TableID cannot be changed.", Toast.LENGTH_SHORT);
                    return;
                }

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.tableDAO().updateTable(newTable);

                Toast.makeText(view.getContext(),
                        "Table succesfully updated!", Toast.LENGTH_SHORT).show();
                // Go back to list view
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        if (table.isOccupied()) {
            takeTableButton(table);
        }

        deleteButton(table);
    }

    protected void deleteButton(Table table){
        // Delete button is hidden by default in view
        deleteTableBtn.setText("Delete table");
        deleteTableBtn.setVisibility(View.VISIBLE);
        deleteTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.tableDAO().deleteTable(table);
                Toast.makeText(getContext(), "Table deleted", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    protected void takeTableButton(Table table){
        takeTableBtn.setVisibility(View.VISIBLE);
        // Delete button is hidden by default in view
        takeTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get logged username on share prefs
                SharedPreferences sharedPreferences = getSharedPreferencesInstance(requireContext());
                String currentUserName = sharedPreferences.getString(AccountActivity.SPKEY_NAME, null);

                // Search for employee username
                Employee emp = RestaurantDatabase.getInstance(requireContext()).
                        employeeDAO().getEmployeeByName(currentUserName);

                // Update table
                table.setServingEmployeeId(emp.getUid());

                Calendar calendar = Calendar.getInstance();
                String orderDate = calendar.getTime().toString();

                Order order = new Order(new ArrayList<>(), table.getQRCodeValue(), emp.getUid(), orderDate);

                Bundle bundle = new Bundle();
                bundle.putParcelable(OrdersViewFragment.getBundleKey(), order);
                // GOTO create order
                Navigation.findNavController(view).navigate(R.id.CRUD_Order, bundle);
            }
        });
    }
}
