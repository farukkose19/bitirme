package com.kose.faruk.mydictionary;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Lenovo on 5.12.2017.
 */

public class viewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments=new ArrayList<>();
    ArrayList<String> tabTitles=new ArrayList<>();
    public viewPagerAdapter (FragmentManager fragmentManager){
        super(fragmentManager);
    }

    public void addFragment(Fragment fragment,String titles){
        this.fragments.add(fragment);
        this.tabTitles.add(titles);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public CharSequence getPageTitle(int position){
        //return tabTitles.get(position);
        return null;
    }
}
