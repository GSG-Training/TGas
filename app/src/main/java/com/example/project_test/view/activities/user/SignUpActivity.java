package com.example.project_test.view.activities.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_test.R;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {
   TextInputEditText edit_fname,edit_lname,edit_email,edit_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edit_fname=findViewById(R.id.edit_first_name);
        edit_lname=findViewById(R.id.edit_last_name);
        edit_email=findViewById(R.id.edit_email);
        edit_phone=findViewById(R.id.edit_phone_number);

        findViewById(R.id.btn_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit_fname.getText().toString().equals("")
                &&!edit_lname.getText().toString().equals("")
                &&!edit_email.getText().toString().equals("")
                &&!edit_phone.getText().toString().equals("")){
                  SaveInSharedPreference();
                  startActivity(new Intent(SignUpActivity.this,SignUpActivity2.class));
                }
            }
        });
    }

    private void SaveInSharedPreference() {
        SharedPreferences sharedPreferences=getSharedPreferences("userSignUp",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("fname",edit_fname.getText().toString());
        editor.putString("lname",edit_lname.getText().toString());
        editor.putString("email",edit_email.getText().toString());
        editor.putString("phone",edit_phone.getText().toString());


        editor.apply();
    }
}