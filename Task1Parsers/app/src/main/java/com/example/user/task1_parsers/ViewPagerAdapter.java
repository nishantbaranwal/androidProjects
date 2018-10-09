package com.example.user.task1_parsers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    Context context;
    public ViewPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context=context;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        if(position==0) {
            fragment=new JSONParserFragment();
        }
        else
            fragment=new XMLParserFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "JSON Parser";
        }
        else
            return "XML Parser";
    }
}
