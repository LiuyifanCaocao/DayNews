package com.jredu.liuyifan.mygoalapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by DELL on 2016/9/26.
 */
public class LoginViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> datas;
    ArrayList<String> titles;

    public LoginViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(ArrayList<Fragment> datas) {
        this.datas = datas;
    }
    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? null : titles.get(position);
    }
}
