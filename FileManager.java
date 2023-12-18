import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String DATA_FILE_PATH = "/Users/minhseikel/Developer/Java Dev/swap-meet-java/data.txt";

    public static List<Vendor> loadVendorsFromDataFile() {

  List<Vendor> vendors = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE_PATH))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");

            // Assuming Vendor has a constructor that accepts the vendor name
            Vendor vendor = new Vendor(parts[0]);
            // Instantiate the appropriate subclass based on the category
                Item item;
            // Start parsing the inventory from index 1 (after the vendor name)
            for (int i = 1; i < parts.length; i += 3) {
                // Assuming the format is category|itemId|condition
                String category = parts[i];
                int itemId = Integer.parseInt(parts[i + 1]);
                double condition = Double.parseDouble(parts[i + 2]);

                
                switch (category) {
                    case "Decor":
                         item = new Decor(itemId, condition);
                        break;
                    case "Clothing":
                         item = new Clothing(itemId, condition);
                        break;
                    case "Electronics":
                         item = new Electronics(itemId, condition);
                        break;
                    // Add more cases for other categories if needed
                    default:
                        throw new IllegalArgumentException("Unknown category: " + category);
                }
                // Item item;
                // try {
                //     Class<?> itemClass = Class.forName(category);
                //     item = (Item) itemClass.getDeclaredConstructor(int.class, double.class).newInstance(itemId, condition);
                // } catch ( ReflectiveOperationException e) {
                //     throw new IllegalArgumentException("Unknown category: " + category, e);
                // }

                // Add the item to the vendor's inventory
                
                vendor.add(item);
            }

            vendors.add(vendor);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return vendors;
}


public static void saveDataFile(List<Vendor> vendors) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE_PATH))) {
        for (Vendor vendor : vendors) {
            // Write vendor name
            writer.write(vendor.toString() + "|");

            // Write vendor inventory
            for (Item item : vendor.inventory) {
                writer.write(item.getCategory() + "|" + item.getItemID() + "|" + item.getCondition());

                // Check if it's not the last item in the inventory, then add separator
                if (vendor.inventory.indexOf(item) < vendor.inventory.size() - 1) {
                    writer.write("|");
                }
            }

            // End of vendor entry
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
