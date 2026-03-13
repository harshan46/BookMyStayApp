import java.util.*;

/**

 * Reservation
 * Represents a confirmed reservation.
 *
 * @author Harshan Santhanam
 * @version 10.0
 */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean cancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.cancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId +
                " | Cancelled: " + cancelled);
    }
}

/**

 * RoomInventory
 * Maintains available room counts.
 *
 * @version 10.0
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void increaseAvailability(String roomType) {
        int count = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, count + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/**

 * CancellationService
 * Handles safe booking cancellation and rollback.
 *
 * Uses Stack to track released room IDs.
 *
 * @version 10.0
 */
class CancellationService {

    private Map<String, Reservation> reservations;
    private Stack<String> releasedRoomIds;
    private RoomInventory inventory;

    public CancellationService(RoomInventory inventory) {
        this.inventory = inventory;
        reservations = new HashMap<>();
        releasedRoomIds = new Stack<>();
    }

    // Store confirmed reservation
    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
    }

    // Cancel booking
    public void cancelReservation(String reservationId) {


        System.out.println("\nProcessing cancellation for: " + reservationId);

        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        Reservation reservation = reservations.get(reservationId);

        if (reservation.isCancelled()) {
            System.out.println("Cancellation Failed: Reservation already cancelled.");
            return;
        }

        // Record released room ID in rollback stack
        releasedRoomIds.push(reservation.getRoomId());

        // Restore inventory
        inventory.increaseAvailability(reservation.getRoomType());

        // Mark reservation cancelled
        reservation.cancel();

        System.out.println("Cancellation Successful!");
        System.out.println("Released Room ID: " + reservation.getRoomId());


    }

    // Display rollback stack
    public void displayRollbackStack() {


        System.out.println("\nRecently Released Room IDs (Rollback Stack):");
        for (String id : releasedRoomIds) {
            System.out.println(id);
        }


    }
}

/**

 * UseCase10BookingCancellation
 *
 * Demonstrates safe cancellation and rollback using Stack.
 *
 * @author Harshan Santhanam
 * @version 10.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {


        System.out.println("===================================");
        System.out.println("       Book My Stay - v10.1");
        System.out.println(" Booking Cancellation & Rollback");
        System.out.println("===================================");

        RoomInventory inventory = new RoomInventory();

        CancellationService cancellationService = new CancellationService(inventory);

        // Simulate confirmed reservations
        Reservation r1 = new Reservation("RES101", "Alice", "Single Room", "SR101");
        Reservation r2 = new Reservation("RES102", "Bob", "Double Room", "DR201");

        cancellationService.addReservation(r1);
        cancellationService.addReservation(r2);

        // Perform cancellations
        cancellationService.cancelReservation("RES101");

        // Attempt invalid cancellation
        cancellationService.cancelReservation("RES999");

        // Attempt duplicate cancellation
        cancellationService.cancelReservation("RES101");

        // Display rollback stack
        cancellationService.displayRollbackStack();

        // Display inventory state
        inventory.displayInventory();

        System.out.println("\nSystem state restored safely after cancellation.");


    }
}
