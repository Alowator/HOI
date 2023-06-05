package alowator.storage.collection;

import alowator.storage.entity.Route;

import java.sql.SQLException;
import java.util.List;

public interface RoutesCollection {
    List<Route> getAll(String source, String destination, String departureDate, String bookingClass, String maxConnections) throws Exception;

    Route.Trip getTrip(int flightId) throws SQLException;
}
