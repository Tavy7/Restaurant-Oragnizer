package ro.unibuc.myapplication.Fragments.CRUDs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.R;

public class Item_CRUD extends Fragment {
    protected EditText itemName;
    protected EditText itemDesc;
    protected EditText itemPrice;
    protected EditText itemDiscount;
    protected Button addItem;
    protected Button deleteItem;


    public Item_CRUD(){
        super(R.layout.fragment_add_item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemName = view.findViewById(R.id.addItemName);
        itemDesc = view.findViewById(R.id.addItemDescription);
        itemPrice = view.findViewById(R.id.addItemPrice);
        itemDiscount = view.findViewById(R.id.addItemDiscount);
        addItem = view.findViewById(R.id.addItemBtn2);
        deleteItem = view.findViewById(R.id.deleteItemBtn);

        // If bundle is not null that means the
        // fragment was called to update an item
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Item item = bundle.getParcelable(ItemsViewFragment.getBundleKey());
            buttonUpdateItem(item);
        }
        else {
            buttonInsertNewItem();
        }
    }

    // Returns the object if the data is ok
    // else returns null
    protected Item verifyDataInserted(View view){
    // Check if item name is not empty
        String itemNameVal = itemName.getText().toString();
        if (itemNameVal == ""){
            Toast.makeText(view.getContext(),
                    "Name cannot be empty!", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Check for description
        String itemDescVal = itemDesc.getText().toString();
        if (itemDescVal.equals("")){
            itemDescVal = "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                    " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        }

        // Check if item price is not empty
        String itemPriceStr = itemPrice.getText().toString();
        if (itemPriceStr.equals("")){
            Toast.makeText(view.getContext(),
                    "Price cannot be empty!", Toast.LENGTH_SHORT).show();
            return null;
        }

        // If price is not a float
        if (!itemPriceStr.matches("[-+]?[0-9]*\\.?[0-9]+")){
            Toast.makeText(view.getContext(),
                    "Price has to be a number!", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Check if item discount is not empty
        // so we can safely cast it to int
        String itemDiscountStr = itemDiscount.getText().toString();
        int itemDiscountVal = 0;
        if (!itemDiscountStr.equals("")) {
            // Check is string is integer
            if (itemDiscountStr.matches("[-+]?[0-9]*")) {
                itemDiscountVal = Integer.parseInt(itemDiscountStr);
            }
        }
        return new Item(itemNameVal, Float.parseFloat(itemPriceStr),
                itemDescVal, itemDiscountVal);
    }

    protected void buttonInsertNewItem(){
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = verifyDataInserted(view);

                if (item == null) {
                    return;
                }

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                // Returns new generated id
                long newGeneratedId = db.itemDao().insertItem(item);
                Toast.makeText(view.getContext(),
                        "Item succesfully inserated!", Toast.LENGTH_SHORT).show();

                // Go back to list view
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    protected void buttonUpdateItem(Item item){
        itemName.setText(item.getName());
        itemDesc.setText(item.getDescription());
        itemPrice.setText(String.valueOf(item.getPrice()));
        itemDiscount.setText(String.valueOf(item.getDiscount()));

        addItem.setText("Edit item");
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item newItem = verifyDataInserted(view);

                if (newItem == null) {
                    return;
                }
                // Item generated is new so it has no id
                newItem.setIid(item.getIid());

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.itemDao().updateItem(newItem);

                Toast.makeText(view.getContext(),
                        "Item succesfully updated!", Toast.LENGTH_SHORT).show();
                // Go back to list view
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Delete button is hidden by default in view
        deleteItem.setVisibility(View.VISIBLE);
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.itemDao().deleteItem(item);
                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
