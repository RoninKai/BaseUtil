package com.tanker.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;

/**
 * author : zhoukai
 * e-mail : zhoukai@zto.cn
 * time   : 2018/04/08
 * desc   :
 */
public class NetworkUtils {

    private NetworkUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity == null){
            return false;
        }
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        if(info == null || !info.isConnected()){
            return false;
        }
        return info.getState() == NetworkInfo.State.CONNECTED;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        if(!isConnected(context)){
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 打开WIFI设置界面
     * @param context
     * @param requestCode   请求code
     */
    public static void openSetting(Context context, int requestCode) {
        Intent intent = null;
        if(Build.VERSION.SDK_INT > 10 && !TextUtils.equals(Build.MANUFACTURER,"Meizu")){
            intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        }else {
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        }
        ((Activity)context).startActivityForResult(intent,requestCode);
    }

}