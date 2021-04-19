package ro.unibuc.myapplication.Fragments.CRUDs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Models.Customer;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.R;

public class CRUD_Customer extends Fragment {
    protected EditText customerName;
    protected EditText customerEmail;
    protected EditText customerCreditCard;
    protected Button addCustomer;
    protected Button deleteCustomer;

    CRUD_Customer(){ super(R.layout.fragment_add_customer); };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        customerName = view.findViewById(R.id.addCustomerName);
        customerEmail = view.findViewById(R.id.addCustomerMail);
        customerCreditCard = view.findViewById(R.id.add_customer_cc);
        addCustomer = view.findViewById(R.id.saveCustomerBtn);
        deleteCustomer = view.findViewById(R.id.deleteCustomerBtn);

        // If bundle is not null that means the
        // fragment was called to update an item
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Customer customer = bundle.getParcelable(CustomersViewFragment.getBundleKey());
            buttonUpdateItem(customer);
        }
        else {
            buttonInsertNewItem();
        }
    }

    // Returns the object if the data is ok
    // else returns null
    protected Customer verifyDataInserted(View view){
        // Check if item name is not empty
        String customerNameVal = customerName.getText().toString();
        if (customerNameVal == ""){
            Toast.makeText(view.getContext(),
                    "Name cannot be empty!", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Check for description
        String customerEmailVal = customerEmail.getText().toString();
        if (customerEmailVal.equals("")){
            Toast.makeText(view.getContext(),
                    "Email cannot be empty!", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (!customerEmailVal.contains("@")){
            Toast.makeText(view.getContext(),
                    "Email has to be vaild!", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Check for description
        String customerCcVal = customerCreditCard.getText().toString();
        if (customerCcVal.equals("")){
            Toast.makeText(view.getContext(),
                    "CC cannot be empty!", Toast.LENGTH_SHORT).show();
            return null;
        }

//        if (customerCcVal.length() != 16){
//            Toast.makeText(view.getContext(),
//                    "CC has to be 16 characthers!", Toast.LENGTH_SHORT).show();
//            return null;
//        }

        RestaurantDatabase db = RestaurantDatabase.getInstance(requireContext());
        List<Order> orderList = db.orderDAO().getAllOrders();

        return new Customer(customerNameVal, customerCcVal, orderList, customerEmailVal);
    }

    protected void buttonInsertNewItem(){
        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer customer = verifyDataInserted(view);

                if (customer == null) {
                    return;
                }

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                // Returns new generated id
                long newGeneratedId = db.customerDAO().insertCustomer(customer);
                Toast.makeText(view.getContext(),
                        "Customer succesfully inserated!", Toast.LENGTH_SHORT).show();

                // Go back to list view
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    protected void buttonUpdateItem(Customer customer){
        customerName.setText(customer.getName());
        customerEmail.setText(customer.getEmail());
        customerCreditCard.setText(customer.getCreditCard());

        addCustomer.setText("Edit customer");
        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer newCustomer = verifyDataInserted(view);

                if (newCustomer == null) {
                    return;
                }
                // Item generated is new so it has no id
                newCustomer.setUid(customer.getUid());

                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.customerDAO().updateCustomer(newCustomer);

                Toast.makeText(view.getContext(),
                        "Customer succesfully updated!", Toast.LENGTH_SHORT).show();
                // Go back to list view
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Delete button is hidden by default in view
        deleteCustomer.setVisibility(View.VISIBLE);
        deleteCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestaurantDatabase db = RestaurantDatabase.getInstance(view.getContext());
                db.customerDAO().deleteCustomer(customer);

                Toast.makeText(getContext(), "Customer deleted", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
