package com.hhh.lib_base;

import android.app.Activity;

import java.util.Stack;

/**
 * 存储activity栈
 */
public class ActivityManager {

    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager(){
    }

    /**
     * 获取activityManager
     * @return
     */
    public static ActivityManager getActivityManager(){
        if (instance == null) {
            synchronized (ActivityManager.class){
                if(instance==null){
                    instance = new ActivityManager();
                    instance.activityStack = new Stack();
                }
            }

        }
        return instance;
    }

    /**
     * 移除指定activity
     * @param activity
     */
    public void removeActivity(Activity activity){
        if(activity!=null){
            activity.finish();
            activityStack.remove(activity);
        }
    }


    /**
     * 获取栈顶的activity
     * @return
     */
    public Activity currentActivity(){
        Activity activity;
        try {
            activity=activityStack.lastElement();
        }catch (Exception e){
            activity = null;
        }

        return activity;
    }

    /**
     * 清空栈内所有activity
     */
    public void clearStacks(){
        while ( !activityStack.empty() ){
            final Activity activity = activityStack.pop();
            activity.finish();
        }
    }

    /**
     * 清除名字为name之前的activity
     * @param name
     */
    public void closeUntilThisActicity(String name){
        while ( !activityStack.empty() ){
            final Activity activity = activityStack.pop();

            if( name.equals(activity.getClass().getSimpleName() ) ){
                activityStack.push(activity);
                break;
            }else{
                activity.finish();
            }
        }
    }

    /**
     *
     * @param activity
     */
    public void pushActivity(Activity activity){
        if(activityStack==null){
            activityStack=new Stack<Activity>();
        }
        activityStack.add(activity);
    }

}
