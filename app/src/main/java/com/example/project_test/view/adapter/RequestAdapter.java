package com.example.project_test.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;
import com.example.project_test.model.entitiy.Request;
import com.example.project_test.model.utils.Company;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Request> requests;
    Context context;
    OnItemClick onItemClick;
  public   interface OnItemClick{
        void onDeleteItem(int position);
        void onPreviewItem(Request request);
    }

    public RequestAdapter(List<Request> requests, Context context,OnItemClick onItemClick) {
        this.requests = requests;
        this.context = context;
        this.onItemClick=onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0){
            View view= LayoutInflater.from(context).inflate(R.layout.order_waiting_accept_cell,parent,false);
            return new RequestHolder1(view);


        }
        else{
            View view= LayoutInflater.from(context).inflate(R.layout.order_cell,parent,false);
            return new RequestHolder2(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType()==0){
            RequestHolder1 requestHolder1= (RequestHolder1) holder;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = formatter.format(new Date(requests.get(position).getDate()));
             requestHolder1.txt_date.setText(dateString);
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Companies");
            databaseReference.child(requests.get(position).getTo()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Company company=snapshot.getValue(Company.class);
                    requestHolder1.txt_company.setText("to "+company.getCompanyName());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            requestHolder1.txt_review_purchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onPreviewItem(requests.get(position));
                }
            });
            requestHolder1.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onDeleteItem(position);
                }
            });
        }else {
            RequestHolder2 requestHolder2= (RequestHolder2) holder;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = formatter.format(new Date(requests.get(position).getDate()));
            requestHolder2.txt_date.setText(dateString);
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Companies");
            databaseReference.child(requests.get(position).getTo()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Company company=snapshot.getValue(Company.class);
                    requestHolder2.txt_company.setText("to "+company.getCompanyName());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

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
        }else {
            return 1;
        }
    }

    class RequestHolder1 extends RecyclerView.ViewHolder{
         TextView txt_company,txt_date,txt_review_purchase;
         ImageView img_delete;

        public RequestHolder1(@NonNull View itemView) {
            super(itemView);
            txt_company=itemView.findViewById(R.id.txt_company_order);
            txt_date=itemView.findViewById(R.id.txt_date);
            img_delete=itemView.findViewById(R.id.img_delete);
            txt_review_purchase=itemView.findViewById(R.id.txt_review_purchase);
        }
    }

    class RequestHolder2 extends RecyclerView.ViewHolder{
          TextView txt_company,txt_date,txt_review_purchase;

        public RequestHolder2(@NonNull View itemView) {
            super(itemView);
            txt_company=itemView.findViewById(R.id.txt_order);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_review_purchase=itemView.findViewById(R.id.txt_review_purchase);

        }
    }

}
