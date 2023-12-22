import java.util.Scanner;

public class MenuManager {
    private static ServiceManager serviceManager;
    private static Scanner scanner;
    private static boolean showMenu = true;

    public MenuManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
        this.scanner = new Scanner(System.in);
    }

    public static void runMenu() {

        do {
            displayMenu();
            int userInput = getUserChoice(scanner);

            // if (manualExit(scanner)){
            // showMenu = false;
            // break;
            // }

            switch (userInput) {
                case 1:
                    System.out.println(serviceManager.getallVendorsAndInventory());
                    break;
                case 2:
                    serviceManager.createNewVendorAndInventory();
                    break;
                case 3:
                    serviceManager.updateVendorName();
                    break;
                case 4:
                    serviceManager.getVendorInventory();
                    break;
                case 5:
                    serviceManager.addItemtoVendorInventory();
                    break;
                case 6:
                    serviceManager.removeItemFromSpecificVendor();
                    break;
                case 7:
                    serviceManager.getItemsByCategoryFromVendor();
                    break;
                case 8:
                serviceManager.itemLookUpFromVendor();
                    break;
                case 9:
                    serviceManager.swapItemsBetweenVendors();
                    break;
                case 10:
                    showMenu = false;
                    break;

            }

            // Consume newline character
            scanner.nextLine();

            if (userInput < 10) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();// Wait for user to press Enter
            }
        } while (showMenu);

        System.out.println("Exiting out of SwapMeet Application. Goodbye!");
        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤");
        System.out.println("                    MENU                     ");
        System.out.println("----------------------------------------------");
        System.out.println("1. List all vendors");
        System.out.println("2. Add a new vendor");
        System.out.println("3. Edit a vendor name");
        System.out.println("4. Get inventory listing for a specific vendor");
        System.out.println("5. Add an item to the vendor's inventory");
        System.out.println("6. Remove an item from the vendor's inventory");
        System.out.println("7. Get all items by category for a specific vendor");
        System.out.println("8. Check item availability in vendor inventory");
        System.out.println("9. Swap specific items between vendors");
        System.out.println("10. Exit");
        System.out.println("➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤➤");
    }

    public static int getUserChoice(Scanner scanner) {
        System.out.print("Enter the menu option you would like to select(1-10): ");

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a menu option between 1 to 10.");
            scanner.next();
            System.out.print("Enter the menu option you would like to select (1-10): ");
        }
        return scanner.nextInt();
    }

    // public static boolean manualExit(Scanner scanner) {
    // String userExitPrompt = scanner.nextLine().trim().toLowerCase();
    // return userExitPrompt.equals("exit");

    // }
}
