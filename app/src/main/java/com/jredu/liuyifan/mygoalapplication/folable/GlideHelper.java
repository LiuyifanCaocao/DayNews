package com.jredu.liuyifan.mygoalapplication.folable;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by DELL on 2016/10/19.
 */
public class GlideHelper {
    private GlideHelper() {}

    public static void loadPaintingImage(ImageView image, Painting painting) {
        Glide.with(image.getContext().getApplicationContext())
                .load(painting.getLocation())
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(image);
    }
}
