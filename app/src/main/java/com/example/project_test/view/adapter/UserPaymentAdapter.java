package com.example.project_test.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;
import com.example.project_test.model.entitiy.Payments;
import com.example.project_test.model.utils.Company;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserPaymentAdapter extends RecyclerView.Adapter<UserPaymentAdapter.UserPaymentHolder> {
    Context context;
    List<Payments> payments;
    DatabaseReference databaseReference;
    OnItemClicked onItemClicked;
    public  interface  OnItemClicked{
        void onClicked(String request_id);
    }


    public UserPaymentAdapter(Context context, List<Payments> payments,OnItemClicked onItemClicked) {
        this.context = context;
        this.payments = payments;
        databaseReference=FirebaseDatabase.getInstance().getReference("Companies");
        this.onItemClicked=onItemClicked;

    }

    @NonNull
    @Override
    public UserPaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_payment_cell,parent,false);
        return new UserPaymentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPaymentHolder holder, int position) {
        databaseReference.child(payments.get(position).getCompany()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Company company=snapshot.getValue(Company.class);
                holder.txt_paid_cell.setText(context.getString(R.string.you_paid_for_company1_100_at_10_1_2020,company.getCompanyName(),payments.get(position).getPrice(),payments.get(position).getDate()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.txt_review_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked.onClicked(payments.get(position).getRequest_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public  class UserPaymentHolder extends RecyclerView.ViewHolder{
         TextView txt_paid_cell;
         TextView txt_review_purchase;

        public UserPaymentHolder(@NonNull View itemView) {
            super(itemView);
            txt_paid_cell=itemView.findViewById(R.id.txt_paid_cell);
            txt_review_purchase=itemView.findViewById(R.id.txt_review_purchase);

        }
    }
}
