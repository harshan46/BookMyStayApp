import java.io.*;
import java.util.*;

/**

 * Reservation
 * Represents a confirmed booking.
 */
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

/**

 * SystemState
 * Stores inventory and booking history for persistence.
 */
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

/**

 * PersistenceService
 * Handles saving and loading system state.
 */
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    // Save system state
    public static void saveState(SystemState state) {


        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state.");
        }


    }

    // Load system state
    public static SystemState loadState() {


        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state restored successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading system state. Starting with empty state.");
        }

        return null;
    }
}

/**

 * UseCase12DataPersistenceRecovery
 * Demonstrates saving and restoring system state.
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("       Book My Stay - v12.0");
        System.out.println(" Data Persistence & Recovery");
        System.out.println("===================================");

        // Attempt to restore previous state
        SystemState restoredState = PersistenceService.loadState();

        Map<String, Integer> inventory;
        List<Reservation> bookingHistory;

        if (restoredState != null) {

            inventory = restoredState.inventory;
            bookingHistory = restoredState.bookingHistory;

        } else {

            inventory = new HashMap<>();
            inventory.put("Single Room", 3);
            inventory.put("Double Room", 2);

            bookingHistory = new ArrayList<>();
        }

        // Display current inventory
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        // Simulate new booking
        Reservation newReservation =
                new Reservation("RES201", "Alice", "Single Room");

        bookingHistory.add(newReservation);

        inventory.put("Single Room",
                inventory.get("Single Room") - 1);

        System.out.println("\nNew Booking Added:");
        System.out.println(newReservation);

        // Save updated state before shutdown
        SystemState newState = new SystemState(inventory, bookingHistory);
        PersistenceService.saveState(newState);

        System.out.println("\nBooking History:");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }

        System.out.println("\nSystem shutdown complete. State persisted.");

    }
}
