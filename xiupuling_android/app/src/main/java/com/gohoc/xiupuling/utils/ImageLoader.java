package com.gohoc.xiupuling.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/6/14 0014
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class ImageLoader implements com.lzy.imagepicker.loader.ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)//
                .load(Uri.fromFile(new File(path)))//
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
