package com.example.sosgaza.services;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.accessibility.AccessibilityEvent;

import com.example.sosgaza.activity.SosCountdownActivity;
import com.example.sosgaza.core.Constants;

import java.util.ArrayList;
import java.util.List;

public class MyAccessibilityService extends AccessibilityService {

    private final List<Long> timestamps = new ArrayList<>();
    private ScreenReceiver screenReceiver;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        screenReceiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (screenReceiver != null) {
            unregisterReceiver(screenReceiver);
        }
    }

    private void recordPowerPress() {
        long now = System.currentTimeMillis();
        timestamps.add(now);
        if (timestamps.size() > Constants.POWER_PRESS_COUNT) {
            timestamps.remove(0);
        }

        if (timestamps.size() == Constants.POWER_PRESS_COUNT &&
                (timestamps.get(Constants.POWER_PRESS_COUNT - 1) -
                        timestamps.get(0)) <= Constants.POWER_PRESS_DELAY) {
            openApp();
            timestamps.clear();
        }
    }

    private void openApp() {
        Intent intent = new Intent(this, SosCountdownActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onInterrupt() {
    }

    public class ScreenReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null &&
                    (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)
                            || intent.getAction().equals(Intent.ACTION_SCREEN_ON))) {
                recordPowerPress();
            }
        }
    }
}

