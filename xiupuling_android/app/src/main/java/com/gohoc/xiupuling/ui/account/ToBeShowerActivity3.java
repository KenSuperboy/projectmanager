package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToBeShowerActivity3 extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.save_button_ll)
    LinearLayout saveButtonLl;
    private String url;
    private static int TERMINALER_REQUEST_RESULT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_be_shower3);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("成为展主");
        url = Utils.getSystemInfoBean(this).getData().getAboutShowerpolicy();
        setWebView();
    }

    /**
     * 设置webivew
     */
    private void setWebView() {
        webView.setWebViewClient(new WebViewClient() {
            // public boolean shouldOverrideUrlLoading(WebView view, String url)
            // {
            // loadUrl(url);
            // return true;
            // }

            @Override
            public void onPageFinished(WebView view, String url) {
                closeProgressDialog();
            }
        });
        WebSettings webSettings = webView.getSettings();
        // 这行很重要一点要有，不然网页的认证按钮会无效
        webSettings.setJavaScriptEnabled(true);
        //webSettings.setSupportZoom(true);
        //  webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        loadUrl(url);

    }

    public void loadUrl(String url) {
        if (webView != null) {
            webView.loadUrl(url);
            showProgressDialog("页面加载中，请稍后..");
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.save_button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.save_button_ll:
                startActivityForResult(new Intent(ToBeShowerActivity3.this, ToBeShowerActivity4.class), TERMINALER_REQUEST_RESULT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(RESULT_OK);
        finish();
    }
}
