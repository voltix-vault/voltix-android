package com.voltix.wallet.mediator;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.compose.animation.ExperimentalAnimationApi;

import com.voltix.wallet.app.activity.MainActivity;


public class ServerManager extends BroadcastReceiver {

    private static final String ACTION = "com.voltix.wallet.mediator.ServerManager";

    private static final String CMD_KEY = "CMD_KEY";
    private static final String MESSAGE_KEY = "MESSAGE_KEY";

    private static final int CMD_VALUE_START = 1;
    private static final int CMD_VALUE_ERROR = 2;
    private static final int CMD_VALUE_STOP = 4;

    public static void onServerStart(Context context, String hostAddress) {
        sendBroadcast(context, CMD_VALUE_START, hostAddress);
    }

    public static void onServerError(Context context, String error) {
        sendBroadcast(context, CMD_VALUE_ERROR, error);
    }
    public static void onServerStop(Context context) {
        sendBroadcast(context, CMD_VALUE_STOP);
    }

    private static void sendBroadcast(Context context, int cmd) {
        sendBroadcast(context, cmd, null);
    }

    private static void sendBroadcast(Context context, int cmd, String message) {
        Intent broadcast = new Intent(ACTION);
        broadcast.putExtra(CMD_KEY, cmd);
        broadcast.putExtra(MESSAGE_KEY, message);
        context.sendBroadcast(broadcast);
    }

    @ExperimentalAnimationApi
    private MainActivity mActivity;
    private Intent mService;

    @SuppressLint("UnsafeOptInUsageError")
    public ServerManager(@SuppressLint("UnsafeOptInUsageError") MainActivity activity) {
        this.mActivity = activity;
        mService = new Intent(activity, CoreService.class);
    }

    @SuppressLint("UnsafeOptInUsageError")
    public void register() {
        IntentFilter filter = new IntentFilter(ACTION);
        mActivity.registerReceiver(this, filter);
    }

    @SuppressLint("UnsafeOptInUsageError")
    public void unRegister() {
        mActivity.unregisterReceiver(this);
    }
    @SuppressLint("UnsafeOptInUsageError")
    public void startServer() {
        mActivity.startService(mService);
    }

    @SuppressLint("UnsafeOptInUsageError")
    public void stopServer() {
        mActivity.stopService(mService);
    }

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION.equals(action)) {
            int cmd = intent.getIntExtra(CMD_KEY, 0);
            switch (cmd) {
                case CMD_VALUE_START: {
                    String ip = intent.getStringExtra(MESSAGE_KEY);
//                    mActivity.onServerStart(ip);
                    break;
                }
                case CMD_VALUE_ERROR: {
                    String error = intent.getStringExtra(MESSAGE_KEY);
//                    mActivity.onServerError(error);
                    break;
                }
                case CMD_VALUE_STOP: {
//                    mActivity.onServerStop();
                    break;
                }
            }
        }
    }
}