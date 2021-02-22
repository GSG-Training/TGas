package com.example.project_test.view.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.project_test.R;
import com.example.project_test.model.entitiy.Request;
import com.example.project_test.view.adapter.RequestAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyorderActivity extends AppCompatActivity implements View.OnClickListener {
    List<Request> waitingList,execList,completeList,canceledList;
    List<String>  waitingKeys,execKeys,completeKeys,canceledKeys;
     DatabaseReference databaseReference;
     FirebaseUser user;
     RecyclerView recycler_waiting,recycler_exec,recycler_complete,recycler_canceled;
     ImageView img_wait,img_complete,img_canceled,img_exec;
     int click1=0,click2=0,click3=0,click4=0;
    RequestAdapter waitingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        initUiComponent();

    } //end onCreate

    private void initUiComponent() {
        recycler_waiting=findViewById(R.id.recycle_accepted);
        recycler_exec=findViewById(R.id.recycle_not_complete);
        recycler_complete=findViewById(R.id.recycle_completed);
        recycler_canceled=findViewById(R.id.recycle_canceled);
        img_wait=findViewById(R.id.img_wait);
        img_complete=findViewById(R.id.img_complete);
        img_canceled=findViewById(R.id.img_canceled);
        img_exec=findViewById(R.id.img_exec);
        img_wait.setOnClickListener(this);
        img_canceled.setOnClickListener(this);
        img_exec.setOnClickListener(this);
        img_complete.setOnClickListener(this);
        waitingList=new ArrayList<>();
        execList=new ArrayList<>();
        completeList=new ArrayList<>();
        canceledList=new ArrayList<>();
        waitingKeys=new ArrayList<>();
        execKeys=new ArrayList<>();
        completeKeys=new ArrayList<>();
        canceledKeys=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("Requests");
         waitingAdapter=new RequestAdapter(waitingList, this, new RequestAdapter.OnItemClick() {
            @Override
            public void onDeleteItem(int position) {
                showDeleteAlertDialog(position);
            }

            @Override
            public void onPreviewItem(Request request) {
                showPreviewAlertDialog(request);
            }
        });
        RequestAdapter execAdapter=new RequestAdapter(execList, this, new RequestAdapter.OnItemClick() {
            @Override
            public void onDeleteItem(int position) {
                //don't need
            }

            @Override
            public void onPreviewItem(Request request) {
                showPreviewAlertDialog(request);

            }
        });
        RequestAdapter completeAdapter=new RequestAdapter(completeList, this, new RequestAdapter.OnItemClick() {
            @Override
            public void onDeleteItem(int position) {
                //don't need


            }

            @Override
            public void onPreviewItem(Request request) {
                showPreviewAlertDialog(request);

            }
        });
        RequestAdapter canceledAdapter=new RequestAdapter(canceledList, this, new RequestAdapter.OnItemClick() {
            @Override
            public void onDeleteItem(int position) {
                //don't need

            }

            @Override
            public void onPreviewItem(Request request) {
                showPreviewAlertDialog(request);

            }
        });

        user= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Request request=dataSnapshot.getValue(Request.class);
                    if(request.getFrom().equals(user.getUid())){
                      switch (request.getStatus()){
                          case 0: waitingList.add(request);
                                  waitingKeys.add(dataSnapshot.getKey());
                                  waitingAdapter.notifyDataSetChanged();
                                  break;

                          case 1: execList.add(request);
                              execKeys.add(dataSnapshot.getKey());
                              execAdapter.notifyDataSetChanged();
                              break;
                          case 2: completeList.add(request);
                              completeKeys.add(dataSnapshot.getKey());
                              completeAdapter.notifyDataSetChanged();
                              break;
                          case 3: canceledList.add(request);
                              canceledKeys.add(dataSnapshot.getKey());
                              canceledAdapter.notifyDataSetChanged();
                              break;


                      } //end switch

                    }  //end if


                } // end for loop
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recycler_waiting.setLayoutManager(new LinearLayoutManager(this));
        recycler_waiting.setAdapter(waitingAdapter);
        recycler_canceled.setLayoutManager(new LinearLayoutManager(this));
        recycler_canceled.setAdapter(canceledAdapter);
        recycler_complete.setLayoutManager(new LinearLayoutManager(this));
        recycler_complete.setAdapter(completeAdapter);
        recycler_exec.setLayoutManager(new LinearLayoutManager(this));
        recycler_exec.setAdapter(execAdapter);

    }

    private void showPreviewAlertDialog(Request request) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.preview_request_dialog,null);
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

    private void showDeleteAlertDialog(int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
       builder.setTitle("Delete Request")
               .setMessage("Are you sure you want to delete request")
               .setCancelable(false)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       databaseReference.child(waitingKeys.get(position)).removeValue();
                       waitingList.remove(position);
                       waitingKeys.remove(position);
                       waitingAdapter.notifyDataSetChanged();
                   }
               })
               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();

                   }
               });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private void rotate(ImageView imageView,float degree) {
        final RotateAnimation rotateAnim = new RotateAnimation(0.0f, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
        imageView.startAnimation(rotateAnim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.img_wait:
                  if(click1==0){
                      rotate(img_wait,180);
                      recycler_waiting.setVisibility(View.VISIBLE);
                      click1=1;
                  }else {
                      rotate(img_wait,0);
                      recycler_waiting.setVisibility(View.GONE);
                      click1=0;
                  }
                  break;
            case  R.id.img_complete:
                if(click2==0){
                    rotate(img_complete,180);
                    recycler_complete.setVisibility(View.VISIBLE);
                    click2=1;
                }else {
                    rotate(img_complete,0);
                    recycler_complete.setVisibility(View.GONE);
                    click2=0;
                }
                break;
            case  R.id.img_canceled:
                if(click3==0){
                    rotate(img_canceled,180);
                    recycler_canceled.setVisibility(View.VISIBLE);
                    click3=1;
                }else {
                    rotate(img_canceled,0);
                    recycler_canceled.setVisibility(View.GONE);
                    click3=0;
                }
                break;
            case  R.id.img_exec:
                if(click4==0){
                    rotate(img_exec,180);
                    recycler_exec.setVisibility(View.VISIBLE);
                    click4=1;
                }else {
                    rotate(img_exec,0);
                    recycler_exec.setVisibility(View.GONE);
                    click4=0;
                }
                break;
        }
    }
}