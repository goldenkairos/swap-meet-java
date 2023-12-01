public class Main {
    public static void main(String[] args){
        Vendor vendor1 = new Vendor("Victor");
        vendor1.setName("Yuki");
        System.out.println(vendor1.getName());

        Decor newHat = new Decor(0);
        System.out.println(newHat.category);
        newHat.setItemID(3);
        System.out.println(newHat.getItemID(newHat));

        vendor1.inventory.add(newHat);
        
        vendor1.inventory.add(new Decor(1));
        vendor1.inventory.add(new Decor(2));
        vendor1.inventory.add(new Electronics(3));
        vendor1.inventory.add(new Clothing(4));

        System.out.println(vendor1.inventory);

        System.out.println(vendor1.getByCategory("Decor"));

        for (Item item : vendor1.inventory){
            System.out.println(item.getItemCategory());
        }

    }
}
