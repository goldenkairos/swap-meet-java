public class Main {
    public static void main(String[] args){
        
        Vendor vendor1 = new Vendor("Victor");
        System.out.println(vendor1.toString());
        vendor1.setName("Yuki");

        Decor newHat = new Decor(1.5);
        System.out.println(newHat.category);
        // newHat.setItemID(3);
        System.out.println(newHat.getItemID());
        Clothing newShirt = new Clothing(5);
        System.out.println(newShirt.getItemID());

        vendor1.inventory.add(newHat);
        
        vendor1.inventory.add(new Decor(1));
        vendor1.inventory.add(new Decor(2));
        vendor1.inventory.add(new Electronics(3));
        // vendor1.inventory.add(new Clothing(4));

        ServiceManager serviceManager = ServiceManager.getInstance();
        
        System.out.println(vendor1.getVendorWithInventory());

        // System.out.println(vendor1.toString()+"'s inventory list before swaping: "+vendor1.inventory);

        //adding new vendor
        serviceManager.addVendor(vendor1);

        //updating data file
        System.out.println(serviceManager.getAllVendors());
        System.out.println(serviceManager.getallVendorsAndInventory());

    }
}
