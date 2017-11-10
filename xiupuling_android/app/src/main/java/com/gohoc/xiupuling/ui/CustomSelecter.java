package com.gohoc.xiupuling.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 28713 on 2017/3/24.
 */


public class CustomSelecter extends View {
    private Context context;
    private Paint mPaint;  //画笔
    private Paint mPaint1;  //画笔
    private int wSpecMode;
    private int wSpecSize;
    private int hSpecMode;
    private int hSpecSize;
    private int centerX;
    private int centerY;
    private Path path;
    private int bgSize = 30;
    private int bgLineHeight = 30;
    private List<Integer> pointList = new ArrayList<>();
    private int count = 4;
    private int postion = 1;
    private Canvas canvas;
    private OnCilckListener onCilckListener;


    public interface OnCilckListener {
        void onItemCilck(int postion);
    }

    private Point point1, point2, point3, point4;


    private int bk_color = ContextCompat.getColor(getContext(), R.color.colorLoginbkDf);
    private int blue = ContextCompat.getColor(getContext(), R.color.gallery_blue);


    public CustomSelecter(Context context) {
        this(context, null);
    }

    public CustomSelecter(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSelecter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPaint();
    }


    private void initPaint() {

        //初始化画笔并打开抗锯齿模式
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            /*
         * 设置画笔样式为描边并填充
         *
         * 画笔样式分三种：
         * 1.Paint.Style.STROKE：描边(圆环嘛)
         * 2.Paint.Style.FILL_AND_STROKE：描边并填充
         * 3.Paint.Style.FILL：填充
         */
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        // 设置画笔颜色
        mPaint.setColor(bk_color);
            /*
         * 设置描边的粗细，单位：像素px
         * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
         */
        //  mPaint.setStrokeWidth(10);


        //初始化画笔并打开抗锯齿模式
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
            /*
         * 设置画笔样式为描边并填充
         *
         * 画笔样式分三种：
         * 1.Paint.Style.STROKE：描边(圆环嘛)
         * 2.Paint.Style.FILL_AND_STROKE：描边并填充
         * 3.Paint.Style.FILL：填充
         */
        mPaint1.setStyle(Paint.Style.FILL_AND_STROKE);
        // 设置画笔颜色
        mPaint1.setColor(blue);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        draws(canvas);
    }

    private void draws(Canvas canvas) {
        if (canvas == null)
            return;
        int wDiff = (wSpecSize - 100 - count * bgSize * 2) / (count - 1);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        // 设置画笔颜色
        mPaint.setColor(bk_color);
        Logger.e("wSpecSize: " + wSpecSize + "");
        Logger.e("wDiff:  " + wDiff + "");
        Logger.e("xxxx:  " + (wSpecSize - 100 - count * bgSize) + "");

        int x = 0;
        wDiff = (wSpecSize - bgSize * 2) / (count - 1);
        canvas.drawCircle(centerX + bgSize, centerY, bgSize, mPaint);
        canvas.drawCircle(centerX + bgSize + wDiff, centerY, bgSize, mPaint);
        canvas.drawCircle(centerX + bgSize + wDiff * 2, centerY, bgSize, mPaint);
        canvas.drawCircle(wSpecSize - bgSize * 2 + 5, centerY, bgSize, mPaint);
        point1 = new Point(centerX + bgSize, centerY);
        point2 = new Point(centerX + bgSize + wDiff, centerY);
        point3 = new Point(centerX + bgSize + wDiff * 2, centerY);
        point4 = new Point(wSpecSize - bgSize * 2 + 5, centerY);


        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(30);

        path = new Path();
        path.moveTo(centerX + bgSize, centerY);
        path.lineTo(wSpecSize - bgSize, centerY);
        path.close();
        Logger.e(centerX + "  " + centerY);
        canvas.drawPath(path, mPaint);


        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(25);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("海报", centerX + bgSize, centerY - 80, mPaint);
        canvas.drawText("数码相册", centerX + bgSize + wDiff, centerY - 80, mPaint);
        canvas.drawText("宣传片", centerX + bgSize + wDiff * 2, centerY - 80, mPaint);
        canvas.drawText("图中嵌入视频", wSpecSize - 100, centerY - 80, mPaint);
        Bitmap pic = BitmapFactory.decodeResource(getResources(), R.mipmap.blue12);

        if (postion == 1) {
            canvas.drawText("海报", centerX + bgSize, centerY - 80, mPaint);
            canvas.drawBitmap(pic, point1.x - bgSize, centerY - (pic.getHeight() / 2), mPaint1);
        } else if (postion == 2) {
            canvas.drawText("数码相册", centerX + bgSize + wDiff, centerY - 80, mPaint);
            canvas.drawBitmap(pic, point2.x - bgSize, centerY - (pic.getHeight() / 2), mPaint1);
        } else if (postion == 3) {
            canvas.drawText("宣传片", centerX + bgSize + wDiff * 2, centerY - 80, mPaint);
            canvas.drawBitmap(pic, point3.x - bgSize, centerY - (pic.getHeight() / 2), mPaint1);
        } else {
            canvas.drawText("图中嵌入视频", wSpecSize - 100, centerY - 80, mPaint);
            canvas.drawBitmap(pic, point4.x - bgSize, centerY - (pic.getHeight() / 2), mPaint1);
        }
        ;

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        centerX = bgSize;
        centerY = hSpecSize / 2 + 30;

        Logger.i("onMeasure:" + wSpecMode + " " + wSpecSize + "  " + hSpecMode + " " + hSpecSize);
        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, 300);
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, hSpecSize);
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize, 300);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                double x = event.getX();
                double y = event.getY();

                if (x > point1.x - bgSize && x < point1.x + bgSize * 2) {
                    postion = 1;

                } else if (x > point2.x - bgSize && x < point2.x + bgSize * 2) {
                    postion = 2;

                } else if (x > point3.x - bgSize && x < point3.x + bgSize * 2) {
                    postion = 3;

                } else if (x > point4.x - bgSize && x < point4.x + bgSize * 2) {
                    postion = 4;

                }
                invalidate();
                if (null != onCilckListener)
                    onCilckListener.onItemCilck(postion);
                // draws(canvas);
                Logger.e(postion + "");
                // Logger.e(r+"   "+r1+" "+r2+" "+r3+" "+r4+" "+"  :: "+postion);

                Logger.e(x + "  " + y);
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    public OnCilckListener getOnCilckListener() {
        return onCilckListener;
    }

    public void setOnCilckListener(OnCilckListener onCilckListener) {
        this.onCilckListener = onCilckListener;
    }
}
