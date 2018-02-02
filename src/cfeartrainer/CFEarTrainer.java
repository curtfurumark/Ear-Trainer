/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cfeartrainer;

import persist.SQLitePersist;
import persist.IPersist;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;
import static util.Debug.Debug;

/**
 *
 * @author curt
 */
public class CFEarTrainer extends Application {
    private Stage stageOne;
    private Scene scene;
    //private final Button buttonPlay = new Button("play");
    //private final Button buttonPlayMidiFile = new Button("play midi file");
    private boolean answerOK = false;
    private final Button buttonPlayRandomInterval = new Button("play random interval");
    private final Button buttonUnison = new Button("unison");
    private final Button buttonMinor2nd = new Button("minor 2nd");
    private final Button buttonMajor2nd = new Button("major 2nd");
    private final Button buttonMinor3rd = new Button("minor 3rd");
    private final Button buttonMajor3rd = new Button("major 3rd");
    private final Button buttonPerfect4th = new Button("perfect 4th");
    private final Button buttonTriTone = new Button("tritone");
    private final Button buttonPerfect5th = new Button("perfect 5th");
    private final Button buttonMinor6th = new Button("minor 6th");
    private final Button buttonMajor6th = new Button("major 6th");
    private final Button buttonMinor7th = new Button("minor 7th");
    private final Button buttonMajor7th = new Button("major 7th");
    private final Button buttonOctave =   new Button("octave");
    private Label labelAnswer = new Label("answer");
    private Label labelStatistics = new Label("statistics");
    //private final Button buttonStopPlayingMidiFile = new Button("stop playing midi file please");
    //private final TextField textFieldNote = new TextField();
    private Sequencer sequencer;
    private final int HEIGHT = 400;
    private final int WIDTH = 600;
    private final VBox layout = new VBox();
    private final boolean DEBUG = true;
    private int firstNote = 0;
    private int secondNote = 0;
    private IPersist persist;// = new SQLitePersist();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            launch(args);
    }
    private int correctAnswers;
    private int wrongAnswers;


    
    public CFEarTrainer() throws MidiUnavailableException {
        System.out.println("CFEarTrainer");
        try {
            persist = new SQLitePersist();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("start");
        stageOne = stage;
        stageOne.setOnCloseRequest(ae->closeApplication());
        stageOne.setTitle("cf ear trainer");
        scene = new Scene(layout, WIDTH, HEIGHT);
        stageOne.setScene(scene);
        stageOne.show();
        //buttonPlay.setOnAction(ae->playNote());
        //buttonPlayMidiFile.setOnAction(ae->playMidFile());
        buttonUnison.setOnAction(ae->answer(ae));
        buttonMinor2nd.setOnAction(ae->answer(ae));
        buttonMajor2nd.setOnAction(ae->answer(ae));
        buttonMinor3rd.setOnAction(ae->answer(ae));
        buttonMajor3rd.setOnAction(ae->answer(ae));
        buttonPerfect4th.setOnAction(ae->answer(ae));
        buttonTriTone.setOnAction(ae->answer(ae));
        buttonPerfect5th.setOnAction(ae->answer(ae));
        buttonMinor6th.setOnAction(ae->answer(ae));
        buttonMajor6th.setOnAction(ae->answer(ae));
        buttonMinor7th.setOnAction(ae->answer(ae));
        buttonMajor7th.setOnAction(ae->answer(ae));
        buttonOctave.setOnAction(ae->answer(ae));
        buttonPlayRandomInterval.setOnAction(ae->playRandomInterval());
        
        layout.getChildren().addAll( buttonPlayRandomInterval, 
                buttonUnison,
                buttonMinor2nd,
                buttonMajor2nd,
                buttonMinor3rd, 
                buttonMajor3rd, 
                buttonPerfect4th,
                buttonTriTone, 
                buttonPerfect5th,
                buttonMinor6th,
                buttonMajor6th,
                buttonMinor7th,
                buttonMajor7th,
                buttonOctave,
                labelAnswer,
                labelStatistics);
        //showDefaults();
        sequencer = MidiSystem.getSequencer();
        if ( sequencer == null){
            System.err.println("serious trouble, can't get me a sequencer");
        }

    }
    public void showDefaults(){
        System.out.println("showDefaults");
       
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            Debug(synthesizer);
            Receiver receiver = MidiSystem.getReceiver();
            //Sequencer sequencer = MidiSystem.getSequencer();
            //Transmitter transmitter = MidiSystem.getTransmitter();
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    

    
    private void closeApplication() {
        System.out.println("closeApplication()");
        if (  sequencer != null){
            this.sequencer.close();
        }
        //this.stageOne.close();
    }


    private void playRandomInterval(){
        System.out.println("createRandomInterval()");
        try {
            //Sequence sequence = new Sequence(Sequence.PPQ, 16);
            //****  Create a new MIDI sequence with 24 ticks per beat  ****
            Sequence sequence = new Sequence(javax.sound.midi.Sequence.PPQ,24);
            Track track = sequence.createTrack();
            //****  General MIDI sysex -- turn on General MIDI sound set  ****
            byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
            SysexMessage sm = new SysexMessage();
            sm.setMessage(b, 6);
            MidiEvent me = new MidiEvent(sm,(long)0);
            track.add(me);

            //****  set tempo (meta event)  ****
            MetaMessage mt = new MetaMessage();
            byte[] bt = {0x02, (byte)0x00, 0x00};
            mt.setMessage(0x51 ,bt, 3);
            me = new MidiEvent(mt,(long)0);
            track.add(me);

            //****  set track name (meta event)  ****
            mt = new MetaMessage();
            String TrackName = new String("random interval");
            mt.setMessage(0x03 ,TrackName.getBytes(), TrackName.length());
            me = new MidiEvent(mt,(long)0);
            track.add(me);

            //****  set omni on  ****
            ShortMessage mm = new ShortMessage();
            mm.setMessage(0xB0, 0x7D,0x00);
            me = new MidiEvent(mm,(long)0);
            track.add(me);

            //****  set poly on  ****
            mm = new ShortMessage();
            mm.setMessage(0xB0, 0x7F,0x00);
            me = new MidiEvent(mm,(long)0);
            track.add(me);

            //****  set instrument to Piano  ****
            mm = new ShortMessage();
            mm.setMessage(0xC0, 0x00, 0x00);
            me = new MidiEvent(mm,(long)0);
            track.add(me);

            //first a c....
            mm = new ShortMessage();
            firstNote = CFNote.C1;
            mm.setMessage(ShortMessage.NOTE_ON, CFNote.C1, 0x60);
            me = new MidiEvent(mm,(long)1);
            track.add(me);

            //****  note off - middle C - 120 ticks later  ****
            mm = new ShortMessage();
            mm.setMessage(ShortMessage.NOTE_OFF,CFNote.C1,0x40);
            me = new MidiEvent(mm,(long)121);
            track.add(me);
            
            //a second note
            mm = new ShortMessage();
            if ( answerOK || secondNote == 0){
                secondNote = CFNote.getRandomNote();
            }
            mm.setMessage(0x90,secondNote,0x60);
            me = new MidiEvent(mm,(long)121);
            track.add(me);

            //****  note off - middle C - 120 ticks later  ****
            mm = new ShortMessage();
            mm.setMessage(0x80, secondNote,0x40);
            me = new MidiEvent(mm,(long)242);
            track.add(me);


            //****  set end of track (meta event) 19 ticks later  ****
            mt = new MetaMessage();
            byte[] bet = {}; // empty array
            mt.setMessage(0x2F,bet,0);
            me = new MidiEvent(mt, (long)250);
            track.add(me);
            
            //Sequencer sequencer2 = MidiSystem.getSequencer();
            sequencer.setSequence(sequence);
            sequencer.open();
            sequencer.start();
            //****  write the MIDI sequence to a MIDI file  ****
            //File f = new File("midifile.mid");
            //MidiSystem.write(sequence,1,f);
            if (DEBUG)System.out.println("\tsecondNote is " + secondNote);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void answer(ActionEvent ae) {
        if ( DEBUG) System.out.println("answer");
        String buttonTitle = ((Button)ae.getSource()).getText();
        System.out.println("\tbuttonTitle: " + buttonTitle);
        int interval = secondNote - firstNote;
        int guessedNote = 0;
        boolean answerOK = false;
        if ( DEBUG) System.out.println("\tinterval: " + interval );
        if (buttonTitle.equals("unison")){
            guessedNote = firstNote + 0;
            if ( interval == 0){
                labelAnswer.setText("yes it's a unison");
                correctAnswers++;
                answerOK = true;
            }
            else{
                labelAnswer.setText("no it's not a unison");
                wrongAnswers++;
            }
        }
        if (buttonTitle.equals("minor 2nd")){
            guessedNote = firstNote + 1;
            if ( interval == 1){
                labelAnswer.setText("yes its a minor 2nd");
                correctAnswers++;
                answerOK = true;
            }
            else{
                labelAnswer.setText("no its not a minor 2nd");
                correctAnswers++;
            }
        }
        if (buttonTitle.equals("major 2nd")){
            guessedNote = firstNote + 2;
            if ( interval == 2){
                labelAnswer.setText("yes it's a major 2nd");
                correctAnswers++;
                answerOK = true;
            }
            else{
                labelAnswer.setText("no it's not a major 2nd");
                wrongAnswers++;
            }
        }
        if (buttonTitle.equals("minor 3rd")){
            guessedNote = firstNote + 3;
            if ( interval == 3){
                labelAnswer.setText("yes its a minor 3rd");
                correctAnswers++;
                answerOK = true;
            }
            else{
                labelAnswer.setText("no its not a minor 3rd");
                wrongAnswers++;
            }
        }
        if (buttonTitle.equals("major 3rd")){
            guessedNote = firstNote + 4;
            if ( interval == 4){
                labelAnswer.setText("yes its a major 3rd");
                correctAnswers++;
                answerOK = true;
            }
            else{
                labelAnswer.setText("no its not a major 3rd");
                wrongAnswers++;
            }
        }
        if (buttonTitle.equals("perfect 4th")){
            guessedNote = firstNote + 5;
            if ( interval == 5){
                labelAnswer.setText("yes its a perfect 4th");
                correctAnswers++;
                answerOK = true;
            }
            else{
                labelAnswer.setText("no its not a perfect 4th");
                wrongAnswers++;
            }
        }
        if (buttonTitle.equals("tritone")){
            guessedNote = firstNote + 6;
            if ( interval == 6 ){
                labelAnswer.setText("yes it's a tritone");
                correctAnswers++;
                answerOK = true;
            }else{
                labelAnswer.setText("no its not a tritone");
                wrongAnswers++;
            }
        }
        if (buttonTitle.equals("perfect 5th")){
            guessedNote = firstNote + 7;
            if ( interval == 7 ){
                labelAnswer.setText("yes it's a perfect 5th");
                correctAnswers++;
                answerOK = true;
            }else{
                labelAnswer.setText("no its not a perfect 5th");
                wrongAnswers++;
            }
        }
        if (buttonTitle.equals("perfect 5th")){
            guessedNote = firstNote + 7;
            if ( interval == 7 ){
                labelAnswer.setText("yes it's a perfect 5th");
                correctAnswers++;
            }else{
                labelAnswer.setText("no its not a perfect 5th");
                wrongAnswers++;
            }
        }
        if ( buttonTitle.equals("minor 6th")){
            //buttonTitle.setStyle("-fx-base: #b6e7c9;");
            guessedNote = firstNote + 8;
            if ( interval == 8){
                labelAnswer.setText("yes its a minor 6th");
                correctAnswers++;
                answerOK = true;
            }
            else{
                labelAnswer.setText("no its not a minor 6th");
                wrongAnswers++;
            }    
        }
        if ( buttonTitle.equals("major 6th")){
            //buttonTitle.setStyle("-fx-base: #b6e7c9;");
            guessedNote = firstNote + 9;
            if ( interval == 9){
                labelAnswer.setText("yes its a major 6th");
                correctAnswers++;
                answerOK = true;
            }
            else{
                labelAnswer.setText("no its not a major 6th");
                wrongAnswers++;
            }    
        }
        if ( buttonTitle.equals("minor 7th")){
            //buttonTitle.setStyle("-fx-base: #b6e7c9;");
            guessedNote = firstNote + 10;
            if ( interval == 10){
                labelAnswer.setText("yes its a minor 7th");
                correctAnswers++;
                answerOK = true;
            }
            else{
                labelAnswer.setText("no its not a minor 7th");
                wrongAnswers++;
            }    
        }
        if ( buttonTitle.equals("major 7th")){
            guessedNote = firstNote + 11;
            if ( interval == 11){
                labelAnswer.setText("yes it's a major 7th");
                correctAnswers++;
                answerOK = true;
            }
            else{
                labelAnswer.setText("no its not a major 7th");
                wrongAnswers++;
            }    
        }
        if ( buttonTitle.equals("octave")){
            guessedNote = firstNote + 12;
            if ( interval == 12){
                labelAnswer.setText("yes it's an octave");
                correctAnswers++;
                answerOK = true;
            }
            else{
                labelAnswer.setText("no its not an octave");
                wrongAnswers++;
            }    
        }
        persist.persist(firstNote, secondNote, guessedNote);
        labelStatistics.setText(String.format("%d/%d", correctAnswers, correctAnswers + wrongAnswers));
        if ( answerOK){
            secondNote = 0;
            playRandomInterval();
        }
        this.buttonPlayRandomInterval.requestFocus();
    }   
}
