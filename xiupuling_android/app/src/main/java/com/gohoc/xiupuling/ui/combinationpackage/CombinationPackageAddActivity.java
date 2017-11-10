package com.gohoc.xiupuling.ui.combinationpackage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.combinationbean.CombinationAddBean;
import com.gohoc.xiupuling.bean.combinationbean.CombinationListBean;
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
* 新建作品组合包
* */
public class CombinationPackageAddActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.setting_nike_name_et)
    ClearEditText settingNikeNameEt;
    private String name,title;
    private int type;//0:新增作品组合包   1：编辑作品组合包名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit_name);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        settingNikeNameEt.requestFocus();

        settingNikeNameEt.setHint("作品组合的名称");
        settingNikeNameEt.setImeOptions(EditorInfo.IME_ACTION_GO);

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
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                CombinationPackageAddActivity.this.finish();
                break;
            case R.id.toolbar_right:
                if(type==0){
                    addSubmit();//新增作品组合包
                }else if (type==1){
                    Intent intent=new Intent();
                    intent.putExtra("name",settingNikeNameEt.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
        }
    }

    private void addSubmit() {
        if (TextUtils.isEmpty(settingNikeNameEt.getText())) {
            Utils.toast(this, "请输入作品组合包名称");
            return;
        }
        showProgressDialog("正在加载...");
        RxRetrofitClient.getInstance(CombinationPackageAddActivity.this).addCombinationPackage(settingNikeNameEt.getText().toString(), new Observer<CombinationAddBean>() {
            @Override
            public void onCompleted() {
                closeProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                closeProgressDialog();
                Utils.toast(CombinationPackageAddActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(CombinationAddBean combinationAddBean) {
                LogUtil.d("新建成");
                if(combinationAddBean.code==1){

                    CombinationListBean.DataBean dataBean=new CombinationListBean.DataBean();
                    dataBean.cover_url="";
                    dataBean.create_time=combinationAddBean.data.create_time;
                    dataBean.end_time="";
                    dataBean.is_ignore_other_work=combinationAddBean.data.is_ignore_other_work==0?false:true;
                    dataBean.is_time_limit=combinationAddBean.data.is_time_limit==0?false:true;
                    dataBean.orientation=combinationAddBean.data.orientation==0?false:true;
                    dataBean.package_id=combinationAddBean.data.package_id;
                    dataBean.package_name=combinationAddBean.data.package_name;
                    dataBean.repeat_weekday="";
                    dataBean.start_time="";
                    dataBean.user_id=combinationAddBean.data.user_id;
                    dataBean.work_cnt=0;
                    dataBean.work_seat=0;
                    dataBean.work_size=0;

                    Intent intent=new Intent(mContext, CombinationPackageDetailActivity.class);
                    intent.putExtra("time", "非独占时段轮播");
                    intent.putExtra("data",dataBean);
                    startActivity(intent);
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
