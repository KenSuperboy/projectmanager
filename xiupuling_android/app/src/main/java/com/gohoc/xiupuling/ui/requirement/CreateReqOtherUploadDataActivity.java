package com.gohoc.xiupuling.ui.requirement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.ReqSouresDatesAdater;
import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.account.PersonlCertificationActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import top.zibin.luban.Luban;

import static com.gohoc.xiupuling.constant.Constant.BaseConstant.IMAGE_PICKER;

public class CreateReqOtherUploadDataActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RecyclerView list;
    private ReqSouresDatesAdater reqSouresDatesAdater;
    private ArrayList<PicBean> picList;

    private String fileUrl;
    private int maxCount = 6;
    private ImagePicker imagePicker = ImagePicker.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_req_other_upload_data);
        ButterKnife.bind(this);
        toolbarTitle.setText("例图或素材");
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(true);
        setStatusColor(R.color.colorPrimary);
        initList();
    }

    private void initList() {
        try {
            picList = (ArrayList<PicBean>) getIntent().getExtras().get("picList");
            if (null == picList)
                picList = new ArrayList<>();
        } catch (Exception e) {
        }

        reqSouresDatesAdater = new ReqSouresDatesAdater(this, picList);
        if (picList.size() == 0)
            picList.add(new PicBean());

        list.setLayoutManager(new LinearLayoutManager(this));
        //设置adapter
        reqSouresDatesAdater = new ReqSouresDatesAdater(this, picList);
        list.setAdapter(reqSouresDatesAdater);
        reqSouresDatesAdater.setOnPicChangeListion(
                new ReqSouresDatesAdater.OnPicChangeListion() {
                    @Override
                    public void OnDel(View v, int postion) {
                        picList.remove(postion);
                        if (picList.get(picList.size() - 1).getType() == 1) {
                            picList.add(picList.size(), new PicBean());
                        }
                        reqSouresDatesAdater.notifyDataSetChanged();
                    }

                    @Override
                    public void OnAdd(View v, int postion) {
                        //picList.add(new PicBean());
                        // addPicAdater.notifyDataSetChanged();
                        showDialog();
                    }
                }
        );


    }

    public void showDialog() {
        new AlertView(null, null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            public void onItemClick(Object o, int position) {

                showMenu(position);
            }
        }).show();
    }

    private void showMenu(int type) {
        imagePicker.setMultiMode(true);
        imagePicker.setCrop(false);
        imagePicker.setSelectLimit(maxCount - picList.size() + 1);    //选中数量限制
        if (type == 0) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            startActivityForResult(intent, IMAGE_PICKER);
        } else if (type == 1) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent,IMAGE_PICKER);
        }

    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        goback();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goback();
        }
        return false;
    }

    private void goback() {
        setResult(RESULT_OK, new Intent().putExtra("picList", picList));
        this.finish();
    }


    private void uploadFile(final String url, final String fileName, final int f) {
        if (dialog == null || !dialog.isShowing())
            showProgressDialog("正在提交....", false);

        RxRetrofitClient.getInstance(this).getUploadToken(new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CreateReqOtherUploadDataActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    UploadManager uploadManager = new UploadManager();
                    Logger.e(vCodeBenan.getMessage());

                    File file = null;
                    try {
                        file = Luban.with(CreateReqOtherUploadDataActivity.this).load(new File(url)).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    uploadManager.put(file.getPath(), fileName, vCodeBenan.getMessage(),
                            new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject res) {
                                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                                    if (info.isOK()) {
                                        Logger.d("qiniu:Upload Success:" + fileName);
                                        picList.get(f).setUrl(fileName);
                                        if (f == picList.size() - 2 || f == 5)
                                            closeProgressDialog();
                                    } else {
                                        Logger.d("qiniu Upload Fail");
                                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                    }
                                    Logger.d("qiniu: " + key + ",\r\n " + info + ",\r\n " + res);
                                }
                            }, null);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode ==IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() == 0) return;
                for (ImageItem imageItem : images) {
                    if (reqSouresDatesAdater.getItemCount() == reqSouresDatesAdater.getMaxCount()) {
                        //最后一张
                        picList.get(picList.size() - 1).setType(1).setUpdatefileName(Utils.getUploadKey(UpLoadConstant.REQUIREMENT, imageItem.path)).setLocUrl(imageItem.path);
                        uploadFile(picList.get(picList.size() - 1).getLocUrl(), picList.get(picList.size() - 1).getUpdatefileName(), picList.size() - 1);
                    } else {
                        PicBean picBean = new PicBean();
                        picBean.setLocUrl(imageItem.path);
                        picBean.setType(1);
                        picBean.setUpdatefileName(Utils.getUploadKey(UpLoadConstant.SHOP, imageItem.path));
                        picList.add(picList.size() - 1, picBean);
                        uploadFile(picList.get(picList.size() - 2).getLocUrl(), picList.get(picList.size() - 2).getUpdatefileName(), picList.size() - 2);
                    }

                    reqSouresDatesAdater.notifyDataSetChanged();
                    Logger.d(imageItem.path);
                }
            }
        }
    }
}
