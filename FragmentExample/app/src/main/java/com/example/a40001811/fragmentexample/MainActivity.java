package com.example.a40001811.fragmentexample;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
        ViewPagerAdapter adapter=new ViewPagerAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tableLayout=(TabLayout)findViewById(R.id.sliding_tabs);
        tableLayout.setupWithViewPager(viewPager);
    }
}
