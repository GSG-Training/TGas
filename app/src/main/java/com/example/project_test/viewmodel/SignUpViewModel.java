package com.example.project_test.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.project_test.model.entitiy.Result;
import com.example.project_test.model.repository.SignUpRepository;

public class SignUpViewModel extends AndroidViewModel {
    private SignUpRepository signUpRepository;
    private LiveData<Result> dataSignUp;
    public SignUpViewModel(@NonNull Application application) {
        super(application);
        signUpRepository=SignUpRepository.getInstance(application);
        dataSignUp=signUpRepository.getDataSignUp();
    }


    public  void SignUpUser(String email,String password,String Phone,String FullName,String city){
        signUpRepository.SignUpUser(email,password,Phone,FullName,city);
    }
    public  void SignUpCompany(String email,String password,String CompanyName,String Phone,String FullName,String address,String city){
        signUpRepository.SignUpCompany(email, password, CompanyName, Phone, FullName, address, city);
    }
    public void setActivity(Activity activity) {
        signUpRepository.setActivity(activity);
    }
    public LiveData<Result> getDataSignUp(){

        return dataSignUp;
    }
}
