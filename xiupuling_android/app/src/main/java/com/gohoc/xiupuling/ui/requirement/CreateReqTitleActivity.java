package com.gohoc.xiupuling.ui.requirement;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateReqTitleActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.name_et)
    ClearEditText nameEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_req_title);
        ButterKnife.bind(this);
        toolbarTitle.setText("作品标题");
        setStatusColor(R.color.colorPrimary);
        Utils.showSoftInputFromWindow(this,nameEt);
        try {
            nameEt.setText(getIntent().getStringExtra("title"));
            nameEt.setSelection(getIntent().getStringExtra("title").length());
        }catch (Exception e){}

        nameEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                setResult(RESULT_OK,new Intent().putExtra("title",nameEt.getText()+""));
                CreateReqTitleActivity.this.finish();
                return true;
            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                break;
            case R.id.toolbar_right:
                setResult(RESULT_OK,new Intent().putExtra("title",nameEt.getText()+""));
                break;
        }
        CreateReqTitleActivity.this.finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(nameEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }


}
