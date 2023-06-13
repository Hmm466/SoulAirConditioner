package com.jordy.opensource.soulairconditione.utils;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

/**
 * 音频播放kit
 * 
 * @author ming
 *
 */
public class Player {

	/**
	 * music file path
	 */
	private String mMusicPath;

	private AudioFormat baseFormat;

	private AudioInputStream ais;

	private boolean playEnd;

	private SourceDataLine playLine;

	public void initMusic(String musicPath) {
		if (!initPath(musicPath))
			return;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, baseFormat);
		// System.out.println("info="+info);
		try {
			playLine = (SourceDataLine) AudioSystem.getLine(info);
			playLine.open(baseFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			playLine = null;
		}
	}

	public void close() {
		if (playLine == null)
			return;
		if (playLine.isOpen())
			playLine.close();
	}

	private boolean initPath(String path) {
		this.mMusicPath = path;
		try {
			ais = AudioSystem.getAudioInputStream(new File(path));
			baseFormat = ais.getFormat();
			return true;
		} catch (Exception e) {
			baseFormat = null;
			return false;
		}
	}
	public void play() {
		if (playLine == null || baseFormat == null) {
			return;
		}
		playLine.start();
		startPlay();
	}

	private void startPlay() {
		playEnd = false;
		try {
			playLine.open(baseFormat);
			playLine.start();
			playEnd = false;
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int BUFFER_SIZE = 4000 * 4;
					int intBytes = 0;
					byte[] audioData = new byte[BUFFER_SIZE];
					try {
						while (intBytes != -1) {
							intBytes = ais.read(audioData, 0, BUFFER_SIZE);
							if (intBytes >= 0) {
								int outBytes = playLine.write(audioData, 0, intBytes);
							}
							if (playEnd)
								break;
						}
					} catch (Exception e) {
						// TODO: handle exception
						if (!playEnd) {
							playEnd = true;
							stop();
						}
					} finally {
						playEnd = true;
					}
				}
			}).start();

		} catch (Exception e) {
			// TODO: handle exception
			if (!playEnd) {
				playEnd = true;
				stop();
			}
		}
	}

	public void stop() {
		try {
			playLine.stop();
		} catch (Exception e) {
		}
		close();
		try {
			ais.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		playEnd = true;
	}

}
