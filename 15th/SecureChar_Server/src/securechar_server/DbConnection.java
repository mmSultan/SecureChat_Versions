/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securechar_server;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Mohammad
 */
public class DbConnection {
    private static Connection cn=null;
    private Statement st=null;
    private ResultSet rs=null;
    
    // Constructor 1
    public DbConnection ()
    {
        
    // Initial the connection with the DataBase
    try {
        Class.forName("org.sqlite.JDBC");
         cn = DriverManager.getConnection("jdbc:sqlite:SecureChatDB.sqlite");
         st=cn.createStatement();
    } catch ( ClassNotFoundException | SQLException e ) {
        JOptionPane.showMessageDialog(null, e);
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        //System.exit(0);
      }
      System.out.println("Opened database successfully");
       
    }
    
    
    // Function for Insert, Update, and Delete ;
    public int RunSql (String query){
        int chk=0;
        try {
            chk=st.executeUpdate(query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chk;
    }
    
    
    // Function For Search ( Select query )
    public ResultSet Search (String query){
        rs=null;
        try {
            rs=st.executeQuery(query);
            
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
        
    }
}
