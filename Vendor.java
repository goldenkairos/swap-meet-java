import java.util.ArrayList;
import java.util.List;

import java.util.Iterator;

public class Vendor {

    public static ServiceManager serviceManager;
    static {
        serviceManager = ServiceManager.getInstance();
    }

    protected String name;
    public List<Item> inventory;

    public Vendor(String name) {
        this.inventory = new ArrayList<>();
        this.name = name;
        // serviceManager = ServiceManager.getInstance();
        // serviceManager.addVendor(this);
    }

    public Vendor(String name, List<Item> inventory) {
        this.name = name;
        this.inventory = inventory; /// Use "this" to refer to the instance variable
        // serviceManager.addVendor(this);

    }

    public void setServiceManager(ServiceManager manager) {
        serviceManager = manager;
    }

    public String toString() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item add(Item item) {
        this.inventory.add(item);
        return item;
    }

    // update to pass int itemID to remove item
    public boolean remove(Item item) {

        if (checkAvailability(item)) {
            this.inventory.remove(item);
            return true;
        }
        return false;
    }

    public boolean checkAvailability(Item item) {
        Iterator<Item> iterator = this.inventory.iterator();

        while (iterator.hasNext()) {
            Item unit = iterator.next();
            if (unit.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public List<Item> getByCategory(String category) {
        List<Item> output = new ArrayList<Item>();

        for (Item item : this.inventory) {
            if (item.category.equals(category)) {
                output.add(item);
            }
        }
        return output;

    }

    public String swapItems(Vendor friend, Item myItem, Item theirItem) {
        String confirmation;
        if (this.checkAvailability(myItem) && friend.checkAvailability(theirItem)) {
            this.remove(myItem);
            friend.add(myItem);
            this.add(theirItem);
            friend.remove(theirItem);

            confirmation = "Items have been successfully swapped!";
        } else {
            confirmation = "One of the vendors' inventory does not contain item";
        }
        return confirmation;
    }

    public String swapFirstItem(Vendor friend) {
        String confirmation;

        if (!this.inventory.isEmpty() && !friend.inventory.isEmpty()) {

            var myFirstItem = this.inventory.get(0);
            var theirFirstItem = friend.inventory.get(0);

            swapItems(friend, myFirstItem, theirFirstItem);
            confirmation = "First item has been successfully swapped!";
        } else {
            confirmation = "One of the vendor's inventory list is empty. Nothing has been swapped!";
        }
        return confirmation;

    }

    public Item getBestByCategory(String category) {
        Item bestConditionItem = null;
        double bestCondition = 0.0;

        var listByCategory = getByCategory(category);

        if (listByCategory.size() == 1) {
            return listByCategory.get(0);
        }

        for (Item item : listByCategory) {
            if (item.condition > bestCondition) {
                bestCondition = item.condition;
                bestConditionItem = item;
            }
        }
        return bestConditionItem;
    }

    public boolean swapBestByCategory(Vendor friend, String theirPriority, String myPriority) {
        Item friendItem = friend.getBestByCategory(myPriority);
        Item myItem = this.getBestByCategory(theirPriority);

        if (myItem != null && friendItem != null) {
            this.swapItems(friend, myItem, friendItem);
            return true;
        }

        return false;
    }

    public String getVendorWithInventory() {
        // StringBuilder result = new StringBuilder();
        // result.append(name).append(":[");

        // for (Item item : inventory) {
        // result.append("(")
        // .append(item.getCategory())
        // .append(", itemID:")
        // .append(item.getItemID())
        // .append(", condition:")
        // .append(item.getCondition())
        // .append("),");
        // }

        // // Remove the trailing comma if there are items
        // if (!inventory.isEmpty()) {
        // result.deleteCharAt(result.length() - 1);
        // }

        // result.append("]");

        // return result.toString();
        String inventoryString = String.join("", inventory.stream()
                .map(item -> String.format("ItemID: %d, Condition: %.2f, Category: %s\n",
                        item.getItemID(), item.getCondition(), item.getCategory()))
                .toArray(String[]::new));

        return String.format("%s:\n%s", this.toString(), inventoryString);
    }
}