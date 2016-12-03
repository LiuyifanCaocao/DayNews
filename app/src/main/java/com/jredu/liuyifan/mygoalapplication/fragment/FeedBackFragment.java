package com.jredu.liuyifan.mygoalapplication.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.adapter.TalkingAdapter;
import com.jredu.liuyifan.mygoalapplication.entity.Talking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedBackFragment extends Fragment {
    ListView mListView;
    String s;
    String picUrl;
    String name;
    String time;
    ArrayList<Talking> list;
    RequestQueue mRequestQueue;
    Bitmap bitmap;
    TalkingAdapter talkingAdapter;


    public FeedBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed_back, container, false);
        mListView = (ListView) v.findViewById(R.id.list_view);

        mRequestQueue = Volley.newRequestQueue(getActivity());
        list = new ArrayList<>();
        Talking talking = new Talking(null,"客服","你好","2016-10-20","0");
        list.add(talking);
        talkingAdapter = new TalkingAdapter(getActivity(),list);
        mListView.setAdapter(talkingAdapter);
        return v;
    }

    //获得输入的数据
    public void get_text(String s){
        this.s = s;
        get_time();
        Talking talking = new Talking(bitmap,name,s,time,"1");
        list.add(talking);
        talkingAdapter.notifyDataSetChanged();
    }
    //获得用户信息
    public void get_user(String picUrl ,String name){
        this.picUrl = picUrl;
        this.name = name;
        get_img();
    }

    //获得图片
    public void get_img(){
        ImageRequest imageRequest = new ImageRequest(
                picUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        bitmap = response;
                    }
                },
                0,
                0,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        mRequestQueue.add(imageRequest);
    }

    //获得时间
    public void get_time(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd  HH:mm");
        Date date = new Date(System.currentTimeMillis());
        time = simpleDateFormat.format(date);
    }

}
