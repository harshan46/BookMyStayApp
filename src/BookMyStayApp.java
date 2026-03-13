import java.util.*;

/**

 * Service
 * Represents an optional add-on service for a reservation.
 *
 * @author Harshan Santhanam
 * @version 7.0
 */
class Service {

    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void displayService() {
        System.out.println(serviceName + " - ₹" + price);
    }
}

/**

 * AddOnServiceManager
 * Manages the relationship between reservations and selected services.
 *
 * Uses Map<String, List<Service>> to map reservation IDs to services.
 *
 * @version 7.0
 */
class AddOnServiceManager {

    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, Service service) {


        reservationServices.putIfAbsent(reservationId, new ArrayList<>());

        reservationServices.get(reservationId).add(service);

        System.out.println("Service added to Reservation " + reservationId + ": " + service.getServiceName());


    }

    // Display services for a reservation
    public void displayServices(String reservationId) {


        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected for reservation " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");

        for (Service service : services) {
            service.displayService();
        }


    }

    // Calculate additional cost
    public double calculateTotalServiceCost(String reservationId) {


        List<Service> services = reservationServices.get(reservationId);

        if (services == null) return 0;

        double total = 0;

        for (Service service : services) {
            total += service.getPrice();
        }

        return total;

    }
}

/**

 * UseCase7AddOnServices
 *
 * Demonstrates attaching optional services to an existing reservation
 * without modifying booking or inventory logic.
 *
 * @author Harshan Santhanam
 * @version 7.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {


        System.out.println("===================================");
        System.out.println("       Book My Stay - v7.1");
        System.out.println("       Add-On Service Selection");
        System.out.println("===================================");

        // Example reservation IDs
        String reservation1 = "RES101";
        String reservation2 = "RES102";

        // Create service manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Available services
        Service breakfast = new Service("Breakfast", 500);
        Service airportPickup = new Service("Airport Pickup", 1200);
        Service spa = new Service("Spa Access", 1500);

        // Guest selects services
        serviceManager.addService(reservation1, breakfast);
        serviceManager.addService(reservation1, spa);

        serviceManager.addService(reservation2, airportPickup);

        // Display services for reservations
        serviceManager.displayServices(reservation1);
        serviceManager.displayServices(reservation2);

        // Calculate additional cost
        double cost1 = serviceManager.calculateTotalServiceCost(reservation1);
        double cost2 = serviceManager.calculateTotalServiceCost(reservation2);

        System.out.println("\nTotal Add-On Cost for " + reservation1 + ": ₹" + cost1);
        System.out.println("Total Add-On Cost for " + reservation2 + ": ₹" + cost2);

        System.out.println("\nCore booking and inventory remain unchanged.");

    }
}
