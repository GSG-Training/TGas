package com.example.project_test.view.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_test.R;
import com.example.project_test.model.utils.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
   TextView txtEmail,txtChangeEmail,txtChangePassword,txtChangeLanguage;
   FirebaseUser user;
   DatabaseReference databaseReference;
   String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUiComponent();
    }

    private void initUiComponent() {
        txtEmail=findViewById(R.id.txt_email);
        txtChangeEmail=findViewById(R.id.txt_change_email);
        txtChangePassword=findViewById(R.id.txt_change_password);
        txtChangeLanguage=findViewById(R.id.txt_change_language);
        user= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference=FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user=snapshot.getValue(Users.class);
                txtEmail.setText(user.getEmail());
                password=user.getPassword();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        txtChangeEmail.setOnClickListener(this);
        txtChangeLanguage.setOnClickListener(this);
        txtChangePassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_change_email :
                changeEmail();
                break;
            case R.id.txt_change_password:
                changePassword();
                break;
            case R.id.txt_change_language:
                changeLanguage();
                break;



        }

    }
    private void changeEmail() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.change_email_dialog,null);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        EditText editEmail=view.findViewById(R.id.edit_new_email);
        TextView txtEmpty=view.findViewById(R.id.empty_field);
        Button btnCancel=view.findViewById(R.id.btn_cancel);
        Button btnEdit=view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editEmail.getText().toString())) {
                    txtEmpty.setVisibility(View.GONE);
                    user.updateEmail(editEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SettingActivity.this, "Email address is updated.", Toast.LENGTH_LONG).show();
                                        updateUserDatabase(editEmail.getText().toString());
                                    } else {
                                        Toast.makeText(SettingActivity.this, "Failed to update email!", Toast.LENGTH_LONG).show();
                                    }
                                    alertDialog.dismiss();
                                }
                            });
                }else{

                  txtEmpty.setVisibility(View.VISIBLE);

                }
            }
        });
     btnCancel.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             alertDialog.dismiss();
         }
     });
    }

    private void updateUserDatabase(String newEmail) {
        databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users currentuser=snapshot.getValue(Users.class);
                currentuser.setEmail(newEmail);
                Map<String,Object> update=currentuser.toMap();
                databaseReference.child(user.getUid()).updateChildren(update);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void changePassword() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.new_password_dialog,null);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        TextView txtError=view.findViewById(R.id.error_field);
        EditText editPassword=view.findViewById(R.id.edit_password);
        EditText editNewPassword=view.findViewById(R.id.edit_new_password);
        Button btnCancel=view.findViewById(R.id.btn_cancel);
        Button btnEdit=view.findViewById(R.id.btn_edit);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editPassword.getText().toString())&&!TextUtils.isEmpty(editNewPassword.getText().toString())){
                  if(editPassword.getText().toString().equals(password)){
                      txtError.setVisibility(View.GONE);
                      user.updatePassword(editNewPassword.getText().toString().trim())
                              .addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                      if (task.isSuccessful()) {
                                          Toast.makeText(SettingActivity.this, "Password is updated!", Toast.LENGTH_SHORT).show();
                                          updatePassword(editNewPassword.getText().toString().trim());
                                      } else {
                                          Toast.makeText(SettingActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                          //progressBar.setVisibility(View.GONE);
                                      }
                                      alertDialog.dismiss();
                                  }
                              });



                  }else {

                   txtError.setVisibility(View.VISIBLE);

                  }



                }

            }
        });

    }
   private void updatePassword(String newPassword){
       databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               Users currentuser=snapshot.getValue(Users.class);
               currentuser.setPassword(newPassword);
               Map<String,Object> update=currentuser.toMap();
               databaseReference.child(user.getUid()).updateChildren(update);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }

    private void changeLanguage() {
    }


}