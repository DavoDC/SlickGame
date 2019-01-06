
package code.Manager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Global audio player class.
 * Calls init() as soon as possible to instantiate the clips HashMap.
 * @author David Charkey
 */
public class JukeBox {
	
	private static HashMap<String, Clip> clips;
	private static int gap;
	
	// Creates new clips HashMap.
	public static void init() {
		clips = new HashMap<String, Clip>();
		gap = 0;
	}
	
	// Loads up audio located at path "s" and stores
	// it in the HashMap with key "n".
	public static void load(String s, String n) {
		if(clips.get(n) != null) return;
		Clip clip;
		try {
                    URL url = JukeBox.class.getResource(s); 
                    InputStream is = url.openStream();
                    BufferedInputStream bis = new BufferedInputStream( is );
                    AudioInputStream ais = AudioSystem.getAudioInputStream(bis);    

                    AudioFormat baseFormat = ais.getFormat();
                    AudioFormat decodeFormat = new AudioFormat(
                            AudioFormat.Encoding.PCM_SIGNED,
                            baseFormat.getSampleRate(),
                            16,
                            baseFormat.getChannels(),
                            baseFormat.getChannels() * 2,
                            baseFormat.getSampleRate(),
                            false
                    );
                    AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
                    clip = AudioSystem.getClip();
                    clip.open(dais);
                    clips.put(n, clip);
            }
		catch(Exception e) 
                {
                    System.err.println("Libraries may be missing");
                    e.printStackTrace();
                    
		}
	}
	
	public static void play(String s) {
		play(s, gap);
	}
	
	public static void play(String s, int i) {
		Clip c = clips.get(s);
		if(c == null) return;
		if(c.isRunning()) c.stop();
		c.setFramePosition(i);
		while(!c.isRunning()) c.start();
	}
	
	public static void stop(String s) {
		if(clips.get(s) == null) return;
		if(clips.get(s).isRunning()) clips.get(s).stop();
	}
	
	public static void resume(String s) {
		if(clips.get(s).isRunning()) return;
		clips.get(s).start();
	}
	
	public static void resumeLoop(String s) {
		Clip c = clips.get(s);
		if(c == null) return;
		c.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static void loop(String s) {
		loop(s, gap, gap, clips.get(s).getFrameLength() - 1);
	}
	
	public static void loop(String s, int frame) {
		loop(s, frame, gap, clips.get(s).getFrameLength() - 1);
	}
	
	public static void loop(String s, int start, int end) {
		loop(s, gap, start, end);
	}
	
	public static void loop(String s, int frame, int start, int end) {
		Clip c = clips.get(s);
		if(c == null) return;
		if(c.isRunning()) c.stop();
		c.setLoopPoints(start, end);
		c.setFramePosition(frame);
		c.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static void setPosition(String s, int frame) {
		clips.get(s).setFramePosition(frame);
	}
	
	public static int getFrames(String s) { return clips.get(s).getFrameLength(); }
	public static int getPosition(String s) { return clips.get(s).getFramePosition(); }
	
	public static void close(String s) {
		stop(s);
		clips.get(s).close();
	}
	
	public static void setVolume(String s, float f) {
		Clip c = clips.get(s);
		if(c == null) return;
		FloatControl vol = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		vol.setValue(f);
	}
	
	public static boolean isPlaying(String s) {
		Clip c = clips.get(s);
		if(c == null) return false;
		return c.isRunning();
	}
	
}