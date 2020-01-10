package com.yechy.dailypic.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;
import com.yechy.dailypic.R;
import com.yechy.dailypic.repository.PictureInfo;
import com.yechy.dailypic.util.ImageLoader;

import java.util.List;

/**
 * Created by cloud on 2019-12-20.
 */
public class GalleryPagerAdaper extends PagerAdapter {

    private Context mContext;
    private List<PictureInfo> pictureInfoList;

    public GalleryPagerAdaper(Context context, List<PictureInfo> pictureInfoList) {
        this.mContext = context;
        this.pictureInfoList = pictureInfoList;
    }

    @Override
    public int getCount() {
        if (pictureInfoList != null) {
            return pictureInfoList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_gallery_item, container, false);
        PhotoView photoView = view.findViewById(R.id.pv_gallery_item_image);
        if (pictureInfoList != null && pictureInfoList.size() > position) {
            PictureInfo pictureInfo = pictureInfoList.get(position);
            ImageLoader.load(mContext, photoView, pictureInfo.getUrl());
        }
        container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
