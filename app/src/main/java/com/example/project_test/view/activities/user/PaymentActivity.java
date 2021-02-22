package com.example.project_test.view.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_test.R;
import com.example.project_test.model.entitiy.Payments;
import com.example.project_test.model.entitiy.Request;
import com.example.project_test.model.utils.Users;
import com.example.project_test.view.adapter.UserPaymentAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Payments> payments;
    ImageView img_visible;
    int click=0;
    DatabaseReference databaseReference;
    FirebaseUser user;
    TextView txt_user_balance;
    TextView txt_user_pay;
    DatabaseReference userReference;
    int paid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initUiComponent();
    }

    private void initUiComponent() {
        user= FirebaseAuth.getInstance().getCurrentUser();
        userReference=FirebaseDatabase.getInstance().getReference("users");
        databaseReference= FirebaseDatabase.getInstance().getReference("Payments");
        payments=new ArrayList<>();
        txt_user_balance=findViewById(R.id.txt_number_balance);
        txt_user_pay=findViewById(R.id.txt_number_total_paid_app);
        recyclerView=findViewById(R.id.recycle_payment);
        img_visible=findViewById(R.id.img_visible);
        userReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user=snapshot.getValue(Users.class);
                txt_user_balance.setText(user.getBalance()+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        img_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click==0){
                    click=1;
                    rotate(180);
                    recyclerView.setVisibility(View.VISIBLE);
                }else{
                    click=0;
                    rotate(0);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
        UserPaymentAdapter userPaymentAdapter=new UserPaymentAdapter(this, payments, new UserPaymentAdapter.OnItemClicked() {
            @Override
            public void onClicked(String request_id) {
                ShowPurchaseDialog(request_id);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Payments payment=dataSnapshot.getValue(Payments.class);
                    if(payment.getUser().equals(user.getUid())){
                        payments.add(payment);
                        paid+=payment.getPrice();
                        userPaymentAdapter.notifyDataSetChanged();
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        txt_user_pay.setText(paid+"");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userPaymentAdapter);

    }

    private void ShowPurchaseDialog(String request_id) {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Requests");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.preview_request_dialog,null);
        builder.setView(view);
        TextView txt_buy=view.findViewById(R.id.txt_buy);
        TextView txt_size=view.findViewById(R.id.txt_size);
        TextView txt_address=view.findViewById(R.id.edit_address);
        TextView txt_phone=view.findViewById(R.id.edit_phone);
        TextView txt_price=view.findViewById(R.id.txt_price_request);
        Button btn_ok=view.findViewById(R.id.btn_ok);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        databaseReference.child(request_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Request request=snapshot.getValue(Request.class);
               txt_size.setText(request.getSize());
                if(request.isBuy()){
                    txt_buy.setText("buy one");

                }else{
                    txt_buy.setText("fill  one");

                }
                // txt_size.setText(request.get);
                txt_address.setText(request.getAddress());
                txt_phone.setText(request.getPhoneNumber());
                txt_price.setText(request.getPrice()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private void rotate(float degree) {
        final RotateAnimation rotateAnim = new RotateAnimation(0.0f, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
        img_visible.startAnimation(rotateAnim);
    }
}