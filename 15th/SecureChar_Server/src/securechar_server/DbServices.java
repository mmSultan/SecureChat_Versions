/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securechar_server;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
 

/**
 *
 * @author Mohammed Muayad
 */
public class DbServices {
    
    
    private static final DbConnection Db= new DbConnection();
    private UserInfo USER;
    private String query=null ;
    private ResultSet rs=null;

    public DbServices() {
      
    }
    
    
   public int InsertUser (UserInfo USER){
     //   Db = new DbConnection();
        int chk=0;
         
        query=" Insert Into userInfoTbl (ID, password, fullName, DOB,DpName,admin ) Values ('"+USER.getUserID()+"'"
               +",'"+USER.getPass()+"','"+USER.getFullName()+"','"+USER.getDOB()+"','"+USER.getDepartment()+"','"+USER.getAdmin()+"')";
        // Inserting Info
         chk=Db.RunSql(query);
         if (chk==1){
             JOptionPane.showMessageDialog(null, USER.getUserID()+" "+USER.getFullName()+" "+USER.getDOB()
             +"............Registered Successfully!");
         } else
            JOptionPane.showMessageDialog(null,"Error... Insertion Faild");
         
         return chk;
    }
   public void deleteUser(String ID ){
    //   Db = new DbConnection();
       query="Delete from userInfoTbl where ID='"+ID+"'";
       Db.RunSql(query);
   }
   
   
   public int updateUser(UserInfo USER){
       // Db = new DbConnection();
        int chk;
         //ID='"+USER.getUserID()+"',
        query="update  userInfoTbl set  password='"+USER.getPass()+"', fullName='"+USER.getFullName()+
                "', DOB='"+USER.getDOB()+"',DpName='"+USER.getDepartment()+"' where ID='"+USER.getUserID()+"'  ";
        // Inserting Info
         chk=Db.RunSql(query);
         if (chk==1){
             JOptionPane.showMessageDialog(null, "Update.....Successfully!");
         } else
            JOptionPane.showMessageDialog(null,"Error... Update Faild");
         
         return chk;
       
   }
    
    
    // Function For search 
    public UserInfo SearchId (String id){
     //   Db = new DbConnection();
        USER= new UserInfo();
        rs=null;
        query="Select * from userInfoTbl where Id='"+id+"' " ;
        
        rs=Db.Search(query);
        try {
            rs.next();
            USER.setUserID(rs.getString("ID"));
            USER.setPass(rs.getString("password"));
            USER.setFullName(rs.getString("fullName"));
            USER.setDOB(rs.getString("DOB"));
            USER.setDepartment(rs.getString("DpName"));
            USER.setAdmin(rs.getString("admin"));
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex);
            System.out.println(ex);
         }
        
      return USER;
    }
    
    public ResultSet all (){
      //  Db = new DbConnection();
        rs=null;
        query="Select ID,fullName,DOB,DpName from userInfoTbl " ;
        rs=Db.Search(query);
        return rs;
    } 
    
    
}
