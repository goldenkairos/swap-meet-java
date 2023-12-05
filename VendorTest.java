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

        //swaping valid item
        Ellie.swapItems(Viktor,itemA,itemD);
        Assert.assertEquals(3,Ellie.inventory.size());
        Assert.assertEquals(2,Viktor.inventory.size());
        Assert.assertTrue(Viktor.checkAvailability(itemA));
        Assert.assertFalse(Ellie.checkAvailability(itemA));

        //swaping invalid item
        Assert.assertFalse(Viktor.checkAvailability(itemB)); //Viktor does not have itemB
        String result = Viktor.swapItems(Ellie,itemB,itemC); //swapping invalid item from Viktor's list
        Assert.assertEquals("One of the vendors' inventory does not contain item",result);
        Assert.assertFalse(Viktor.checkAvailability(itemB)); //remains as False 
        Assert.assertTrue(Ellie.checkAvailability(itemC)); //itemC reains in Ellie's inventory
        
    }

    
    

}
