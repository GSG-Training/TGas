package com.example.project_test.view.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_test.R;
import com.example.project_test.model.entitiy.Comments;
import com.example.project_test.model.entitiy.Request;
import com.example.project_test.model.utils.Company;
import com.example.project_test.model.utils.Network.ApiService;
import com.example.project_test.model.utils.Network.Client;
import com.example.project_test.model.utils.Network.MyResponse;
import com.example.project_test.model.utils.Users;
import com.example.project_test.model.utils.notification.Data;
import com.example.project_test.model.utils.notification.MyFirebaseMessageService;
import com.example.project_test.model.utils.notification.Sender;
import com.example.project_test.model.utils.notification.Token;
import com.example.project_test.view.adapter.CommentAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyActivity extends AppCompatActivity implements View.OnClickListener {
    private static String URL="https://fcm.googleapis.com/";
    DatabaseReference mDataBase;
    TextView txt_name,txt_p_voted;
    RatingBar rate_company;
    Button btn_rate,btn_newRequestCompany,btn_message;
    float rate;
    int p_voted;
    Company company;
    String[] weight ={"30Kg","60Kg","80Kg","100Kg","120Kg"};
    FirebaseUser user;
    String UID_company;
    boolean isBuy=true;
    ImageButton img_addComment;
    EditText edit_addComment;
    String username="";
    public String newComment="";
    RecyclerView recycle_comments;
    String userNameNotification;
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        initUiComponent();

    } //end onCreate

    private void initUiComponent() {
        user=FirebaseAuth.getInstance().getCurrentUser();
        txt_name=findViewById(R.id.company_name);
        txt_p_voted=findViewById(R.id.txt_p_voted);
        rate_company=findViewById(R.id.rating_company);
        btn_rate=findViewById(R.id.rate_company);
        img_addComment=findViewById(R.id.image_add_comment);
        btn_newRequestCompany=findViewById(R.id.new_request_company);
        edit_addComment=findViewById(R.id.add_comment);
        recycle_comments=findViewById(R.id.recycle_comments);
        btn_message=findViewById(R.id.message_company);
        btn_rate.setOnClickListener(this);
        img_addComment.setOnClickListener(this);
        btn_newRequestCompany.setOnClickListener(this);
        btn_message.setOnClickListener(this);
        if(getIntent()!=null){
             UID_company =getIntent().getStringExtra("UID");
            mDataBase= FirebaseDatabase.getInstance().getReference("Companies").child(UID_company);
            mDataBase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                     company=snapshot.getValue(Company.class);
                    txt_name.setText(company.getCompanyName());
                    txt_p_voted.setText(getString(R.string.people_voted,company.getP_voted()));
                    p_voted=company.getP_voted();
                    rate=company.getRate();
                    rate_company.setRating(company.getRate());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        getListComments();
        apiService= Client.getInstance(URL).create(ApiService.class);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("users");
        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users currentUser=snapshot.getValue(Users.class);
                userNameNotification=currentUser.getFullName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getListComments() {
        List<Comments> comments=new ArrayList<>();
        CommentAdapter commentAdapter=new CommentAdapter(comments,this);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Comments");
       databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Comments comment = postSnapshot.getValue(Comments.class);
                    if (comment != null) {
                        Log.d("userName", comment.getUsername() + " ");
                if(comment.getCompanyName().equals(txt_name.getText().toString())){
                    comments.add(comment);
                    commentAdapter.notifyDataSetChanged();
                }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recycle_comments.setLayoutManager(new LinearLayoutManager(this));
        recycle_comments.setAdapter(commentAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rate_company:
                showRateDialog();
                  break;
            case R.id.new_request_company:
                showRequestDialog();
                break;
            case R.id.image_add_comment:
                ShowCommentDialog();

            case R.id.message_company:
                Intent intent=new Intent(CompanyActivity.this,MessageActivity.class);
                intent.putExtra("UID",UID_company);
                startActivity(intent);
        }
    }

    private void ShowCommentDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        AlertDialog alertDialog;
        View view;
         if (TextUtils.isEmpty(edit_addComment.getText())){
             view=LayoutInflater.from(this).inflate(R.layout.empty_comment_layout,null);
             builder.setView(view);
             builder.setCancelable(false);
             alertDialog=builder.create();
             alertDialog.show();
             Button btn_ok=view.findViewById(R.id.btn_ok);
             btn_ok.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     alertDialog.dismiss();
                 }
             });

         }else{
            view=LayoutInflater.from(this).inflate(R.layout.add_comment_dialog_layout,null);
            EditText edit_commentDialog=view.findViewById(R.id.edit_comment_dialog);
            edit_commentDialog.setText(edit_addComment.getText());
            Button btn_add=view.findViewById(R.id.btn_add);
             Button btn_cancel=view.findViewById(R.id.btn_cancel);
            RatingBar rate_comment=view.findViewById(R.id.rating_company_dialog);
             builder.setView(view);
             builder.setCancelable(false);
             alertDialog=builder.create();
             alertDialog.show();
             btn_cancel.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     alertDialog.dismiss();
                 }
             });
              btn_add.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      newComment=edit_commentDialog.getText().toString();
                      addCommentInDataBase(rate_comment.getRating());
                      alertDialog.dismiss();
                  }
              });



         }


    }

    private void addCommentInDataBase(float rating) {
        Comments comment=new Comments(user.getUid(),UID_company,newComment,rating,System.currentTimeMillis());
        DatabaseReference dataComment=FirebaseDatabase.getInstance().getReference("Comments");
        dataComment.push().setValue(comment);
        upDateRateCompany(rating,p_voted+1);

    }

    private void showRequestDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.new_request_comany,null);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
       Button btn_confirm_request=view.findViewById(R.id.btn_confirm_request);
        RadioGroup radioGroup=view.findViewById(R.id.txt_buy);
        Spinner spinner=view.findViewById(R.id.txt_size);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,weight);
        spinner.setAdapter(arrayAdapter);
       EditText edit_address=view.findViewById(R.id.edit_address);
        EditText edit_Phone=view.findViewById(R.id.edit_phone);
        TextView txt_price=view.findViewById(R.id.txt_price_request);
        TextView txt_fill=view.findViewById(R.id.txt_fill_field);
        txt_fill.setVisibility(View.GONE);
        RadioButton radio_buy=view.findViewById(R.id.radio_buy);
       // RadioButton radio_fill=view.findViewById(R.id.radio_fill);
        radio_buy.setChecked(true);
        btn_confirm_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_fill.setVisibility(View.GONE);
                if (TextUtils.isEmpty(edit_address.getText())&&TextUtils.isEmpty(edit_Phone.getText())){
                    txt_fill.setVisibility(View.VISIBLE);

             }else{
                    isBuy= radio_buy.isChecked();
              txt_fill.setVisibility(View.GONE);
                    int status=0;
                    Request request=new Request(UID_company,user.getUid(),spinner.getSelectedItem().toString(),edit_address.getText().toString(),edit_Phone.getText().toString(),isBuy,Integer.parseInt(txt_price.getText().toString()),status,System.currentTimeMillis());
                    addRequestInDatabase(request);


             }
              alertDialog.dismiss();
            }
        });

    }

    private void addRequestInDatabase(Request request) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Requests").push().setValue(request);
        //here send Notification to Company

         sendNotification(UID_company,user.getUid());



    }
