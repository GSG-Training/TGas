package com.example.project_test.model.repository;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.project_test.model.entitiy.Result;
import com.example.project_test.model.utils.Company;
import com.example.project_test.model.utils.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ProfileRepository {
    private static  ProfileRepository profileRepository;
    private FirebaseAuth mAuth;
    private MutableLiveData<Result> dataProfile;
    FirebaseUser user;
    Activity activity;
    DatabaseReference mDatabase;
    FirebaseUser firebaseUser;
    public  ProfileRepository(Application application) {
        mAuth=FirebaseAuth.getInstance();
        dataProfile=new MutableLiveData<>() ;
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

    }

    public static ProfileRepository  getInstance(Application application){
        if(profileRepository==null){
            profileRepository=new ProfileRepository(application);
        }

        return profileRepository;
    }
   public void getUserData(){
       mDatabase= FirebaseDatabase.getInstance().getReference("users");
       mDatabase.child(firebaseUser.getUid())
       .addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               Users user=snapshot.getValue(Users.class);
               dataProfile.setValue(Result.success(user));

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });}
       public void getCompanyData(){
           mDatabase= FirebaseDatabase.getInstance().getReference("Companies");
           mDatabase.child(firebaseUser.getUid())
                   .addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           Company company=snapshot.getValue(Company.class);
                           dataProfile.setValue(Result.success(company));

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });


    }

    public void editUserData(String FullName,String phoneNumber){
        mDatabase= FirebaseDatabase.getInstance().getReference("users");
        mDatabase.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Edit","edit ProfileRepository");
                Users user=snapshot.getValue(Users.class);
                user.setFullName(FullName);
                user.setPhoneNumber(phoneNumber);
                Map<String,Object> updates=user.toMap();
                mDatabase.child(firebaseUser.getUid()).updateChildren(updates);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void editCompanyData(String FullName,String phoneNumber,String CompanyName){
        mDatabase= FirebaseDatabase.getInstance().getReference("Companies");
        mDatabase.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Edit","edit ProfileRepository");
                Company company=snapshot.getValue(Company.class);
                company.setFullName(FullName);
                company.setPhoneNumber(phoneNumber);
                company.setCompanyName(CompanyName);
                Map<String,Object> updates=company.toMap();
                mDatabase.child(firebaseUser.getUid()).updateChildren(updates);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public MutableLiveData<Result> getDataProfileRepository() {
        return dataProfile;
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
