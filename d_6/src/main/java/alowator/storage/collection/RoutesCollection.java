package alowator.storage.collection;

import alowator.storage.entity.Route;

import java.util.List;

public interface RoutesCollection {
    List<Route> getAll(String source, String destination, String departureDate, String bookingClass, String maxConnections) throws Exception;

}
