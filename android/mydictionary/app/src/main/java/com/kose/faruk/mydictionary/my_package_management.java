package com.kose.faruk.mydictionary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Muhammed Faruk KOSE on 2.04.2018.
 */

public class my_package_management extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    int userid;

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    viewPagerAdapter viewPagerAdapter;
    private int[] tabIcons = {
            R.drawable.download,
            R.drawable.upload
    };
    my_friends_package my_friends_package;
    my_getting_package my_getting_package;
    int friendID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend);
        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        userid=sharedpreferences.getInt("id",0);
        String username=sharedpreferences.getString("name","kullanici mevcut değil");
        if(userid==0){
            Intent gec = new Intent(my_package_management.this, Login.class);
            startActivity(gec);
        }
        Intent mIntent = getIntent();
        friendID= (int) mIntent.getExtras().getSerializable("friendID");
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        viewPagerAdapter=new viewPagerAdapter(getSupportFragmentManager());
        my_friends_package=new my_friends_package();
        my_getting_package=new my_getting_package();

        Bundle bundle=new Bundle();
        bundle.putInt("friendID",friendID);
        bundle.putInt("userid",userid);
        my_friends_package.setArguments(bundle);
        my_getting_package.setArguments(bundle);

        viewPagerAdapter.addFragment(my_friends_package,"aa");
        viewPagerAdapter.addFragment(my_getting_package,"add friend");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[1]);
        tabLayout.getTabAt(1).setIcon(tabIcons[0]);
    }
}
