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
        while(addingItem){
            String itemCategory = getItemCategoryFromUser(scanner, newVendor);
            double itemCondition = getItemConditionFromUser(scanner);
            scanner.nextLine();  // Consume the newline character

            Item item = createItemFromUserInput(itemCategory, itemCondition);
            newVendor.add(item);

            System.out.print("Do you want to add another item? (y/n): ");
            String userResponse = scanner.nextLine().toLowerCase();

            if (userResponse.equals("n")){
                addingItem = false;
            }
            
        }
        addVendor(newVendor);
    }

    public static String getVendorNameFromUser(Scanner scanner) {
        System.out.print("Enter the vendor name: ");

        String userInputVendorName = scanner.nextLine();

        return userInputVendorName;
    }

    public static String getItemCategoryFromUser(Scanner scanner, Vendor vendor) {
        System.out.print("Enter the category of item you would like to add to " + vendor.toString() + "\'s inventory ");
        String userInputCategory = scanner.nextLine();
        return userInputCategory;
    }

    public static double getItemConditionFromUser(Scanner scanner) {
        System.out.print("Enter the condition of this item: ");
        double userInputItemCondition = scanner.nextDouble();
        return userInputItemCondition;
    }

    public static Item createItemFromUserInput(String category, double condition) {
        Item item = null;
        if (category.equals("Decor")) {
            item = new Decor(condition);
        } else if (category.equals("Clothing")) {
            item = new Clothing(condition);
        } else if (category.equals("Electronics")) {
            item = new Electronics(condition);
        } else {
            System.out.println("Incorrect category");
        }
        return item;

    }
}
