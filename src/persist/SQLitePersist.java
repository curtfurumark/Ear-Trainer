/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persist;

import java.sql.SQLException;
import persist.IPersist;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author curtr
 */
public class SQLitePersist implements IPersist{

    private SQLiteDB1 db = new SQLiteDB1();

    public SQLitePersist() throws ClassNotFoundException, SQLException {
        db.init();
    }
    
    @Override
    public void persist(int firstNote, int secondNote, int guessedNote) {
        DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //String.format("firstNote: %d, secondNote: %d, guessedNote: %d", firstNote, secondNote, guessedNote);
        String insertQuery = String.format("insert into statistics(user, firstNote, secondNote, guessedNote, timeStamp)values('%s', %d, %d, %d, '%s')",
                "curt", firstNote, secondNote,guessedNote, LocalDateTime.now().format(formatter).toString());
        System.out.println("query: " + insertQuery);
        db.insert(insertQuery);
    }   
}
