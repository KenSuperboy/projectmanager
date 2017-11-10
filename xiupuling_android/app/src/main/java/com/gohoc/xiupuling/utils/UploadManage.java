package com.gohoc.xiupuling.utils;

import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.constant.OnUploadListener;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/7/5 0005
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public enum UploadManage {
    INSTANS;


    public int uploadImageCount = 0;

    UploadManager uploadManager = new UploadManager();

    private Map<String, Boolean> map = new HashMap<String, Boolean>();
    private OnUploadListener onUploadListener;
    private boolean isInit = true;

    public void upload(final PicBean picBean, final int potions, VCodeBenan vCodeBenan, OnUploadListener listener) {
        isInit = false;
        map.put(picBean.isVideo() ? picBean.getUpdatefileName() : picBean.getUrl(), false);
        this.onUploadListener = listener;
        uploadManager.put(picBean.getLocUrl(), picBean.getUpdatefileName(), vCodeBenan.getMessage(),
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {

                        if (onUploadListener != null) {
                            onUploadListener.complete(key, info, res, picBean);
                        }
                    }

                }, new UploadOptions(null, null, false,
                        new UpProgressHandler() {
                            public void progress(String key, double percent) {
                                if (onUploadListener != null) {
                                    onUploadListener.progress(key, percent, picBean, potions);
                                }
                            }
                        }, new UpCancellationSignal() {
                    public boolean isCancelled() {
                        if (isInit) {
                            return isInit;
                        }
                        Boolean aBoolean = false;
                        if (map.containsKey(picBean.isVideo() ? picBean.getUpdatefileName() : picBean.getUrl())) {
                            aBoolean = map.get(picBean.isVideo() ? picBean.getUpdatefileName() : picBean.getUrl());
                            map.remove(picBean.getUrl());
                        }
                        return aBoolean;
                    }
                }));
    }

    public void setOnUploadListener(OnUploadListener onUploadListener) {
        this.onUploadListener = onUploadListener;
    }

    public void setCannelUpload(String key) {
        if (map.containsKey(key))
            map.put(key, true);
    }

    public void init() {
        map.clear();
        isInit = true;

    }
}
