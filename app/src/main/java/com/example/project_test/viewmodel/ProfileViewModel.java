package com.example.project_test.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project_test.model.entitiy.Result;
import com.example.project_test.model.repository.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {
    private ProfileRepository profileRepository;
    private LiveData<Result> dataProfile;
    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepository=ProfileRepository.getInstance(application);
        dataProfile=profileRepository.getDataProfileRepository();
    }

    public LiveData<Result> getDataProfile() {
        return dataProfile;
    }
    public void editUserData(String FullName,String phoneNumber){
        Log.d("Edit","edit ProfileViewModel");
        profileRepository.editUserData(FullName,phoneNumber);
    }
    public void editCompanyData(String FullName,String phoneNumber,String companyName){
        Log.d("Edit","edit ProfileViewModel");
        profileRepository.editCompanyData(FullName,phoneNumber,companyName);
    }
    public void getUserData(){
        profileRepository.getUserData();
    }
    public void  getCompanyData(){
        profileRepository.getCompanyData();
    }
    public void setActivity(Activity activity) {
        profileRepository.setActivity(activity);
    }

    }
