package ro.unibuc.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.unibuc.myapplication.Fragments.OnItemClickListener;
import ro.unibuc.myapplication.Models.Customer;
import ro.unibuc.myapplication.R;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder>  {
    protected List<Customer> customerList;
    protected static OnItemClickListener itemClickListener;

    public CustomerAdapter(List<Customer> customerList, OnItemClickListener itemClickListener) {
        this.customerList = customerList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_customer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ViewHolder.bind(customerList.get(position));
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected static TextView customerName;
        protected static TextView customerEmail;
        protected static TextView customerCc;
        protected static RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            customerName = itemView.findViewById(R.id.customerTitle);
            customerEmail = itemView.findViewById(R.id.customerEmail);
            customerCc = itemView.findViewById(R.id.customerCreditCard);
            layout = itemView.findViewById(R.id.customerContainer);
        }

        public static void bind(Customer customer){
            customerName.setText(customer.getName());
            customerEmail.setText(customer.getEmail());

            String creditCard = customer.getCreditCard();
            if (creditCard.length() == 16){
                String last4Chr = creditCard.substring(creditCard.length() - 4);
                StringBuilder hiddenFirst12Characthers = new StringBuilder();

                for (int i = 1; i < 12; i++){
                    hiddenFirst12Characthers.append("*");
                    if(i % 4 == 3){
                        hiddenFirst12Characthers.append(" ");
                    }
                }

                String creditCardCensored = hiddenFirst12Characthers.toString() + last4Chr;
                customerCc.setText(creditCardCensored);
            }
            else{
                creditCard = "No Valid Credit Card";
                customerCc.setText(creditCard);
            }


            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(customer);
                }
            });
        }
    }
}
