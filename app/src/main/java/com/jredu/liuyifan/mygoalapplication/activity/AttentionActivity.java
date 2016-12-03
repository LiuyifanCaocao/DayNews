package com.jredu.liuyifan.mygoalapplication.activity;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.jredu.liuyifan.mygoalapplication.adapter.Attention_add_Adapter;
import com.jredu.liuyifan.mygoalapplication.entity.SportNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttentionActivity extends AppCompatActivity {
    RadioButton mRadioButton1;
    RadioButton mRadioButton2;
    RadioButton mRadioButton3;
    RadioButton mRadioButton4;
    RadioButton mRadioButton5;
    RadioButton mRadioButton6;
    RadioButton mRadioButton7;
    RadioButton mRadioButton8;
    RadioButton mRadioButton9;
    RadioButton mRadioButton10;
    RadioButton mRadioButton11;
    RadioButton mRadioButton12;
    RadioButton mRadioButton13;
    RadioButton mRadioButton14;
    RadioButton mRadioButton15;
    RadioButton mRadioButton16;
    RadioGroup mRadioGroup;
    ImageView imageView_back;

    @ColorInt

    int page = 1;
    boolean b = false;
    GifView mGridView;
    List<SportNews> list;
    RequestQueue mRequestQueue;
    String url;
    Attention_add_Adapter attention_add_adapter;
    ListView mListView;
    Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
        list = new ArrayList<SportNews>();
        imageView_back = (ImageView) findViewById(R.id.back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRadioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        mRadioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        mRadioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        mRadioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        mRadioButton5 = (RadioButton) findViewById(R.id.radioButton5);
        mRadioButton6 = (RadioButton) findViewById(R.id.radioButton6);
        mRadioButton7 = (RadioButton) findViewById(R.id.radioButton7);
        mRadioButton8 = (RadioButton) findViewById(R.id.radioButton8);
        mRadioButton9 = (RadioButton) findViewById(R.id.radioButton9);
        mRadioButton10 = (RadioButton) findViewById(R.id.radioButton10);
        mRadioButton11 = (RadioButton) findViewById(R.id.radioButton11);
        mRadioButton12 = (RadioButton) findViewById(R.id.radioButton12);
        mRadioButton13 = (RadioButton) findViewById(R.id.radioButton13);
        mRadioButton14 = (RadioButton) findViewById(R.id.radioButton14);
        mRadioButton15 = (RadioButton) findViewById(R.id.radioButton15);
        mRadioButton16 = (RadioButton) findViewById(R.id.radioButton16);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButton1:
                        show_list(1);
                        break;
                    case R.id.radioButton2:
                        show_list(2);
                        break;
                    case R.id.radioButton3:
                        show_list(3);
                        break;
                    case R.id.radioButton4:
                        show_list(4);
                        break;
                    case R.id.radioButton5:
                        show_list(5);
                        break;
                    case R.id.radioButton6:
                        show_list(6);
                        break;
                    case R.id.radioButton7:
                        show_list(7);
                        break;
                    case R.id.radioButton8:
                        show_list(8);
                        break;
                    case R.id.radioButton9:
                        show_list(9);
                        break;
                    case R.id.radioButton10:
                        show_list(10);
                        break;
                    case R.id.radioButton11:
                        show_list(11);
                        break;
                    case R.id.radioButton12:
                        show_list(12);
                        break;
                    case R.id.radioButton13:
                        show_list(13);
                        break;
                    case R.id.radioButton14:
                        show_list(14);
                        break;
                    case R.id.radioButton15:
                        show_list(15);
                        break;
                    case R.id.radioButton16:
                        show_list(16);
                        break;
                }
            }
        });
        mListView = (ListView) findViewById(R.id.list_view);
        mGridView = (GifView) findViewById(R.id.gif);
        mGridView.setGifImage(R.raw.gif2);
        mRequestQueue = Volley.newRequestQueue(AttentionActivity.this);
        url = "http://apis.baidu.com/txapi/tiyu/tiyu?num=10&page=" + page+"&word=%E6%9E%97%E4%B8%B9";
        get_json(url);
    }

    //网络请求
    private void get_json(String url) {
        final StringRequest mStringRequest = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        b = false;
                        get_list(response);
                        mGridView.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mToast == null){
                            mToast = Toast.makeText(AttentionActivity.this, "网络求情失败", Toast.LENGTH_SHORT);
                        }else {
                            mToast.setText("网络求情失败");
                            mToast.setDuration(Toast.LENGTH_SHORT);
                        }
                        mToast.show();
                        b = true;
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

    //获得list列表
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
            if (b){
                attention_add_adapter.notifyDataSetChanged();
            }else {
                if (list.size() != 0){
                    attention_add_adapter = new Attention_add_Adapter(AttentionActivity.this, list, mRequestQueue,b);
                    mListView.setAdapter(attention_add_adapter);
                    b = true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //展示列表
    private void show_list(int i){
        list.clear();
        page = i;
        url = "http://apis.baidu.com/txapi/tiyu/tiyu?num=10&page=" + page+"&word=%E6%9E%97%E4%B8%B9";
        get_json(url);
    }

}
