/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securechar_server;

import java.sql.ResultSet;
import java.util.Scanner;

/**
 *
 * @author Mohammed Muayad
 */
public class DbServices {
    
    
    private DbConnection Db;
    private UserInfo user;
    private String query=null ;
    private ResultSet rs=null;
    private Scanner sc;
    
    
   public void InsertUser (UserInfo user){
        Db = new DbConnection();
        int chk=0;
         
//        query=" Insert Into TB_INFO (ID, Name, DOB ) Values ('"+Emp.getId()+"'"
//               +",'"+Emp.getName()+"','"+Emp.getDOB()+"')";
//        // Inserting Info
//         chk=Db.RunSql(query);
//         if (chk==1)
//             System.out.println(Emp.getId()+" "+Emp.getName()+" "+Emp.getDOB()
//             +"............Registered Successfully!");
//             else
//             System.out.println("Error !!!!!!!!");
         
    }
   public void deleteUser(String ID ){
       
   }
   public void updateUser(UserInfo user){
       
   }
    
    
    // Function For search 
    public UserInfo SearchId (String id){
        Db = new DbConnection();
        user= new UserInfo();
//        rs=null;
//        query="Select * from TB_INFO where Id='"+id+"' " ;
//        
//        rs=Db.Search(query);
//        try {
//            rs.next();
//            Emp.setId(rs.getString("ID"));
//            Emp.setName(rs.getString("NAME"));
//            Emp.setDOB(rs.getString("DOB"));
//        } catch (SQLException ex) {
////            Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
      return user;
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
