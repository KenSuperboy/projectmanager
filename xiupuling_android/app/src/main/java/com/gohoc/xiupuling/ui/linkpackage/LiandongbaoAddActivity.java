package com.gohoc.xiupuling.ui.linkpackage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.linkpackage.AddlinkPackageBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 新建联动包
* */
public class LiandongbaoAddActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.setting_nike_name_et)
    ClearEditText settingNikeNameEt;
    private String name,title;
    private int type;//0:新增联动包   1：编辑联动包   2:网站域名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit_name);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        settingNikeNameEt.requestFocus();

        settingNikeNameEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            if(type==0){
                                addSubmit();//新增联动包
                            }else if (type==1){
                                        //编辑联动包
                                Intent intent=new Intent();
                                intent.putExtra("name",settingNikeNameEt.getText().toString());
                                setResult(RESULT_OK,intent);
                                //name
                            }
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
        initData();
    }

    private void initData()
    {
        type=getIntent().getIntExtra("type",0);
        title=getIntent().getStringExtra("title");
        name=getIntent().getStringExtra("name");
        toolbarTitle.setText(title);
        settingNikeNameEt.setText(name);
        settingNikeNameEt.setSelection(name.length());

        if(type==2){
            settingNikeNameEt.setHint( "请输入域名前缀");
            settingNikeNameEt.setImeOptions(EditorInfo.IME_ACTION_GO);
        }else {
            settingNikeNameEt.setHint( "多屏联动包的名称");
            settingNikeNameEt.setImeOptions(EditorInfo.IME_ACTION_GO);
        }

    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                LiandongbaoAddActivity.this.finish();
                break;
            case R.id.toolbar_right:
                if(type==0){
                    addSubmit();//新增联动包
                }else if (type==1){
                    Intent intent=new Intent();
                    intent.putExtra("name",settingNikeNameEt.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }else if(type==2){
                    if (TextUtils.isEmpty(settingNikeNameEt.getText())) {
                        Utils.toast(this, "请输入域名前缀");
                    }else {
                        setResult(RESULT_OK,new Intent().putExtra("name",settingNikeNameEt.getText().toString()));
                        finish();
                    }
                }
                break;
        }
    }

    private void addSubmit() {
        if (TextUtils.isEmpty(settingNikeNameEt.getText())) {
            Utils.toast(this, "请输入多屏联动包名称");
            return;
        }
        showProgressDialog("正在加载...");
        RxRetrofitClient.getInstance(LiandongbaoAddActivity.this).addLinkPackage(settingNikeNameEt.getText().toString(), new Observer<AddlinkPackageBean>() {
            @Override
            public void onCompleted() {
                closeProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                closeProgressDialog();
                Utils.toast(LiandongbaoAddActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(AddlinkPackageBean addlinkPackageBean) {
                LogUtil.d("新建成");
                if(addlinkPackageBean.code==1){
                    Intent intent=new Intent(mContext, LiandongbaoDetailActivity.class);
                    intent.putExtra("linkid",addlinkPackageBean.data.link_id);
                    intent.putExtra("linkname",addlinkPackageBean.data.link_name);
                    mContext.startActivity(intent);
                    finish();
                }
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(settingNikeNameEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);
    }
}
