import java.io.*;
import java.util.List;

public class ServiceManager implements Serializable {
    private List<Vendor> vendors;
    public static ServiceManager instance;

    public ServiceManager() {
        // Initialize vendors by loading data from the file
        this.vendors = FileManager.loadVendorsFromDataFile();
    }

    public static ServiceManager getInstance() {
        if (instance == null){
            instance = new ServiceManager();
            instance.initializeVendors();
        }
        return instance;
    }

    private void initializeVendors() {
        for (Vendor vendor : vendors) {
            vendor.setServiceManager(this); // Set the ServiceManager instance in each Vendor
        }
    }

    public List<Vendor> getAllVendors() {
        return vendors;
    }

    public void addVendor(Vendor vendor) {
        this.vendors.add(vendor);
        FileManager.saveDataFile(this.vendors);
    }

    public Vendor getVendorByName(String name) {
        for (Vendor vendor : vendors) {
            if (vendor.toString().equals(name)) {
                return vendor;
            }
        }
        return null;
    }

    // // Load vendors from the file
    // private List<Vendor> loadVendorsFromDataFile() {

    //     try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.txt"))) {
    //         @SuppressWarnings("unchecked")
    //         List<Vendor> vendors = (List<Vendor>) ois.readObject();
    //         return vendors;
    //     } catch (IOException | ClassNotFoundException e) {
    //         // If the file doesn't exist or an exception occurs, return an empty list
    //         return new ArrayList<>();
    //     }
    // }
}

