import java.util.*;

/**

 * Reservation
 * Represents a guest booking request.
 *
 * @author Harshan Santhanam
 * @version 6.0
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**

 * RoomInventory
 * Maintains availability of rooms.
 *
 * @version 6.0
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseAvailability(String roomType) {
        int current = inventory.getOrDefault(roomType, 0);
        if (current > 0) {
            inventory.put(roomType, current - 1);
        }
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/**

 * BookingService
 * Processes booking requests and allocates rooms.
 *
 * @version 6.0
 */
class BookingService {

    private Queue<Reservation> bookingQueue;
    private RoomInventory inventory;

    // Map room type -> allocated room IDs
    private HashMap<String, Set<String>> allocatedRooms;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        bookingQueue = new LinkedList<>();
        allocatedRooms = new HashMap<>();
    }

    // Add reservation request
    public void addReservation(Reservation reservation) {
        bookingQueue.offer(reservation);
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        String prefix = roomType.replace(" ", "").substring(0, 2).toUpperCase();
        int random = (int)(Math.random() * 900 + 100);
        return prefix + random;
    }

    // Process booking queue
    public void processBookings() {
        while (!bookingQueue.isEmpty()) {

            Reservation reservation = bookingQueue.poll();
            String roomType = reservation.getRoomType();

            System.out.println("\nProcessing booking for " + reservation.getGuestName());

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRooms.putIfAbsent(roomType, new HashSet<>());

                // Ensure unique ID
                while (allocatedRooms.get(roomType).contains(roomId)) {
                    roomId = generateRoomId(roomType);
                }

                allocatedRooms.get(roomType).add(roomId);

                // Update inventory immediately
                inventory.decreaseAvailability(roomType);

                System.out.println("Reservation Confirmed!");
                System.out.println("Guest: " + reservation.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);

            } else {
                System.out.println("Sorry! No rooms available for " + roomType);
            }
        }


    }

    // Display allocated rooms
    public void displayAllocatedRooms() {

        System.out.println("\nAllocated Room IDs:");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

    }
}

/**

 * UseCase6RoomAllocationService
 *
 * Demonstrates booking confirmation and safe room allocation
 * while preventing double booking.
 *
 * @author Harshan Santhanam
 * @version 6.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("       Book My Stay - v6.1");
        System.out.println("  Reservation Confirmation Service");
        System.out.println("===================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize booking service
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests
        bookingService.addReservation(new Reservation("Alice", "Single Room"));
        bookingService.addReservation(new Reservation("Bob", "Double Room"));
        bookingService.addReservation(new Reservation("Charlie", "Suite Room"));
        bookingService.addReservation(new Reservation("David", "Single Room"));

        // Process bookings
        bookingService.processBookings();

        // Display results
        bookingService.displayAllocatedRooms();
        inventory.displayInventory();

        System.out.println("\nRoom allocation completed safely.");


    }
}
