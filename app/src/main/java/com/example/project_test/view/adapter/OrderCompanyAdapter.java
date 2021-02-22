package com.example.project_test.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;
import com.example.project_test.model.entitiy.Request;
import com.example.project_test.model.utils.Company;
import com.example.project_test.model.utils.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderCompanyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Request> requests;
    OnItemClicked onItemClicked;

    public interface OnItemClicked{
        void acceptClick(int position);
        void cancelClick(int position);
        void completeClick(int position);
        void  onPreviewClick(Request request);
    }

    public OrderCompanyAdapter(Context context, List<Request> requests,OnItemClicked onItemClicked) {
        this.context = context;
        this.requests = requests;
        this.onItemClicked=onItemClicked;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0){
            View view= LayoutInflater.from(context).inflate(R.layout.order_waiting_accept_company_layout,parent,false);
            return new OrderHolder(view);


        }
        else if(viewType==1){
            View view= LayoutInflater.from(context).inflate(R.layout.order_company_exec_layout,parent,false);
            return new OrderHolder1(view);

        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.order_cell, parent, false);
            return new OrderHolder2(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            OrderHolder orderHolder = (OrderHolder) holder;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = formatter.format(new Date(requests.get(position).getDate()));
            orderHolder.txt_date.setText(dateString);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
            databaseReference.child(requests.get(position).getFrom()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users user = snapshot.getValue(Users.class);
                    orderHolder.txt_user.setText("from " + user.getFullName());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            orderHolder.txt_review_purchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      onItemClicked.onPreviewClick(requests.get(position));
                }
            });
            orderHolder.txt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.cancelClick(position);
                }
            });
            orderHolder.txt_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.acceptClick(position);
                }
            });

        } else if (holder.getItemViewType() == 1) {
            OrderHolder1 orderHolder1 = (OrderHolder1) holder;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = formatter.format(new Date(requests.get(position).getDate()));
            orderHolder1.txt_date.setText(dateString);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
            databaseReference.child(requests.get(position).getFrom()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users user = snapshot.getValue(Users.class);
                    orderHolder1.txt_user.setText("from" + user.getFullName());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            orderHolder1.txt_review_purchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.onPreviewClick(requests.get(position));
                }
            });
            orderHolder1.txt_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.completeClick(position);
                }
            });
        } else {
            OrderHolder2 orderHolder2 =(OrderHolder2) holder;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = formatter.format(new Date(requests.get(position).getDate()));
            orderHolder2.txt_date.setText(dateString);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
            databaseReference.child(requests.get(position).getFrom()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users user = snapshot.getValue(Users.class);
                    orderHolder2.txt_user.setText("from " + user.getFullName());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            orderHolder2.txt_review_purchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.onPreviewClick(requests.get(position));
                }
            });

        }
    }
    @Override
    public int getItemCount() {
        return requests.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(requests.get(position).getStatus()==0){
            return 0;
        }else if(requests.get(position).getStatus()==1) {
            return 1;
        } else {
            return 2;
        }
    }

    public class OrderHolder extends  RecyclerView.ViewHolder{
        TextView txt_user,txt_accept,txt_cancel,txt_date,txt_review_purchase;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            txt_user=itemView.findViewById(R.id.txt_company_order);
            txt_accept=itemView.findViewById(R.id.txt_accept);
            txt_cancel=itemView.findViewById(R.id.txt_delete);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_review_purchase=itemView.findViewById(R.id.txt_review_purchase);
        }
    }
    public class OrderHolder1 extends  RecyclerView.ViewHolder{
        TextView txt_user,txt_complete,txt_date,txt_review_purchase;
        public OrderHolder1(@NonNull View itemView) {
            super(itemView);
            txt_user=itemView.findViewById(R.id.txt_company_order);
            txt_complete=itemView.findViewById(R.id.txt_complete);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_review_purchase=itemView.findViewById(R.id.txt_review_purchase);



        }
    }
    public class OrderHolder2 extends  RecyclerView.ViewHolder{
        TextView txt_user,txt_date,txt_review_purchase;
        public OrderHolder2(@NonNull View itemView) {
            super(itemView);
            txt_user=itemView.findViewById(R.id.txt_order);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_review_purchase=itemView.findViewById(R.id.txt_review_purchase);

        }
    }
}
