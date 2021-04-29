/**
 * Main Class 
 * Logs the oracle user in and gives them a MENU of possible actions 
 */
import java.sql.*;
import java.util.*;

class Main {
    public static void main(String[] args) {
        boolean connected = false ;
        Scanner myScanner = new Scanner(System.in);  // Create a Scanner object
        //keep trying to connect until the user is connected
        while(!connected){
            /**Enter your Lehigh login id as your username (e.g. xyz123),
            Your initial password is an upper case P followed by your 
            student id number (e.g. P012345678). */

            System.out.println("Enter Oracle user id: "); 
            String oracleId = myScanner.next() ;
            System.out.println("Enter Oracle user password: "); 
            String oraclePassword = myScanner.next() ;
            myScanner.nextLine();
            
            String db_url = "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241" ;
            
            try {
                
                Connection con=DriverManager.getConnection(  
                    db_url, oracleId, oraclePassword);  
                if (con == null){
                    System.out.println("Connection to the DB FAILED!");
                    throw new Exception() ;
                }
                connected = true;
                //step3 create the statement object. Now you are ready to run queries. 
                Statement stmt=con.createStatement(); 
                System.out.println("----------------------------------------------------------------------------------------------");
                System.out.println("Connection to the DB SUCCESSFUL!");
                System.out.print("\n");
                System.out.print("\n");
                System.out.print("MENU\n");
                System.out.print("Each of the actions below has a corresponding number associated with it.\n");
                System.out.print("Type in the number of the desired action and click enter to be directed");
                System.out.println("\n");
                boolean active = true;
                while(active){
                    System.out.println("----------------------------------------------------------------------------------------------");
                    System.out.println("[1] Corporate Management");
                    System.out.println("[2] Customer Interaction");
                    System.out.println("[3] Agent");
                    System.out.println("[4] Adjuster");
                    System.out.println("[5] QUIT Program");
                    System.out.println("----------------------------------------------------------------------------------------------");

                    System.out.println("\n");
                    int action_id = myScanner.nextInt() ;
                    myScanner.nextLine();
                    switch (action_id) {
                        //Corporate Management
                        case 1:
                        
                            System.out.println("----------------------------------------------------------------------------------------------");
                            System.out.println("[1] Corporate Management Interface ");
                            CorpManagement corp_interface = new CorpManagement() ;
                            System.out.println("\n");
                            System.out.println("Would you like to :");
                            System.out.println("\n");
                            System.out.println("[1] Get a report of revenue for certain time frame");
                            System.out.println("[2] Get a report of paid claims");
                            System.out.println("[3] Get a report of unresolved claims ");
                            System.out.println("----------------------------------------------------------------------------------------------");
                            int choice_id = myScanner.nextInt() ;
                            myScanner.nextLine();
                            switch(choice_id){
                                case 1:
                                    System.out.println("Input starting and ending dates for a revenue report  : "); 
                                    System.out.println("Enter start date in Oracle standard format (dd-MON-yy): "); 
                                    String start_date = myScanner.nextLine() ;
                                    //keep asking if the date format isn't correct
                                    while(!format_check(start_date)){
                                        System.out.println("The start date should be in (dd-Mon-yyyy) format: ");
                                        start_date = myScanner.nextLine() ;
                                    }
                                    System.out.println("Enter end date in Oracle standard format (dd-Mon-yyyy): "); 
                                    String end_date = myScanner.nextLine() ;
                                    while(!format_check(end_date)){
                                        System.out.println("The end date should be in (dd-Mon-yyyy) format: ");
                                        end_date = myScanner.nextLine() ;
                                    }
                                    corp_interface.revenue_report(con, start_date, end_date);
                                    break;
                                case 2:
                                    System.out.println("Claims Resolved Report :\n");
                                    corp_interface.claims_resolved_report(con);
                                    break;
                                case 3:
                                    System.out.println("Claims Ongoing Report :\n");
                                    corp_interface.claims_ongoing_report(con);
                                    break;
                            }
                            break;
                            
                        //Customer Interaction
                        case 2:
                            System.out.println("----------------------------------------------------------------------------------------------");
                            System.out.println("[1] Customer Interaction Interface");
                            CustomerAction cust_interface = new CustomerAction() ;
                            System.out.println("\n");
                            System.out.println("Would you like to :");
                            System.out.println("\n");
                            System.out.println("[1] Add a policy");
                            System.out.println("[2] View current beneficiaries by policy_id");
                            System.out.println("[3] Delete a beneficiary");
                            System.out.println("----------------------------------------------------------------------------------------------");
                            int choice_id_2= myScanner.nextInt() ;
                            myScanner.nextLine();
                            switch(choice_id_2){
                                case 1:
                                    System.out.println("To add your policy, please give me your unique customer ID "); 
                                    System.out.println("(IN AN ACTUAL APPLICATION THIS IS WHERE AUTHENTICATION TAKES PLACE 0:) \n instead I will REMIND you what your customer ID is "); 
                                    System.out.println("\n");
                                    try{
                                        PreparedStatement prep_stmnt3=con.prepareStatement("select * from customer");  
                                        ResultSet rs3=prep_stmnt3.executeQuery();  
                                        ResultSetMetaData rsmd3 = rs3.getMetaData();
                                        int column_numb = rsmd3.getColumnCount();
                                        for(int i = 1; i <= column_numb; i++){
                                            System.out.print(rsmd3.getColumnName(i) + "   "); //prints 
                                        }
                                        System.out.print("\n");
                                        while(rs3.next()){
                                            for(int i = 1; i <= column_numb; i++){
                                                System.out.print(rs3.getString(i) + "          "); //prints 
                                            }
                                            System.out.println(); 
                                        } 
                                    }
                                    //catches exceptions caused by incorrectly inputted login info
                                    catch(SQLException logginExc){
                                        //System.out.println(logginExc);
                                        System.out.println("\n");
                                    }
                                    System.out.println("\n");
                                    System.out.println("Type in your customer_id number :");
                                    int customer_id = myScanner.nextInt();
                                    cust_interface.add_policy(con, customer_id);
                                    break;
                                //View current beneficiaries by policy_id
                                case 2:
                                    System.out.println("\n");
                                    try{
                                        PreparedStatement prep_stmnt3=con.prepareStatement("select * from policy");  
                                        ResultSet rs3=prep_stmnt3.executeQuery();  
                                        ResultSetMetaData rsmd3 = rs3.getMetaData();
                                        int column_numb = rsmd3.getColumnCount();
                                        for(int i = 1; i <= column_numb; i++){
                                            System.out.print(rsmd3.getColumnName(i) + "   "); //prints 
                                        }
                                        System.out.print("\n");
                                        while(rs3.next()){
                                            for(int i = 1; i <= column_numb; i++){
                                                System.out.print(rs3.getString(i) + "          "); //prints 
                                            }
                                            System.out.println(); 
                                        } 
                                    }
                                    //catches exceptions caused by incorrectly inputted login info
                                    catch(SQLException logginExc){
                                        //System.out.println(logginExc);
                                        System.out.println("\n");
                                    }
                                    System.out.println("To view all of your beneficiaries, please give me any of your unique policy ID "); 
                                    System.out.println("\n");
                                    int policy_id = myScanner.nextInt();
                                    cust_interface.benfcrs_by_policy_id(con, policy_id);
                                    break ;
                                //Delete a beneficiary
                                case 3:
                                    
                                System.out.println(); 
                                    try{
                                        PreparedStatement prep_stmnt3=con.prepareStatement("select * from customer");  
                                        ResultSet rs3=prep_stmnt3.executeQuery();  
                                        ResultSetMetaData rsmd3 = rs3.getMetaData();
                                        int column_numb = rsmd3.getColumnCount();
                                        for(int i = 1; i <= column_numb; i++){
                                            System.out.print(rsmd3.getColumnName(i) + "   "); //prints 
                                        }
                                        System.out.print("\n");
                                        while(rs3.next()){
                                            for(int i = 1; i <= column_numb; i++){
                                                System.out.print(rs3.getString(i) + "   "); //prints 
                                            }
                                            System.out.println(); 
                                        } 
                                        System.out.println(); 
                                    }
                                    //catches exceptions caused by incorrectly inputted login info
                                    catch(SQLException logginExc){
                                        System.out.println(logginExc);
                                        //System.out.println("\n");
                                    }
                                    //prompt user to give customer_id
                                    System.out.println("Please type in your customer_id so that we can display all of your beneficiaries:"); 
                                    int customer_id2 = myScanner.nextInt() ;
                                    try{
                                        
                                        
                                        String sql_query = "select policy.id as policy_id, policy.customer_id, policy.active, beneficiary.id as beneficiary_id, beneficiary.overview as overview from policy inner join beneficiary on policy.id = beneficiary.policy_id where policy.customer_id = ?" ;
                                        PreparedStatement prep_stmnt1 = con.prepareStatement(sql_query);
                                        
                                        prep_stmnt1.setInt(1,customer_id2);
                                        ResultSet rs1=prep_stmnt1.executeQuery();  
                                        
                                        ResultSetMetaData rsmd1 = rs1.getMetaData();
                                        int column_numb = rsmd1.getColumnCount();
                                        for(int i = 1; i <= column_numb; i++){
                                            System.out.print(rsmd1.getColumnName(i) + "   "); //prints 
                                        }
                                        System.out.print("\n");
                                        while(rs1.next()){
                                            for(int i = 1; i <= column_numb; i++){
                                                System.out.print(rs1.getString(i) + "              "); //prints 
                                            }
                                            System.out.println(); 
                                        } }
                                    catch(SQLException exc){
                                        System.out.println(exc);
                                    }
                                    System.out.println(); 
                                    //prompt user to give customer_id
                                    System.out.println("Please type the ID of the beneficiary you'd like to delete :"); 
                                    int ben_id = myScanner.nextInt() ;
                                    cust_interface.del_beneficiary(con, customer_id2,ben_id);
                                    break;
                            //Add a policy
                            
                                }
                            break;
                        
                        //Agent
                        case 3:
                            System.out.println("----------------------------------------------------------------------------------------------");
                            System.out.print("[3] Agent");
                            Agent agent_intrface = new Agent() ;
                            System.out.println("\n");
                            System.out.println("Would you like to :");
                            System.out.println("\n");
                            System.out.println("[1] Get customers with pending claims ");
                            System.out.println("[2] Deactivate a customer's policy");
                            System.out.println("[3] Remove a customer from the database");
                            System.out.println("----------------------------------------------------------------------------------------------");
                            int choice_id_3= myScanner.nextInt() ;
                            myScanner.nextLine();
                            switch(choice_id_3){
                                //get customers with pending claims
                                case 1:
                                    agent_intrface.customers_pending_claims(con);
                                    break;
                                //deactivate a customer's policy
                                case 2:
                                    System.out.print("\n");
                                    try{
                                        PreparedStatement prep_stmnt3=con.prepareStatement("select * from policy");  
                                        ResultSet rs3=prep_stmnt3.executeQuery();  
                                        ResultSetMetaData rsmd3 = rs3.getMetaData();
                                        int column_numb = rsmd3.getColumnCount();
                                        for(int i = 1; i <= column_numb; i++){
                                            System.out.print(rsmd3.getColumnName(i) + "   "); //prints 
                                        }
                                        System.out.print("\n");
                                        while(rs3.next()){
                                            for(int i = 1; i <= column_numb; i++){
                                                System.out.print(rs3.getString(i) + "          "); //prints 
                                            }
                                            System.out.println(); 
                                        } 
                                    }
                                    //catches exceptions caused by incorrectly inputted login info
                                    catch(SQLException logginExc){
                                        //System.out.println(logginExc);
                                        System.out.println("\n");
                                    }
                                    System.out.println("Type in the policy_id which you want to deactivate: ");
                                    System.out.print("\n");
                                    int policy_id= myScanner.nextInt() ;
                                    // debugging: System.out.format("policy_id is :%d ", policy_id);
                                    agent_intrface.deactvt_policy(con,policy_id);
                                    try{
                                        PreparedStatement prep_stmnt3=con.prepareStatement("select * from policy");  
                                        ResultSet rs3=prep_stmnt3.executeQuery();  
                                        ResultSetMetaData rsmd3 = rs3.getMetaData();
                                        int column_numb = rsmd3.getColumnCount();
                                        for(int i = 1; i <= column_numb; i++){
                                            System.out.print(rsmd3.getColumnName(i) + "   "); //prints 
                                        }
                                        System.out.print("\n");
                                        while(rs3.next()){
                                            for(int i = 1; i <= column_numb; i++){
                                                System.out.print(rs3.getString(i) + "          "); //prints 
                                            }
                                            System.out.println(); 
                                        } 
                                    }
                                    //catches exceptions caused by incorrectly inputted login info
                                    catch(SQLException logginExc){
                                        //System.out.println(logginExc);
                                        System.out.println("\n");
                                    }
                                    break;
                                //remove a customer from the database    
                                case 3:
                            }
                            break;
                        
                        //Adjuster
                        case 4:
                            System.out.println("----------------------------------------------------------------------------------------------");
                            System.out.print("[4] Adjuster");
                            Adjuster adjuster_intrf = new Adjuster() ;
                            System.out.println("\n");
                            System.out.println("Would you like to :");
                            System.out.println("\n");
                            System.out.println("[1] Assign Outsourcing entities to a claim");
                            System.out.println("----------------------------------------------------------------------------------------------");
                            int choice_id_4= myScanner.nextInt() ;
                            myScanner.nextLine();
                            switch(choice_id_4){
                                case 1:
                                    try{
                                        PreparedStatement prep_stmnt1=con.prepareStatement("select * from claim");  
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
                                    System.out.println("\n");
                                    adjuster_intrf.assign_outcrng(con);
                                    break;
                            }
                            break;
                        case 5:
                            System.out.println("Are you sure that you want to quit :'(  ?");
                            System.out.println("[y] \n[n]");
                            String answer = myScanner.nextLine();
                            if(answer.equals("y")){
                                active = false;
                            }
                    }
                    
                }
                //last step close the connection object  
                con.close();  
                
                    
            }
            //catches exceptions caused by incorrectly inputted query parameters
            catch(SQLDataException dataExc){
                //System.out.println(dataExc);
                System.out.println("The User has inputted invalid data. Double check that the start and end dates");
                System.out.println("you have inputted are real dates between  04-Jan-2010  and  30-Dec-2016. \n");
                
            }
            //catches exceptions caused by incorrectly inputted login info
            catch(SQLException logginExc){
                //System.out.println(logginExc);
                System.out.println("The User has inputted invalid login information. Double check that your username");
                System.out.println("and password are correct. Password's are usually \"P[insertStudentLIN]\".\n");
                
            }
            //ambiguous exception 
            catch(Exception e){ 
                //print stack trace for debugging purposes
                System.out.println(e);
                e.printStackTrace();
            }  
        }   
     
    }

    //helper function for verifying the format of date inputted by user
    public static boolean format_check(String date){
        try{
            if(!Character.isDigit(date.charAt(0)) || 
                !Character.isDigit(date.charAt(1)) ||
                !Character.isDigit(date.charAt(7)) ||
                !Character.isDigit(date.charAt(8)) ||
                !Character.isDigit(date.charAt(9)) ||
                !Character.isDigit(date.charAt(10))){
            System.out.println("The first two and the last 4 characters should be a digit\n");
            System.out.println("Are all of these chars?: " + date.charAt(0) + " " + date.charAt(1)+ " " + date.charAt(7) + " " + date.charAt(8)+ " " + date.charAt(9) + " " + date.charAt(10)+ "\n");
            return false ;
            }
            //day as int
            int day = Integer.parseInt(date.substring(0, 2));
            //year as int 
            int year = Integer.parseInt(date.substring(7,date.length()));
            if(day>31 || day <1){
                System.out.println("The day should be between 1 & 31");
                System.out.println("day: " + day + " year: " + year + "\n");
                return false;
            }
            //list and array for checking months
            List<String> monthList = Arrays.asList( "Jan", "Feb", "Mar","Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
            ArrayList<String> months = new ArrayList<String>();
            //fill arraylist with list
            months.addAll(monthList);
            String inputtedMonth = date.substring(3, 6) ;
            if(!months.contains(inputtedMonth)){
                System.out.println("Months have a very specific format: ");
                System.out.println(months + "\n");
            return false ;
            }
            //list and array for checking day is valid w respect to month
            List<Integer> dayMonthList = Arrays.asList( 31,     28,     31,  30,     31,    30,    31,     31,   30,    31,   30,    31);
            ArrayList<Integer> daysMonth = new ArrayList<Integer>();
            //fill arraylist with list
            daysMonth.addAll(dayMonthList);
            int month_index = months.indexOf(inputtedMonth);
            
            if(day>daysMonth.get(month_index)){
                System.out.println("\nYou have entered invalid day and month combination ");
                System.out.println("The month of " + inputtedMonth + " has "+ daysMonth.get(month_index) + " days and not " + day + " as you have entered \n");
                return false;
            }
            if(Character.compare(date.charAt(2),'-') != 0 || 
            Character.compare(date.charAt(6),'-') != 0 ){
                System.out.println("Days, months and years should be separated by two hyphens\n");
            return false ;
            }
        
            return true;
        }    
        //incorrect formatting of the date
        catch(StringIndexOutOfBoundsException exc){
            System.out.println("\nThe User has inputted invalid data. Double check that the start and end dates");
            System.out.println("you have inputted are real dates of the following format dd-Mon-yy. dd and yy should be digits!! \n");
            return false ;
        }
    
        catch(NumberFormatException exc2){
            System.out.println("\nThe User has inputted invalid data. Double check that the start and end dates");
            System.out.println("you have inputted are real dates of the following format dd-Mon-yy. dd and yy should be digits!! \n");
            return false ;
        }
    }

}