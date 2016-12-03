package com.jredu.liuyifan.mygoalapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.ant.liao.GifView;
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
public class HomeHotSpotFragment extends Fragment {
    RequestQueue mRequestQueue;
    String url;
    ListView mListView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int page = 1;
    List<Joke> list;
    boolean b;
    GifView mGifView;
    RelativeLayout relativeLayout;
    Toast mToast;


    public HomeHotSpotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home_hot_spot, container, false);
        mListView = (ListView) v.findViewById(R.id.list_view);
        list = new ArrayList<Joke>();
        //动图
        mGifView = (GifView) v.findViewById(R.id.gif);
        mGifView.setGifImage(R.raw.gif2);
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
                "http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text?page="+page+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        get_list(response);
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            mGifView.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tuSi("网络连接失败");
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
                        obj2.getString("text"),
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
