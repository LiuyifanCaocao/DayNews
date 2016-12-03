package com.jredu.liuyifan.mygoalapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.activity.Show_News_Activity;
import com.jredu.liuyifan.mygoalapplication.adapter.SocietyAdapter;
import com.jredu.liuyifan.mygoalapplication.entity.SportNews;

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
public class VideoRecommendFragment extends Fragment {
    RequestQueue mRequestQueue;
    String url;
    ListView mListView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int page = 1;
    List<SportNews> list;
    boolean b ;
    final int HOME_RECOMMEND_FRAGMENT = 10001;
    final int SHOW_NEWS_ACTIVITY = -10001;

    public VideoRecommendFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video_recomment, container, false);
        mListView = (ListView) v.findViewById(R.id.list_view);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.black1);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        url = "http://apis.baidu.com/txapi/tiyu/tiyu?num=10&page=" + page + "&word=%E6%9E%97%E4%B8%B9";
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_json();
                page +=1;
            }
        });
        get_json();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Show_News_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ctime",list.get(position).getTime());
                bundle.putString("title",list.get(position).getTitle());
                bundle.putString("description",list.get(position).getDescription());
                bundle.putString("picUrl",list.get(position).getPicUrl());
                bundle.putString("url",list.get(position).getUrl());
                intent.putExtras(bundle);
                startActivityForResult(intent,HOME_RECOMMEND_FRAGMENT);
            }
        });
        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        mRequestQueue.cancelAll(getContext());
    }

    private void get_json() {
        final StringRequest mStringRequest = new StringRequest(
                "http://apis.baidu.com/txapi/tiyu/tiyu?num=10&page=" + page + "&word=%E6%9E%97%E4%B8%B9",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        get_list(response);
                        if (mSwipeRefreshLayout != null){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
                        if (mSwipeRefreshLayout != null){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("apikey", "3457f565f8ed592707432f9d44440f96");
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String s = new String(new String(response.data, "utf-8"));
                    return Response.success(s, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        mRequestQueue.add(mStringRequest);
    }

    private List<SportNews> get_list(String s) {
        list = new ArrayList<SportNews>();
        try {
            JSONObject obj = new JSONObject(s);
            String code = obj.getString("code");
            String msg = obj.getString("msg");
            JSONArray mJsonArray = obj.getJSONArray("newslist");
            for (int i = 0; i < mJsonArray.length(); i++) {
                JSONObject obj2 = mJsonArray.getJSONObject(i);
                SportNews sportNews = new SportNews(
                        obj2.getString("ctime"),
                        obj2.getString("title"),
                        obj2.getString("description"),
                        obj2.getString("picUrl"),
                        obj2.getString("url")
                );
                list.add(sportNews);
            }
            SocietyAdapter societyAdapter = new SocietyAdapter(getActivity(), list, mRequestQueue,b);
            mListView.setAdapter(societyAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void show_recommend() {
        get_json();
    }

    //设置知否显示图片
    public void show_or_not(String s){
        if ( s.equals("2")) {
            this.b = false;
        } else {
            this.b = true;
        }
    }
}
