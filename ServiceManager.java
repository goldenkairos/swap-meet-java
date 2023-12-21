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

    public void addVendorToFile(Vendor vendor) {
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

    public String promptUserForNewName(Scanner scanner) {
        String vendorName = getVendorNameFromUser(scanner);
        boolean vendorDatabaseCheck = true;

        while (vendorDatabaseCheck) {
            if (checkExistingVendor(vendorName)) {
                System.out.print("This vendor name already exists in the database. Please choose another name: ");
                vendorName = getVendorNameFromUser(scanner);
            } else {
                vendorDatabaseCheck = false;
            }
        }
        return vendorName;
    }

    public void addItemtoVendor(Vendor newVendor, Scanner scanner) {
        String itemCategory = getItemCategoryFromUser(scanner, newVendor);
        double itemCondition = getItemConditionFromUser(scanner);
        scanner.nextLine(); // Consume the newline character

        Item item = createItemFromUserInput(itemCategory, itemCondition);
        newVendor.add(item);
        System.out.println("New item ItemID "+item.getItemID()+" has been added!\n" );
    }

    public void removeItemfromVendor(Vendor vendor, Scanner scanner) {
        Item removedItem = promptUserForValidItemId(vendor, scanner);
        int removedItemId = removedItem.getItemID();
        vendor.remove(removedItem);
        System.out
                .println("ItemID:" + removedItemId + " has been successfully removed from " + vendor.toString()
                        + "\'s inventory!");
        FileManager.saveDataFile(this.vendors);
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
        System.out.print(
                "\nEnter the category of item you would like to add to " + vendor.toString()
                        + "\'s inventory (Decor, Electronics or Clothing): ");
        while (true) {

            if (scanner.hasNextLine()) {
                String userInputCategory = scanner.nextLine().trim().toLowerCase();

                if (!userInputCategory.isEmpty() && userInputCategory.equals("decor")
                        || userInputCategory.equals("clothing") || userInputCategory.equals("electronics")) {
                    return userInputCategory;
                } else {
                    System.out.print(
                            "Invalid input. Item category must be Decor or Clothing or Electronics. Please re-enter item category again: ");
                }
            }
        }
    }

    // NOTE: Add validation check to make sure user only enters between 0-5.0
    public static double getItemConditionFromUser(Scanner scanner) {
        System.out.print("Enter the condition of this item (0.0 to 5.0): ");
        while (true) {

            if (scanner.hasNextDouble()) {
                double userInputItemCondition = scanner.nextDouble();

                if (userInputItemCondition >= 0.0 && userInputItemCondition <= 5.0) {
                    return userInputItemCondition;
                } else {
                    System.out.print(
                            "Invalid input: Item condition must be between 0.0 to 5.0. Please enter item condition again: ");
                }
            } else {
                System.out.print(
                        "Invalid input. Please enter a valid double value: ");
                scanner.next();
            }
        }
    }

    public static int getItemIDFromUser(Scanner scanner) {
        // System.out.println("Enter the itemID of the item you would like to remove: ");

        while (true) {

            if (scanner.hasNextInt()) {
                int userInputItemID = scanner.nextInt();
                return userInputItemID;
            } else {
                System.out.print("Invalid input: ItemID must be an integer. Please enter ItemID again: ");
                scanner.next();
            }
        }
    }

    // NOTE: use switch/case if we do not want to initiate Item item
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

    public Item getItemByItemID(Vendor vendor, int userItemID) {
        for (Item item : vendor.inventory) {
            if (item.getItemID() == userItemID) {
                return item;
            }
        }
        return null;
    }

    public boolean checkExistingItem(Vendor vendor, int userItemID) {
        Item item = getItemByItemID(vendor, userItemID);

        if (item != null) {
            return true;
        } else {
            return false;
        }
    }

    public Item promptUserForValidItemId(Vendor vendor, Scanner scanner) {
        System.out.print("Enter the itemID of the item you would like to remove: ");
        int itemID = getItemIDFromUser(scanner);
        boolean itemDatabaseCheck = false;

        while (!itemDatabaseCheck) {
            if (!checkExistingItem(vendor, itemID)) {
                System.out.print("Item does not exist in " + vendor.toString()
                        + "\'s inventory. Please provide a valid ItemID: ");
                // scanner.nextLine();
                itemID = getItemIDFromUser(scanner);
            } else {
                itemDatabaseCheck = true;
            }
        }
        scanner.nextLine();

        Item itemFromUserID = getItemByItemID(vendor, itemID);
        return itemFromUserID;
    }

    public boolean promptNextStep(Scanner scanner) {
        return !scanner.nextLine().toLowerCase().equals("n");
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

        String vendorName = promptUserForNewName(scanner);

        Vendor newVendor = new Vendor(vendorName);

        boolean addingItem = true;
        while (addingItem) {
            addItemtoVendor(newVendor, scanner);

            System.out.println("Do you want to add another item? (y/n): ");
            String userResponse = scanner.nextLine().toLowerCase();

            if (userResponse.equals("n")) {
                addingItem = false;
            }
        }
        addVendorToFile(newVendor);
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
            String updatedVendorName = promptUserForNewName(scanner);

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

            System.out.print("\nSelect name of the vendor you would like to view inventory: ");
            String vendorNameFromuser = getVendorNameFromUser(scanner);
            Vendor vendor = instance.getVendorByName(vendorNameFromuser);
            if (vendor != null) {
                System.out.println(vendor.getVendorWithInventory());
            } else {
                System.out.println("This vendor does not exist in our database.\n");
            }

            System.out.print("Do you want to look up another vendor? (y/n): ");
            viewingVendor = promptNextStep(scanner);

        }
    }

    // Menu Option 5 to add item to specific vendor's inventory
    public void addItemtoVendorInventory() {
        boolean addingItem = true;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select name of the vendor you would like to add to inventory: ");
        String vendorNameFromuser = getVendorNameFromUser(scanner);
        Vendor vendor = instance.getVendorByName(vendorNameFromuser);

        if (vendor != null) {
            while (addingItem) {
                addItemtoVendor(vendor, scanner);

                System.out.print("Do you want to add another item? (y/n): ");
                addingItem = promptNextStep(scanner);
            }
        }
        FileManager.saveDataFile(this.vendors);
        System.out.println("Item(s) have been successfully added to " + vendor.toString() + "\'s inventory!");
    }

    // Menu Option 6 to remove item from specific vendor when providing a valid
    // itemID
    public void removeItemFromSpecificVendor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select name of the vendor you would like to remove from inventory: ");
        String vendorNameFromuser = getVendorNameFromUser(scanner);
        Vendor vendor = instance.getVendorByName(vendorNameFromuser);

        // immidiate return if vendor does not have any inventory
        if (vendor.inventory.isEmpty()) {
            System.out.println("Vendor " + vendor.toString() + "'s inventory is empty! We cannot remove any items.");
            return;
        }

        boolean removingItem = true;
        System.out.println("\nHere is the inventory listing of vendor " + vendor.getVendorWithInventory());

        while (removingItem && !vendor.inventory.isEmpty()) {
            removeItemfromVendor(vendor, scanner);

            if (vendor.inventory.size() == 0) {
                removingItem = false;
                System.out.println(
                        "Vendor " + vendor.toString()
                                + "\'s no longer has any item in inventory. Item removal process is completed!");
                break;
            }

            System.out.print("\nDo you want to remove another item? (y/n): ");
            removingItem = promptNextStep(scanner);

            if (!removingItem) {
                System.out.println(
                        "Item removal process is completed. Here is the updated invendory listing of vendor "
                                + vendor.getVendorWithInventory());
            }
        }

    }
}