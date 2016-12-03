package com.jredu.liuyifan.mygoalapplication.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.adapter.LoginViewPagerAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    HomeHotSpotFragment mHomeHotSpotFragment;
    HomeRecommendFragment mHomeRecommendFragment;
    HomePictureFragment mHomePictureFragment;
    HomeBodyFragment mHomeBodyFragment;
    HomeSocietyFragment mHomeSocietyFragment;
    HomeVideoFragment mHomeVideoFragment;
    FragmentManager mFragmentManager;
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<Fragment> fragments;
    ArrayList<String> titles;



    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) v.findViewById(R.id.view_pager);
        mHomeHotSpotFragment = new HomeHotSpotFragment();
        mHomeBodyFragment = new HomeBodyFragment();
        mHomePictureFragment = new HomePictureFragment();
        mHomeSocietyFragment = new HomeSocietyFragment();
        mHomeVideoFragment = new HomeVideoFragment();
        mHomeRecommendFragment = new HomeRecommendFragment();
        fragments = new ArrayList<>();
        fragments.add(mHomeRecommendFragment);
        fragments.add(mHomeHotSpotFragment);
        fragments.add(mHomeVideoFragment);
        fragments.add(mHomeSocietyFragment);
        fragments.add(mHomePictureFragment);
        fragments.add(mHomeBodyFragment);
        titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("段子");
        titles.add("视频");
        titles.add("社会");
        titles.add("图片");
        titles.add("健康");
        viewPager.getChildAt(viewPager.getCurrentItem());
        mFragmentManager = getActivity().getSupportFragmentManager();
        LoginViewPagerAdapter loginViewPagerAdapter = new LoginViewPagerAdapter(mFragmentManager);
        loginViewPagerAdapter.setData(fragments);
        loginViewPagerAdapter.setTitles(titles);

        viewPager.setAdapter(loginViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }
    public void show_not(String s){
        mHomeRecommendFragment.show_or_not(s);
        mHomeSocietyFragment.show_or_not(s);
        mHomeHotSpotFragment.show_or_not(s);
        mHomeVideoFragment.show_or_not(s);
    }
}
