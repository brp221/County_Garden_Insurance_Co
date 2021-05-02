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

    public void profitability_by_customer(Connection con){
        try{
            String profit_by_cust = "SELECT * FROM (select claim.customer_id as customer_ID, claim.tot_amount as BILL, claim_payments.company_liability as CountyGardensLiability, claim.summary as summary from claim inner join claim_payments on claim.id = claim_payments.claim_id) lower_db inner join (select policy.customer_id, sum(payment.amount) as amount_paid from policy inner join payment on policy.id = payment.policy_id group by customer_id) higher_db  on lower_db.customer_id = higher_db.customer_id" ;
            PreparedStatement prep_stmnt1=con.prepareStatement(profit_by_cust);  
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
}
    
