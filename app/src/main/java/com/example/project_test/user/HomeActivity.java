package com.example.project_test.user;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project_test.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
   DrawerLayout drawerLayout;
   Toolbar toolbar;
   FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout=findViewById(R.id.drawer_home);
        toolbar=findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        actionBarDrawerToggle.syncState();
        findViewById(R.id.img_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                finish();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}