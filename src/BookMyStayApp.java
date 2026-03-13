import java.util.LinkedList;
import java.util.Queue;

/**

 * Reservation
 *
 * Represents a guest's booking request.
 * This class captures booking intent but does not
 * allocate rooms or modify inventory.
 *
 * @author Harshan Santhanam
 * @version 5.0
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

/**

 * BookingRequestQueue
 *
 * Maintains booking requests using FIFO ordering.
 * This class collects booking requests without performing
 * room allocation or modifying inventory.
 *
 * @version 5.0
 */
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request to queue
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display all queued requests
    public void displayRequests() {
        System.out.println("\nCurrent Booking Requests in Queue (FIFO Order):");

        for (Reservation reservation : requestQueue) {
            reservation.displayReservation();
        }


    }
}

/**

 * UseCase5BookingRequestQueue
 *
 * Demonstrates how booking requests are collected
 * and stored in arrival order using a Queue.
 * No inventory updates or room allocation occur here.
 *
 * @author Harshan Santhanam
 * @version 5.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("       Book My Stay - v5.1");
        System.out.println("   Booking Request Queue (FIFO)");
        System.out.println("===================================");

        // Initialize booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate guest booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add requests to queue (arrival order preserved)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queued booking requests
        bookingQueue.displayRequests();

        System.out.println("\nRequests are stored in arrival order and ready for processing.");

    }
}
