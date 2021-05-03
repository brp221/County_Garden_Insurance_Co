import java.sql.*;
import java.util.*;

public class CustomerAction {
    
    //add a policy given a customer id
    public boolean add_policy(Connection con,int customer_id){


        try{
            PreparedStatement prep_stmnt1=con.prepareStatement("select max(id),max(customer_id),min(customer_id) from policy");  
            ResultSet rs1=prep_stmnt1.executeQuery();  
            int policy_id = 0;
            int curr_max_cust_id = 0;
            int curr_min_cust_id = 0;
            while(rs1.next()){
                //System.out.format("  agent_id: %d ", agent_id ); 
                policy_id = 1 + rs1.getInt(1) ;
                curr_min_cust_id = rs1.getInt(2);
                curr_max_cust_id = rs1.getInt(3);

            } 
            //System.out.format("  agent_id: %d ", agent_id ); 
            if((customer_id > curr_max_cust_id)||(customer_id < curr_min_cust_id)){
                System.out.println("\nThe customer_id you have inputted does not exist ");
                return false;
            }
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

            return true;
        }
        //catches exceptions caused by incorrectly inputted login info
        catch(SQLException logginExc){
            System.out.println(logginExc);
            System.out.println("\n");
            return false;
        }
    }

    //view all beneficiaries given a policy id
    public void benfcrs_by_policy_id(Connection con, int policy_id){
        try{
            System.out.println("\n");
            PreparedStatement prep_stmnt1=con.prepareStatement("select * from beneficiary where policy_id = ?");  
            prep_stmnt1.setInt(1, policy_id);
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

    //delete a beneficiary given a customer_id and a beneficiaryid
    public void del_beneficiary(Connection con, int customer_id,int beneficiary_id)
    {   
        
        try{
            String delete_benef = "delete from (select beneficiary.id as beneficiary_id, policy.id as policy_id, policy.customer_id, policy.active,  beneficiary.overview as overview from policy inner join beneficiary on policy.id = beneficiary.policy_id ) where beneficiary_id = ? and customer_id = ?" ;
            PreparedStatement prep_stmnt1=con.prepareStatement(delete_benef);  
            prep_stmnt1.setInt(1,beneficiary_id);
            prep_stmnt1.setInt(2, customer_id);
            int deleted_row=prep_stmnt1.executeUpdate();
            System.out.format("Row Deleted: %d", deleted_row);
            System.out.println("\n");
            
        }
        //catches exceptions caused by incorrectly inputted login info
        catch(SQLException logginExc){
            System.out.println(logginExc);
            System.out.println("\n");
        }
        
    };


}
