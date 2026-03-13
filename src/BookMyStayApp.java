import java.util.*;

/**

 * Reservation
 * Represents a booking request submitted by a guest.
 *
 * @author Harshan Santhanam
 * @version 11.0
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
 * Shared resource that stores room availability.
 * All updates are synchronized to ensure thread safety.
 *
 * @version 11.0
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    // synchronized critical section
    public synchronized boolean allocateRoom(String roomType) {


        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }

        return false;


    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory State:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/**

 * ConcurrentBookingProcessor
 * Processes booking requests using multiple threads.
 *
 * @version 11.0
 */
class ConcurrentBookingProcessor implements Runnable {

    private Queue<Reservation> bookingQueue;
    private RoomInventory inventory;

    public ConcurrentBookingProcessor(Queue<Reservation> bookingQueue, RoomInventory inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {


        while (true) {

            Reservation reservation;

            // synchronized queue access
            synchronized (bookingQueue) {

                if (bookingQueue.isEmpty()) {
                    return;
                }

                reservation = bookingQueue.poll();
            }

            String guest = reservation.getGuestName();
            String roomType = reservation.getRoomType();

            System.out.println(Thread.currentThread().getName() +
                    " processing booking for " + guest);

            boolean success = inventory.allocateRoom(roomType);

            if (success) {
                System.out.println("Booking confirmed for " + guest +
                        " (" + roomType + ")");
            } else {
                System.out.println("Booking failed for " + guest +
                        " (No rooms available)");
            }
        }


    }
}

/**

 * UseCase11ConcurrentBookingSimulation
 *
 * Simulates concurrent booking requests processed by multiple threads.
 * Synchronization ensures safe access to shared resources.
 *
 * @author Harshan Santhanam
 * @version 11.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("      Book My Stay - v11.1");
        System.out.println(" Concurrent Booking Simulation");
        System.out.println("===================================");

        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Simulated concurrent booking requests
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Single Room"));
        bookingQueue.add(new Reservation("Charlie", "Single Room"));
        bookingQueue.add(new Reservation("David", "Double Room"));
        bookingQueue.add(new Reservation("Eve", "Double Room"));

        // Create worker threads
        Thread t1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory), "Worker-1");
        Thread t2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory), "Worker-2");
        Thread t3 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory), "Worker-3");

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }

        inventory.displayInventory();

        System.out.println("\nConcurrent booking simulation completed safely.");

    }
}
