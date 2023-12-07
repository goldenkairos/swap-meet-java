import org.junit.*;
import java.util.Arrays;

public class UnitTests {

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

        Assert.assertEquals(3, vendor.inventory.size());
    }

    @Test
    public void testSwapItems() {
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

        // swaping valid item
        Assert.assertTrue(Viktor.checkAvailability(itemD)); // validate valid item
        Assert.assertTrue(Ellie.checkAvailability(itemA)); // validate valid item

        Ellie.swapItems(Viktor, itemA, itemD);

        Assert.assertEquals(3, Ellie.inventory.size());
        Assert.assertEquals(2, Viktor.inventory.size());
        Assert.assertTrue(Viktor.checkAvailability(itemA));
        Assert.assertFalse(Ellie.checkAvailability(itemA));

        // swaping invalid item
        Assert.assertFalse(Viktor.checkAvailability(itemB)); // Viktor does not have itemB

        String result = Viktor.swapItems(Ellie, itemB, itemC); // swapping invalid item from Viktor's list

        Assert.assertEquals("One of the vendors' inventory does not contain item", result);
        Assert.assertFalse(Viktor.checkAvailability(itemB)); // remains as False
        Assert.assertTrue(Ellie.checkAvailability(itemC)); // itemC reains in Ellie's inventory

    }

    @Test
    public void testSwapFirstItemSuccessfully() {
        Vendor Vee = new Vendor("Vee");
        Item itemA = new Decor(1);
        Item itemB = new Clothing(2);
        Item itemC = new Electronics(3);

        Vee.add(itemA);
        Vee.add(itemB);
        Vee.add(itemC);

        Vendor Zen = new Vendor("Zen");
        Item itemD = new Decor(4);

        Zen.add(itemD);

        var VeeFirstItem = Vee.inventory.get(0);
        var ZenFirstItem = Zen.inventory.get(0);

        Assert.assertTrue(Vee.checkAvailability(VeeFirstItem)); // validate valid item
        Assert.assertTrue(Zen.checkAvailability(ZenFirstItem)); // validate valid item

        Vee.swapFirstItem(Zen);
        Assert.assertTrue(Vee.checkAvailability(ZenFirstItem));
        Assert.assertFalse(Vee.checkAvailability(VeeFirstItem)); // validate valid item
        Assert.assertTrue(Zen.checkAvailability(VeeFirstItem)); //
        Assert.assertFalse(Zen.checkAvailability(ZenFirstItem));
    }

    @Test
    public void testSwapFirstItemUnSuccessfully() {
        Vendor Kay = new Vendor("Kay");

        Vendor Sid = new Vendor("Sid");
        Item itemD = new Clothing(10);

        Sid.add(itemD);

        // var KayFirstItem = Kay.inventory.get(0);
        var SidFirstItem = Sid.inventory.get(0);

        Assert.assertEquals(Kay.inventory.size(),0); // validate invalid item
        Assert.assertTrue(Sid.checkAvailability(SidFirstItem)); // validate valid item

        String confirmation = Sid.swapFirstItem(Kay);

        Assert.assertEquals("One of the vendor's inventory list is empty. Nothing has been swapped!", confirmation);
        Assert.assertEquals(Kay.inventory.size(),0); // 
        Assert.assertTrue(Sid.checkAvailability(SidFirstItem));
    }

    @Test
    public void testItemGetSetCondition(){
        Decor itemA = new Decor(1);
        Assert.assertEquals(0.0,itemA.getCondition(),0.001);
        itemA.setCondition(.5);
        Assert.assertEquals("Gently pre-loved, like a well-worn teddy bears.",itemA.conditionDescription());


        Clothing itemB = new Clothing(2,3.5);
        Assert.assertEquals(3.5,itemB.getCondition(),0.001);
        itemB.setCondition(5);
        Assert.assertEquals("Mint condition. It's practically a museum piece.",itemB.conditionDescription());

        Electronics itemC = new Electronics(3);
        itemC.setCondition(1.5);
        String description = itemC.conditionDescription();
        Assert.assertEquals("Showing signs of a life well-lived and some battle scars.",description);

    }

    @Test
    public void testgetBestByCategory(){
        Decor itemA = new Decor(1);
        Clothing itemB = new Clothing(2,3.5);
        Clothing itemF = new Clothing(3,3.5);
        Electronics itemC = new Electronics(4,5);
        Electronics itemD = new Electronics(5,2.5);
        Electronics itemE = new Electronics(6,3.5);

        //unique item with the best condition
        Vendor Barbie = new Vendor("Barbie",Arrays.asList(itemA,itemB,itemC,itemD,itemE));
        Item barbieBestItem = Barbie.getBestByCategory("Electronics");
        Assert.assertEquals(barbieBestItem,itemC);

        //vendor with one item
        Vendor Ken = new Vendor("Ken",Arrays.asList(itemA));
        Item kenBestItem = Ken.getBestByCategory("Decor");
        Assert.assertEquals(kenBestItem,itemA);

        //vendor with duplicated best item
        Vendor Skipper = new Vendor("Skipper",Arrays.asList(itemB,itemC,itemF));
        Item skipperBestItem = Skipper.getBestByCategory("Clothing");
        Assert.assertTrue(skipperBestItem == itemB || skipperBestItem== itemF);

        //vendor with no item
        Vendor emptyVendor = new Vendor("EmptyVendor");
        Item emptyVendorBestItem = emptyVendor.getBestByCategory("Clothing");
        Assert.assertNull(emptyVendorBestItem);
    }

}
