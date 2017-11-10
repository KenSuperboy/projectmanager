package com.gohoc.xiupuling.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimpleEditActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.et)
    ClearEditText et;
    private String title = "";
    private String value = "";
    private String tipes = "";
    private String emptyTipes = "不能为空";
    private int textType;
    private boolean limteEmpty = false;//是否判断返回内容空

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_edit);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        title = getIntent().getStringExtra("title");
        value = getIntent().getStringExtra("value");
        tipes = getIntent().getStringExtra("tipes");
        textType = getIntent().getIntExtra("textType", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        limteEmpty = getIntent().getBooleanExtra("limteEmpty", false);
        emptyTipes = getIntent().getStringExtra("emptyTipes");
        toolbarTitle.setText(title);
        if (value == null) value = "";
        et.setText(value + "");
        et.setHint(tipes + "");
        et.setInputType(textType);
        Utils.showSoftInputFromWindow(this,et);
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                if (limteEmpty && TextUtils.isEmpty(et.getText())) {
                    Utils.toast(SimpleEditActivity.this, emptyTipes);
                    break;
                }
                setResult(RESULT_OK, new Intent().putExtra("value", et.getText() + ""));
                finish();
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(et, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }


}
