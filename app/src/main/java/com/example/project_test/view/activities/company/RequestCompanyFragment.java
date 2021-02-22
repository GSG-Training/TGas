package com.example.project_test.view.activities.company;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_test.R;
import com.example.project_test.model.entitiy.Request;
import com.example.project_test.view.adapter.NewRequestCompanyAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RequestCompanyFragment extends Fragment {
   RecyclerView recyclerView;
   FirebaseUser user;
   DatabaseReference databaseReference;
   List<Request> requests=new ArrayList<>();
   List<String> requestKeys=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_request_company, container, false);
        user= FirebaseAuth.getInstance().getCurrentUser();
        recyclerView=view.findViewById(R.id.recycle_request);
        NewRequestCompanyAdapter newRequestCompanyAdapter=new NewRequestCompanyAdapter(getContext(), requests, new NewRequestCompanyAdapter.OnItemClicked() {
            @Override
            public void onClick(int position) {
            showRequestPreview(position);
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference("Requests");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Request request=dataSnapshot.getValue(Request.class);
                    if (request.getTo().equals(user.getUid())){
                        requestKeys.add(dataSnapshot.getKey());
                        requests.add(request);
                        newRequestCompanyAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(newRequestCompanyAdapter);
        return view;
    }

    private void showRequestPreview(int position) {
        Request request=requests.get(position);
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view= LayoutInflater.from(getContext()).inflate(R.layout.preview_request_dialog,null);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        TextView txt_buy=view.findViewById(R.id.txt_buy);
        TextView txt_size=view.findViewById(R.id.txt_size);
        TextView txt_address=view.findViewById(R.id.edit_address);
        TextView txt_phone=view.findViewById(R.id.edit_phone);
        TextView txt_price=view.findViewById(R.id.txt_price_request);
        Button btn_ok=view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if(request.isBuy()){
            txt_buy.setText("buy one");

        }else{
            txt_buy.setText("fill  one");

        }
         txt_size.setText(request.getSize());
        txt_address.setText(request.getAddress());
        txt_phone.setText(request.getPhoneNumber());
        txt_price.setText(request.getPrice()+"");

    }
}