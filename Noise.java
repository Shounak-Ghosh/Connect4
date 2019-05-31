package Connect4;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Noise
{
    private static Clip dropNoise;
    private static Clip buttonNoise;
    private static Clip menuNoise;

    public static void initializeSounds() {
        
        try
        {
            dropNoise = AudioSystem.getClip();
            dropNoise.open(AudioSystem.getAudioInputStream(
                    Noise.class.getResource("drop.wav")));
            
            buttonNoise = AudioSystem.getClip();
            buttonNoise.open(AudioSystem.getAudioInputStream(
                    Noise.class.getResource("button.wav")));
            
            menuNoise = AudioSystem.getClip();
            menuNoise.open(AudioSystem.getAudioInputStream(
                    Noise.class.getResource("menu_music.wav")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("sounds initialized");
    }
    
    public static void playDropNoise() {
        if (dropNoise==null) return;
        dropNoise.setFramePosition(0);
        dropNoise.start();
    }
    
    public static void playButtonNoise() {
        if (buttonNoise==null) return;
        buttonNoise.setFramePosition(0);
        buttonNoise.start();
    }
    
    public static void startMenuMusic() {
        if (menuNoise==null) return;
        menuNoise.setFramePosition(0);
        menuNoise.start();
    }
    
    public static void stopMenuMusic() {
        if (menuNoise==null) return;
        menuNoise.stop();
        menuNoise.setFramePosition(0);
    }
}
