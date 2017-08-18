package com.test.titlemenu;

import com.test.titlemenu.bean.MainListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟数据
 * Created by lady_zhou on 2017/8/17.
 */

public class VirtualData {
    public static List<MainListBean> getDatas(int count) {
        List<MainListBean> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            MainListBean mainListBean = new MainListBean();
            mainListBean.setName("name" + (i + 1));
            list.add(mainListBean);
        }
        return list;
    }
}
