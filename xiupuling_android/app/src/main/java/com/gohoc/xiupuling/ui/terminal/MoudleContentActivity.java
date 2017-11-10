package com.gohoc.xiupuling.ui.terminal;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.MaterialBean;
import com.gohoc.xiupuling.bean.MoudleContMultiple;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class MoudleContentActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    @BindView(R.id.list)
    RecyclerView list;
    private MyAdater myAdater;
    private List<MoudleContMultiple> moudleContMultiple = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moudle_content);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("内容列表");
        getModuleMaterialList(getIntent().getStringExtra("module_id"));
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();

    }

    private void getModuleMaterialList(String module_id) {
        RxRetrofitClient.getInstance(this).getModuleMaterialList(module_id, new Observer<MaterialBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(MoudleContentActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(MaterialBean materialBean) {
                if (materialBean.getCode() == 1) {
                    initList(materialBean);
                }

            }
        });
    }

    private void initList(MaterialBean materialBean) {
        moudleContMultiple.add(new MoudleContMultiple(materialBean.getData(), MoudleContMultiple.HEAD_TYPE));
        for (int a = 0; a < materialBean.getData().getMaterial_list().size(); a++) {
            moudleContMultiple.add(new MoudleContMultiple(materialBean.getData().getMaterial_list().get(a), MoudleContMultiple.CONTENT_TYPE));
        }

        myAdater = new MyAdater(moudleContMultiple);
        list.setAdapter(myAdater);
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    class MyAdater extends BaseMultiItemQuickAdapter<MoudleContMultiple, BaseViewHolder> {

        public MyAdater(List<MoudleContMultiple> data) {
            super(data);
            addItemType(MoudleContMultiple.HEAD_TYPE, R.layout.item_tplay_list);
            addItemType(MoudleContMultiple.CONTENT_TYPE, R.layout.item_tplay_list_sub);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, MoudleContMultiple moudleContMultiple) {
            switch (baseViewHolder.getItemViewType()) {
                case MoudleContMultiple.HEAD_TYPE:
                    baseViewHolder.setText(R.id.title_tv, moudleContMultiple.getDataBean().getModule_name());
                    break;
                case MoudleContMultiple.CONTENT_TYPE:
                    baseViewHolder.setText(R.id.name_tv, moudleContMultiple.getMaterialListBean().getName())
                            .setText(R.id.time_tv, moudleContMultiple.getMaterialListBean().getPlaytime() + "s");
                    break;
            }
        }
    }


}
