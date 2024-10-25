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
        try {
            // reading files content
            File customers = new File("src/applyDiscount/customers.txt");
            Scanner customersInfo = new Scanner(new FileReader(customers));
            File customersDiscount = new File("src/applyDiscount/customerDiscount.txt");
            FileWriter writer = new FileWriter(customersDiscount);
            
            while(customersInfo.hasNextLine()){
                // 1st line Customer Name (First and last together)
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
                
                 
                //Validating customers info before we calculate the discount
                if (validName(name) && validTotalPurchase(totalPurS) && validClass(classS) && validYear(lastPurchaseS, currentYear)) {
                    //  Customer names is array that is split using the " " space between the [0] first and [1] second name
                    String[] names = name.split(" ");
                    String firstName = names[0];
                    String secondName = names[1];
                    
                    // Parse totalpur to double
                    double totalPurchase = Double.parseDouble(totalPurS);
                    
                    // Parse class to int
                    int classI = Integer.parseInt(classS);
                    
                    // Parse lastPurchase to int
                    int lastPurchase = Integer.parseInt(lastPurchaseS);
                    
                    double discountValue = discountApply(classI, lastPurchase, totalPurchase);
                    double finalDiscount = totalPurchase * discountValue;
                    
                    //Write valid result to customerdiscount.txt
                    writer.write(firstName + " " + secondName + "\n");
                    writer.write("Final Price: " + finalDiscount + "\n");
                } else {
                    writer.write("Invalid data for customer: " + name + "\n"); 
                }
            writer.close();
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
     * We will only accept to year 2010 
     * 
     * In this method we are making sure those rules above are applied, and if not
     * we will send an error message that describes the issue.
     */
    private static boolean validYear(String lastPurchaseS, int currentYear){
        try {
            // parse our lastPurchaseS string into int
            int yearInt = Integer.parseInt(lastPurchaseS);
            if (yearInt > 2010 && yearInt <= currentYear) {
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
    
    private static double discountApply(int classI, int lastPur, double totalPur){
         if(totalPur == 0){
             return 0;
         }
         if (classI == 1){
                 if(lastPur == 2024){
                     return 0.7;
                 }
                 else if (lastPur < 2024 && lastPur >= 2019){
                     return 0.8;
                 }
                 else if (lastPur < 2019){
                     return 0.9;
                 }
         }
         if (classI == 2){
             if(lastPur == 2024){
                     return 0.85;
                 }
                 else if (lastPur < 2024 && lastPur >= 2019){
                     return 0.87;
                 }
                 else if (lastPur < 2019){
                     return 0.95;
                 }
         }
         if (classI == 3){
             if(lastPur == 2024){
                     return 0.97;
                 }
         }
         return 0;
    }
                 
}
