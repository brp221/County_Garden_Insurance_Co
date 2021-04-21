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
                System.out.println("Connection to the DB SUCCESSFUL!");
                System.out.println("Input starting and ending dates for stock price data: "); 
                System.out.println("Enter start date in Oracle standard format (dd-MON-yy): "); 
                //String startDate = myScanner.nextLine() ;
                //String endDate = myScanner.nextLine() ;
                /*
                //while -> if?
                while(!format_check(startDate)){
                    System.out.println("The start date should be in (dd-MON-yy) format: ");
                    startDate = myScanner.nextLine() ;
                }
                System.out.println("Enter end date in Oracle standard format (dd-MON-yy): "); 
                String endDate = myScanner.nextLine() ;
                while(!format_check(endDate)){
                    System.out.println("The end date should be in (dd-MON-yy) format: ");
                    endDate = myScanner.nextLine() ;
                }
                **/
                //step3 create the statement object  
                Statement stmt=con.createStatement();  
                
                //step4 execute queries
                
                //query 1: getting number of trading days between 2 dates
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

                //last step close the connection object  
                con.close();  
                    
            }
            //catches exceptions caused by incorrectly inputted query parameters
            catch(SQLDataException dataExc){
                //System.out.println(dataExc);
                System.out.println("The User has inputted invalid data. Double check that the start and end dates");
                System.out.println("you have inputted are real dates between  04-Jan-2010  and  30-Dec-2016. \n");
                
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
                !Character.isDigit(date.charAt(8)) ){
            System.out.println("The first two and the last 2 characters should be a digit\n");
            System.out.println("Are all of these chars?: " + date.charAt(0) + " " + date.charAt(1)+ " " + date.charAt(7) + " " + date.charAt(8)+ "\n");
            return false ;
            }
            //day as int
            int day = Integer.parseInt(date.substring(0, 2));
            //year as int 
            int year = Integer.parseInt(date.substring(7,date.length()));
            if(day>31 || day <1 || year<10 || year>16){
                System.out.println("The day should be between 1 & 31 and the year should be between 10 & 16\n");
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
    }
    }