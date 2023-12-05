public class Main {
    public static void main(String[] args){
        Vendor vendor1 = new Vendor("Victor");
        System.out.println(vendor1.toString());
        vendor1.setName("Yuki");

        Decor newHat = new Decor(0);
        System.out.println(newHat.category);
        newHat.setItemID(3);
        System.out.println(newHat.getItemID(newHat));

        vendor1.inventory.add(newHat);
        
        vendor1.inventory.add(new Decor(1));
        vendor1.inventory.add(new Decor(2));
        vendor1.inventory.add(new Electronics(3));
        vendor1.inventory.add(new Clothing(4));

        System.out.println(vendor1.toString()+"'s inventory list before swaping: "+vendor1.inventory);

        // Vendor vendor2 = new Vendor("Yoda");

        // //Swapping first item unsuccessfully
        // System.out.println(vendor2.swap_first_item(vendor1));
        // System.out.println(vendor1.toString()+"'s inventory list after first item swap: "+vendor1.inventory);
        // System.out.println(vendor2.toString()+"'s inventory list after first item swap: "+vendor2.inventory);

        // //Swapping newHat
        // System.out.println("Item to be swapped: "+newHat.toString());

        // System.out.println(vendor1.swapItems(newHat, vendor2));
        // System.out.println(vendor1.toString()+"'s inventory list: "+vendor1.inventory);
        // System.out.println(vendor2.toString()+"'s inventory list: "+vendor2.inventory);

        // //Swap item again, should return false
        // System.out.println(vendor1.swap_items(newHat, vendor2));

        // //Adding new item to vendor2
        // Electronics newitem = new Electronics(5);
        // vendor2.add(newitem);
        // //Swap new item from vendor 2 to vendor 1
        // System.out.println(vendor2.swap_items(newitem, vendor1));
        // System.out.println(vendor1.toString()+"'s inventory list: "+vendor1.inventory);
        // System.out.println(vendor2.toString()+"'s inventory list: "+vendor2.inventory);

        // //Swapping first item
        // System.out.println(vendor1.swap_first_item(vendor2));
        // System.out.println(vendor1.toString()+"'s inventory list after first item swap: "+vendor1.inventory);
        // System.out.println(vendor2.toString()+"'s inventory list after first item swap: "+vendor2.inventory);


    }
}
