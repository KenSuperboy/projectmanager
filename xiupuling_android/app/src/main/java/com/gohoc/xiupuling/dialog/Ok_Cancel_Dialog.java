package com.gohoc.xiupuling.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.callback.Callback_Ok_Cancel;
/*
* 确定取消按钮
* */
public class Ok_Cancel_Dialog extends Dialog {
	private Window mWindow;
	private Context context;
	private TextView tv_top_title,tv_title,btn_ok,btn_cancel;
	private String topTitleString,titleString;
	public Callback_Ok_Cancel mCallback_ok_cancel;

	public Ok_Cancel_Dialog(Context context, String titleString) {
		super(context);
		this.context = context;
		this.titleString=titleString;
		mWindow = this.getWindow();
		mWindow.setBackgroundDrawable(new ColorDrawable(0));
		setCancelable(false);
		setCanceledOnTouchOutside(false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mWindow.setContentView(R.layout.dialog_ok_cancel);
	}

	public void setTopTitlt(String my)
	{
		topTitleString=my;
	}

	public void setCallback(Callback_Ok_Cancel dialogClick)
	{
		this.mCallback_ok_cancel=dialogClick;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_ok_cancel);

		tv_top_title=(TextView)findViewById(R.id.tv_top_title);
		if(!TextUtils.isEmpty(topTitleString)){
			tv_top_title.setVisibility(View.VISIBLE);
			tv_top_title.setText(topTitleString);
		}else {
			tv_top_title.setVisibility(View.GONE);
		}

		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText(titleString);

		btn_ok=(TextView) findViewById(R.id.btn_ok);
		btn_cancel=(TextView) findViewById(R.id.btn_cancel);
		
		findViewById(R.id.btn_ok).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(mCallback_ok_cancel!=null){
							mCallback_ok_cancel.okBack();
						}

						dismiss();
					}
				});

		findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCallback_ok_cancel!=null){
					mCallback_ok_cancel.cancelBack();
				}
				dismiss();
			}
		});
	}

}
