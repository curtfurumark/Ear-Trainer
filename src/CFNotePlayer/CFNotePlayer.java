/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CFNotePlayer;

import cfeartrainer.CFEarTrainer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 *
 * @author curtr
 */
public class CFNotePlayer {
        public void playNote(){
        try {
            //String note = textFieldNote.getText();
            String note = "C1";
            System.out.println("playNote: " + note);
            ShortMessage mess = new ShortMessage();
            mess.setMessage(ShortMessage.NOTE_ON, 0, 63, 93);
            long timeStamp = -1;
            Receiver receiver = MidiSystem.getReceiver();
            receiver.send(mess, timeStamp);
            System.out.println("message sent");
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }    
    
    }
}
