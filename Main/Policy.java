import java.sql.*;
import java.util.*;

public class Policy {
    public void printPolicy(Connection con){
        try{
            PreparedStatement prep_stmnt1=con.prepareStatement("SELECT * FROM POLICY ");  
            ResultSet rs1=prep_stmnt1.executeQuery();  
            ResultSetMetaData rsmd = rs1.getMetaData();
            int column_numb = rsmd.getColumnCount();
            for(int i = 1; i <= column_numb; i++){
                System.out.print(rsmd.getColumnName(i) + "   "); //prints 
            }
            System.out.print("\n");
            while(rs1.next()){
                for(int i = 1; i <= column_numb; i++){
                    System.out.print(rs1.getString(i) + "   "); //prints 
                }
                System.out.println(); 
            } 
        }
        //catches exceptions caused by incorrectly inputted login info
        catch(SQLException logginExc){
            //System.out.println(logginExc);
            System.out.println("The User has inputted invalid login information. Double check that your username");
            System.out.println("and password are correct. Password's are usually \"P[insertStudentLIN]\".\n");
            
        }
    };
}
