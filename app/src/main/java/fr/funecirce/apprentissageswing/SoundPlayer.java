package fr.funecirce.apprentissageswing;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer extends Thread {

    Clip clip;

    AudioInputStream audioStream;
    
    String thePath;

    public SoundPlayer(String path) {
        thePath = path;
        try {
            audioStream = AudioSystem.getAudioInputStream(new File(thePath));
            
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // TODO Auto-generated catch block
            System.out.println("Error loading the sound file: " + e.getMessage());
        }
    }

    public void setVolume(float volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log(volume));
        
    }

    @Override
    public void start() {
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

}
