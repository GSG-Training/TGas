package com.example.project_test.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_test.R;
import com.example.project_test.company.CompanyHome;
import com.example.project_test.company.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
   TextInputEditText edit_email,edit_password;
   Button btn_login;
   TextView txt_signup,txt_error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        edit_email=findViewById(R.id.edit_email);
        edit_password=findViewById(R.id.edit_password);
        btn_login=findViewById(R.id.btn_login);
        txt_signup=findViewById(R.id.txt_signup);
        txt_error=findViewById(R.id.txt_error);
        txt_error.setVisibility(View.GONE);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_email.getText().toString().equals("")&&!edit_password.getText().toString().equals("")){
                    SignIn();

                }
            }
        });
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
    }

    private void SignIn() {
        mAuth.signInWithEmailAndPassword(edit_email.getText().toString(), edit_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            txt_error.setVisibility(View.GONE);
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.child("users").hasChild(user.getUid())){
                                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));

                                    }
                                    else {
                                        startActivity(new Intent(LoginActivity.this, CompanyHome.class));

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            txt_error.setVisibility(View.VISIBLE);
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        //Check if user is signed in (non-null) and update UI accordingly.
       /* FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }*/
    }
}