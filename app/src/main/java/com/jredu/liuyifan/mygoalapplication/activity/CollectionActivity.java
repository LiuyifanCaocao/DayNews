package com.jredu.liuyifan.mygoalapplication.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.adapter.PhotoAdapter;
import com.jredu.liuyifan.mygoalapplication.entity.Image;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CollectionActivity extends AppCompatActivity {
    GridView mGridView;
    TextView mTextView;
    TextView getmTextView_edit;
    File appDir;
    HashMap<String,String> fileList;
    ArrayList<String> mStrings;
    ArrayList<Image> mList;
    PhotoAdapter photoAdapter;
    Toast mToast;
    ScaleAnimation scaleAnimation;
    TextView mTextView_add1;
    TextView mTextView_add2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        mTextView_add1 = (TextView) findViewById(R.id.text_view_add1);
        mTextView_add2 = (TextView) findViewById(R.id.text_view_add2);

        //路径
        appDir = new File(Environment.getExternalStorageDirectory(),"Boohee");
        mList = new ArrayList<>();
        mStrings = new ArrayList<>();
        mGridView = (GridView) findViewById(R.id.grid_view);
        getmTextView_edit = (TextView) findViewById(R.id.edit);
        mTextView = (TextView) findViewById(R.id.text);
        fileList = new HashMap<>();
        getFileList(appDir,fileList);
        show_img();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        show_big_img();
        getmTextView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getmTextView_edit.getText().equals("编辑")){
                    getmTextView_edit.setText("编辑中...");
                    getmTextView_edit.setTextColor(Color.GREEN);
                    delete();
                }else {
                    getmTextView_edit.setText("编辑");
                    getmTextView_edit.setTextColor(Color.GRAY);
                    show_big_img();
                }
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }


    //图片放大
    public void show_big_img(){
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                scaleAnimation = new ScaleAnimation(1f,1.2f,1f,1.2f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                scaleAnimation.setDuration(500);
                scaleAnimation.setFillAfter(true);
                view.startAnimation(scaleAnimation);
            }
        });
    }

    //删除图片
    public void delete(){
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(CollectionActivity.this)
                        .setTitle("删除选中图片")
                        .setMessage("确定删除选中图片")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                File file = new File(mList.get(position).getPicUrl());
                                if (file.isFile() && file.exists()) {
                                    file.delete();
                                    mList.remove(position);
                                    photoAdapter.notifyDataSetChanged();
                                }else {
                                    if (mToast != null){
                                        mToast = Toast.makeText(CollectionActivity.this,"删除失败",Toast.LENGTH_SHORT);
                                    }else {
                                        mToast.setText("删除失败");
                                        mToast.setDuration(Toast.LENGTH_SHORT);
                                    }
                                    mToast.show();
                                }
                            }
                        })
                        .show();
            }
        });

    }

    //遍历文件
    private void getFileList(File path, HashMap<String, String> fileList){
        //如果是文件夹的话
        if(path.isDirectory()){
            //返回文件夹中有的数据
            File[] files = path.listFiles();
            //先判断下有没有权限，如果没有权限的话，就不执行了
            if(null == files){
                return;
            }

            for(int i = 0; i < files.length; i++){
                getFileList(files[i], fileList);
            }
        }
        //如果是文件的话直接加入
        else{
            //进行文件的处理
            String filePath = path.getAbsolutePath();
            mStrings.add(filePath);
        }
    }

    //读取
    public void get_img(String file_Path){
        String s = Environment.getExternalStorageState();
        if (s.equals(Environment.MEDIA_MOUNTED)){
            if (appDir.exists()){
                File file = new File(file_Path);
                BufferedInputStream bis = null;
                try {
                    FileInputStream fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    Image image = new Image(file_Path,bitmap);
                    mList.add(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            Toast.makeText(getApplication(),"false",Toast.LENGTH_SHORT).show();
        }
    }

    //显示图片
    public void show_img(){
        for (int i = 0 ; i < mStrings.size() ; i++){
            get_img(mStrings.get(i));
        }
        if (mStrings.size()>0){
            mTextView_add1.setVisibility(View.GONE);
            mTextView_add2.setVisibility(View.GONE);
        }
        photoAdapter = new PhotoAdapter(CollectionActivity.this,mList);
        mGridView.setAdapter(photoAdapter);
    }
}
