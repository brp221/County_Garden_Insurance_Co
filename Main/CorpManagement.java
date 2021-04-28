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
    }
    public void claims_resolved_report(Connection con){
        {
            System.out.println(); 
            System.out.println(); 
            try{
                PreparedStatement prep_stmnt1=con.prepareStatement("select * from claim where status = 'RESOLVED' ");  
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
                System.out.println(); 
                System.out.println(); 
            
                /**RESOLVED CLAIMS THAT HAVE BEEN PAID BY THE COMPANY */
                PreparedStatement prep_stmnt3=con.prepareStatement("SELECT SUM(TOT_AMOUNT) FROM claim inner join claim_payments on claim_payments.id = claim.id where claim.status = 'RESOLVED' ");  
                ResultSet rs3=prep_stmnt3.executeQuery();  
                while(rs3.next()){
                    System.out.format("Total Claims paid by County Gardens : %f \n", rs3.getDouble(1)); 
                    System.out.format("Total Claims paid by County Gardens : %f \n", rs3.getFloat(1)); 
                } 

                /**ALL CLAIMS*/
                PreparedStatement prep_stmnt4=con.prepareStatement("select sum(tot_amount) from claim");  
                ResultSet rs4=prep_stmnt4.executeQuery();  
                while(rs4.next()){
                    System.out.format("Total amount in Claims (all time) : %f \n", rs4.getDouble(1)); 
                } 
            }
            //catches exceptions caused by incorrectly inputted login info
            catch(SQLException logginExc){
                //System.out.println(logginExc);
                System.out.println("\n");
            }
        };
    }
    public void claims_ongoing_report(Connection con){
    try{

        PreparedStatement prep_stmnt2=con.prepareStatement("select * from claim where status = 'ONGOING' ");  
        ResultSet rs2=prep_stmnt2.executeQuery();  
        ResultSetMetaData rsmd2 = rs2.getMetaData();
        int column_numb2 = rsmd2.getColumnCount();
        for(int i = 1; i <= column_numb2; i++){
            System.out.print(rsmd2.getColumnName(i) + "   "); //prints 
        }
        System.out.print("\n");
        while(rs2.next()){
            for(int i = 1; i <= column_numb2; i++){
                System.out.print(rs2.getString(i) + "   "); //prints 
            }
            System.out.println(); 
        } 
        System.out.println(); 
        System.out.println(); 
        PreparedStatement prep_stmnt1=con.prepareStatement("select sum(tot_amount) from claim where status = 'ONGOING' ");  
        ResultSet rs1=prep_stmnt1.executeQuery();  
        while(rs1.next()){
            System.out.format("Total amount in unresolved claims: %f \n", rs1.getDouble(1)); 
        } 
    }
    //catches exceptions caused by incorrectly inputted login info
    catch(SQLException logginExc){
        //System.out.println(logginExc);
        System.out.println("\n");
    }
    };

}
