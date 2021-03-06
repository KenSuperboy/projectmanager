package com.lzy.imagepicker.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.R;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.util.BitmapUtil;
import com.lzy.imagepicker.view.CropImageView;

import java.io.File;
import java.util.ArrayList;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ImageCropMultipleActivity extends ImageBaseActivity implements View.OnClickListener, CropImageView.OnBitmapSaveCompleteListener {

    private CropImageView mCropImageView;
    private Bitmap mBitmap;
    private boolean mIsSaveRectangle;
    private int mOutputX;
    private int mOutputY;
    private ArrayList<ImageItem> mImageItems;
    private ImagePicker imagePicker;
    private TextView tv_yuantu;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);
        Log.d("TAG","首次图片裁剪");
        imagePicker = ImagePicker.getInstance();

        //初始化View
        findViewById(R.id.btn_back).setOnClickListener(this);
        Button btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setText("裁剪上传");
        btn_ok.setOnClickListener(this);
        TextView tv_des = (TextView) findViewById(R.id.tv_des);
        tv_des.setText(getString(R.string.photo_crop));

        tv_yuantu=(TextView)findViewById(R.id.tv_yuantu);
        tv_yuantu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //原图上传
                if(number==mImageItems.size()-1){
                    mItemArrayList.add(mImageItems.get(number));

                    if(type==0){
                        Intent intent = new Intent();
                        intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, mItemArrayList);
                        setResult(RESULT_OK, intent);//单选不需要裁剪，返回数据
                        finish();
                    }else {
                        //从预览进来
                        Intent intent = new Intent();
                        intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, mItemArrayList);
                        setResult(RESULT_OK, intent);//单选不需要裁剪，返回数据
                        finish();
                    }

                }else {
                    mItemArrayList.add(mImageItems.get(number));
                    number++;
                    setCropImageView(mImageItems.get(number).path);
                }
            }
        });

        mCropImageView = (CropImageView) findViewById(R.id.cv_crop_image);
        mCropImageView.setOnBitmapSaveCompleteListener(this);

        //获取需要的参数
        mOutputX = imagePicker.getOutPutX();
        mOutputY = imagePicker.getOutPutY();
        mIsSaveRectangle = imagePicker.isSaveRectangle();
        mImageItems = imagePicker.getSelectedImages();
        String imagePath = mImageItems.get(0).path;
        Log.d("TAG","有多少张照片："+mImageItems.size()+":传入裁剪区域："+imagePath);

        for (int i=0;i<mImageItems.size();i++){
            Log.d("TAG","照片的详情："+mImageItems.get(i).path);
        }

        isExit(imagePath);

        mCropImageView.setFocusStyle(imagePicker.getStyle());
        mCropImageView.setFocusWidth(imagePicker.getFocusWidth());
        mCropImageView.setFocusHeight(imagePicker.getFocusHeight());

        //缩放图片
        setCropImageView(imagePath);

        type=getIntent().getIntExtra("type",0);
    }

    private void setCropImageView(String imagePath)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        options.inSampleSize = calculateInSampleSize(options, displayMetrics.widthPixels, displayMetrics.heightPixels);
        options.inJustDecodeBounds = false;



        mBitmap = BitmapFactory.decodeFile(imagePath, options);
        Log.d("TAG","mBitmap获得："+mBitmap);//这个地方存在手机适配问题
//      mCropImageView.setImageBitmap(mBitmap);
        //设置默认旋转角度
        mCropImageView.setImageBitmap(mCropImageView.rotate(mBitmap, BitmapUtil.getBitmapDegree(imagePath)));

//        mCropImageView.setImageURI(Uri.fromFile(new File(imagePath)));
    }

    private ArrayList<ImageItem> mItemArrayList=new ArrayList<>();
    private int number=0;

    //测试存不存在
    private void isExit(String pathString)
    {
        File file=new File(pathString);
        if(file.exists()){
            Log.d("TAG","文件存在");
        }else {
            Log.d("TAG","文件不存在");
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = width / reqWidth;
            } else {
                inSampleSize = height / reqHeight;
            }
        }
        return inSampleSize;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_back) {
            setResult(RESULT_CANCELED);
            finish();
        } else if (id == R.id.btn_ok) {
            mCropImageView.saveBitmapToFile(imagePicker.getCropCacheFolder(this), mOutputX, mOutputY, mIsSaveRectangle);
        }
    }

    @Override
    public void onBitmapSaveSuccess(File file) {
//        Toast.makeText(ImageCropActivity.this, "裁剪成功:" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();

        //裁剪后替换掉返回数据的内容，但是不要改变全局中的选中数据
        //mImageItems.remove(0);
        ImageItem imageItem = new ImageItem();
        imageItem.path = file.getAbsolutePath();
        //mImageItems.add(imageItem);

        if(number==mImageItems.size()-1){
            mItemArrayList.add(imageItem);

            Intent intent = new Intent();
            intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, mItemArrayList);
            setResult(RESULT_OK, intent);//单选不需要裁剪，返回数据
            finish();
        }else {
            mItemArrayList.add(imageItem);
            number++;
            setCropImageView(mImageItems.get(number).path);
        }

        /*Intent intent = new Intent();
        intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, mImageItems);
        setResult(ImagePicker.RESULT_CODE_ITEMS, intent);   //单选不需要裁剪，返回数据
        finish();*/
    }

    @Override
    public void onBitmapSaveError(File file) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCropImageView.setOnBitmapSaveCompleteListener(null);
        if (null != mBitmap && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }
}
