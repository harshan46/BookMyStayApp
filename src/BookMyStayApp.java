import java.util.*;

/**

 * Custom exception for invalid booking scenarios
 *
 * @author Harshan Santhanam
 * @version 9.0
 */
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

/**

 * RoomInventory
 * Maintains room availability and validates inventory updates.
 *
 * @version 9.0
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    // Validate room type
    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    // Check availability
    public void validateAvailability(String roomType) throws InvalidBookingException {
        int available = inventory.get(roomType);


        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }


    }

    // Safe inventory update
    public void decreaseAvailability(String roomType) throws InvalidBookingException {


        int available = inventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("Inventory cannot become negative.");
        }

        inventory.put(roomType, available - 1);

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
 * Validates booking input and processes reservations safely.
 *
 * @version 9.0
 */
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(String guestName, String roomType) {


        try {

            System.out.println("\nProcessing booking for " + guestName);

            // Validate input
            if (guestName == null || guestName.trim().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }

            // Validate room type
            inventory.validateRoomType(roomType);

            // Validate availability
            inventory.validateAvailability(roomType);

            // Update inventory safely
            inventory.decreaseAvailability(roomType);

            System.out.println("Booking successful!");
            System.out.println("Guest: " + guestName);
            System.out.println("Room Type: " + roomType);

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("Unexpected system error occurred.");

        }


    }
}

/**

 * UseCase9ErrorHandlingValidation
 *
 * Demonstrates structured validation and error handling
 * to protect system state from invalid inputs.
 *
 * @author Harshan Santhanam
 * @version 9.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {


        System.out.println("===================================");
        System.out.println("       Book My Stay - v9.1");
        System.out.println("   Error Handling & Validation");
        System.out.println("===================================");

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        // Valid booking
        bookingService.bookRoom("Alice", "Single Room");

        // Invalid room type
        bookingService.bookRoom("Bob", "Luxury Room");

        // No availability
        bookingService.bookRoom("Charlie", "Suite Room");

        // Invalid guest name
        bookingService.bookRoom("", "Double Room");

        inventory.displayInventory();

        System.out.println("\nSystem continues running safely after errors.");


    }
}
