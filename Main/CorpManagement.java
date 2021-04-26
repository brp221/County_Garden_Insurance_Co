import java.sql.*;
import java.util.*;

public class CorpManagement {
    public void revenue_report(Connection con, String start_date, String end_date)
    {
        try{
            PreparedStatement prep_stmnt1=con.prepareStatement("select sum(amount) as total from payment where date_paid >= TO_DATE(? ,'dd-MON-yy') and date_paid <= TO_DATE(? ,'dd-MON-yy')");  
            prep_stmnt1.setString(1, start_date);
            prep_stmnt1.setString(2, end_date); 
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
            System.out.println("\n");
        }
    };
    public void claims_report(Connection con){
        {
            try{
                PreparedStatement prep_stmnt1=con.prepareStatement("select sum(amount) as total from payment where date_paid >= TO_DATE(? ,'dd-MON-yy') and date_paid <= TO_DATE(? ,'dd-MON-yy')");  
                //prep_stmnt1.setString(1, start_date);
                //prep_stmnt1.setString(2, end_date); 
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
                System.out.println("\n");
            }
        };
    }
}
