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
    
}
