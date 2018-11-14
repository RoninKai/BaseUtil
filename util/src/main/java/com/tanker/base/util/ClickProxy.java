package com.tanker.base.util;

import android.view.View;

/**
 * @author : Tanker
 * @email :zhoukai@zto.cn
 * @date : 2018/11/14
 * @describe : 点击事件代理类
 */
public class ClickProxy implements View.OnClickListener {

    private View.OnClickListener onClickProxy;
    private long lastClick = 0;
    private long times;

    public ClickProxy(View.OnClickListener onClickListener){
        this.onClickProxy = onClickListener;
        times = 1000;
    }

    public ClickProxy(View.OnClickListener onClickListener,long intervalTime){
        this.onClickProxy = onClickListener;
        this.times = intervalTime;
    }

    @Override
    public void onClick(View view) {
        if(System.currentTimeMillis() - lastClick >= times){
            onClickProxy.onClick(view);
            lastClick = System.currentTimeMillis();
        }
    }

}