package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;
import com.gohoc.xiupuling.bean.PushLocationShowBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;


public class PushLcListFragment extends BaseFragment {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;
    private View viewContainer;
    private String rq_id = "";
    private String work_id = "";
    private int pageNumber = 1;
    private int pageSize = 10;
    private Adater adater;



    public String getRq_id() {
        return rq_id;
    }

    public PushLcListFragment setRq_id(String rq_id) {
        this.rq_id = rq_id;
        return this;
    }

    public String getWork_id() {
        return work_id;
    }

    public PushLcListFragment setWork_id(String work_id) {
        this.work_id = work_id;
        return this;
    }

    public PushLcListFragment() {

    }

    public static PushLcListFragment newInstance() {
        PushLcListFragment fragment = new PushLcListFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.fragment_push_lc_list, container, false);
        ButterKnife.bind(this, viewContainer);


        distributeLocationList(rq_id, work_id, pageNumber + "", pageSize + "");
        return viewContainer;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    private void distributeLocationList(String rq_id, String work_id, String pageNumber, String pageSize) {
        RxRetrofitClient.getInstance(getActivity()).distributeLocationList(rq_id, work_id, pageNumber, pageSize, new Observer<PushLocationShowBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(getActivity(), "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(PushLocationShowBean pushLocationShowBean) {
                if (pushLocationShowBean.getCode() == 1) {
                    if (pushLocationShowBean.getData().getList() != null && pushLocationShowBean.getData().getList().size() > 0) {
                        initList(pushLocationShowBean);
                        noDateImg.setVisibility(View.GONE);
                    } else {
                        noDateImg.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
    }

    private void initList(final PushLocationShowBean pushLocationShowBean) {
        adater = new Adater(R.layout.item_push_order_list, pushLocationShowBean.getData().getList());
        list.setAdapter(adater);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        adater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                startActivity(new Intent(getActivity(),PushTerminalDtActivity.class).putExtra("id", pushLocationShowBean.getData().getList().get(i).getShop_id())
                        .putExtra("t_id", pushLocationShowBean.getData().getList().get(i).getTerminal_id())
                        .putExtra("range_id", pushLocationShowBean.getData().getList().get(i).getRange_id())



                );
            }
        });
    }

    class Adater extends BaseQuickAdapter<PushLocationShowBean.DataBean.ListBean, BaseViewHolder> {


        public Adater(int layoutResId, List<PushLocationShowBean.DataBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, PushLocationShowBean.DataBean.ListBean listBean) {
            baseViewHolder.setText(R.id.serial_tv, (baseViewHolder.getPosition()  + 1)+"");
            baseViewHolder.setText(R.id.title_tv,listBean.getShop_name()+"");
            baseViewHolder.setText(R.id.termial_id_tv,listBean.getTerminal_no()+"");
        }
    }


}
