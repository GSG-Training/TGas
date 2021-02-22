package com.example.project_test.view.activities.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_test.R;

import com.example.project_test.model.entitiy.Result;
import com.example.project_test.view.activities.company.CompanyHome;
import com.example.project_test.viewmodel.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements Observer<Result>, View.OnClickListener {
    private FirebaseAuth mAuth;
   TextInputEditText edit_email,edit_password;
   Button btn_login;
   TextView txt_signup,txt_error;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUiComponent();


    } // end onCreate

    private void initUiComponent() {
        mAuth = FirebaseAuth.getInstance();
        loginViewModel=new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(LoginViewModel.class);
        loginViewModel.getDataLogin().observe(this,this);
        loginViewModel.setActivity(this);
        edit_email=findViewById(R.id.edit_email);
        edit_password=findViewById(R.id.edit_password);
        btn_login=findViewById(R.id.btn_login);
        txt_signup=findViewById(R.id.txt_signup);
        txt_error=findViewById(R.id.txt_error);
        txt_error.setVisibility(View.GONE);
        btn_login.setOnClickListener(this);
        txt_signup.setOnClickListener(this);
    }



    @Override
    public void onStart() {
        super.onStart();
        //Check if user is signed in (non-null) and update UI accordingly.
     //   FirebaseUser currentUser = mAuth.getCurrentUser();
      //  if(currentUser != null){
          //  startActivity(new Intent(LoginActivity.this,CompanyHome.class));
       // }
    }

    @Override
    public void onChanged(Result result) {
       switch (result.status){
           case SUCCESS:
               String data=(String)result.data;
               if(data.equals("user")){
                   txt_error.setVisibility(View.GONE);
                   startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                   finish();

               }
               else if (data.equals("Company")){
                   txt_error.setVisibility(View.GONE);
                   startActivity(new Intent(LoginActivity.this, CompanyHome.class));
                   finish();

           }


               break;
           case ERROR:
               txt_error.setVisibility(View.VISIBLE);

       }


       }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                if (!TextUtils.isEmpty(edit_email.getText().toString())&&!TextUtils.isEmpty(edit_password.getText().toString())){
                    //     SignIn();
                    if(isNetworkConnected()) {
                        loginViewModel.Login(edit_email.getText().toString(), edit_password.getText().toString());
                    }else{
                        txt_error.setVisibility(View.GONE);
                        View parentLayout = findViewById(R.id.content);
                        Snackbar snackbar = Snackbar
                                .make(parentLayout, "No Internet Connection ", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });
                        snackbar.setActionTextColor(Color.WHITE);
                        snackbar.show();


                    }

                }
                break;

        case R.id.txt_signup: startActivity(new Intent(LoginActivity.this,GuideActivity.class));
        finish();
        break;

    }}
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}