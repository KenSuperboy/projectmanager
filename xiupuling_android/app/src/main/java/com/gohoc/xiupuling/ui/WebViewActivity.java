package com.gohoc.xiupuling.ui;

import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.gohoc.xiupuling.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BasicActivity {

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private String url;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        url = this.getIntent().getStringExtra("url");
        title = this.getIntent().getStringExtra("title");
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText(title + "");
        setWebView();
    }


    /**
     * 设置webivew
     */
    private void setWebView() {
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); // Android默认的处理方式
                handler.proceed();  // 接受所有网站的证书
                //handleMessage(Message msg); // 进行其他处理
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                closeProgressDialog();
            }
        });
        WebSettings webSettings = webView.getSettings();
        // 这行很重要一点要有，不然网页的认证按钮会无效
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        loadUrl(url);

    }

    public void loadUrl(String url) {
        if (webView != null) {
            webView.loadUrl(url);
            showProgressDialog("页面加载中，请稍后..");
        }
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        finish();
    }

    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
