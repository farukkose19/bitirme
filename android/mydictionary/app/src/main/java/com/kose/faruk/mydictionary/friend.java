package com.kose.faruk.mydictionary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.kullanici;
import org.json.JSONArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.kose.faruk.mydictionary.getFriends;

/**
 * Created by Lenovo on 3.12.2017.
 */

public class friend extends AppCompatActivity implements sendmyfriends,remyfriend {

    SharedPreferences sharedpreferences;
    int userid;

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    viewPagerAdapter viewPagerAdapter;
    private int[] tabIcons = {
            R.drawable.party,
            R.drawable.users
    };
    addFriend addFriend;
    getFriends getFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend);
        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        userid=sharedpreferences.getInt("id",0);
        String username=sharedpreferences.getString("name","kullanici mevcut değil");
        if(userid==0){
            Intent gec = new Intent(friend.this, Login.class);
            startActivity(gec);
        }

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        viewPagerAdapter=new viewPagerAdapter(getSupportFragmentManager());
        getFriends=new getFriends();
        addFriend=new addFriend();

        Bundle bundle=new Bundle();
        bundle.putInt("userid",userid);
        getFriends.setArguments(bundle);
        addFriend.setArguments(bundle);

        viewPagerAdapter.addFragment(getFriends,"get friend");
        viewPagerAdapter.addFragment(addFriend,"add friend");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    @Override
    public void send(List<kullanici> friends) {

        addFriend.myfriends(friends);
    }

    @Override
    public void refresh(kullanici k) {
        getFriends.yeniArkGet(k);
    }
}