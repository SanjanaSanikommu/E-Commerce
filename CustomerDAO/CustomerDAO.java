package CustomerDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import user.user;



import java.sql.*;



public class CustomerDAO 	
{
   static Connection currentCon = null;
   static ResultSet rs = null;  
	
	
	
   public static user login(user bean) {
	
      //preparing some objects for connection 
      Statement stmt = null;    
	
      String email = bean.getUserEmail();
      String pass = bean.getPassword();   
	    
      String searchQuery =
            "select * from user where email='"
                     + email
                     + "' AND pass='"
                     + pass
                     + "'";
	    
   // "System.out.println" prints in the console; Normally used to trace the process
   System.out.println("Your user name is " + email);          
   System.out.println("Your password is " + pass);
   System.out.println("Query: "+searchQuery);
   //String query = "select gid from reg2 where email ='"+email+"'";
   
   try 
   {
      //connect to DB 
      currentCon = DBConnection.getConnection();
      stmt=currentCon.createStatement();
      rs = stmt.executeQuery(searchQuery);	        
      boolean more = rs.next();
	       
      // if user does not exist set the isValid variable to false
      if (!more) 
      {
         System.out.println("Invalid Credentials!!");
         bean.setValid(false);
      } 
	        
      //if user exists set the isValid variable to true
      else if (more) 
      {
         String firstname = rs.getString("email");
        
	     	
         //System.out.println("Welcome " + username1);
         bean.setUserEmail(firstname);
        
         bean.setValid(true);
      }
      /*rs = stmt.executeQuery(query);	        
      boolean more1 = rs.next();
	       
      // if user does not exist set the isValid variable to false
      if (!more1) 
      {
         System.out.println("Invalid Credentials!!");
         bean.setValid(false);
      } 
	        
      //if user exists set the isValid variable to true
      else if (more1) 
      {
         String firstname = rs.getString("fname");
         String lastName = rs.getString("lName");
         String contact_no = rs.getString("contact");
         String guest_id = rs.getString("gid");
	     	
         //System.out.println("Welcome " + username1);
         bean.setFname(firstname);
         bean.setLname(lastName);
         bean.setContact(contact_no);
         bean.setGid(guest_id);
         bean.setValid(true);
      }*/
   } 
   catch (Exception ex) 
   {
      System.out.println("Log In failed: An Exception has occurred! " + ex);
   } 
	    
   //some exception handling
   finally 
   {
      if (rs != null)	{
         try {
            rs.close();
         } catch (Exception e) {}
            rs = null;
         }
	
      if (stmt != null) {
         try {
            stmt.close();
         } catch (Exception e) {}
            stmt = null;
         }
	
      if (currentCon != null) {
         try {
            currentCon.close();
         } catch (Exception e) {
         }

         currentCon = null;
      }
   }

return bean;
	
   }	
}
