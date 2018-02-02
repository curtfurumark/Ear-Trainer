/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.sound.midi.Synthesizer;

/**
 *
 * @author curtr
 */
public class Debug {
    public static void Debug(Synthesizer synthesizer){
        System.out.println("default synthesizer: " + synthesizer.toString());
    }
}
