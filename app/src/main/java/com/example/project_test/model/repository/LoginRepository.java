package com.example.project_test.model.repository;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.project_test.model.entitiy.Result;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginRepository {
    private static LoginRepository instance;
    FirebaseAuth mAuth;
    private MutableLiveData<Result> dataLogin;
    Activity activity;

    public static LoginRepository getInstance(Application application) {
        if (instance == null) {
            instance = new LoginRepository(application);
        }
        return instance;
    }

    private LoginRepository(Application application) {
        mAuth = FirebaseAuth.getInstance();
        dataLogin = new MutableLiveData<>();
        //  dataWrapper=new MutableLiveData<>() ;

    }

    public void Login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //  txt_error.setVisibility(View.GONE);
                                Log.d("TAG", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.child("users").hasChild(user.getUid())) {
                                            //  startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                            dataLogin.setValue(Result.success("user"));

                                        } else {
                                            Log.d("CompanyHome", "Company");
                                            dataLogin.setValue(Result.success("Company"));
                                            // startActivity(new Intent(LoginActivity.this, CompanyHome.class));

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            } else {


                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                dataLogin.setValue(Result.error("NotValidUser"));
                                //  Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }



                    }
                });

    }

    public LiveData<Result> getDataLogin() {
        return dataLogin;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
