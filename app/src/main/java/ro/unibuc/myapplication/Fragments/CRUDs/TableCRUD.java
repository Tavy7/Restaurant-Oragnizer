package ro.unibuc.myapplication.Fragments.CRUDs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Models.Table;
import ro.unibuc.myapplication.R;

public class TableCRUD extends Fragment {
    protected EditText addTableId;
    protected Switch isOccupiedSwitch;
    protected Button addTableBtn;
    protected Button deleteTableBtn;

    TableCRUD(){ super(R.layout.fragment_add_table); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addTableId = view.findViewById(R.id.addTableID);
        isOccupiedSwitch = view.findViewById(R.id.isTableOccupied);
        addTableBtn = view.findViewById(R.id.saveTableBtn);
        deleteTableBtn = view.findViewById(R.id.deleteTableBtn);

        // If bundle is not null that means the
        // fragment was called to update an item
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Table table = bundle.getParcelable(ViewTablesFragment.getBundleKey());
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

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.tableDAO().updateTable(newTable);

                Toast.makeText(view.getContext(),
                        "Table succesfully updated!", Toast.LENGTH_SHORT).show();
                // Go back to list view
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Delete button is hidden by default in view
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
}
