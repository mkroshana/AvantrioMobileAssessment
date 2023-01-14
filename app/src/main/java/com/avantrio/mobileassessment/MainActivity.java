package com.avantrio.mobileassessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity
{
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new UsersFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_users);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Fragment fragment = null;
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.nav_users:
                        fragment = new UsersFragment();
                        break;

                    case R.id.nav_settings:
                        fragment = new SettingsFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();

                return true;
            }
        });
    }
}