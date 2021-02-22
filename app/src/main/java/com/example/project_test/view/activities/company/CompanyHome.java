package com.example.project_test.view.activities.company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.project_test.R;
import com.example.project_test.model.utils.Company;
import com.example.project_test.model.utils.notification.MyFirebaseMessageService;
import com.example.project_test.model.utils.notification.Token;
import com.example.project_test.view.activities.ProfileActivity;
import com.example.project_test.view.activities.user.AboutUsActivity;
import com.example.project_test.view.activities.user.HomeActivity;
import com.example.project_test.view.activities.user.LoginActivity;
import com.example.project_test.view.activities.user.MyorderActivity;
import com.example.project_test.view.activities.user.PaymentActivity;
import com.example.project_test.view.activities.user.PolicyActivity;
import com.example.project_test.view.activities.user.SettingActivity;
import com.example.project_test.view.adapter.ViewPagerCompanyAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompanyHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseUser user;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView userName_header;
    View header_view;
    Toolbar toolbar;
    TextView txtNameCompany;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        header_view= navigationView.getHeaderView(0);
        userName_header=header_view.findViewById(R.id.company_name_header);
        txtNameCompany=findViewById(R.id.company_name);
        ratingBar=findViewById(R.id.company_rating);
        toolbar=findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        actionBarDrawerToggle.syncState();
        user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Companies");
        databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Company company=snapshot.getValue(Company.class);
                userName_header.setText(company.getCompanyName());
                txtNameCompany.setText(company.getCompanyName());
                ratingBar.setRating(company.getRate());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        updateToken(MyFirebaseMessageService.getToken(this));
        ViewPager2 viewPager2=findViewById(R.id.viewpager2);
        viewPager2.setAdapter(new ViewPagerCompanyAdapter(getSupportFragmentManager(),getLifecycle()));
        TabLayout tabLayout=findViewById(R.id.tab_company);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }

    private void updateToken(String token) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Token");
        Token token1=new Token(token);
        databaseReference.child(user.getUid()).setValue(token1);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.profile:
                Intent intent=new Intent(CompanyHome.this, ProfileActivity.class);
                intent.putExtra("data","0");
                startActivity(intent);
                break;

            case R.id.order:
                startActivity(new Intent(CompanyHome.this, OrderCompanyActivity.class));

                break;
            case  R.id.payment:
                startActivity(new Intent(CompanyHome.this, PaymentActivity.class));
                break;
            case R.id.about_us:
                startActivity(new Intent(CompanyHome.this, AboutUsActivity.class));

                break;

            case  R.id.policy_data:
                startActivity(new Intent(CompanyHome.this, PolicyActivity.class));
                break;

            case R.id.setting:
                startActivity(new Intent(CompanyHome.this, SettingActivity.class));

                break;
            case R.id.logout:
                showDialogLogOut();
                break;


        }



        return true;
}
    private void showDialogLogOut() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Logout")
                .setMessage("Are you sure to logout ?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(CompanyHome.this, LoginActivity.class));
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

}