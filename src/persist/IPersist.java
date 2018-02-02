/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persist;

/**
 *
 * @author curtr
 */
public interface IPersist {
    public void persist(int firstNote, int secondNote, int guessedNote);
}
