package com.jredu.liuyifan.mygoalapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jredu.liuyifan.mygoalapplication.R;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    Intent intent;


    private Tencent mTencent;
    private String APP_ID = "222222";
    private IUiListener loginListener;
    private String SCOPE = "all";
    private IUiListener userInfoListener;

    Bundle bundle;
    String nickname;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this,HomeActivity.class);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };

        timer.schedule(timerTask,1000*2);
    }
    /*//初始化QQ登录分享的需要的资源
    private void initQqLogin() {
        mTencent = Tencent.createInstance(APP_ID, this);
        //创建QQ登录回调接口
        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                //登录成功后调用的方法

                JSONObject jo = (JSONObject) o;
                Log.e("COMPLETE:", jo.toString());
                String openID;
                try {
                    openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
                //登录失败后回调该方法
                Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                Log.e("LoginError:", uiError.toString());
            }

            @Override
            public void onCancel() {
                //取消登录后回调该方法
                Toast.makeText(MainActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
            }
        };

        userInfoListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                if (o == null) {
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) o;
                    Log.e("JO:", jo.toString());
                    int ret = jo.getInt("ret");
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");
                    String figureurl = jo.getString("figureurl");
                    Toast.makeText(MainActivity.this, "你好，" + nickName, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                }

                JSONObject json = (JSONObject) o;
                // 昵称

                try {
                    nickname = ((JSONObject) o).getString("nickname");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // 头像

                try {
                    path = json.getString("figureurl_qq_1");
                   *//* MyImgThread imgThread = new MyImgThread(path);
                    Thread thread = new Thread(imgThread);
                    thread.start();*//*
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        };
    }

    public void qqLogin() {
        initQqLogin();
        mTencent.login(this, SCOPE, loginListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constants.REQUEST_LOGIN) {
            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
                Tencent.handleResultData(data, loginListener);
                final UserInfo info = new UserInfo(this, mTencent.getQQToken());
                info.getUserInfo(userInfoListener);
                Timer timer = new Timer();
            }

        }

    }*/

}
/**
 * @author mrsimple
 */
/*public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh);

        // 模拟一些数据
        final List<String> datas = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            datas.add("item - " + i);
        }

        // 构造适配器
        final BaseAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                datas);
        // 获取listview实例
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);

            }
        });
        listView.setAdapter(adapter);

        // 获取RefreshLayout实例
        final RefreshLayout myRefreshListView = (RefreshLayout)
                findViewById(R.id.swipe_layout);

        // 设置下拉刷新监听器
        myRefreshListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "refresh", Toast.LENGTH_SHORT).show();

                myRefreshListView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 更新数据
                        datas.add("a");
                        adapter.notifyDataSetChanged();
                        // 更新完后调用该方法结束刷新
                        myRefreshListView.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        // 加载监听器
        myRefreshListView.setOnLoadListener(new RefreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {

                Toast.makeText(MainActivity.this, "load", Toast.LENGTH_SHORT).show();

                //延迟
                myRefreshListView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        datas.add("a");
                        adapter.notifyDataSetChanged();
                        // 加载完后调用该方法
                        myRefreshListView.setLoading(false);
                    }
                }, 1500);

            }
        });
    }

}*/
