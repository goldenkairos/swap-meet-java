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

    public void addVendor(Vendor vendor) {
        this.vendors.add(vendor);
        FileManager.saveDataFile(this.vendors);
    }

    public Vendor getVendorByName(String name) {
        for (Vendor vendor : vendors) {
            if (vendor.toString().toLowerCase().equals(name.toLowerCase())) {
                return vendor;
            }
        }
        return null;
    }

    public boolean checkExistingVendor(String name) {
        Vendor vendor = getVendorByName(name);

        if (vendor != null) {
            return true;
        } else {
            return false;
        }
    }

    public void addItemtoVendor(Vendor newVendor, Scanner scanner) {
        String itemCategory = getItemCategoryFromUser(scanner, newVendor);
        double itemCondition = getItemConditionFromUser(scanner);
        scanner.nextLine(); // Consume the newline character

        Item item = createItemFromUserInput(itemCategory, itemCondition);
        newVendor.add(item);
    }

    // Receive user input for new vendor's name
    public static String getVendorNameFromUser(Scanner scanner) {
        while (true) {
            if (scanner.hasNextLine()) {

                String userInputVendorName = scanner.nextLine().trim();

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
                    "\nEnter the category of item you would like to add to " + vendor.toString()
                            + "\'s inventory (Decor, Electronics or Clothing): ");

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

    // NOTE: Add validation check to make sure user only enters between 0-5.0
    public static double getItemConditionFromUser(Scanner scanner) {
        System.out.print("Enter the condition of this item: ");
        double userInputItemCondition = scanner.nextDouble();
        return userInputItemCondition;
    }

    // NOTE: can we switch to switch/case?
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

    // Menu Option 1 to list all vendors and inventories
    public List<String> getallVendorsAndInventory() {
        List<String> output = new ArrayList<>();
        for (Vendor vendor : vendors) {
            output.add(vendor.getVendorWithInventory() + "\n");
        }
        return output;

    }

    // Menu Option 2 to create new vendor and adding item(s) to vendor's inventory
    // list
    public void createNewVendorAndInventory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new vendor name: ");
        String vendorName = getVendorNameFromUser(scanner);
        boolean newVendorCheck = true;

        while (newVendorCheck) {
            if (checkExistingVendor(vendorName)) {
                System.out.print("This vendor name already exists in the database. Please choose another name: ");
                vendorName = getVendorNameFromUser(scanner);
            } else {
                newVendorCheck = false;
            }
        }

        Vendor newVendor = new Vendor(vendorName);

        boolean addingItem = true;
        while (addingItem) {
            addItemtoVendor(newVendor, scanner);

            System.out.print("Do you want to add another item? (y/n): ");
            String userResponse = scanner.nextLine().toLowerCase();

            if (userResponse.equals("n")) {
                addingItem = false;
            }
        }
        addVendor(newVendor);
    }

    // Menu Option 3 to modify vendor name of existing vendor
    public void updateVendorName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select the vendor name you would like to modify: ");
        String vendorNameFromuser = getVendorNameFromUser(scanner);

        Vendor vendor = instance.getVendorByName(vendorNameFromuser);
        // boolean existingVendorCheck = true;

        if (vendor != null) {
            String oldName = vendor.toString();

            System.out.print("\nEnter the updated name: ");
            String updatedVendorName = scanner.nextLine();
            boolean existingVendorCheck = true;

            while (existingVendorCheck) {
                if (checkExistingVendor(updatedVendorName)) {
                    System.out.print("This vendor name already exists in the database. Please choose another name: ");
                    updatedVendorName = getVendorNameFromUser(scanner);
                } else {
                    existingVendorCheck = false;
                }
            }

            vendor.setName(updatedVendorName);

            FileManager.saveDataFile(this.vendors);

            String output = "Vendor " + oldName + " has been updated to: " + vendor.toString();
            System.out.println(output);
        } else {
            System.out.println("This vendor does not exist in our database.");
        }
    }

    // Menu Option 4 to get inventory list of specific vendor
    public void getVendorInventory() {
        boolean viewingVendor = true;
        Scanner scanner = new Scanner(System.in);
        while (viewingVendor) {

            System.out.print("Select name of the vendor you would like to view inventory: ");
            String vendorNameFromuser = getVendorNameFromUser(scanner);
            Vendor vendor = instance.getVendorByName(vendorNameFromuser);
            if (vendor != null) {
                System.out.println(vendor.getVendorWithInventory());
            } else {
                System.out.println("This vendor does not exist in our database.");
            }

            System.out.print("\nDo you want to look up another vendor? (y/n): ");
            String userResponse = scanner.nextLine();

            if (userResponse.toLowerCase().equals("n")) {
                viewingVendor = false;
            }
        }
    }

    // Menu Option 5 to add item to specific vendor's inventory
    public void addItemtoVendorInventory() {
        boolean addingItem = true;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select name of the vendor you would like to view inventory: ");
        String vendorNameFromuser = getVendorNameFromUser(scanner);
        Vendor vendor = instance.getVendorByName(vendorNameFromuser);

        if (vendor != null) {
            while (addingItem) {
                addItemtoVendor(vendor, scanner);

                System.out.print("Do you want to add another item? (y/n): ");
                String userResponse = scanner.nextLine();

                if (userResponse.toLowerCase().equals("n")) {
                    addingItem = false;
                }
            }
        }
        FileManager.saveDataFile(this.vendors);
        System.out.println("Item(s) have been successfully added to " + vendor.toString() + "\'s inventory!");
    }

}

// Create a method to check if the name already exists. Add that to validtiono
// Option menu2 and 3