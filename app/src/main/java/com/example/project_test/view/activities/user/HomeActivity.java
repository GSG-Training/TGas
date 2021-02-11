package com.example.project_test.view.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.project_test.R;
import com.example.project_test.view.activities.ProfileActivity;
import com.example.project_test.view.adapter.CompanyAdapter;
import com.example.project_test.model.utils.Company;
import com.example.project_test.model.utils.Users;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   DrawerLayout drawerLayout;
   Toolbar toolbar;
   RecyclerView recyclerView;
   List<Company> companies=new ArrayList<>();
   NavigationView navigationView;
   TextView userName_header;
   View header_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUiComponent();
    } //end OnCreate

    private void initUiComponent() {
        recyclerView=findViewById(R.id.recycle_company);
        drawerLayout=findViewById(R.id.drawer_home);
        navigationView=findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        header_view= navigationView.getHeaderView(0);
        userName_header=header_view.findViewById(R.id.user_name_header);
        toolbar=findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        actionBarDrawerToggle.syncState();
        CompanyAdapter companyAdapter=new CompanyAdapter(this,companies);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Companies");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    companies.add(postSnapshot.getValue(Company.class));
                    companyAdapter.notifyDataSetChanged();
                    Log.d("SIZE", String.valueOf(companies.size()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(companyAdapter);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        Log.d("UID",user.getUid());
        //show UserInformation
        DatabaseReference userReference=FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null) {
                    Users user = snapshot.getValue(Users.class);
                    userName_header.setText(user.getFullName());
                    Log.d("name",user.getFullName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showDialogLogOut() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Logout")
                .setMessage("Are you sure to logout ?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
      AlertDialog alertDialog=builder.create();
      alertDialog.show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                break;

            case R.id.order:
                startActivity(new Intent(HomeActivity.this, MyorderActivity.class));

                break;
            case  R.id.payment:
                startActivity(new Intent(HomeActivity.this, PaymentActivity.class));
                break;
            case R.id.about_us:
                startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));

                break;

            case  R.id.policy_data:
                startActivity(new Intent(HomeActivity.this, PolicyActivity.class));
                break;

            case R.id.setting:

                break;
            case R.id.logout:
                showDialogLogOut();
                break;


        }



        return true;
    }
}