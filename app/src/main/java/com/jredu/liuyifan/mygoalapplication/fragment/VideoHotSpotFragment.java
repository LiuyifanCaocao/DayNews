package com.jredu.liuyifan.mygoalapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
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
import com.jredu.liuyifan.mygoalapplication.adapter.JokeAdapter;
import com.jredu.liuyifan.mygoalapplication.entity.Joke;

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
public class VideoHotSpotFragment extends Fragment {
    RequestQueue mRequestQueue;
    String url;
    ListView mListView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int page = 1;
    List<Joke> list;
    boolean b;
    final int HOME_RECOMMEND_FRAGMENT = 10001;
    final int SHOW_NEWS_ACTIVITY = -10001;
    RotateAnimation animation;
    public VideoHotSpotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_video_hot_spot, container, false);
        mListView = (ListView) v.findViewById(R.id.list_view);
        list = new ArrayList<Joke>();
        mRequestQueue = Volley.newRequestQueue(getActivity());
        url = "http://apis.baidu.com/txapi/tiyu/tiyu?num=10&page=" + page + "&word=%E6%9E%97%E4%B8%B9";
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.black);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page += 1;
                get_json();
            }
        });
        /*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Show_News_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ctime",list.get(position).getId());
                bundle.putString("title",list.get(position).getTitle());
                bundle.putString("picUrl",list.get(position).getImg());
                intent.putExtras(bundle);
                startActivityForResult(intent,HOME_RECOMMEND_FRAGMENT);
            }
        });*/
        get_json();
        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        mRequestQueue.cancelAll(getContext());
    }

    private void get_json() {
        final StringRequest mStringRequest = new StringRequest(
                "http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_pic?page="+page,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        get_list(response);
                        if (mSwipeRefreshLayout != null) {
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

    private List<Joke> get_list(String s) {
        try {
            JSONObject obj = new JSONObject(s);
            JSONObject showapi_res_body = obj.getJSONObject("showapi_res_body");
            JSONArray mJsonArray = showapi_res_body.getJSONArray("contentlist");
            for (int i = 0; i < mJsonArray.length(); i++) {
                JSONObject obj2 = mJsonArray.getJSONObject(i);
                Joke joke = new Joke(
                        obj2.getString("ct"),
                        obj2.getString("title"),
                        obj2.getString("img"),
                        obj2.getString("type")
                );
                list.add(joke);
            }
            JokeAdapter jokeAdapter = new JokeAdapter(getActivity(),list,mRequestQueue,b);
            mListView.setAdapter(jokeAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void show_hot() {
        get_json();
    }

    public void show_or_not(String s){
        if ( s.equals("2")) {
            this.b = false;
        } else {
            this.b = true;
        }
    }

    //图片旋转
    public void img_rotate(){
        animation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(5000);//设定转一圈的时间
        animation.setRepeatCount(Animation.INFINITE);//设定无限循环
        animation.setRepeatMode(Animation.RESTART);
    }
}
