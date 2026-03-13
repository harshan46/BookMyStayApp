import java.util.HashMap;
import java.util.Map;

/**

 * RoomInventory
 *
 * Manages centralized inventory for all room types using HashMap.
 * This class acts as the single source of truth for room availability.
 *
 * @author Harshan Santhanam
 * @version 3.0
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor initializes the room inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types with their availability
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 3);


    }

    // Retrieve availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability in a controlled manner
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found in inventory.");
        }
    }

    // Display the entire inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}

/**

 * UseCase3InventorySetup
 *
 * Demonstrates centralized inventory management
 * using a HashMap data structure.
 *
 * @author Harshan Santhanam
 * @version 3.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("      Book My Stay - v3.1");
        System.out.println(" Centralized Room Inventory");
        System.out.println("===================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Retrieve availability example
        System.out.println("\nChecking availability for Double Room...");
        int available = inventory.getAvailability("Double Room");
        System.out.println("Double Rooms Available: " + available);

        // Update availability example
        System.out.println("\nUpdating Double Room availability...");
        inventory.updateAvailability("Double Room", 5);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication finished.");


    }
}
