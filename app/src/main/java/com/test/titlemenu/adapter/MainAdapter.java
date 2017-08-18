package com.test.titlemenu.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.titlemenu.R;
import com.test.titlemenu.bean.MainListBean;

import java.util.List;

/**
 * Created by lady_zhou on 2017/8/17.
 */

public class MainAdapter extends BaseQuickAdapter<MainListBean, BaseViewHolder> {
    public MainAdapter (@Nullable List<MainListBean> data) {
        super(R.layout.item_mainlist, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainListBean item) {
        helper.setText(R.id.tv_name, item.getName());

    }
}
