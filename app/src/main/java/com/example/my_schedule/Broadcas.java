package com.example.my_schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

public class Broadcas extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer medd
                = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        medd.setLooping(true);
        medd.start();
    }
}
