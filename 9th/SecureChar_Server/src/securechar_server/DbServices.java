/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securechar_server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JOptionPane;
 

/**
 *
 * @author Mohammed Muayad
 */
public class DbServices {
    
    
    private DbConnection Db;
    private UserInfo Emp;
    private String query=null ;
    private ResultSet rs=null;
    
    
   public int InsertUser (UserInfo Emp){
        Db = new DbConnection();
        int chk=0;
         
        query=" Insert Into userInfoTbl (ID, password, fullName, DOB,DpName,admin ) Values ('"+Emp.getUserID()+"'"
               +",'"+Emp.getPass()+"','"+Emp.getFullName()+"','"+Emp.getDOB()+"','"+Emp.getDepartment()+"','"+Emp.getAdmin()+"')";
        // Inserting Info
         chk=Db.RunSql(query);
         if (chk==1){
             JOptionPane.showMessageDialog(null, Emp.getUserID()+" "+Emp.getFullName()+" "+Emp.getDOB()
             +"............Registered Successfully!");
         } else
            JOptionPane.showMessageDialog(null,"Error... Insertion Faild");
         
         return chk;
    }
   public void deleteUser(String ID ){
       
   }
   public void updateUser(UserInfo user){
       
   }
    
    
    // Function For search 
    public UserInfo SearchId (String id){
        Db = new DbConnection();
        Emp= new UserInfo();
        rs=null;
        query="Select * from userInfoTbl where Id='"+id+"' " ;
        
        rs=Db.Search(query);
        try {
            rs.next();
            Emp.setUserID(rs.getString("ID"));
            Emp.setPass(rs.getString("password"));
            Emp.setFullName(rs.getString("fullName"));
            Emp.setDOB(rs.getString("DOB"));
            Emp.setDepartment(rs.getString("DpName"));
            Emp.setAdmin(rs.getString("admin"));
            
        } catch (SQLException ex) {
            System.out.println(ex);
         }
        
      return Emp;
    }
    
    // List All Emoloyee
//    public ResultSet ListAll (){
//        Db = new DbConnection();
//        rs=null;
//        query="Select ID,Name,DOB from TB_INFO";
//        rs=Db.Search(query);
//      return rs;
//    }
    
}
