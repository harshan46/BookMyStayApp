import java.util.HashMap;
import java.util.Map;

/**

 * Abstract Room class representing common room properties.
 *
 * @author Harshan Santhanam
 * @version 4.0
 */
abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price per Night: ₹" + price);
    }
}

/**

 * Single Room implementation
 * @version 4.0
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 200, 2000);
    }
}

/**

 * Double Room implementation
 * @version 4.0
 */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 350, 3500);
    }
}

/**

 * Suite Room implementation
 * @version 4.0
 */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 600, 7000);
    }
}

/**

 * RoomInventory manages centralized room availability.
 * It acts as the system state holder.
 *
 * @version 4.0
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();


        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 0); // Example unavailable room


    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

/**

 * SearchService handles read-only room search operations.
 *
 * @version 4.0
 */
class SearchService {

    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms() {

        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        System.out.println("\nAvailable Rooms:\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Filter rooms with zero availability
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println("------------------------------");
            }
        }


    }
}

/**

 * UseCase4RoomSearch
 *
 * Demonstrates read-only room search functionality.
 * Inventory is accessed but not modified.
 *
 * @author Harshan Santhanam
 * @version 4.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("       Book My Stay - v4.1");
        System.out.println("     Room Search & Availability");
        System.out.println("===================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        SearchService searchService = new SearchService(inventory);

        // Perform room search
        searchService.searchAvailableRooms();

        System.out.println("\nSearch completed. No inventory changes were made.");

    }
}