//check here
    private void sendNotification(String company_uid, String uid) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Token");
        Query query=reference.orderByKey().equalTo(company_uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                   Token token=dataSnapshot.getValue(Token.class);
                   Data data=new Data(uid,R.drawable.ic_launcher_background,username+" add a new Request","New Request",company_uid);
                   Log.d("TOKEN",token.getToken()+"is token");
                   Sender sender=new Sender(data,token.getToken());
                   apiService.sendNotification(sender)
                           .enqueue(new Callback<MyResponse>() {
                               @Override
                               public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                   if(response.code()==200){
                                       if (response.body().success!=1){
                                           Toast.makeText(CompanyActivity.this, "Failed"+response.errorBody(), Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               }

                               @Override
                               public void onFailure(Call<MyResponse> call, Throwable t) {

                               }
                           });
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void upDateRateCompany(float rate,int p_voted) {
               company.setRate(rate);
               company.setP_voted(p_voted);
               Map<String,Object> updateMap=company.toMap();
               mDataBase.updateChildren(updateMap);


    }


    private void showRateDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.rating_dialog,null);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        Button btn_rate_dialog=view.findViewById(R.id.btn_rate);
        RatingBar rating_company_dialog=view.findViewById(R.id.rating_company_dialog);
        btn_rate_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float newRate= (rating_company_dialog.getRating()+rate)/(p_voted+1);
                upDateRateCompany(newRate,p_voted+1);
              alertDialog.dismiss();
            }
        });

    }


}