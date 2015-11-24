package com.stefanosiano.common.tochange;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.stefanosiano.wavyfish.utilities.Settings;

public class SoundLoader {
	public static Sound dead, bubble, slam, flap, collect_coin, victory;
	public static Music musicIntro, musicOriginal, musicHorror, musicTema, musicSpace;

	public static void load(){
		String path = "data/audio/";
		dead = Gdx.audio.newSound(Gdx.files.internal(path + "dead.mp3"));
		bubble = Gdx.audio.newSound(Gdx.files.internal(path + "bubble.mp3"));
		slam = Gdx.audio.newSound(Gdx.files.internal(path + "slam.mp3"));
		flap = Gdx.audio.newSound(Gdx.files.internal(path + "flap.mp3"));
		victory = Gdx.audio.newSound(Gdx.files.internal(path + "victory.mp3"));
		collect_coin = Gdx.audio.newSound(Gdx.files.internal(path + "collect_coin.mp3"));
		musicIntro = Gdx.audio.newMusic(Gdx.files.internal(path + "interface.mp3"));
		musicOriginal = Gdx.audio.newMusic(Gdx.files.internal(path + "run.mp3"));
		musicHorror = Gdx.audio.newMusic(Gdx.files.internal(path + "lost_village.mp3"));
		musicTema = Gdx.audio.newMusic(Gdx.files.internal(path + "coldstone.mp3"));
		musicSpace = Gdx.audio.newMusic(Gdx.files.internal(path + "freeze.mp3"));
	}
	
	public static void dispose() {
	    dead.dispose();
	    bubble.dispose();
	    slam.dispose();
	    flap.dispose();
	    victory.dispose();
	    musicIntro.dispose();
	    musicOriginal.dispose();
	    musicHorror.dispose();
	    musicTema.dispose();
	    musicSpace.dispose();
	}
	
	public static void playMusic(Music musicChosen){
		if(Settings.MUSIC_ENABLED){
			musicChosen.play();
			musicChosen.setLooping(true);
			musicChosen.setVolume(1f);
		}
		else{
			if(musicChosen.isPlaying())
				musicChosen.pause();
		}
	}
	
	public static void stopMusics(){
		musicIntro.stop();
		musicOriginal.stop();
		musicHorror.stop();
		musicTema.stop();
		musicSpace.stop();
	}
}
