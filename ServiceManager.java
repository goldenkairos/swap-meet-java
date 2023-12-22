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

    public Vendor getValidVendor(Scanner scanner, String prompt) {
        Vendor vendor = null;
        while (vendor == null) {
            // System.out.print("Select name of the vendor: ");
            System.out.print(prompt);
            String vendorNameFromUser = getVendorNameFromUser(scanner);

            if (checkExistingVendor(vendorNameFromUser)) {
                vendor = instance.getVendorByName(vendorNameFromUser);
            } else {
                System.out.println("\nThis vendor does not exist in our database. Try again!\n");
            }
        }
        return vendor;
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
        System.out.println("New item ItemID " + item.getItemID() + " has been added!\n");
    }

    public void removeItemfromVendor(Vendor vendor, Scanner scanner) {
        Item removedItem = promptUserForValidItemId(vendor, scanner, "Enter the itemID of the item you would like to remove: ");
        int removedItemId = removedItem.getItemID();
        vendor.remove(removedItem);
        System.out
                .print("\nItemID:" + removedItemId + " has been successfully removed from " + vendor.toString()
                        + "\'s inventory!\n");
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
                    System.out.print("\nInvalid input. Vendor name cannot be empty. Please enter vendor name again: ");
                }
            } else {
                System.out.println("\nInvalid input. Please enter a valid vendor name");
            }
        }

    }

    public static String getItemCategoryFromUser(Scanner scanner, Vendor vendor) {
        System.out.print(
                "\nEnter the inventory category (Decor, Electronics or Clothing): ");
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
        while (true) {
            String userInput = scanner.nextLine().trim();
    
            if (!userInput.isEmpty()) {
                try {
                    int userInputItemID = Integer.parseInt(userInput);
                    return userInputItemID;
                } catch (NumberFormatException e) {
                    System.out.print("\nInvalid input: ItemID must be an integer. Please enter ItemID again: ");
                }
            } else {
                System.out.print("\nInvalid input. ItemID cannot be empty. Please enter ItemID again: ");
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

    // potentially reduce this code. we can use getItemByitemID when null and !=null
    public boolean checkExistingItem(Vendor vendor, int userItemID) {
        Item item = getItemByItemID(vendor, userItemID);

        if (item != null) {
            return true;
        } else {
            return false;
        }
    }

    public Item promptUserForValidItemId(Vendor vendor, Scanner scanner, String prompt) {
        System.out.print(prompt);
        // int itemID = getItemIDFromUser(scanner);
        boolean itemDatabaseCheck = false;
        int itemID;
        do {
            itemID = getItemIDFromUser(scanner);

            // if(getItemByItemID(vendor,itemID)!=null){
            if (!checkExistingItem(vendor, itemID)) {
                System.out.print("Item does not exist in " + vendor.toString()
                        + "\'s inventory. Please provide a valid ItemID: ");
                // scanner.nextLine();
                // itemID = getItemIDFromUser(scanner);
            } else {
                itemDatabaseCheck = true;
                // System.out.print("Item"+itemID+ "is found in " + vendor.toString()
                //         + "\'s inventory.");
            }
        } while (!itemDatabaseCheck);

        // scanner.nextLine();

        Item itemFromUserID = getItemByItemID(vendor, itemID);
        return itemFromUserID;
    }

    public int itemIDLookup(Vendor vendor, Scanner scanner) {

        int itemID;

        itemID = getItemIDFromUser(scanner);

        if (!checkExistingItem(vendor, itemID)) {
            System.out.print("\nItem does not exist in " + vendor.toString()
                    + "\'s inventory!\n");
        } else {
            System.out.print("\nItemID " + itemID + " is found in " + vendor.toString()
                    + "\'s inventory!\n");

        }

        return itemID;
    }

    public boolean promptNextStep(Scanner scanner) {
        return !scanner.nextLine().toLowerCase().equals("n");
    }

    public String getAllItemDescription(List<Item> items) {
        return String.join("", items.stream()
                .map(Item::getItemDescription)
                .toArray(String[]::new));
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
        // System.out.print("Select the vendor name you would like to modify: ");
        // String vendorNameFromuser = getVendorNameFromUser(scanner);

        // Vendor vendor = instance.getVendorByName(vendorNameFromuser);
        Vendor vendor = getValidVendor(scanner,"\nSelect the vendor name you would like to modify: ");
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

            // System.out.print("\nSelect name of the vendor you would like to view inventory: ");
            // String vendorNameFromuser = getVendorNameFromUser(scanner);
            // Vendor vendor = instance.getVendorByName(vendorNameFromuser);
            Vendor vendor = getValidVendor(scanner,"\nSelect name of the vendor you would like to view inventory: ");
            if (vendor != null) {
                System.out.println("\n"+vendor.getVendorWithInventory());
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
        // System.out.print("Select name of the vendor you would like to add to inventory: ");
        // String vendorNameFromuser = getVendorNameFromUser(scanner);
        // Vendor vendor = instance.getVendorByName(vendorNameFromuser);
        Vendor vendor = getValidVendor(scanner,"Select name of the vendor you would like to add to inventory: ");

        if (vendor != null) {
            while (addingItem) {
                addItemtoVendor(vendor, scanner);

                System.out.print("Do you want to add another item? (y/n): ");
                addingItem = promptNextStep(scanner);
            }
        }
        FileManager.saveDataFile(this.vendors);
        System.out.println("\nProcess completes. Here is the updated inventory listing for vendor"
                + vendor.getVendorWithInventory());
    }

    // Menu Option 6 to remove item from specific vendor when providing a valid
    // itemID
    public void removeItemFromSpecificVendor() {
        Scanner scanner = new Scanner(System.in);
        Vendor vendor = getValidVendor(scanner,"Select the vendor you would like to remove from inventory: ");
        // System.out.print("Select name of the vendor you would like to remove from inventory: ");
        // String vendorNameFromuser = getVendorNameFromUser(scanner);
        // Vendor vendor = instance.getVendorByName(vendorNameFromuser);

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

    // Menu Option 7 to get all items by category for specific vendor
    public void getItemsByCategoryFromVendor() {
        Scanner scanner = new Scanner(System.in);
        Vendor vendor = getValidVendor(scanner,"Select the vendor you would like to get items by category: ");

        // immidiate return if vendor does not have any inventory
        if (vendor.inventory.isEmpty()) {
            System.out.println("Vendor " + vendor.toString() + "'s inventory is empty!");
            return;
        }
        boolean viewingItems = true;

        do {
            String categoryFromUser = getItemCategoryFromUser(scanner, vendor);
            List<Item> itemsByCategory = vendor.getByCategory(categoryFromUser);
            
            if (!itemsByCategory.isEmpty()){
            System.out.println(getAllItemDescription(itemsByCategory));
        } else {
            System.out.println(vendor.toString()+" does not have any item in "+categoryFromUser+" category");
        }

            System.out.print("\nDo you want to lookup another category? (y/n): ");
            viewingItems = promptNextStep(scanner);
        } while (viewingItems);

    }

    // // Menu Option 8 to check availability of an item in vendor inventory
    public void itemLookUpFromVendor() {
        Scanner scanner = new Scanner(System.in);
        boolean continueLookup = true;

        do {
            Vendor vendor = getValidVendor(scanner,"Select the vendor you would like to view inventory: ");

            int itemID;
            do {
                System.out.print("Enter the itemID of the item you would like to lookup: ");
                itemID = getItemIDFromUser(scanner);

                if (!checkExistingItem(vendor, itemID)) {
                    System.out.print("Item does not exist in " + vendor.toString()
                            + "\'s inventory.\n");
                } else {
                    System.out.print("\nItemID " + itemID + " is found in " + vendor.toString()
                            + "\'s inventory!\n");
                }
                // scanner.nextLine();
                System.out.print("\nDo you want to lookup another item? (y/n): ");
            } while (promptNextStep(scanner));

            System.out.print("\nDo you want to lookup items for another vendor? (y/n): ");
            continueLookup = promptNextStep(scanner);
        } while (continueLookup);

        System.out.print("Search complete. Directing to Main Menu.");
    }

    //Menu Option 9 to swap items between 2 vendors
    public void swapItemsBetweenVendors(){
        Scanner scanner = new Scanner(System.in);
        Vendor vendor1 = getValidVendor(scanner,"Select the first vendor you would like to trade: ");
        System.out.print(vendor1.getVendorWithInventory());
        Item itemFromVendor1 = promptUserForValidItemId(vendor1, scanner,"Enter the itemID of the item "+vendor1.toString()+" would like to trade: ");

        Vendor vendor2 = getValidVendor(scanner,"Select the second vendor you would like to trade: ");
        System.out.print(vendor2.getVendorWithInventory());
        Item itemFromVendor2 = promptUserForValidItemId(vendor2, scanner,"Enter the itemID of the item "+vendor2.toString()+" would like to trade: ");

        vendor1.swapItems(vendor2, itemFromVendor1, itemFromVendor2);
        FileManager.saveDataFile(this.vendors);

        System.out.print("\nItems have been successfully swapped!\n");

        System.out.print("\nUpdated inventory listing: "+vendor1.getVendorWithInventory()+vendor1.getVendorWithInventory());
        
;        }


}