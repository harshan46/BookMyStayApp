import java.util.*;

/**

 * Reservation
 * Represents a confirmed reservation.
 *
 * @author Harshan Santhanam
 * @version 8.0
 */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
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

    public String getRoomId() {
        return roomId;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId);
    }
}

/**

 * BookingHistory
 * Maintains a chronological list of confirmed reservations.
 *
 * @version 8.0
 */
class BookingHistory {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed reservation to history
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Retrieve all reservations
    public List<Reservation> getReservations() {
        return reservations;
    }

    // Display booking history
    public void displayHistory() {


        System.out.println("\nBooking History:");
        for (Reservation r : reservations) {
            r.displayReservation();
        }

    }
}

/**

 * BookingReportService
 * Generates reports based on booking history.
 *
 * @version 8.0
 */
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Generate summary report
    public void generateSummaryReport() {


        List<Reservation> reservations = history.getReservations();

        System.out.println("\n===== Booking Summary Report =====");

        System.out.println("Total Reservations: " + reservations.size());

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : reservations) {
            roomTypeCount.put(
                    r.getRoomType(),
                    roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        System.out.println("\nReservations by Room Type:");
        for (Map.Entry<String, Integer> entry : roomTypeCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }


    }
}

/**

 * UseCase8BookingHistoryReport
 *
 * Demonstrates historical tracking of confirmed bookings
 * and report generation without modifying stored data.
 *
 * @author Harshan Santhanam
 * @version 8.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("       Book My Stay - v8.1");
        System.out.println("      Booking History & Report");
        System.out.println("===================================");

        // Initialize booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed reservations
        history.addReservation(new Reservation("RES101", "Alice", "Single Room", "SR101"));
        history.addReservation(new Reservation("RES102", "Bob", "Double Room", "DR201"));
        history.addReservation(new Reservation("RES103", "Charlie", "Suite Room", "SU301"));
        history.addReservation(new Reservation("RES104", "David", "Single Room", "SR102"));

        // Display stored booking history
        history.displayHistory();

        // Generate report
        BookingReportService reportService = new BookingReportService(history);
        reportService.generateSummaryReport();

        System.out.println("\nReport generation completed without modifying booking history.");


    }
}
