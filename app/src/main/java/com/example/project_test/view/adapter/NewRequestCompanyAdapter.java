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
import com.example.project_test.model.utils.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewRequestCompanyAdapter extends RecyclerView.Adapter<NewRequestCompanyAdapter.NewRequestCompanyHolder> {
    Context context;
    public List<Request> requests;
    OnItemClicked onItemClicked;
  public  interface OnItemClicked{
      public void onClick(int position);
  }
    public NewRequestCompanyAdapter(Context context, List<Request> requests,OnItemClicked onItemClicked) {
        this.context = context;
        this.requests = requests;
        this.onItemClicked=onItemClicked;
    }

    @NonNull
    @Override
    public NewRequestCompanyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.new_request__company_home,parent,false);
        return new NewRequestCompanyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewRequestCompanyHolder holder, int position) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(requests.get(position).getFrom()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);
                String userName=users.getFullName();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
                Date date=new Date(requests.get(position).getDate());
                String time=simpleDateFormat.format(date);
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                String request_date=simpleDateFormat1.format(date);
                holder.txtUser.setText("From "+userName+" at "+time);
                holder.txtDate.setText(request_date);
                holder.txtClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClicked.onClick(position);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public  class  NewRequestCompanyHolder extends RecyclerView.ViewHolder{
         TextView txtUser,txtDate,txtClick;

        public NewRequestCompanyHolder(@NonNull View itemView) {
            super(itemView);
            txtUser=itemView.findViewById(R.id.txt_user_time);
            txtDate=itemView.findViewById(R.id.date_request);
            txtClick=itemView.findViewById(R.id.txt_click_view);
        }
    }
}
