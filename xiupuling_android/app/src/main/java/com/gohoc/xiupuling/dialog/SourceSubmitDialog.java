package com.gohoc.xiupuling.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.callback.SourceSubmitCallback;
import com.gohoc.xiupuling.utils.LogUtil;

/*
* 素材上传
* */
public class SourceSubmitDialog extends Dialog {

	private Context context;
	private int type;//1:(图片、视频、拍摄)   2:(视频)   3:(图片、拍摄)   4:（视频、水印）  5:（水印） 6：（图片、拍摄、背景音乐） 7：(背景音乐)
	private SourceSubmitCallback mSourceSubmitCallback;
	private Window window = null;

	private LinearLayout linear_tupian_layout,linear_shiping_layout,linear_yinping_layout,linear_shuiying_layout,linear_paishe_layout;
	private RelativeLayout relative_tupian_layout,relative_shiping_layout,relative_yinping_layout,relative_shuiying_layout,relative_paishe_layout;
	private ImageView iv_tupian,iv_shiping,iv_yinping,iv_shuiying,iv_paishe;
	private TextView tv_tupian,tv_shiping,tv_yinping,tv_shuiying,tv_paishe;

	private boolean tupianClick,shipingClick,yinpingClick,shuiyingClick,paisheClick;

	/**
	 * @param context
	 */
	public SourceSubmitDialog(Context context, SourceSubmitCallback sourceSubmitCallback, int mytype) {
		super(context, R.style.MyDialogStyleBottom);
		this.context = context;
		this.mSourceSubmitCallback=sourceSubmitCallback;
		this.type=mytype;
		setCanceledOnTouchOutside(true);
	}
	

	public void windowDeploy() {
		window = getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.gravity = Gravity.BOTTOM|Gravity.LEFT;
		WindowManager m = window.getWindowManager();   
		Display d = m.getDefaultDisplay();
		wl.width = d.getWidth();
		window.setAttributes(wl);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCanceledOnTouchOutside(true);
	    setContentView(R.layout.dialog_sourcesubmit_layout);
		windowDeploy();
		
		initView();
		initListener();
		//initData();
	}
	
	private void initView()
	{
		linear_tupian_layout=(LinearLayout)findViewById(R.id.linear_tupian_layout);
		linear_shiping_layout=(LinearLayout)findViewById(R.id.linear_shiping_layout);
		linear_yinping_layout=(LinearLayout)findViewById(R.id.linear_yinping_layout);
		linear_shuiying_layout=(LinearLayout)findViewById(R.id.linear_shuiying_layout);
		linear_paishe_layout=(LinearLayout)findViewById(R.id.linear_paishe_layout);

		relative_tupian_layout=(RelativeLayout) findViewById(R.id.relative_tupian_layout);
		relative_shiping_layout=(RelativeLayout)findViewById(R.id.relative_shiping_layout);
		relative_yinping_layout=(RelativeLayout)findViewById(R.id.relative_yinping_layout);
		relative_shuiying_layout=(RelativeLayout)findViewById(R.id.relative_shuiying_layout);
		relative_paishe_layout=(RelativeLayout)findViewById(R.id.relative_paishe_layout);

		iv_tupian=(ImageView)findViewById(R.id.iv_tupian);
		iv_shiping=(ImageView)findViewById(R.id.iv_shiping);
		iv_yinping=(ImageView)findViewById(R.id.iv_yinping);
		iv_shuiying=(ImageView)findViewById(R.id.iv_shuiying);
		iv_paishe=(ImageView)findViewById(R.id.iv_paishe);

		tv_tupian=(TextView)findViewById(R.id.tv_tupian);
		tv_shiping=(TextView)findViewById(R.id.tv_shiping);
		tv_yinping=(TextView)findViewById(R.id.tv_yinping);
		tv_shuiying=(TextView)findViewById(R.id.tv_shuiying);
		tv_paishe=(TextView)findViewById(R.id.tv_paishe);
	}

