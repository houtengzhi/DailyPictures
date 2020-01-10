package com.yechy.dailypic.util;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by cloud on 2019-12-20.
 */
public class ImageLoader {

    public static void load(Context context, ImageView imageView, String url) {
        GlideApp.with(context)
                .load(url)
                .into(imageView);
    }
}
