package com.jredu.liuyifan.mygoalapplication.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.activity.Show_News_Activity;
import com.jredu.liuyifan.mygoalapplication.adapter.PictureAdapter;
import com.jredu.liuyifan.mygoalapplication.entity.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePictureFragment extends Fragment {
    RequestQueue mRequestQueue = null;
    ListView mListView;
    List<Image> mList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    float mPosX ;
    float mPosY ;
    float mCurPosX ;
    float mCurPosY ;
    int i = 5;
    Toast mToast;
    final int HOME_RECOMMEND_FRAGMENT = 10001;
    final int SHOW_NEWS_ACTIVITY = -10001;

    private boolean isBottom() {

        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }
        return false;
    }


    private void get_touch(){
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosY - mPosY > 0
                                && (Math.abs(mCurPosY - mPosY) > 50)) {
                            //向下滑動
                            Toast.makeText(getActivity(),"down",Toast.LENGTH_SHORT).show();

                        } else if (mCurPosY - mPosY < 0
                                && (Math.abs(mCurPosY - mPosY) > 50)) {
                            //向上滑动
                            if (isBottom() == true){

                                Toast.makeText(getActivity(),"up",Toast.LENGTH_SHORT).show();
                            }

                        }

                        break;

                }
                return false;
            }
        });
    }
    public HomePictureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_picture, container, false);
        mListView = (ListView) v.findViewById(R.id.list_view);
        mRequestQueue  = Volley.newRequestQueue(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.black1);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                show_list();
                i += 1;
            }
        });
        show_list();
        //get_touch();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Show_News_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",mList.get(position).getUrl());
                intent.putExtras(bundle);
                startActivityForResult(intent,HOME_RECOMMEND_FRAGMENT);
            }
        });
        return v;
    }

    //网络请求
    private void doVolley(){
        final StringRequest mStringRequest = new StringRequest(
                "http://apis.baidu.com/txapi/mvtp/meinv?num="+i,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        get_image(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mSwipeRefreshLayout != null){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        tuSi("网络连接失败");
                        mToast.show();
                    }
                }
        ){
            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("apikey","3457f565f8ed592707432f9d44440f96");
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                try {String s = new String(new String(response.data,"utf-8"));
                    String jsonObject = new String(
                            new String(response.data, "UTF-8"));
                    return        Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (Exception je) {
                    return Response.error(new ParseError(je));
                }
            }
        };
        mStringRequest.setTag("get");
        mRequestQueue.add(mStringRequest);
    }

    //图片请求
    private List<Image> get_image(String s){
        mList = new ArrayList<Image>();
        try {
            JSONObject mObject = new JSONObject(s);
            String code = mObject.getString("code");
            final String msg = mObject.getString("msg");
            JSONArray jsonArray = mObject.getJSONArray("newslist");
            for (int i = 0 ; i < jsonArray.length() ; i++){
                final JSONObject object = jsonArray.getJSONObject(i);
                ImageRequest mImageRequest = new ImageRequest(
                        object.getString("picUrl"),
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                Image image;
                                try {
                                    image = new Image(
                                            object.getString("ctime"),
                                            object.getString("title"),
                                            object.getString("description"),
                                            object.getString("picUrl"),
                                            object.getString("url"),
                                            response
                                            );
                                    mList.add(image);
                                    if (mList.size()>4){
                                        PictureAdapter mPictureAdapter = new PictureAdapter(getActivity(),mList,mRequestQueue);
                                        mListView.setAdapter(mPictureAdapter);
                                        if (mSwipeRefreshLayout != null){
                                            mSwipeRefreshLayout.setRefreshing(false);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        300,
                        300,
                        Bitmap.Config.RGB_565,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (mSwipeRefreshLayout != null){
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }
                );
                mRequestQueue.add(mImageRequest);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mList;
    }

    //父类显示子类的数据
    public void show_list(){
        doVolley();
    }

    //吐司
    public void tuSi(String s){
        if (mToast == null){
            mToast = Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT);
        }else {
            mToast.setText(s);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

}
