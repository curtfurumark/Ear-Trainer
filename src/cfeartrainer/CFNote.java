/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cfeartrainer;

import java.util.Random;

/**
 *
 * @author curtr
 */
public class CFNote {
    public static boolean DEBUG = true;
    public static int   C1 = 60;
    public static int   Db1 = 61;
    public static int   D1 = 62;
    public static int   Eb1 = 63;
    public static int   E1 = 64;
    public static int   F1 = 65;
    public static int   Gb1 = 66;
    public static int   G1 = 67;
    public static int   Ab1 = 68;
    public static int   A1 = 69;
    public static int   Bb1 = 70;
    public static int   B1 = 71;
    public static int   C2 = 72;
    
    public static int getRandomNote(){
        Random random = new Random();
        int note = random.nextInt(C2 - C1) + C1;
        if ( DEBUG ) System.out.println("\tnrandom note: " + note);
        return note;
    }
}
