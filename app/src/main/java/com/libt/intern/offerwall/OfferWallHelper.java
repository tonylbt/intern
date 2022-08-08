package com.libt.intern.offerwall;

import android.content.Context;
import android.widget.Toast;

import com.libt.intern.util.Logger;
import com.san.offerwall.core.BaseSetting;
import com.san.offerwall.core.ICommonInterface;
import com.san.offerwall.core.OfferWallSdk;
import com.san.offerwall.core.OfferwallListener;

public class OfferWallHelper {
    private static final String TAG = "OfferWallHelper";
    private static final String OFFERWALL_PID = "2190";

    public static void init(Context context) {
        setOfferWallListener();
        OfferWallSdk.init(context, getGameSettings(context));
    }

    private static void setOfferWallListener() {
        OfferWallSdk.setOfferwallListener(new OfferwallListener() {
            @Override
            public void onOfferwallAvailable(boolean isAvailable) {
                //积分墙初始化成功回调
                Logger.d(TAG, "setOfferWallListener().onOfferwallAvailable() isAvailable=" + isAvailable);
            }

            @Override
            public void onOfferwallOpen() {
                //打开积分墙页面回调
                Logger.d(TAG, "setOfferWallListener().onOfferwallOpen()");
            }

            @Override
            public void onOfferwallShowFailed(String errorMsg) {
                //积分墙页面展示失败时回调，errorMsg 表示错误信息，包含网络错误，请求超时等问题。
                Logger.d(TAG, "setOfferWallListener().onOfferwallShowFailed(）errorMsg=" + errorMsg);
            }

            @Override
            public void onOfferwallAdCredited(int lastEarnedCredits, int totalEverEarnedCredits) {
                //积分墙任务完成时回调，参数 lastEarnedCredits 表示当前任务奖励数量, totalEverEarnedCredits 表示用户从积分墙获取的总奖励数，包含本次任务奖励。
                Logger.d(TAG, "setOfferWallListener().onOfferwallAdCredited() lastEarnedCredits=%d, totalEverEarnedCredits=%d", lastEarnedCredits, totalEverEarnedCredits);
            }

            @Override
            public void onOfferwallClose() {
                //积分墙页面关闭时回调
                Logger.d(TAG, "setOfferWallListener().onOfferwallClose()");
            }
        });
    }

    private static BaseSetting getGameSettings(Context context) {
        return new BaseSetting.Builder()
                .setCommonInterface(new ICommonInterface() {
                    @Override
                    public String getCountryCode() {
                        //返回一个大写的国家码标识，如果返回空则默认读取本地系统所在国家
                        return "IN";
                    }
                    @Override
                    public String getUserId() {
                        //返回已登录的用户 Id
                        return "intern_" + OFFERWALL_PID;
                    }
                    @Override
                    public String getLoginToken() {
                        //返回已登录的用户的 token, 如果没有可传空
                        return "99ca730644a9e5a09f9243907582729494dfe54b_" + System.currentTimeMillis();
                    }
                    @Override
                    public void showPermissionDialog(Context context) {
                        //展示请求使用状态权限的对话框来引导用户去授权，需要接入方自己设计相关弹窗页面
                        Logger.d(TAG, "getGameSettings().showPermissionDialog()。。。。。。");
                        Toast.makeText(context, "showPermissionDialog()...", Toast.LENGTH_LONG).show();
                    }
                })
                .build();
    }

    public static void showOfferWall(Context context) {
        //第二个参数表示积分墙的广告位 id, 需要找我方运营人员提供
        OfferWallSdk.showOfferWall(context, OFFERWALL_PID);
    }
}
