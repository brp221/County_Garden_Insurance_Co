import java.sql.*;
import java.util.*;

public class CustomerAction {
    public void add_customer(Connection con, String bio)
    {
        try{
            PreparedStatement prep_stmnt1=con.prepareStatement("select max(id) from customer");  
            ResultSet rs1=prep_stmnt1.executeQuery();  
            int agent_id = 0;
            while(rs1.next()){
                //System.out.format("  agent_id: %d ", agent_id ); 
                agent_id = 1 + rs1.getInt(1) ;
            } 
            //System.out.format("  agent_id: %d ", agent_id ); 
            PreparedStatement prep_stmnt2=con.prepareStatement("select max(id) from customer");  
            ResultSet rs2=prep_stmnt2.executeQuery();  
            int customer_id = 0;
            while(rs2.next()){
                //System.out.format("  Customer_id: %d ", customer_id ); 
                customer_id = 1 + rs2.getInt(1) ;
            } 
            //System.out.format("  Customer_id: %d ", customer_id ); 
            
            String sql = "INSERT INTO CUSTOMER(ID,BIO,AGENT_ID) VALUES(?,?,?) " ;
            PreparedStatement prep_stmnt3=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  
            prep_stmnt3.setInt(1, customer_id);
            prep_stmnt3.setString(2, bio); 
            prep_stmnt3.setInt(3, agent_id);
            int rs3=prep_stmnt3.executeUpdate();  
            if(rs3 == 1){
                //yay successfully inserted into database
                PreparedStatement prep_stmnt4=con.prepareStatement("SELECT * FROM customer where id = ?");  
                prep_stmnt4.setInt(1, customer_id);
                ResultSet rs4=prep_stmnt4.executeQuery();  
                ResultSetMetaData rsmd = rs4.getMetaData();
                int column_numb = rsmd.getColumnCount();
                for(int i = 1; i <= column_numb; i++){
                    System.out.print(rsmd.getColumnName(i) + "   "); //prints 
                }
                System.out.print("\n");
                while(rs4.next()){
                    for(int i = 1; i <= column_numb; i++){
                        System.out.print(rs4.getString(i) + "   "); //prints 
                    }
                    System.out.println(); 
                } 
            }
            

        }
        //catches exceptions caused by incorrectly inputted login info
        catch(SQLException logginExc){
            System.out.println(logginExc);
            System.out.println("\n");
        }
    };

    public void add_policy(Connection con,int customer_id){


        try{
            PreparedStatement prep_stmnt1=con.prepareStatement("select max(id) from policy");  
            ResultSet rs1=prep_stmnt1.executeQuery();  
            int policy_id = 0;
            while(rs1.next()){
                //System.out.format("  agent_id: %d ", agent_id ); 
                policy_id = 1 + rs1.getInt(1) ;
            } 
            //System.out.format("  agent_id: %d ", agent_id ); 


            String sql = "INSERT INTO POLICY(ID,CUSTOMER_ID,ACTIVE) VALUES(?,?,?) " ;
            PreparedStatement prep_stmnt3=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  
            prep_stmnt3.setInt(1, policy_id);
            prep_stmnt3.setInt(2, customer_id); 
            prep_stmnt3.setString(3, "YES");
            int rs3=prep_stmnt3.executeUpdate();  
            if(rs3 == 1){
                //yay successfully inserted into database
                PreparedStatement prep_stmnt4=con.prepareStatement("SELECT * FROM policy where id = ?");  
                prep_stmnt4.setInt(1, policy_id);
                ResultSet rs4=prep_stmnt4.executeQuery();  
                ResultSetMetaData rsmd = rs4.getMetaData();
                int column_numb = rsmd.getColumnCount();
                for(int i = 1; i <= column_numb; i++){
                    System.out.print(rsmd.getColumnName(i) + "   "); //prints 
                }
                System.out.print("\n");
                while(rs4.next()){
                    for(int i = 1; i <= column_numb; i++){
                        System.out.print(rs4.getString(i) + "   "); //prints 
                    }
                    System.out.println(); 
                } 
            }

        }
        //catches exceptions caused by incorrectly inputted login info
        catch(SQLException logginExc){
            System.out.println(logginExc);
            System.out.println("\n");
        }
    }
}