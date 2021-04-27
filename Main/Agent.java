import java.sql.*;
import java.util.*;

public class Agent {
    public void customers_pending_claims(Connection con)
    {
        try{
            PreparedStatement prep_stmnt1=con.prepareStatement("select customer.ID,customer.bio from customer inner join claim on customer.id = claim.customer_id where claim.status = 'ONGOING'");  
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
        }
        //catches exceptions caused by incorrectly inputted login info
        catch(SQLException logginExc){
            System.out.println(logginExc);
            System.out.println("\n");
        }
    };
}
