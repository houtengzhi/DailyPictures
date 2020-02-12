package com.yechy.dailypic.util;

import android.content.Context;
import android.widget.ImageView;

import com.yechy.dailypic.R;

/**
 * Created by cloud on 2019-12-20.
 */
public class ImageLoader {

    private static final String TAG = "ImageLoader";

    public static void load(Context context, ImageView imageView, String url) {
        L.d(TAG, "load(), url=%s", url);
        GlideApp.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(imageView);
    }
}
