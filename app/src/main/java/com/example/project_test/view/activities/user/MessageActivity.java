package com.example.project_test.view.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_test.R;
import com.example.project_test.model.entitiy.Chats;
import com.example.project_test.model.utils.Company;
import com.example.project_test.view.adapter.ChatAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {
   Toolbar toolbar;
   TextView txtUserName;
   ImageView imgBack;
   FirebaseUser user;
   DatabaseReference databaseReference;
   String receiverUID;
   EditText editMessage;
   ImageView btnSend;
   RecyclerView recyclerView;
   List<Chats> chats=new ArrayList<>();
    ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        user= FirebaseAuth.getInstance().getCurrentUser();
        toolbar=findViewById(R.id.toolbar);
        if(getIntent()!=null){
            if(getIntent().hasExtra("UID"));
            receiverUID=getIntent().getStringExtra("UID");
        }
        txtUserName=toolbar.findViewById(R.id.txt_name);
        recyclerView=findViewById(R.id.recycle_message);
         chatAdapter=new ChatAdapter(this,chats);
        editMessage=findViewById(R.id.edit_message);
        btnSend=findViewById(R.id.send_message);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editMessage.getText().toString().equals(""))
                { sendMessage(editMessage.getText().toString());}
                else {
                    Toast.makeText(MessageActivity.this, "Can't send empty message", Toast.LENGTH_SHORT).show();
                }
            }
        });
        toolbar.findViewById(R.id.back_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        databaseReference=FirebaseDatabase.getInstance().getReference("Companies");
        databaseReference.child(receiverUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Company company=snapshot.getValue(Company.class);
                txtUserName.setText(company.getCompanyName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
   getListChat();
   recyclerView.setLayoutManager(new LinearLayoutManager(this));
   recyclerView.setAdapter(chatAdapter);
    }

    private void getListChat() {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Chats chat=dataSnapshot.getValue(Chats.class);
                    if(chat.getSender().equals(user.getUid())){
                        chats.add(chat);
                        chatAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String message) {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("chats");
        Chats chat =new Chats(user.getUid(),receiverUID,message);
        databaseReference.push().setValue(chat);
    }
}