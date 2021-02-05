package com.example.project_test.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_test.R;
import com.example.project_test.company.CompanyHome;
import com.example.project_test.company.HomeFragment;
import com.example.project_test.user.utils.Company;
import com.example.project_test.user.utils.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Spinner spinner_city;
    RadioButton radio_client,radio_representative;
    LinearLayout linear_client;
    ConstraintLayout cons_representative;
    TextInputEditText edit_passwordC,edit_confirm_passwordC;
    EditText edit_company_name,edit_company_address,edit_passwordR,edit_confirm_passwordR;
    Button btn_sign;
    String city[]={"Gaza","khanyonis","deir-balah","Rafah","Nuseirat","North Gaza","South Gaza"};
    RadioGroup radio_group;
    TextView text_notmatched,txt_length_password;
    DatabaseReference mDatabase;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.continue_signup);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        spinner_city=findViewById(R.id.spinner_city);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,city);
        spinner_city.setAdapter(arrayAdapter);
        spinner_city.setId(0);
        radio_client=findViewById(R.id.radio_client);
        radio_representative=findViewById(R.id.radio_representative);
        linear_client=findViewById(R.id.linear_client);
        cons_representative=findViewById(R.id.cons_representative);
        edit_passwordC=findViewById(R.id.edit_password_client);
        edit_confirm_passwordC=findViewById(R.id.edit_confirm_password_client);
        edit_passwordR=findViewById(R.id.edit_company_password);
        edit_confirm_passwordR=findViewById(R.id.edit_company_confirm_password);
        edit_company_name=findViewById(R.id.edit_company_name);
        edit_company_address=findViewById(R.id.edit_company_address);
        btn_sign=findViewById(R.id.btn_signup);
        text_notmatched=findViewById(R.id.not_matched);
        text_notmatched.setVisibility(View.GONE);
        txt_length_password=findViewById(R.id.txt_length_password);
        radio_group=findViewById(R.id.radio_group);
        radio_representative.setChecked(true);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radio_client:
                        linear_client.setVisibility(View.VISIBLE);
                        cons_representative.setVisibility(View.GONE);
                        text_notmatched.setVisibility(View.GONE);
                        break;

                    case R.id.radio_representative:
                    linear_client.setVisibility(View.GONE);
                    cons_representative.setVisibility(View.VISIBLE);
                        text_notmatched.setVisibility(View.GONE);
                        break;
                }
            }
        });
       btn_sign.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!(!radio_group.isClickable()&&edit_passwordC.getText().toString().equals("")&&edit_confirm_passwordC.getText().toString().equals(""))) {
                   if (edit_passwordC.getText().toString().length() >= 6) {
                       if (edit_confirm_passwordC.getText().toString().equals(edit_passwordC.getText().toString())) {
                           SignUpUserFirebase();
                           txt_length_password.setVisibility(View.GONE);
                           text_notmatched.setVisibility(View.GONE);
                           startActivity(new Intent(SignUpActivity2.this, HomeActivity.class));

                       } else {
                           text_notmatched.setVisibility(View.VISIBLE);
                           txt_length_password.setVisibility(View.GONE);
                       }

                   }else {
                      txt_length_password.setVisibility(View.VISIBLE);
                   }
               }
              if(!(!radio_group.isClickable()&&edit_passwordR.getText().toString().equals("")&&edit_confirm_passwordR.getText().toString().equals("")&&edit_company_address.getText().toString().equals("")&&edit_company_name.getText().toString().equals(""))){
              if((edit_passwordR.getText().toString().equals(edit_confirm_passwordR.getText().toString()))){
                  SignUpUserFirebase();
                  text_notmatched.setVisibility(View.GONE);
                  startActivity(new Intent(SignUpActivity2.this, CompanyHome.class));

              }else{
                  text_notmatched.setVisibility(View.VISIBLE);
              }





              }



           }
       });

    }

    private void SaveUserDataInRealTimeDatabase() {
        SharedPreferences sharedPreferences=getSharedPreferences("userSignUp",MODE_PRIVATE);
        String email=sharedPreferences.getString("email","");
        String phone=sharedPreferences.getString("phone","");
        String password=edit_passwordC.getText().toString();
        String FullName=sharedPreferences.getString("fname","")+" "+sharedPreferences.getString("lname","");
        Users muser=new Users(email,phone,password,FullName,spinner_city.getSelectedItem().toString());
        mDatabase.child("users").child(user.getUid()).setValue(muser);




    }
    private void SaveCompanyDataInRealTimeDatabase() {
        SharedPreferences sharedPreferences=getSharedPreferences("userSignUp",MODE_PRIVATE);
        String email=sharedPreferences.getString("email","");
        String phone=sharedPreferences.getString("phone","");
        String CompanyName=edit_company_name.getText().toString();
        String FullName=sharedPreferences.getString("fname","")+" "+sharedPreferences.getString("lname","");
        String password=edit_passwordR.getText().toString();
        String address=edit_company_address.getText().toString();
      Company company=new Company(email,phone,CompanyName,FullName,password,address,spinner_city.getSelectedItem().toString());
        mDatabase.child("Companies").child(user.getUid()).setValue(company);




    }

    private void SignUpUserFirebase() {
        SharedPreferences sharedPreferences=getSharedPreferences("userSignUp",MODE_PRIVATE);
        String email=sharedPreferences.getString("email","");
        String password="";
        if(radio_group.getCheckedRadioButtonId()==R.id.radio_client){
            password=edit_passwordC.getText().toString();}
            else{
            password=edit_passwordR.getText().toString();
            }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                             user = mAuth.getCurrentUser();
                             if(radio_client.isChecked()){
                                 SaveUserDataInRealTimeDatabase();
                             }else {
                                 SaveCompanyDataInRealTimeDatabase();
                             }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity2.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}