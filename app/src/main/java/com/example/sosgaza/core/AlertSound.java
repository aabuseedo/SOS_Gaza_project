package com.example.sosgaza.core;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.sosgaza.R;

public class AlertSound {
    private static AlertSound instance;

    private MediaPlayer successPlayer, failPlayer;

    private AlertSound(Context context) {
        successPlayer = MediaPlayer.create(context, R.raw.success);
        failPlayer = MediaPlayer.create(context, R.raw.fail);
    }

    public static synchronized AlertSound getInstance(Context context) {
        if (instance == null) {
            instance = new AlertSound(context);
        }
        return instance;
    }

    public void playSuccessSound() {
        stopCurrentSound();
        successPlayer.start();
    }

    public void playFailSound() {
        stopCurrentSound();
        failPlayer.start();
    }

    public void stopCurrentSound() {
        if (successPlayer != null && successPlayer.isPlaying()) {
            successPlayer.pause();
            successPlayer.seekTo(0);
        }
        if (failPlayer != null && failPlayer.isPlaying()) {
            failPlayer.pause();
            failPlayer.seekTo(0);
        }
    }

    public void release() {
        if (successPlayer != null) {
            successPlayer.release();
            successPlayer = null;
        }
        if (failPlayer != null) {
            failPlayer.release();
            failPlayer = null;
        }
        instance = null;
    }
}

