package com.elevationstudios.gladerunner;

import android.util.Log;

import com.elevationstudios.framework.Music;

public enum SoundEffect {

    AHH, ATTACK, CHOKE_FULL, GULP, HURT, JUMP, LEDGE_CLIMB, STRONG_ATTACK, ORCHESTRA_MUSIC, MASTERMIND_MUSIC;

    public static void PlayMusic(SoundEffect effect) {

        if(Assets.currentMusic != null) {
            Assets.currentMusic.stop();
        }
        Music music = Assets.cachedMusic.get(effect);
        music.setLooping(true);
        music.setVolume(0.1f);
        Assets.currentMusic = music;
        Assets.currentMusic.play();
    }

    public static void PlaySound(SoundEffect effect) {
        Assets.cachedSoundEffects.get(effect).play(0.1f);
    }
}
