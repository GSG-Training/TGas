package com.example.project_test.view.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_test.R;
import com.example.project_test.model.entitiy.Result;
import com.example.project_test.view.activities.company.CompanyHome;
import com.example.project_test.view.activities.company.HomeFragment;
import com.example.project_test.model.utils.Company;
import com.example.project_test.model.utils.Users;
import com.example.project_test.viewmodel.LoginViewModel;
import com.example.project_test.viewmodel.SignUpViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.project_test.model.entitiy.Result.Status.ERROR;

public class SignUpActivity2 extends AppCompatActivity implements View.OnClickListener, Observer<Result> {
    private FirebaseAuth mAuth;
    Spinner spinner_city;
    RadioButton radio_client,radio_representative;
    LinearLayout linear_client;
    ConstraintLayout cons_representative;
    TextInputEditText edit_passwordC,edit_confirm_passwordC;
    EditText edit_company_name,edit_company_address,edit_passwordR,edit_confirm_passwordR;
    Button btn_sign;
    String city[]={"Gaza","khanyonis","deir-balah","Rafah","Nuseirat","North Gaza","South Gaza"};
    RadioGroup radio_group;
    TextView text_notmatched,txt_length_password;
    DatabaseReference mDatabase;
    FirebaseUser user;
     SignUpViewModel signUpViewModel;
    SharedPreferences sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.continue_signup);
        initUiComponent();




    }//end onCreate

    private void initUiComponent() {
        signUpViewModel=new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(SignUpViewModel.class);
        signUpViewModel.getDataSignUp().observe(this,this);
        signUpViewModel.setActivity(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        spinner_city=findViewById(R.id.spinner_city);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,city);
        spinner_city.setAdapter(arrayAdapter);
        spinner_city.setId(0);
        radio_client=findViewById(R.id.radio_client);
        radio_representative=findViewById(R.id.radio_representative);
        linear_client=findViewById(R.id.linear_client);
        cons_representative=findViewById(R.id.cons_representative);
        edit_passwordC=findViewById(R.id.edit_password_client);
        edit_confirm_passwordC=findViewById(R.id.edit_confirm_password_client);
        edit_passwordR=findViewById(R.id.edit_company_password);
        edit_confirm_passwordR=findViewById(R.id.edit_company_confirm_password);
        edit_company_name=findViewById(R.id.edit_company_name);
        edit_company_address=findViewById(R.id.edit_company_address);
        btn_sign=findViewById(R.id.btn_signup);
        text_notmatched=findViewById(R.id.not_matched);
        text_notmatched.setVisibility(View.GONE);
        txt_length_password=findViewById(R.id.txt_length_password);
        sharedPreference=getSharedPreferences("userSignUp",MODE_PRIVATE);
        radio_group=findViewById(R.id.radio_group);
        radio_representative.setChecked(true);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radio_client:
                        linear_client.setVisibility(View.VISIBLE);
                        cons_representative.setVisibility(View.GONE);
                        text_notmatched.setVisibility(View.GONE);
                        break;

                    case R.id.radio_representative:
                        linear_client.setVisibility(View.GONE);
                        cons_representative.setVisibility(View.VISIBLE);
                        text_notmatched.setVisibility(View.GONE);
                        break;
                }
            }
        });
        btn_sign.setOnClickListener(this);



    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup: if(!(!radio_group.isClickable()&&edit_passwordC.getText().toString().equals("")&&edit_confirm_passwordC.getText().toString().equals(""))) {
                if (edit_passwordC.getText().toString().length() >= 6) {
                    if (edit_confirm_passwordC.getText().toString().equals(edit_passwordC.getText().toString())) {
                        String email=sharedPreference.getString("email","");
                        String phone=sharedPreference.getString("phone","");
                        String password=edit_passwordC.getText().toString();
                        String FullName=sharedPreference.getString("fname","")+" "+sharedPreference.getString("lname","");
                        signUpViewModel.SignUpUser(email,password,phone,FullName,spinner_city.getSelectedItem().toString());
                        txt_length_password.setVisibility(View.GONE);
                        text_notmatched.setVisibility(View.GONE);

                    } else {
                        text_notmatched.setVisibility(View.VISIBLE);
                        txt_length_password.setVisibility(View.GONE);
                    }

                }else {
                    txt_length_password.setVisibility(View.VISIBLE);
                }
            }
                if(!(!radio_group.isClickable()&&edit_passwordR.getText().toString().equals("")&&edit_confirm_passwordR.getText().toString().equals("")&&edit_company_address.getText().toString().equals("")&&edit_company_name.getText().toString().equals(""))){
                    if (edit_passwordR.getText().toString().length() >= 6) {
                        if ((edit_passwordR.getText().toString().equals(edit_confirm_passwordR.getText().toString()))) {
                            String email = sharedPreference.getString("email", "");
                            String phone = sharedPreference.getString("phone", "");
                            String CompanyName = edit_company_name.getText().toString();
                            String FullName = sharedPreference.getString("fname", "") + " " + sharedPreference.getString("lname", "");
                            String password = edit_passwordR.getText().toString();
                            String address = edit_company_address.getText().toString();
                            signUpViewModel.SignUpCompany(email, password, CompanyName, phone, FullName, address, spinner_city.getSelectedItem().toString());
                            text_notmatched.setVisibility(View.GONE);
                            txt_length_password.setVisibility(View.GONE);

                        } else {
                            text_notmatched.setVisibility(View.VISIBLE);
                            txt_length_password.setVisibility(View.GONE);

                        }

                    }else {

                        txt_length_password.setVisibility(View.VISIBLE);


                    }



                }



        }
        }//end onClick

    @Override
    public void onChanged(Result o) {
        switch (o.status){
            case SUCCESS:
                String data=(String)o.data;
                if(data.equals("user")){
                    startActivity(new Intent(SignUpActivity2.this,HomeActivity.class));

                }else if(data.equals("company")){
                    startActivity(new Intent(SignUpActivity2.this,HomeFragment.class));


                }


             break;
            case ERROR:
                View parentLayout = findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar
                        .make(parentLayout, "No Internet Connection ", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }

                        });
                snackbar.setActionTextColor(Color.RED);
                View sbView = snackbar.getView();
                //  TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                //  textView.setTextColor(Color.YELLOW);
                snackbar.show();
               Log.d("SignUpActivity2","Failed in SignUp");
        }
    }
}
