public class Main {
    public static void main(String[] args){
        Vendor vendor1 = new Vendor();
        System.out.println(vendor1.inventory);

        Decor newHat = new Decor(0);
        System.out.println(newHat.category);
    }
}