	private void initListener()
	{
		linear_tupian_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mSourceSubmitCallback!=null&&tupianClick){
					mSourceSubmitCallback.tupian();
				}
			}
		});

		linear_shiping_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mSourceSubmitCallback!=null&&shipingClick){
					mSourceSubmitCallback.shiping();
				}
			}
		});

		linear_yinping_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mSourceSubmitCallback!=null&&yinpingClick){
					mSourceSubmitCallback.yinping();
				}
			}
		});

		linear_shuiying_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mSourceSubmitCallback!=null&&shuiyingClick){
					mSourceSubmitCallback.shuiying();
				}
			}
		});

		linear_paishe_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mSourceSubmitCallback!=null&&paisheClick){
					mSourceSubmitCallback.paishe();
				}
			}
		});
	}

	//设置各种view，各种类型区分是否可以点击
	/*private void setMyView(int relative_putian,int iv_putian_bg,int tv_putian_color,int relative_shiping,int iv_shiping_bg,int tv_shiping_color,int relative_yinping,int iv_yinping_bg,int tv_yinping_color,int relative_shuiying,int iv_shuiying_bg,int tv_shuiying_color,int relative_paishe,int iv_paishe_bg,int tv_paishe_color)
	{
		relative_tupian_layout.setBackgroundResource(relative_putian);
		relative_shiping_layout.setBackgroundResource(relative_shiping);
		relative_yinping_layout.setBackgroundResource(relative_yinping);
		relative_shuiying_layout.setBackgroundResource(relative_shuiying);
		relative_paishe_layout.setBackgroundResource(relative_paishe);

		iv_tupian.setImageResource(iv_putian_bg);
		iv_shiping.setImageResource(iv_shiping_bg);
		iv_yinping.setImageResource(iv_yinping_bg);
		iv_shuiying.setImageResource(iv_shuiying_bg);
		iv_paishe.setImageResource(iv_paishe_bg);

		tv_tupian.setTextColor(context.getResources().getColor(tv_putian_color));
		tv_shiping.setTextColor(context.getResources().getColor(tv_shiping_color));
		tv_yinping.setTextColor(context.getResources().getColor(tv_yinping_color));
		tv_shuiying.setTextColor(context.getResources().getColor(tv_shuiying_color));
		tv_paishe.setTextColor(context.getResources().getColor(tv_paishe_color));
	}*/

	public void setMyClick(boolean b_tupian,boolean b_shiping,boolean b_yinping,boolean b_shuiying,boolean b_paishe)
	{
		LogUtil.d(""+b_tupian+"====:"+b_shiping+"====:"+b_yinping+"=====:"+b_shuiying+"=====:"+b_paishe);
		tupianClick=b_tupian;
		shipingClick=b_shiping;
		yinpingClick=b_yinping;
		shuiyingClick=b_shuiying;
		paisheClick=b_paishe;
	}

	@Override
	public void show() {
		super.show();
		showMyview();
	}

	private void showMyview()
	{
		//图片相关
		if(tupianClick){
			relative_tupian_layout.setBackgroundResource(R.drawable.white_stroke_5);
			iv_tupian.setImageResource(R.mipmap.btn_tupian);
			tv_tupian.setTextColor(context.getResources().getColor(R.color.withe));
		}else {
			relative_tupian_layout.setBackgroundResource(R.drawable.gray_stroke_5);
			iv_tupian.setImageResource(R.mipmap.btn_tupian_dis);
			tv_tupian.setTextColor(context.getResources().getColor(R.color.source_dis));
		}

		//视频相关
		if(shipingClick){
			relative_shiping_layout.setBackgroundResource(R.drawable.white_stroke_5);
			iv_shiping.setImageResource(R.mipmap.btn_shipin);
			tv_shiping.setTextColor(context.getResources().getColor(R.color.withe));
		}else {
			relative_shiping_layout.setBackgroundResource(R.drawable.gray_stroke_5);
			iv_shiping.setImageResource(R.mipmap.btn_shipin_dis);
			tv_shiping.setTextColor(context.getResources().getColor(R.color.source_dis));
		}

		//音乐相关
		if(yinpingClick){
			relative_yinping_layout.setBackgroundResource(R.drawable.white_stroke_5);
			iv_yinping.setImageResource(R.mipmap.btn_yinpin);
			tv_yinping.setTextColor(context.getResources().getColor(R.color.withe));
		}else {
			relative_yinping_layout.setBackgroundResource(R.drawable.gray_stroke_5);
			iv_yinping.setImageResource(R.mipmap.btn_yinpin_dis);
			tv_yinping.setTextColor(context.getResources().getColor(R.color.source_dis));
		}


		//图片水印
		if(shuiyingClick){
			relative_shuiying_layout.setBackgroundResource(R.drawable.white_stroke_5);
			iv_shuiying.setImageResource(R.mipmap.btn_tupian);
			tv_shuiying.setTextColor(context.getResources().getColor(R.color.withe));
		}else {
			relative_shuiying_layout.setBackgroundResource(R.drawable.gray_stroke_5);
			iv_shuiying.setImageResource(R.mipmap.btn_tupian_dis);
			tv_shuiying.setTextColor(context.getResources().getColor(R.color.source_dis));
		}

		//拍摄
		if(paisheClick){
			relative_paishe_layout.setBackgroundResource(R.drawable.white_stroke_5);
			iv_paishe.setImageResource(R.mipmap.btn_paizhao);
			tv_paishe.setTextColor(context.getResources().getColor(R.color.withe));
		}else {
			relative_paishe_layout.setBackgroundResource(R.drawable.gray_stroke_5);
			iv_paishe.setImageResource(R.mipmap.btn_paizhao_dis);
			tv_paishe.setTextColor(context.getResources().getColor(R.color.source_dis));
		}
	}

	/*private void initData()
	{
		switch (type){
			case 1://1:(图片、视频、拍摄)
				setMyView(R.drawable.white_stroke_5,R.mipmap.btn_tupian,R.color.withe,R.drawable.white_stroke_5,R.mipmap.btn_shipin,R.color.withe,R.drawable.gray_stroke_5,R.mipmap.btn_yinpin_dis,R.color.source_dis,R.drawable.gray_stroke_5,R.mipmap.btn_tupian_dis,R.color.source_dis,R.drawable.white_stroke_5,R.mipmap.btn_paizhao,R.color.withe);
				setMyClick(true,true,false,false,true);
				break;
			case 2://   2:(视频)
				setMyView(R.drawable.gray_stroke_5,R.mipmap.btn_tupian_dis,R.color.source_dis,R.drawable.white_stroke_5,R.mipmap.btn_shipin,R.color.withe,R.drawable.gray_stroke_5,R.mipmap.btn_yinpin_dis,R.color.source_dis,R.drawable.gray_stroke_5,R.mipmap.btn_tupian_dis,R.color.source_dis,R.drawable.gray_stroke_5,R.mipmap.btn_paizhao_dis,R.color.source_dis);
				setMyClick(false,true,false,false,false);
				break;
			case 3://  3:(图片、拍摄)
				setMyView(R.drawable.white_stroke_5,R.mipmap.btn_tupian,R.color.withe,R.drawable.gray_stroke_5,R.mipmap.btn_shipin_dis,R.color.source_dis,R.drawable.gray_stroke_5,R.mipmap.btn_yinpin_dis,R.color.source_dis,R.drawable.gray_stroke_5,R.mipmap.btn_tupian_dis,R.color.source_dis,R.drawable.white_stroke_5,R.mipmap.btn_paizhao,R.color.withe);
				setMyClick(true,false,false,false,true);
				break;
			case 4://   4:（视频、水印）
				setMyView(R.drawable.gray_stroke_5,R.mipmap.btn_tupian_dis,R.color.source_dis,R.drawable.white_stroke_5,R.mipmap.btn_shipin,R.color.withe,R.drawable.gray_stroke_5,R.mipmap.btn_yinpin_dis,R.color.source_dis,R.drawable.white_stroke_5,R.mipmap.btn_tupian,R.color.white,R.drawable.gray_stroke_5,R.mipmap.btn_paizhao_dis,R.color.source_dis);
				setMyClick(false,true,false,true,false);
				break;
			case 5://  5:（水印）
				setMyView(R.drawable.gray_stroke_5,R.mipmap.btn_tupian_dis,R.color.source_dis,R.drawable.gray_stroke_5,R.mipmap.btn_shipin_dis,R.color.source_dis,R.drawable.gray_stroke_5,R.mipmap.btn_yinpin_dis,R.color.source_dis,R.drawable.white_stroke_5,R.mipmap.btn_tupian,R.color.white,R.drawable.gray_stroke_5,R.mipmap.btn_paizhao_dis,R.color.source_dis);
				setMyClick(false,false,false,true,false);
				break;
			case 6:// 6：（图片、拍摄、背景音乐）
				setMyView(R.drawable.white_stroke_5,R.mipmap.btn_tupian,R.color.withe,R.drawable.gray_stroke_5,R.mipmap.btn_shipin_dis,R.color.source_dis,R.drawable.white_stroke_5,R.mipmap.btn_yinpin,R.color.withe,R.drawable.gray_stroke_5,R.mipmap.btn_tupian_dis,R.color.source_dis,R.drawable.white_stroke_5,R.mipmap.btn_paizhao,R.color.withe);
				setMyClick(true,false,true,false,true);
				break;
			case 7:// 7：(背景音乐)
				setMyView(R.drawable.gray_stroke_5,R.mipmap.btn_tupian_dis,R.color.source_dis,R.drawable.gray_stroke_5,R.mipmap.btn_shipin_dis,R.color.source_dis,R.drawable.white_stroke_5,R.mipmap.btn_yinpin,R.color.withe,R.drawable.gray_stroke_5,R.mipmap.btn_tupian_dis,R.color.source_dis,R.drawable.gray_stroke_5,R.mipmap.btn_paizhao_dis,R.color.source_dis);
				setMyClick(false,false,true,false,false);
				break;
		}
	}*/
}
