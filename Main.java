public class Main {
    public static void main(String[] args) {
        ServiceManager serviceManager = ServiceManager.getInstance();

        MenuManager menuManager = new MenuManager(serviceManager);
        menuManager.runMenu();
    }
}
