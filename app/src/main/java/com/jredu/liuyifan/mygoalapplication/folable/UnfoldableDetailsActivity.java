package com.jredu.liuyifan.mygoalapplication.folable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.entity.Image;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UnfoldableDetailsActivity extends AppCompatActivity {
    private View listTouchInterceptor;
    private View detailsLayout;
    private UnfoldableView unfoldableView;
    Painting painting;
    ImageView imageView;

    HashMap<String,String> fileList = new HashMap<>();
    File appDir = new File(Environment.getExternalStorageDirectory(),"Boohee");
    ArrayList<String> mStrings = new ArrayList<>();
    ArrayList<Image> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfoldable_details);
        imageView = (ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getFileList(appDir,fileList);
        for (int i = 0 ; i < mStrings.size() ; i++){
            get_img(mStrings.get(i));
        }

        ListView listView = Views.find(this, R.id.list_view);
        listView.setAdapter(new PaintingsAdapter(this,get_Painting()));

        listTouchInterceptor = Views.find(this, R.id.touch_interceptor_view);
        listTouchInterceptor.setClickable(false);

        detailsLayout = Views.find(this, R.id.details_layout);
        detailsLayout.setVisibility(View.INVISIBLE);

        unfoldableView = Views.find(this, R.id.unfoldable_view);

        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.unfold_glance);
        unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
                detailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
                detailsLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (unfoldableView != null
                && (unfoldableView.isUnfolded() || unfoldableView.isUnfolding())) {
            unfoldableView.foldBack();
        } else {
            super.onBackPressed();
        }
    }

    public void openDetails(View coverView, Painting painting) {
        final ImageView image = Views.find(detailsLayout, R.id.details_image);
        final TextView title = Views.find(detailsLayout, R.id.details_title);
        final TextView description = Views.find(detailsLayout, R.id.details_text);

        GlideHelper.loadPaintingImage(image, painting);
        title.setText(painting.getTitle());

        SpannableBuilder builder = new SpannableBuilder(this);
        builder
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append("drop-down").append(": ")
                .clearStyle()
                .append(painting.getYear()).append("\n")
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append("图片地址").append(": ")
                .clearStyle()
                .append(painting.getLocation());
        description.setText(builder.build());

        unfoldableView.unfold(coverView, detailsLayout);
    }
    //遍历文件
    public void getFileList(File path, HashMap<String, String> fileList){
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
        }
    }

    //获得类
    public ArrayList<Painting> get_Painting(){
        ArrayList<Painting> arrayList = new ArrayList<>();
        for (int i = 0; i<mList.size() ; i++){
            painting = new Painting(i,"点击查看大图","下拉折叠",mList.get(i).getPicUrl().toString());
            arrayList.add(painting);
        }
        return arrayList;
    }
}
