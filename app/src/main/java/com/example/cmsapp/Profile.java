package com.example.cmsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation2);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id == R.id.nav_profile){
                    return false;
                }
                else if(id==R.id.nav_add){
                    startActivity(new Intent(Profile.this,Add.class));
                    overridePendingTransition(0, 0);
                }
                else if(id==R.id.nav_home){
                    startActivity(new Intent(Profile.this,MainActivity.class));
                    overridePendingTransition(0, 0);
                }

                return true;
            }
        });
    }
}