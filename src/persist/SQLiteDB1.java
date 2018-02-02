/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author curt
 */
public class SQLiteDB1 {
    private Connection connection;
    private final String driverName = "org.sqlite.JDBC"; 
    private final String dbUrl = "jdbc:sqlite:cfdb.db";
    private static final boolean DEBUG = false;
        
    public void init() throws ClassNotFoundException, SQLException{
        System.out.println("SQLiteDB.init()");
          Class.forName(driverName);
        connection = DriverManager.getConnection(dbUrl);
        String query  = "CREATE TABLE IF NOT EXISTS statistics "
                + "(user varchar(12), "
                + "firstNote int, "
                + "secondNote int, "
                + "guessedNote int, "
                + "timeStamp varchar(12) )";
        Statement statement = connection.createStatement();
        boolean stat  = statement.execute(query);
        if (DEBUG)System.out.println("\tresult of create todo table: " + stat);
    }
    public boolean insert(String query){
        boolean stat = false;
        try {
            if (DEBUG){System.out.println("SQLiteDB.insert " + query);}
            Statement statement = connection.createStatement();
            statement.execute(query);
            stat = true;
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteDB1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stat;
    }
    public ResultSet executeQuery(String query){
        ResultSet res  = null;
        try {
            if (DEBUG){System.out.println("SQLiteDB.executeQuery " + query);}
            Statement statement = connection.createStatement();
            res = statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteDB1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    public boolean update(String query){
        boolean stat = false;
        try {
            if (DEBUG){System.out.println("SQLiteDB.update " + query);}
            Statement statement = connection.createStatement();
            int whatever = statement.executeUpdate(query);
            if ( DEBUG)System.out.println("\texecuteUpdate: " + whatever);
            stat = true;
        } catch (SQLException ex) {
            stat = true;
            Logger.getLogger(SQLiteDB1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stat;
    
    }

    public SQLiteDB1() {
    }
    
    public void addColumnLastUpdate(){
        System.out.println("addColumnLastUpdate()");
        String query = "alter  table cf_todo add column lastUpdate varchar(15)";
        update(query);
    }
   

    public static void main(String[] args){
        try {
            SQLiteDB1 db = new SQLiteDB1();
            db.init();
            db.update("alter table cf_todo add column date varchar(10)");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQLiteDB1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteDB1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
