/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CFMidiFilePlayer;

import cfeartrainer.CFEarTrainer;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 *
 * @author curtr
 */
public class CFMidiFilePlayer {
    private Sequencer sequencer;
        public void playMidiFile(String file){
        file = "C:\\Users\\curtr\\Documents\\2018\\CFEarTrainer\\res\\drumloop.mid";
        System.out.println("playMidiFile: " + file);
        try {
            sequencer = MidiSystem.getSequencer();
            if ( sequencer != null){
                File midiFile = new File(file);
                Sequence sequence = MidiSystem.getSequence(midiFile);
                sequencer.setSequence(sequence);
                sequencer.open();
                //LOOP_CONTINUOUSLY 
                sequencer.setLoopCount(1);
                sequencer.start();
            }else{
                System.err.println("some kind of error:..");
            }
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    private void playMidFile() {
        String fileName =  "c:\\whatever\\file.mid";
        playMidiFile(fileName);  
    }
    private void stopPlayingMidiFile() {
        System.out.println("stopPlayingMidiFile()");
        if ( sequencer != null){
            sequencer.stop();        
        }
    }
}
