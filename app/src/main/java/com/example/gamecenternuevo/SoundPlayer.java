package com.example.gamecenternuevo;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {

    //----------------------------Atributos -------------------------------------------------------
    private static SoundPool soundPool;
    private static  int recoil;
    private static int shot;
    private static int type;
    private static int imperialSong;
    private static int rifle;
    private static int sbor;
    private static int zvuk1;
    private static int zvuk2;
    private static int musica;

        // --- soundPool is Depricated in api 21 (lolipop
        // -----opcional
    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 2;

    // ------------------------------------ Constuctor ---------------------------------------------
    public SoundPlayer(Context context){
        // ---SoundPool is depricated in API level 21. (Lollipop)
        // ------------------ parte opcional -------------------------------
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        }else{
            // public SoundPool (int maxStreams, int streamType, int srcQuality)
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC,0);
        }
        // ----------------final opcional --------------------
        // public SoundPool (int maxStreams, int streamType, int srcQuality)
        // fila siguente comentamos porque esta en la parte opcional
        // sSoundPool = new SoundPool(6, AudioManager.STREAM_MUSIC,0);

        shot = soundPool.load(context,R.raw.rev,1);
        recoil = soundPool.load(context,R.raw.kurok_rev,1);
        type = soundPool.load(context,R.raw.single_key_type,1);
        imperialSong = soundPool.load(context,R.raw.imperial,1);
        rifle = soundPool.load(context,R.raw.rifle,1);
        sbor = soundPool.load(context,R.raw.sbor,1);
        zvuk1 = soundPool.load(context,R.raw.zvuk1,1);
        zvuk2 = soundPool.load(context,R.raw.zvuk2,1);
        musica = soundPool.load(context,R.raw.max_payne,1);

    }

    // ------------------------------- METODOS -----------------------------------------------------

    public void playRecoil(){
        // final int	play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(recoil,1.0f,1.0f,1,0,1.0f);
    }

    public void playShoot(){
        // final int	play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(shot,1.0f,1.0f,1,0,1.0f);
    }

    public void playType(){
        // final int	play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(type,1.0f,1.0f,1,0,1.0f);
    }

    public void playImperialSong(){
        // final int	play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(imperialSong,1.0f,1.0f,1,0,1.0f);
    }
    public void playRifle(){
        // final int	play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(rifle,1.0f,1.0f,1,0,1.0f);
    }
    public void playSbor(){
        // final int	play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(sbor,1.0f,1.0f,1,0,1.0f);
    }
    public void playZvuk1(){
        // final int	play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(zvuk1,1.0f,1.0f,1,0,1.0f);
    }
    public void playZvuk2(){
        // final int	play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(zvuk2,1.0f,1.0f,1,0,1.0f);
    }
    // musica
    public void playMusica(){
        // final int	play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(musica,1.0f,1.0f,1,-1,1.0f);
    }
}
