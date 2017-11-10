package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.AddPicAdater;
import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.PicActivity;
import com.gohoc.xiupuling.utils.DeviceUtils;
import com.gohoc.xiupuling.utils.Utils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
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

public class SelectShopPicActivity extends BasicActivity implements View.OnTouchListener {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.selectShopPicActivity)
    RelativeLayout selectShopPicActivity;
    private AddPicAdater addPicAdater;
    private ArrayList<PicBean> picList;
    private BottomSheetDialog dialogs;

    private static int PIC_REQUEST_RESULT = 1000;
    private int index = 1;
    private boolean isFulls = false;
    private ImagePicker imagePicker = ImagePicker.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shop_pic);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("店铺照片");
        toolbarRight.setText("完成");
        toolbarLeftTitle.setText("返回");
        Intent intent = getIntent();
        if (null != intent) {
            Bundle data = intent.getExtras();
            if (null != data)
                picList = (ArrayList<PicBean>) data.get("picList");
            if (null == picList)
                picList = new ArrayList<PicBean>();
        }
        initRv();
        initDialog();
    }


    private void initRv() {
        if (picList.size() == 0)
            picList.add(new PicBean());

        recyclerView.setLayoutManager(new GridLayoutManager(SelectShopPicActivity.this, 2));
        //设置adapter
        addPicAdater = new AddPicAdater(SelectShopPicActivity.this, picList);
        recyclerView.setAdapter(addPicAdater);
        addPicAdater.setOnPicChangeListion(
                new AddPicAdater.OnPicChangeListion() {
                    @Override
                    public void OnDel(View v, int postion) {
                        picList.remove(postion);
                        addPicAdater.notifyDataSetChanged();
                        if (picList.get(picList.size() - 1).getType() == 1) {
                            picList.add(picList.size(), new PicBean());
                        }
                        addPicAdater.notifyDataSetChanged();
                    }

                    @Override
                    public void OnAdd(View v, int postion) {
                        //picList.add(new PicBean());
                        // addPicAdater.notifyDataSetChanged();
                        if (TextUtils.isEmpty(picList.get(postion).getUrl()) && TextUtils.isEmpty(picList.get(postion).getLocUrl())) {
                            if (dialogs != null)
                                dialogs.show();
                        } else
                            startActivityForResult(new Intent(SelectShopPicActivity.this, PicActivity.class).putExtra("picList", picList).putExtra("curr", postion), PIC_REQUEST_RESULT);
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() == 0) return;
                for (int a = 0; a < images.size(); a++) {
                    if (addPicAdater.getItemCount() == addPicAdater.getMaxCount()) {
                        //最后一张
                        picList.get(picList.size() - 1).setType(1).setUpdatefileName(Utils.getUploadKey(UpLoadConstant.SHOP, images.get(a).path)).setLocUrl(images.get(a).path);
                        uploadFile(picList.get(picList.size() - 1).getLocUrl(), picList.get(picList.size() - 1).getUpdatefileName(), picList.size() - 1, images.size());
                    } else {
                        PicBean picBean = new PicBean();
                        picBean.setLocUrl(images.get(a).path);
                        picBean.setType(1);
                        picBean.setUpdatefileName(Utils.getUploadKey(UpLoadConstant.SHOP, images.get(a).path));
                        picList.add(picList.size() - 1, picBean);
                        uploadFile(picList.get(picList.size() - 2).getLocUrl(), picList.get(picList.size() - 2).getUpdatefileName(), picList.size() - 2, images.size());
                    }

                    addPicAdater.notifyDataSetChanged();
                    Logger.d(a);
                }
            }
        } else if (resultCode == RESULT_OK) {
            picList.clear();
            picList.addAll((ArrayList<PicBean>) data.getExtras().get("picList"));
            addPicAdater.notifyDataSetChanged();
        }
    }

    private void showMenu(int type) {
        imagePicker.setSelectLimit(addPicAdater.getMaxCount() - (addPicAdater.getItemCount() - 1));    //选中数量限制
        imagePicker.setFocusWidth(DeviceUtils.getScreenSize(this)[0]);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(DeviceUtils.getScreenSize(this)[0]*9/16);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1920);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1080);//保存文件的高度。单位像素

        if (type == 0) {
            imagePicker.setMultiMode(false);
            imagePicker.setCrop(true);
            Intent intent = new Intent(this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            startActivityForResult(intent, IMAGE_PICKER);
        } else if (type == 1) {
            imagePicker.setMultiMode(true);
            imagePicker.setCrop(false);
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, IMAGE_PICKER);
        }
    }


    public void initDialog() {
        dialogs = new BottomSheetDialog(SelectShopPicActivity.this);
        View dialogView = LayoutInflater.from(SelectShopPicActivity.this)
                .inflate(R.layout.select_pic_pop, null);
        LinearLayout fileLL = (LinearLayout) dialogView.findViewById(R.id.file_ll);
        LinearLayout cameraLL = (LinearLayout) dialogView.findViewById(R.id.camera_ll);

        fileLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(1);//相册上传
                dialogs.dismiss();
            }
        });
        cameraLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(0);//拍照上传
                dialogs.dismiss();
            }
        });
        dialogs.setContentView(dialogView);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goBack() {
        setResult(RESULT_OK, new Intent().putExtra("picList", picList));
        SelectShopPicActivity.this.finish();
    }


    private void uploadFile(String url, final String fileName, final int f, final int upCount) {
        if (dialog == null || !dialog.isShowing())
            showProgressDialog("正在上传....", false);
        File file = null;
        try {
            file = Luban.with(this).load(new File(url)).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final File finalFile = file;
        RxRetrofitClient.getInstance(this).getUploadToken(new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(SelectShopPicActivity.this, "请检查网络是否正常");
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


                    uploadManager.put(finalFile.getPath(), fileName, vCodeBenan.getMessage(),
                            new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject res) {
                                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                                    if (info.isOK()) {
                                        Logger.d("qiniu:Upload Success:" + fileName);
                                        picList.get(f).setUrl(fileName);
                                        if (index == upCount) {
                                            index = 1;
                                            closeProgressDialog();
                                        } else
                                            index++;

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


    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                goBack();
                break;
            case R.id.toolbar_right:
                goBack();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (v.getId() == R.id.toolbar_left_title || v.getId() == R.id.toolbar_right) {
                goBack();
            } else {
                isFulls = !isFulls;
                full(isFulls, toolbar);
            }
        }
        return true;
    }
}
