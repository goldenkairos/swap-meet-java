import org.junit.*;

public class VendorTest {

    @Test
    public void testAddItem() {
        Vendor vendor = new Vendor("Vendor1");
        Item item = new Decor(1);

        vendor.add(item);

        Assert.assertTrue(vendor.checkAvailability(item));
    }

    @Test
    public void testRemoveItem() {
        Vendor vendor = new Vendor("Vendor1");
        Item item = new Decor(1);

        vendor.add(item);
        vendor.remove(item);

        Assert.assertFalse(vendor.checkAvailability(item));
    }

    @Test
    public void testAddMultipleItems() {
        Vendor vendor = new Vendor("Vendor1");
        Item itemA = new Decor(1);
        Item itemB = new Decor(2);
        Item itemC = new Decor(3);

        vendor.add(itemA);
        vendor.add(itemB);
        vendor.add(itemC);

        Assert.assertEquals(3,vendor.inventory.size());
    }

    @Test
    public void swapItems() {
        Vendor Ellie = new Vendor("Ellie");
        Item itemA = new Decor(1);
        Item itemB = new Clothing(2);
        Item itemC = new Electronics(3);

        Ellie.add(itemA);
        Ellie.add(itemB);
        Ellie.add(itemC);

        Vendor Viktor = new Vendor("Viktor");
        Item itemD = new Decor(4);
        Item itemE = new Electronics(5);

        Viktor.add(itemD);
        Viktor.add(itemE);

        //swaping invalid item
        Ellie.swap_items(itemA,Viktor);
        Assert.assertEquals(2,Ellie.inventory.size());
        Assert.assertEquals(3,Viktor.inventory.size());

        String result = Viktor.swap_items(itemB, Ellie);
        Assert.assertEquals("Vendor Viktor's inventory does not contain Clothing.itemID2",result);
    }

    
    

}
