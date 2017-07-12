package com.example.a05_chen.LogTest;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/7/6.
 */

public class ActivityController  {
    private  static  List<Activity> activities = new ArrayList<>();

    public static  void addActivity(Activity activity){
        activities.add(activity);
    }

    
    public  static  void removeActivity(Activity activity){
        if(activity!=null) {
            activities.remove(activity);
        }
    }


    public  static  void finishAll(){

        for (Activity activity:activities){
            if(!activity.isFinishing()) {
                activity.finish();

            }
        }
    }
    
    
    

}
