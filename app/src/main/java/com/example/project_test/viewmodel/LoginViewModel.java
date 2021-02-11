package com.example.project_test.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project_test.model.entitiy.Result;
import com.example.project_test.model.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {
    private LiveData<Result>  dataLogin;
    private LoginRepository loginRepository;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository=LoginRepository.getInstance(application);
        dataLogin=loginRepository.getDataLogin();
    }
    public LiveData<Result> getDataLogin(){


        return dataLogin;
    }

    public  void Login(String email,String password){
        //request data from repository
        loginRepository.Login(email,password);


    }

    public void setActivity(Activity activity) {
        loginRepository.setActivity(activity);
    }
}
