package com.jredu.liuyifan.mygoalapplication.util;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by DELL on 2016/10/11.
 */
public class RotateView extends ImageView implements Runnable {

    private float mCurDegree = 0;//当前旋转角度
    public RotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        new Thread(this).start();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //设定旋转中心
        setPivotX(getMeasuredWidth()/2);
        setPivotY(getMeasuredHeight()/2);
    }

    @Override
    public void run() {
        while(true){
            setRotation(mCurDegree);
            mCurDegree += 5;
            postInvalidate();
            SystemClock.sleep(16);
        }
    }
}