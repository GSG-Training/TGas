package com.example.project_test.model.repository;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.project_test.model.entitiy.Result;
import com.example.project_test.model.utils.Company;
import com.example.project_test.model.utils.Users;
import com.example.project_test.view.activities.user.SignUpActivity2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpRepository {
    private static  SignUpRepository signUpRepository;
    private FirebaseAuth mAuth;
    private MutableLiveData<Result> dataSignUp;
    FirebaseUser user;
    Activity activity;
    DatabaseReference mDatabase;

    public SignUpRepository(Application application)
    {
        mAuth=FirebaseAuth.getInstance();
        dataSignUp=new MutableLiveData<>() ;
        mDatabase= FirebaseDatabase.getInstance().getReference();
    }
      public  static SignUpRepository getInstance(Application application){
        if (signUpRepository==null){
            signUpRepository=new SignUpRepository(application);
        }
        return signUpRepository;

      }
      public  void SignUpUser(String email,String password,String Phone,String FullName,String city) {
          mAuth.createUserWithEmailAndPassword(email, password)
                  .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()) {
                              // Sign in success, update UI with the signed-in user's information
                              Log.d("SignUp","inSignUp:task");
                              Log.d("TAG", "createUserWithEmail:success");
                              user = mAuth.getCurrentUser();
                              SaveUserDataInRealTimeDatabase(email,password,Phone,FullName,city);
                              dataSignUp.setValue(Result.success("user"));


                          } else {

                              // If sign in fails, display a message to the user.
                              Log.w("SignUp", "inSignUp:else task", task.getException());
                              Toast.makeText(activity.getApplicationContext(), "Authentication failed.",
                                      Toast.LENGTH_SHORT).show();
                              dataSignUp.setValue(Result.error("error"));

                          }

                      }
                  });
      }
          public  void SignUpCompany(String email,String password,String CompanyName,String Phone,String FullName,String address,String city){
              mAuth.createUserWithEmailAndPassword(email, password)
                      .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                              if (task.isSuccessful()) {
                                  // Sign in success, update UI with the signed-in user's information
                                  Log.d("SignUp","inSignUp:task");
                                  Log.d("TAG", "createUserWithEmail:success");
                                  user = mAuth.getCurrentUser();
                                  SaveCompanyDataInRealTimeDatabase(email,password,CompanyName,Phone,FullName,address,city);
                                  dataSignUp.setValue(Result.success("company"));


                              } else {
                                  dataSignUp.setValue(Result.error("error"));
                                  // If sign in fails, display a message to the user.
                                  Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                //  Toast.makeText(activity.getApplicationContext(), "Authentication failed.",
                                      //    Toast.LENGTH_SHORT).show();

                              }

                          }
                      });




      }

    private void SaveCompanyDataInRealTimeDatabase(String email,String password,String CompanyName,String phone,String FullName,String address,String city) {
        Company company=new Company(email,phone,CompanyName,FullName,password,address,city,0,0);
        mDatabase.child("Companies").child(user.getUid()).setValue(company);
    }

    private void SaveUserDataInRealTimeDatabase(String email,String password,String phone,String FullName,String city) {
        int balance=0;
        Users muser=new Users(email,phone,password,FullName,city,balance);
        mDatabase.child("users").child(user.getUid()).setValue(muser);
    }

    public MutableLiveData<Result> getDataSignUp() {
        return dataSignUp;
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
