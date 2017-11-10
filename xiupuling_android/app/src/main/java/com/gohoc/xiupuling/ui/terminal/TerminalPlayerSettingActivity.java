package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.ModuleListAdater;
import com.gohoc.xiupuling.bean.MoudlesBean;
import com.gohoc.xiupuling.bean.MoudlesLeve0Bean;
import com.gohoc.xiupuling.bean.MoudlesLeve1Bean;
import com.gohoc.xiupuling.bean.MoudlesLeve2Bean;
import com.gohoc.xiupuling.bean.OpenModuleBean;
import com.gohoc.xiupuling.bean.TerminalLoopBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class TerminalPlayerSettingActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.curr_list)
    RecyclerView currList;
    @BindView(R.id.img_iv)
    ImageView imgIv;
    private String terminal_id;
    private ModuleListAdater moduleListAdater;
    List<MultiItemEntity> data = new ArrayList<>();
    private TopCurListAdapter topCurListAdapter;
    private int[] imgArr = {R.mipmap.icon_loop_1, R.mipmap.icon_loop_2, R.mipmap.icon_loop_3, R.mipmap.icon_loop_4, R.mipmap.icon_loop_5, R.mipmap.icon_loop_6};
    private List<TerminalLoopBean> terminalLoopBeanList = new ArrayList<>();
    private OpenModuleBean openModuleBeans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_player_setting1);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("播放模块");
        terminal_id = getIntent().getStringExtra("terminal_id");
        getDatas();
    }

    private void getDatas() {
        RxRetrofitClient.getInstance(TerminalPlayerSettingActivity.this).getTopModuleList(new Observer<MoudlesBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(TerminalPlayerSettingActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(MoudlesBean moudlesBean) {
                //Logger.json(JSON.toJSONString(responseBody));
                getSelectedModuleList(moudlesBean);
            }
        });

    }

    private void initMoudles(MoudlesBean moudlesBean, OpenModuleBean openModuleBean) {
        Logger.e(moudlesBean.getData().size() + "");
        MoudlesLeve0Bean moudlesLeve0Bean1 = new MoudlesLeve0Bean(null);
        for (int a = 0; a < moudlesBean.getData().size(); a++) {
            if (moudlesBean.getData().get(a).getModule_type() == 1) { //标签墙
                MoudlesLeve1Bean moudlesLeve1Bean = new MoudlesLeve1Bean(moudlesBean.getData().get(a));
                moudlesLeve0Bean1.addSubItem(moudlesLeve1Bean);
                for (int b = 0; b < moudlesBean.getData().get(a).getModule_list().size(); b++) {
                    MoudlesLeve2Bean moudlesLeve2Bean = new MoudlesLeve2Bean(moudlesBean.getData().get(a).getModule_list().get(b));

                    //判断是否在打开
                    for (int c = 0; c < openModuleBean.getData().size(); c++) {
                        if (moudlesBean.getData().get(a).getModule_list().get(b).getModule_id().equals(openModuleBean.getData().get(c).getModule_id())) {
                            moudlesLeve1Bean.setOpenCount(moudlesLeve1Bean.getOpenCount() + 1);
                            moudlesLeve2Bean.setCheck(true);
                        }
                    }
                    moudlesLeve1Bean.addSubItem(moudlesLeve2Bean);
                }
                break;
            }
        }
        MoudlesLeve0Bean moudlesLeve0Bean2 = new MoudlesLeve0Bean("新增私人电台，请登录官网操作：\n http://www.xiupuling.com");
        for (int a = 0; a < moudlesBean.getData().size(); a++) {  //私人电台
            if (moudlesBean.getData().get(a).getModule_type() == 3) {
                MoudlesLeve1Bean moudlesLeve1Bean = new MoudlesLeve1Bean(moudlesBean.getData().get(a));
                moudlesLeve0Bean2.addSubItem(moudlesLeve1Bean);
                for (int b = 0; b < moudlesBean.getData().get(a).getModule_list().size(); b++) {
                    MoudlesLeve2Bean moudlesLeve2Bean = new MoudlesLeve2Bean(moudlesBean.getData().get(a).getModule_list().get(b));
                    for (int c = 0; c < openModuleBean.getData().size(); c++) {
                        if (moudlesBean.getData().get(a).getModule_list().get(b).getModule_id().equals(openModuleBean.getData().get(c).getModule_id())) {
                            moudlesLeve1Bean.setOpenCount(moudlesLeve1Bean.getOpenCount() + 1);
                            moudlesLeve2Bean.setCheck(true);
                        }
                    }
                    moudlesLeve1Bean.addSubItem(moudlesLeve2Bean);
                }
                break;
            }
        }
        MoudlesLeve0Bean moudlesLeve0Bean3 = new MoudlesLeve0Bean("系统电台");
        for (int a = 0; a < moudlesBean.getData().size(); a++) {   //系统电台
            if (moudlesBean.getData().get(a).getModule_type() != 1 && moudlesBean.getData().get(a).getModule_type() != 3) {
                MoudlesLeve1Bean moudlesLeve1Bean = new MoudlesLeve1Bean(moudlesBean.getData().get(a));
                moudlesLeve0Bean3.addSubItem(moudlesLeve1Bean);
                for (int b = 0; b < moudlesBean.getData().get(a).getModule_list().size(); b++) {
                    MoudlesLeve2Bean moudlesLeve2Bean = new MoudlesLeve2Bean(moudlesBean.getData().get(a).getModule_list().get(b));
                    for (int c = 0; c < openModuleBean.getData().size(); c++) {
                        if (moudlesBean.getData().get(a).getModule_list().get(b).getModule_id().equals(openModuleBean.getData().get(c).getModule_id())) {
                            moudlesLeve1Bean.setOpenCount(moudlesLeve1Bean.getOpenCount() + 1);
                            moudlesLeve2Bean.setCheck(true);
                        }
                    }
                    moudlesLeve1Bean.addSubItem(moudlesLeve2Bean);
                }

            }
        }
        data.add(moudlesLeve0Bean1);
        data.add(moudlesLeve0Bean2);
        data.add(moudlesLeve0Bean3);


        moduleListAdater = new ModuleListAdater(data);
        moduleListAdater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (baseQuickAdapter.getItemViewType(i) == MoudlesBean.MODULE_SUB) {
                    MoudlesLeve2Bean moudlesLeve2Bean = (MoudlesLeve2Bean) data.get(i);
                    startActivity(new Intent(TerminalPlayerSettingActivity.this, MoudleContentActivity.class).putExtra("module_id", moudlesLeve2Bean.getModuleListBean().getModule_id()));
                }

            }
        });
        moduleListAdater.setOnCheckChangeListenter(new ModuleListAdater.OnCheckChangeListenter() {
            @Override
            public void onChange(ToggleButton sb, MoudlesBean.DataBean.ModuleListBean moduleListBean, int parentPosition) {
                Logger.e("onChange::" + sb.isChecked());
                MoudlesLeve1Bean moudlesLeve1Bean = (MoudlesLeve1Bean) moduleListAdater.getItem(parentPosition);
                int index = getTerminalIndex(moduleListBean.getModule_id());
                if (index == -1) {
                    //判断是否可以打开
                    if (isCanOpen(moudlesLeve1Bean.getLeve2datas().getModule_type())) {
                        sb.setChecked(!sb.isChecked());
                        Utils.toast(TerminalPlayerSettingActivity.this, moudlesLeve1Bean.getLeve2datas().getModule_type_str());
                        return;
                    }
                    if (terminalLoopBeanList.size() == 6) {
                        sb.setChecked(!sb.isChecked());
                        Utils.toast(TerminalPlayerSettingActivity.this, "最多只能选择6个播放内容");
                        return;
                    }
                    ((MoudlesLeve1Bean) moduleListAdater.getItem(parentPosition)).setOpenCount(moudlesLeve1Bean.getOpenCount() + 1);
                    terminalLoopBeanList.add(new TerminalLoopBean(moduleListBean.getModule_id(), moduleListBean.getModule_name(), moduleListBean.getModule_type()));

                } else {
                    ((MoudlesLeve1Bean) moduleListAdater.getItem(parentPosition)).setOpenCount(moudlesLeve1Bean.getOpenCount() - 1);
                    terminalLoopBeanList.remove(index);
                }

                int size = terminalLoopBeanList.size() > 0 ? terminalLoopBeanList.size() - 1 : 0;
                if (size > 5)
                    size = 5;
                imgIv.setImageResource(imgArr[size]);
                moduleListAdater.notifyItemChanged(parentPosition);
                topCurListAdapter.notifyDataSetChanged();
            }
        });
        list.setAdapter(moduleListAdater);
        list.setLayoutManager(new LinearLayoutManager(this));
  /*      moduleListAdater.notifyDataSetChanged();
        moduleListAdater.expandAll();*/
    /*    expandOrCollapseAll(1);*/
        expandOrCollapseAll(1);
    }

    /***
     * 判断对应模型打开数量是否超过限制
     * @param module_type
     * @return
     */
    private boolean isCanOpen(int module_type) {
        int count = 0;
        for (int a = 0; a < terminalLoopBeanList.size(); a++) {
            if (terminalLoopBeanList.get(a).getModuleType() == module_type)
                count++;
        }
        if (module_type == 1)
            return count > 10000 ? true : false;//标签墙
        else if (module_type == 3)
            return count > 0 ? true : false;//私人电台
        else
            return count > 2 ? true : false; //系统电台
    }

    private int getTerminalIndex(String id) {
        for (int a = 0; a < terminalLoopBeanList.size(); a++) {
            if (id.equals(terminalLoopBeanList.get(a).getId()))
                return a;
        }
        return -1;
    }

    private void expandOrCollapseAll(int t) {
        Logger.e(data.size() + "");

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getItemType() == 1) {
                if (t == 0)
                    moduleListAdater.collapse(i + moduleListAdater.getHeaderLayoutCount(), false, true);
                else
                    moduleListAdater.expand(i + moduleListAdater.getHeaderLayoutCount(), false, true);
            }

        }
    }

    private void initTop() {
        terminalLoopBeanList.add(new TerminalLoopBean("宣传展示", "宣传展示", 0));
        for (int a = 0; a < openModuleBeans.getData().size(); a++) {
            terminalLoopBeanList.add(new TerminalLoopBean(openModuleBeans.getData().get(a).getModule_id(), openModuleBeans.getData().get(a).getModule_name(), openModuleBeans.getData().get(a).getModule_type()));
        }
        int size = openModuleBeans.getData().size() > 0 ? openModuleBeans.getData().size() - 1 : 0;
        if (size > 5)
            size = 5;
        imgIv.setImageResource(imgArr[size]);

        topCurListAdapter = new TopCurListAdapter(R.layout.item_cur_terminal, terminalLoopBeanList);
        currList.setAdapter(topCurListAdapter);
        currList.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void getSelectedModuleList(final MoudlesBean moudlesBean) {
        RxRetrofitClient.getInstance(TerminalPlayerSettingActivity.this).getSelectedModuleList(terminal_id, new Observer<OpenModuleBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //  Toast.makeText(TerminalPlayerSettingActivity.this, "请检查网络是否正常", Toast.LENGTH_SHORT).show();
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(OpenModuleBean openModuleBean) {
                if (openModuleBean.getCode() == 1) {
                    openModuleBeans = openModuleBean;
                    //处理在线
                    initTop();
                    initMoudles(moudlesBean, openModuleBean);
                }

            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                TerminalPlayerSettingActivity.this.finish();
                break;
            case R.id.toolbar_right:
                showProgressDialog("正在提交...");
                updateSelectedModules();
                break;
        }
    }

    private void updateSelectedModules() {
        RxRetrofitClient.getInstance(this).updateSelectedModules(terminal_id, getSelectedModuleIds(), new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {
                closeProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                closeProgressDialog();
                Utils.toast(TerminalPlayerSettingActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                closeProgressDialog();
                if (vCodeBenan.getCode() == 1) {
                    finish();
                }
            }
        });
    }

    private String getSelectedModuleIds() {

        StringBuffer sb = new StringBuffer();
/*        for (int a = 0; a < data.size(); a++) {

            if (data.get(a) instanceof MoudlesLeve2Bean) {
                MoudlesLeve2Bean moudlesLeve2Bean = (MoudlesLeve2Bean) data.get(a);
                if (moudlesLeve2Bean.isCheck()) {
                    sb.append(moudlesLeve2Bean.getModuleListBean().getModule_id() + ",");
                }
            }
        }*/

        for (int a = 1; a < terminalLoopBeanList.size(); a++) {
            sb.append(terminalLoopBeanList.get(a).getId() + ",");
        }


        if (sb.length() < 1)
            return "";
        return sb.toString();

    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                break;
            case R.id.toolbar_right:
                break;
        }
    }

    class TopCurListAdapter extends BaseQuickAdapter<TerminalLoopBean, BaseViewHolder> {

        public TopCurListAdapter(int layoutResId, List<TerminalLoopBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, TerminalLoopBean terminalLoopBean) {
            baseViewHolder.setText(R.id.title_tv, (baseViewHolder.getAdapterPosition() + 1) + "、" + terminalLoopBean.getName());
        }
    }
}
