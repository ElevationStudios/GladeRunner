package com.elevationstudios.gladerunner;

import android.util.Log;

import com.elevationstudios.framework.Music;

public enum SoundEffect {

    AHH, BUTTON_CLICK, ATTACK, CHOKE_FULL, COINS, GULP, HURT, JUMP, LEDGE_CLIMB, STRONG_ATTACK, ROCK_HIT, SPIKE_TRAP,  ORCHESTRA_MUSIC, MASTERMIND_MUSIC;

    public static void PlayMusic(SoundEffect effect) {
        if (Settings.getSoundEnabled()) {

            if (Assets.currentMusic != null) {
                Assets.currentMusic.stop();
            }
            Music music = Assets.cachedMusic.get(effect);
            music.setLooping(true);
            music.setVolume(0.1f);
            Assets.currentMusic = music;
            Assets.currentMusic.play();
        }
    }

    public static void PlaySound(SoundEffect effect) {
        if(Settings.getSoundEnabled())
            Assets.cachedSoundEffects.get(effect).play(0.1f);
    }

    public static void PauseSound(SoundEffect effect) {
        Assets.cachedSoundEffects.get(effect).dispose();
    }


}
