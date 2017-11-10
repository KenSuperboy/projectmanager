package com.gohoc.xiupuling.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.callback.Callback_Ok_Cancel;

/*
* 删除我的作品库里面的详情
* */
public class Delete_Dialog extends Dialog {
	private Window mWindow;
	private Context context;
	private TextView tv_top_title,tv_title,btn_ok,btn_cancel;
	private SpannableString spannableString;
	public Callback_Ok_Cancel mCallback_ok_cancel;

	public Delete_Dialog(Context context, SpannableString myspannableString) {
		super(context);
		this.context = context;
		this.spannableString=myspannableString;
		mWindow = this.getWindow();
		mWindow.setBackgroundDrawable(new ColorDrawable(0));
		setCancelable(false);
		setCanceledOnTouchOutside(false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mWindow.setContentView(R.layout.dialog_ok_cancel);
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
		tv_top_title.setVisibility(View.GONE);

		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText(spannableString);

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
