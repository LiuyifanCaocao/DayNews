package com.jredu.liuyifan.mygoalapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.ant.liao.GifView;
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
public class HomeRecommendFragment extends Fragment {
    RequestQueue mRequestQueue;
    String url;
    ListView mListView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int page = 1;
    List<SportNews> list;
    boolean b ;
    GifView mGifView;
    final int HOME_RECOMMEND_FRAGMENT = 10001;
    final int SHOW_NEWS_ACTIVITY = -10001;
    RotateAnimation animation;
    ImageView mImageView_load;
    SocietyAdapter societyAdapter;
    Toast mToast;

    //图片旋转
    public void img_rotate(){
        animation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(5000);//设定转一圈的时间
        animation.setRepeatCount(Animation.INFINITE);//设定无限循环
        animation.setRepeatMode(Animation.RESTART);
        mImageView_load.startAnimation(animation);
    }

    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/download_test/";

    public HomeRecommendFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_recomment, container, false);
        list = new ArrayList<SportNews>();
        mListView = (ListView) v.findViewById(R.id.list_view);

        //动图
        mGifView = (GifView) v.findViewById(R.id.gif);
        mGifView.setGifImage(R.raw.gif2);
        mImageView_load = (ImageView) v.findViewById(R.id.load);
        img_rotate();
        mRequestQueue = Volley.newRequestQueue(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.black1);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        url = "http://apis.baidu.com/txapi/tiyu/tiyu?num=10&page=" + page + "&word=%E6%9E%97%E4%B8%B9";
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (list.size() > 1){
                    page +=1;
                    get_json();
                }
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
                        mGifView.setVisibility(View.GONE);
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

    private List<SportNews> get_list(String s) {
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
                list.add(0,sportNews);
            }
            mImageView_load.clearAnimation();
            mImageView_load.setVisibility(View.GONE);
            if (list.size()>10){
                societyAdapter.notifyDataSetChanged();
            }else {
                societyAdapter = new SocietyAdapter(getActivity(), list, mRequestQueue,b);
                mListView.setAdapter(societyAdapter);
            }
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























    /*//下载
    public void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(ALBUM_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(ALBUM_PATH + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }
    private Runnable saveFileRunnable = new Runnable(){
        @Override
        public void run() {
            try {
                saveFile(mBitmap, mFileName);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    };
    private Runnable connectNet = new Runnable(){
        @Override
        public void run() {
            try {
                String filePath = "http://img.my.csdn.net/uploads/201211/21/1353511891_4579.jpg";
                mFileName = "test.jpg";

                //以下是取得图片的两种方法
                //////////////// 方法1：取得的是byte数组, 从byte数组生成bitmap
                byte[] data = getImage(filePath);
                if(data!=null){
                    mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap
                }else{
                }
                ////////////////////////////////////////////////////////

                /*//******** 方法2：取得的是InputStream，直接从InputStream生成bitmap ***********//*
               *//* mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));*//*
                /*//********************************************************************//*

                // 发送消息，通知handler在主线程中更新UI
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    };
    public byte[] getImage(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return readStream(inStream);
        }
        return null;
    }
    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
*/

}
