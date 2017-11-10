package com.gohoc.xiupuling.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.nanchen.compresshelper.CompressHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/8/9 0009
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class ImageUtils {


    public static String compress(Context context, String path) {
        int height = 0;
        int width = 0;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
//      通过这个Bitmap获得图片的宽高
        BitmapFactory.decodeFile(path, options);
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            height = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0);
            width = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0);
        } catch (IOException e) {
            e.printStackTrace();
            return path;
        }
        if (height == 0 || width == 0) {
            return path;
        }
        if (height > 1920 || width > 1920) {
            float scale = (float) height / (float) width;
            if (height > width) {
                height = 1920;
                width = Math.round(height / scale);
            } else {
                width = 1920;
                height = Math.round(width * scale);
            }
            String s = null;
            try {
                File oldFile = new File(path);
                File newFile = new CompressHelper.Builder(context)
                        .setMaxWidth(width)  // 默认最大宽度为720
                        .setMaxHeight(height) // 默认最大高度为960
                        .setQuality(100)    // 默认压缩质量为80
                        .setFileName(oldFile.getName()) // 设置你需要修改的文件名
                        .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .build()
                        .compressToFile(oldFile);
                s = newFile.getAbsolutePath();
                Bitmap bitmap1 = BitmapFactory.decodeFile(s);
//                long length = new File(path).length();
                Log.e("通过bitmap获取到的图片大小0", "width:" + bitmap1.getWidth() + "height" + bitmap1.getHeight() + s);
//                Bitmap bitmap = Bitmap.createScaledBitmap(bitmap1, width, height, true);
//                recycleBitmap(bitmap1);
//                Bitmap bitmap2 = compressImage(bitmap, 80);
//                recycleBitmap(bitmap);
//                int h = bitmap.getHeight();
//                int w = bitmap.getWidth();
//                s = saveCacheImage(context, new File(path).getName(), bitmap2);
//                long length1 = new File(path).length();
//                Log.e("通过bitmap获取到的图片大小1", "width:" + width + "height" + height + "大小==" + length1);
//                recycleBitmap(bitmap2);
            } catch (Exception e) {
                e.printStackTrace();
                return path;
            }
            return s;
        }
        return path;
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image, int options) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }


    /**
     * 回收bitmap图片
     *
     * @param bitmap
     */
    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }


    /**
     * 将Bimap保存为缓存文件中
     *
     * @param context
     * @param FileName
     * @param bitmap
     * @return 缓存文件的路径
     */
    public static String saveCacheImage(Context context, String FileName, Bitmap bitmap) {
        boolean isSuccess = true;
        File cacheDir = createCacheDir(context);
        File file = new File(cacheDir, FileName);
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            isSuccess = false;
        } catch (IOException e) {
            isSuccess = false;
        } catch (Exception e) {
            isSuccess = false;
        } finally {
            if (isSuccess) {
                return file.getPath();
            } else {
                return "";
            }
        }
    }


    /**
     * 创建缓存目录
     */
    public static File createCacheDir(Context act) {
        File appCacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//是否有SD卡
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                appCacheDir = act.getExternalCacheDir();
            }
            if (appCacheDir == null) {//有些手机需要通过自定义目录
                appCacheDir = new File(Environment.getExternalStorageDirectory(), "Android/data/" + act.getPackageName() + "/cache/");
            }
        } else {
            appCacheDir = act.getCacheDir();
        }
        if (!appCacheDir.exists()) {
            appCacheDir.mkdirs();
        }
        return appCacheDir;
    }
}
