package com.dongdong.spellandrtimer;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundPoolMap;

    public static void initSound(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(2).build();
        } else {
            soundPool = new SoundPool(2, AudioManager.STREAM_NOTIFICATION, 0);
        }
        soundPoolMap = new HashMap(16);
        soundPoolMap.put(R.raw.top, soundPool.load(context, R.raw.top, 1));
        soundPoolMap.put(R.raw.jungle, soundPool.load(context, R.raw.jungle, 2));
        soundPoolMap.put(R.raw.mid, soundPool.load(context, R.raw.mid, 3));
        soundPoolMap.put(R.raw.ad, soundPool.load(context, R.raw.ad, 4));
        soundPoolMap.put(R.raw.support, soundPool.load(context, R.raw.support, 5));
        soundPoolMap.put(R.raw.flash, soundPool.load(context, R.raw.flash, 6));
        soundPoolMap.put(R.raw.ignite, soundPool.load(context, R.raw.ignite, 7));
        soundPoolMap.put(R.raw.ghost, soundPool.load(context, R.raw.ghost, 8));
        soundPoolMap.put(R.raw.smite, soundPool.load(context, R.raw.smite, 9));
        soundPoolMap.put(R.raw.barrier, soundPool.load(context, R.raw.barrier, 10));
        soundPoolMap.put(R.raw.exhaust, soundPool.load(context, R.raw.exhaust, 11));
        soundPoolMap.put(R.raw.cleanse, soundPool.load(context, R.raw.cleanse, 12));
        soundPoolMap.put(R.raw.clarity, soundPool.load(context, R.raw.clarity, 13));
        soundPoolMap.put(R.raw.heal, soundPool.load(context, R.raw.heal, 14));
        soundPoolMap.put(R.raw.teleport, soundPool.load(context, R.raw.teleport, 15));
        soundPoolMap.put(R.raw.mark, soundPool.load(context, R.raw.mark, 16));
    }

    public static void play(int raw_id) {
        if(soundPoolMap.containsKey(raw_id)) {
            soundPool.play(soundPoolMap.get(raw_id),1,1,1,0,1f);
        }
    }
}
