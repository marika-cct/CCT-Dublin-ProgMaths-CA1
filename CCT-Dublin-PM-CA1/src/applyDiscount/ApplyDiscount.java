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
            File customers = new File("customers.txt");
            Scanner customersInfo = new Scanner(new FileReader(customers));
            File customersDiscount = new File("customerDiscount.txt");
            FileWriter writer = new FileWriter(customersDiscount);

            while (customersInfo.hasNextLine()) {
                // Assigning line 1 in the loop as name
                String nameS = customersInfo.nextLine();

                // Assigning line 2 in the loop as total
                String totalS = customersInfo.nextLine();

                // Assigning line 3 in the loop as class
                String classS = customersInfo.nextLine();

                // Assigning line 4 in the loop as year
                String yearS = customersInfo.nextLine();

                // We are checking if any of the fields above are empty, and printing en error messages directly
                // explaining what field is missing
                if (nameS.isEmpty()){
                    System.out.println("Name field is empty. Please input your first and second name.");
                }
                else if (totalS.isEmpty()) {
                    System.out.println("Total purchase field is missing. Please input your total purchase");
                }
                else if (classS.isEmpty()) {
                    System.out.println("Class field is empty. Please input a class of 1, 2 or 3.");
                }
                else if (yearS.isEmpty()) {
                    System.out.println();
                }

                /** This is another solution however we dont get a specific error message that would notify the user what
                 *  exact field they are missing.
                 *
                 * if (nameS.isEmpty() || totalS.isEmpty() || classS.isEmpty() || yearS.isEmpty()) {
                 *     System.out.println("Data file is empty.");
                 * }
                */

                // Using our validation methods to make sure inputs are correct type, lenght or format, depending on
                // the field .
                if (validName(nameS) && validTotalPurchase(totalS) && validClass(classS) && validYear(yearS)) {
                    //  Names is array or two items, that is split using the " " space between the [0] first and
                    //  [1] second name.
                    String[] names = nameS.split(" ");
                    String firstName = names[0];
                    String secondName = names[1];

                    // Parse total purchase to double
                    double totalPurchase = Double.parseDouble(totalS);

                    // Parse class to int
                    int classI = Integer.parseInt(classS);

                    // Parse last purchase to int
                    int lastPurchase = Integer.parseInt(yearS);

                    // Calculating the discount value of customers & initialising finalDiscount to use for our final
                    // output to the user and text file
                    double discountValue = discountApply(classI, lastPurchase, totalPurchase);
                    double finalDiscount;

                    // First we are checking if the discount value is 0. We then will print a personalised message to
                    // users, that will tell them that they did not receive any discount.
                    if (discountValue == 0){
                        finalDiscount = totalPurchase;

                        //Writing a custom message to let user know that their final price has not changed.
                        writer.write(firstName + " " + secondName + "\n" + "Your Final Price: " + finalDiscount + "\nYour final price has not changed\n\n");
                    }
                    else {
                        finalDiscount = totalPurchase * discountValue;

                        //Writing a custom message to let user know that their final price has changed, and display their discount value
                        writer.write(firstName + " " + secondName + "\n" + "Your Final Price: " + finalDiscount + "\n\n");
                    }
                }
                else {
                    writer.write("Invalid data for customer: " + nameS + "\n\n");
                }
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
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
        if (names.length > 2 && names.length < 2){
            System.out.println("Input first and second name.");
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
    private static boolean validTotalPurchase(String totalS){
        // as we currently hold totalPur as string we need to parse it into double
        double totalPur = Double.parseDouble(totalS);
        // we must check if the purchase is 0 or less as we cannot give a discount on 0 or negative purchases , otherwise we return true
        if (totalPur <= 0){
            System.out.println("No purchases have been made.");
            return false;
        }
        return true;
    }
    
    /**
     * Validating class
     * Customer classes can only be 1, 2 or 3
     * 
     * In this method we are making sure those rules above are applied, and if not
     * we will send an error message that describes the issue.
     */
    private static boolean validClass(String classS){
        // we need to parse class of type string it into int
        int classI = Integer.parseInt(classS);
        // we must check if class is not 1, 2 or 3, other return true
        if (classI < 1 || classI > 3) {
            System.out.println("Invalid number, Class must be 1, 2 or 3.");
            return false;
        }
        return true;
    }
    
     /**
     * Validating purchase year
     * We will only accept to year 2010 
     * 
     * In this method we are making sure those rules above are applied, and if not
     * we will send an error message that describes the issue.
     */
    private static boolean validYear(String yearS){
        // parse our lastPurchaseS string into int
        int yearInt = Integer.parseInt(yearS);
        // if the year isnt between 2024 and 2000 for our example, the date will be invalid
        if (yearInt > 2000 && yearInt <= 2024) {
            return true;
        } else {
            System.out.println("Year must be a valid year.");
            return false;
        }
    }

    /**
     * Calculating the discount based on class and last purchase year
     *
     * Returning as doubles, based on the discount. Example: Class 1, if last purchase was made in 2024 then the total
     * would be discounted by 30%. Discounting a value of 100% cost, by 30%, would leave us with 70% final price after discount.
     */
    private static double discountApply(int classI, int lastPur, double totalPur){
        // Check if total purchase is less than zero before proceeding
         if(totalPur == 0){
             return 0;
         }
         // Dividing the calculations based on class to keep code neat and more readable
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
         // Calculations for class 2
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
         // Calculations for class 3
         if (classI == 3){
             if(lastPur == 2024){
                     return 0.97;
             }
             else return 0;
         }
         return 0;
    }              
}
