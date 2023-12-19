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
            //     showMenu = false;
            //     break;
            // }

            switch (userInput) {
                case 1:
                    System.out.println(serviceManager.getallVendorsAndInventory());
                    break;
                case 2:
                    System.out.println("Add a new vendor");
                    serviceManager.createNewVendorAndInventory();
                    break;
                case 3:
                    serviceManager.updateVendorName();
                    break;
                case 4:
                    serviceManager.getVendorInventory();
                    break;
                case 5:
                    System.out.println("5");
                    break;
                case 6:
                    System.out.println("6");
                    break;
                case 7:
                    System.out.println("7");
                    break;
                case 8:
                    System.out.println("8");
                    break;
                case 9:
                    System.out.println("9");
                    break;
                case 10:
                    System.out.println("10");
                    break;
                case 11:
                    System.out.println("11");
                    break;
                case 12:
                    showMenu = false;
                    break;

            }

            // Consume newline character
            scanner.nextLine();

            if (userInput < 12) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();// Wait for user to press Enter
            }
        } while (showMenu);

        System.out.println("Exiting out of SwapMeet Application. Goodbye!");
        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("Welcome to Swap Meet application");
        System.out.println("\nMenu:\n");
        System.out.println("1. List all vendors");
        System.out.println("2. Add a new vendor");
        System.out.println("3. Edit a vendor name");
        System.out.println("4. Get inventory listing for a specific vendor");
        System.out.println("5. Add an item to the vendor's inventory");
        System.out.println("6. Remove an item from the vendor's inventory");
        System.out.println("7. Get all items by category for a specific vendor");
        System.out.println("8. Check item availability in vendor inventory");
        System.out.println("9. Get the best item by category for a vendor");
        System.out.println("10. Swap specific items between vendors");
        System.out.println("11. Swap the best items between vendors");
        System.out.println("12. Exit");
    }

    public static int getUserChoice(Scanner scanner) {
        System.out.print("Enter the menu option you would like to select(1-12): ");

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a menu option between 1 to 12.");
            scanner.next();
            System.out.print("Enter the menu option you would like to select (1-12): ");
        }
        return scanner.nextInt();
    }

    // public static boolean manualExit(Scanner scanner) {
    //    String userExitPrompt = scanner.nextLine().trim().toLowerCase();
    //     return userExitPrompt.equals("exit");

    // }
}
