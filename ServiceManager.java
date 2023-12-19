import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServiceManager implements Serializable {
    private List<Vendor> vendors;
    public static ServiceManager instance;

    public ServiceManager() {
        // Initialize vendors by loading data from the file
        this.vendors = FileManager.loadVendorsFromDataFile();
    }

    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
            instance.initializeVendors();
        }
        return instance;
    }

    private void initializeVendors() {
        for (Vendor vendor : vendors) {
            vendor.setServiceManager(this); // Set the ServiceManager instance in each Vendor
        }
    }

    public List<Vendor> getAllVendors() {
        return vendors;
    }

    public List<String> getallVendorsAndInventory() {
        List<String> output = new ArrayList<>();
        for (Vendor vendor : vendors) {
            output.add(vendor.getVendorWithInventory() + "\n");
        }
        return output;

    }

    public void addVendor(Vendor vendor) {
        this.vendors.add(vendor);
        FileManager.saveDataFile(this.vendors);
    }

    public Vendor getVendorByName(String name) {
        for (Vendor vendor : vendors) {
            if (vendor.toString().equals(name)) {
                return vendor;
            }
        }
        return null;
    }

    public void createNewVendorAndInventory() {
        Scanner scanner = new Scanner(System.in);
        String vendorName = getVendorNameFromUser(scanner);
        Vendor newVendor = new Vendor(vendorName);

        boolean addingItem = true;
        while (addingItem) {
            String itemCategory = getItemCategoryFromUser(scanner, newVendor);
            double itemCondition = getItemConditionFromUser(scanner);
            scanner.nextLine(); // Consume the newline character

            Item item = createItemFromUserInput(itemCategory, itemCondition);
            newVendor.add(item);

            System.out.print("Do you want to add another item? (y/n): ");
            String userResponse = scanner.nextLine().toLowerCase();

            if (userResponse.equals("n")) {
                addingItem = false;
            }

        }
        addVendor(newVendor);
    }

    // Receive user input for new vendor's name
    public static String getVendorNameFromUser(Scanner scanner) {
        while (true) {
            System.out.print("Enter the vendor name: ");

            if (scanner.hasNextLine()) {

                String userInputVendorName = scanner.nextLine().trim();

                // if (MenuManager.manualExit(scanner)){
                // return null;
                // }

                if (!userInputVendorName.isEmpty()) {
                    return userInputVendorName;
                } else {
                    System.out.print("Invalid input. Vendor name cannot be empty.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid vendor name");
            }
        }

    }

    public static String getItemCategoryFromUser(Scanner scanner, Vendor vendor) {
        while (true) {
            System.out.print(
                    "\nEnter the category of item you would like to add to " + vendor.toString() + "\'s inventory ");

            if (scanner.hasNextLine()) {
                String userInputCategory = scanner.nextLine().trim().toLowerCase();

                if (!userInputCategory.isEmpty() && userInputCategory.equals("decor")
                        || userInputCategory.equals("clothing") || userInputCategory.equals("electronics")) {
                    return userInputCategory;
                } else {
                    System.out.print("Invalid input. Item category must be Decor or Clothing or Electronics.");
                }
            }
        }
    }

    public static double getItemConditionFromUser(Scanner scanner) {
        System.out.print("Enter the condition of this item: ");
        double userInputItemCondition = scanner.nextDouble();
        return userInputItemCondition;
    }

    public static Item createItemFromUserInput(String category, double condition) {
        Item item = null;
        if (category.toLowerCase().equals("decor")) {
            item = new Decor(condition);
        } else if (category.toLowerCase().equals("clothing")) {
            item = new Clothing(condition);
        } else if (category.toLowerCase().equals("electronics")) {
            item = new Electronics(condition);
        }
        return item;
    }

    public void updateVendorName(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select the vendor you would like to modify - ");
        String vendorNameFromuser = getVendorNameFromUser(scanner);

        Vendor vendor = instance.getVendorByName(vendorNameFromuser);

        if(vendor!=null){
        String oldName = vendor.toString();

        System.out.print("\nEnter the updated name: ");
        String updatedVendorName = scanner.nextLine();

        vendor.setName(updatedVendorName);

        FileManager.saveDataFile(this.vendors);

        String output = "Vendor " +oldName+" has been updated to: " + vendor.toString();
        System.out.println(output);
    } else {
        System.out.println("This vendor does not exist in our database.");
    } 
    }

}