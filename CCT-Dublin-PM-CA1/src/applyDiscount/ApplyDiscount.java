// Importing relevant Java libraries
package applyDiscount;
import java.io.*;
import java.util.Scanner;
import java.time.LocalDate;

/**
 * @author Marika
 * Github repo link https://github.com/marika-cct/CCT-Dublin-ProgMaths-CA1
 */

public class ApplyDiscount {

    public static void main(String[] args) {  
        
        /** Creating Variables to hold my two text files:
         *  customerList that holds customer data we will be using
         *  and customerDiscount that will take the final output and have that data stored there
        */
        String customerList = "src/applyDiscount/customers.txt";
        String customerDiscount = "src/applyDiscount/customerDiscount.txt";
        
        try {
            // Creating an instance of File and using it read its contents with scanner
            File customers = new File(customerList);
            Scanner customersInfo = new Scanner(new FileReader(customers));
            FileWriter writer = new FileWriter(customerDiscount);
            
            while(customersInfo.hasNextLine()){
                // 1st line Customer Name (First and Surname together)
                String name = customersInfo.nextLine();

                // 2nd line Total Purchase
                String totalPurS = customersInfo.nextLine();
                
                // 3rd line Class
                String classS = customersInfo.nextLine();
                
                // 4th line Last Purchase
                String lastPurchaseS = customersInfo.nextLine();
                
                // Check for incomplete data/blank field. Skipping to next cst if data missing
                if (name.isEmpty() || totalPurS.isEmpty() || classS.isEmpty() || lastPurchaseS.isEmpty()) {
                    writer.write("Data is missing. Customer is not recognised by the system.\n");
                    continue;
                }
                
                // Creating a current year variable to save the current year
                int currentYear = LocalDate.now().getYear();
            }
            
        } catch(IOException e){
            System.out.println("IO Exception. Error wiriting new file");
        }
    }
    
    /**
     * Validating customer names
     * Customer names will be an array that will be split by " " between the two names,
     * where the [0] first name can only contain letters - no numbers, or other symbols
     * and the [1] second name can contain letters AND numbers - no symbols
     * 
     * In this method we are making sure those rules above are applied, and if not
     * we will send an error message that describes the issue.
     */
    private static boolean validName(String name){
        // Here we are taking our parameter name as an array and splitting it with the space that should be between the first and second names
        String[] names = name.split(" ");
        
        // First we are checking to make sure that the array names has two parts - the first and the second name
        if (names.length != 2){
            System.out.println("First or Second name was not found.");
            return false;
        }
        // Second we are checking if first name contains anything other than letetrs
        if (!names[0].matches("[A-Za-z]+")){
            System.out.println("First name must only contain letters");
            return false;
        }
        // Third we are checking if second name contains anything other than letters and numbers
        if (!names[1].matches("[A-Za-z0-9]+")){
            System.out.println("Second name must only contain letters and numbers");
            return false;
        }
        // If all of these pass, then we return true 
        return true;
    }
    
    /**
     * Validating total purchase
     * Customer total purchase must be a double
     * 
     * In this method we are making sure those rules above are applied, and if not
     * we will send an error message that describes the issue.
     */
    private static boolean validTotalPurchase(String totalPurS){
        try{
            // as we currently hold totalPur as string we need to parse it into double
            double totalPur = Double.parseDouble(totalPurS);
            // we must check if the purchase is 0 or less as we cannot give a discount on 0 or negative purchases , otherwise we return true
            if (totalPur <= 0){
                System.out.println("No purchases have been made.");
                return false;
            }
            return true;
        // here we are checking if there was a format error, a missing decimal point
        } catch (NumberFormatException error){
            System.out.println("Numer Format Exception. Number must have a decimal point.");
            return false;
        }
    }
    
    /**
     * Validating class
     * Customer classes can only be 1, 2 or 3
     * 
     * In this method we are making sure those rules above are applied, and if not
     * we will send an error message that describes the issue.
     */
    private static boolean validClass(String classS){
        try {
            // we need to parse class of type string it into int
            int classI = Integer.parseInt(classS);
            // we must check if class is not 1, 2 or 3, other return true
            if (classI < 1 || classI > 3) {
                System.out.println("Invalid number, Class must be 1, 2 or 3.");
                return false;
            }
            return true;
        } catch (NumberFormatException error) {
            System.out.println("Numer Format Exception. Number must be an integer.");
            return false;
        }
    }
    
     /**
     * Validating purchase year
     * We will only accept to year 2019 
     * 
     * In this method we are making sure those rules above are applied, and if not
     * we will send an error message that describes the issue.
     */
    private static boolean validYear(String lastPurchaseS, int currentYear){
        try {
            // parse our lastPurchaseS string into int
            int yearInt = Integer.parseInt(lastPurchaseS);
            if (yearInt > 2019 && yearInt <= currentYear) {
                return true;
            } else {
                System.out.println("Year must be a valid year.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Numer Format Exception. Number must be an integer.");
            return false;
        }
    }
}
