package com.example.gamecenternuevo;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {

    private static SoundPool sSoundPool;
    private static  int recoil;
    private static int shot;

    public SoundPlayer(Context context){
        // public SoundPool (int maxStreams, int streamType, int srcQuality)
        sSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);

        shot = sSoundPool.load(context,R.raw.rev,1);
        recoil = sSoundPool.load(context,R.raw.kurok_rev,1);
    }

    public void playRecoil(){
        // final int	play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        sSoundPool.play(recoil,1.0f,1.0f,1,0,1.0f);
    }

    public void playShoot(){
        // final int	play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        sSoundPool.play(shot,1.0f,1.0f,1,0,1.0f);
    }
}
