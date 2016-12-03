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
import com.jredu.liuyifan.mygoalapplication.entity.SportNews;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {
    VideoBodyFragment videoBodyFragment;
    VideoPictureFragment videoPictureFragment;
    VideoHotSpotFragment videoHotSpotFragment;
    VideoRecommendFragment videoRecommendFragment;
    VideoSocietyFragment videoSocietyFragment;

    ArrayList<Fragment> fragments;
    ArrayList<String> titles;

    TabLayout tabLayout;
    ViewPager viewPager;

    FragmentManager fragmentManager;

    boolean b;
    List<SportNews> list;
    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) v.findViewById(R.id.view_pager);

        videoBodyFragment = new VideoBodyFragment();
        videoPictureFragment = new VideoPictureFragment();
        videoHotSpotFragment = new VideoHotSpotFragment();
        videoRecommendFragment = new VideoRecommendFragment();
        videoSocietyFragment = new VideoSocietyFragment();

        fragments = new ArrayList<>();
        fragments.add(videoRecommendFragment);
        fragments.add(videoHotSpotFragment);
        fragments.add(videoPictureFragment);
        fragments.add(videoSocietyFragment);
        fragments.add(videoBodyFragment);

        titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("热点");
        titles.add("图片");
        titles.add("健康");
        titles.add("社会");
        viewPager.getChildAt(viewPager.getCurrentItem());

        fragmentManager = getActivity().getSupportFragmentManager();
        LoginViewPagerAdapter loginViewPagerAdapter = new LoginViewPagerAdapter(fragmentManager);
        loginViewPagerAdapter.setData(fragments);
        loginViewPagerAdapter.setTitles(titles);

        viewPager.setAdapter(loginViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        return v;
    }


    public void show_or_not(String s){
        if ( s.equals("2")) {
            this.b = false;
        } else {
            this.b = true;
        }
    }

}
