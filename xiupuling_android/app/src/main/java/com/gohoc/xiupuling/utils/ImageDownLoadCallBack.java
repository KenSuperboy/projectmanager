package com.gohoc.xiupuling.utils;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by 28713 on 2017/2/20.
 */

public interface  ImageDownLoadCallBack {
    void onDownLoadSuccess(File file);
    void onDownLoadSuccess(Bitmap bitmap);
    void onDownLoadFailed();
}
