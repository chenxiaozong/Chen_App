package com.example.a01drawround.UIUtils;

import android.content.Context;

/**
 * Created by chen on 2017/4/13.
 * 专门提供为处理一些UI相关的问题而创建的工具类，
 * 提供资源获取的通用方法，避免每次都写重复的代码获取结果。
 */

public class UIUtils  {

    //与屏幕分辨率相关的
    public static int dp2px(Context context,int dp){
        float density =context.getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5);//四舍五入

    }

    public static int px2dp(Context context,int px){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);//四舍五入

    }

}
