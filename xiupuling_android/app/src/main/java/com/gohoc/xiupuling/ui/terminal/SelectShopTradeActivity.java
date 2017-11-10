package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BusinessListAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.bean.BusinessBean;
import com.gohoc.xiupuling.bean.MenuLocationBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class SelectShopTradeActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.auto_city_tv)
    TextView autoCityTv;
    @BindView(R.id.auto_city_lv)
    LinearLayout autoCityLv;
    @BindView(R.id.business_list)
    RecyclerView businessList;
    private MenuLocationBean menuLocationBean ;
    private String groupId;
    private BusinessListAdater businessListAdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shop_trade);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("商圈");
        groupId= this.getIntent().getStringExtra("groudId");
        Bundle db=getIntent().getExtras();
        if(null!=db)
        {
            menuLocationBean= (MenuLocationBean) db.get("menuLocationBean");
            if(null!=menuLocationBean)
            {
                autoCityTv.setText(menuLocationBean.getProvince()+menuLocationBean.getCity()+menuLocationBean.getDistrict()+"");
            }
        }
        getGroup();

    }


    private void getGroup() {
        RxRetrofitClient.getInstance(SelectShopTradeActivity.this).getGroup(menuLocationBean.getDistrictId(), "1", new Observer<BusinessBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(SelectShopTradeActivity.this,"请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(BusinessBean businessBean) {
                if (businessBean.getCode() == 1) {
                    initRcList(businessBean);
                }
            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.auto_city_lv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                SelectShopTradeActivity.this.finish();
                break;
            case R.id.auto_city_lv:
                break;
        }
    }

    public void initRcList(final BusinessBean businessBean) {
        businessListAdater = new BusinessListAdater(SelectShopTradeActivity.this, businessBean);
        businessList.setAdapter(businessListAdater);
        businessList.setLayoutManager(new LinearLayoutManager(SelectShopTradeActivity.this));
        setChecked(groupId,businessBean.getData());
        // provinceList.setItemAnimator(new DefaultItemAnimator());
        businessListAdater.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.d(position);

               for(int a=0;a< businessBean.getData().size();a++)
               {
                   if(businessBean.getData().get(a).isSelect())
                   {
                       businessBean.getData().get(a).setSelect(false);
                       businessListAdater.notifyItemChanged(a);
                   }
               }
                businessBean.getData().get(position).setSelect(true);
                businessListAdater.notifyItemChanged(position);

                setResult(RESULT_OK,new Intent().putExtra("groupId",businessBean.getData().get(position).getGroup_id()+"").putExtra("groupName",businessBean.getData().get(position).getGroup_name()));
                SelectShopTradeActivity.this.finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


    }

    private void setChecked(String id, List<BusinessBean.DataBean> data)
    {
        Logger.e(id);
        for(int a=0;a< data.size();a++)
        {
            if((data.get(a).getGroup_id()+"".trim()).equals(id))
            {
                data.get(a).setSelect(true);
                businessListAdater.notifyItemChanged(a);
            }
        }

    }
}
