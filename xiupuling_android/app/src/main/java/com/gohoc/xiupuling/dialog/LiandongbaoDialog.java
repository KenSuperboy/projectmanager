package com.gohoc.xiupuling.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.callback.LiandongbaoCallback;

/*
* 联动包各种操作
* */
public class LiandongbaoDialog extends Dialog {

	private Context context;
	private LiandongbaoCallback mLiandongbaoCallback;
	private Window window = null;

	private TextView tv_add,tv_xuanze,tv_paixun,tv_clear;

	/**
	 * @param context
	 */
	public LiandongbaoDialog(Context context) {
		super(context, R.style.MyDialogStyleBottom);
		this.context = context;
		setCanceledOnTouchOutside(true);
	}


	public void setCallback(LiandongbaoCallback liandongbaoCallback)
	{
		this.mLiandongbaoCallback=liandongbaoCallback;
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
	    setContentView(R.layout.duopingliandong_editdialog_layout);
		windowDeploy();
		
		initView();
		initListener();
	}

	private String addTextString;
	public void setAddText(String addText)
	{
		this.addTextString=addText;
	}

	private void initView()
	{
		tv_add=(TextView)findViewById(R.id.tv_add);
		tv_xuanze=(TextView)findViewById(R.id.tv_xuanze);
		tv_paixun=(TextView)findViewById(R.id.tv_paixun);
		tv_clear=(TextView)findViewById(R.id.tv_clear);

		if(!TextUtils.isEmpty(addTextString)){
			tv_add.setText(addTextString);
		}
	}

	private void initListener()
	{
		tv_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mLiandongbaoCallback!=null){
					mLiandongbaoCallback.tianjia();
				}
			}
		});

		tv_xuanze.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mLiandongbaoCallback!=null){
					mLiandongbaoCallback.xuanze();
				}
			}
		});

		tv_paixun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mLiandongbaoCallback!=null){
					mLiandongbaoCallback.paixu();
				}
			}
		});

		tv_clear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mLiandongbaoCallback!=null){
					mLiandongbaoCallback.qingkong();
				}
			}
		});
	}
}
