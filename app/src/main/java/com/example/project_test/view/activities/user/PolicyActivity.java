package com.example.project_test.view.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_test.R;

public class PolicyActivity extends AppCompatActivity {
    RadioButton radioButton;
    Button btn_continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        radioButton=findViewById(R.id.radio_agree);
        btn_continue=findViewById(R.id.btn_continue);

      btn_continue.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(PolicyActivity.this,SignUpActivity.class));
              finish();
          }
      });
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btn_continue.setEnabled(true);
                }else {
                    btn_continue.setEnabled(false);

                }
            }
        });
    }
}