public class Main {
    public static void main(String[] args) {
        ServiceManager serviceManager = ServiceManager.getInstance();
        // Vendor vendor1 = new Vendor("Victor");
        // System.out.println(vendor1.toString());
        // Vendor vendor1 = new Vendor("Yuki");

        // Decor newHat = new Decor();
        // Electronics newDrum = new Electronics();
        // System.out.println(newHat.category);
        // // newHat.setItemID(3);
        // System.out.println(newHat.getItemID());2
        
        // Clothing newShirt = new Clothing(5);
        // System.out.println(newShirt.getItemID());

        // vendor1.inventory.add(newHat);
        // vendor1.inventory.add(newDrum);

        // vendor1.inventory.add(new Decor(1));
        // vendor1.inventory.add(new Decor(10,2.5));
        // vendor1.inventory.add(new Electronics(3));
        // // vendor1.inventory.add(new Clothing(4));

        // System.out.println(vendor1.getVendorWithInventory());

        // System.out.println(vendor1.toString()+"'s inventory list before swaping:
        // "+vendor1.inventory);

        // adding new vendor
        // serviceManager.addVendor(vendor1);

        // updating data file
        // System.out.println(serviceManager.getAllVendors());
        // System.out.println(serviceManager.getallVendorsAndInventory());
        MenuManager menuManager = new MenuManager(serviceManager);
        menuManager.runMenu();
    }
}
