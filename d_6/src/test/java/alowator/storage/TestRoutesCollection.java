package alowator.storage;

import alowator.storage.entity.Airport;
import alowator.storage.entity.Route;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static alowator.storage.TestCityCollection.verifyCityEntry;
import static org.junit.jupiter.api.Assertions.*;

public class TestRoutesCollection {
    @Test
    public void testGetAll() throws Exception {
        String source = "Moscow";
        String destination = "LED";
        String departureDate = "2017-08-22";
        String bookingClass = "Business";
        int maxConnections = 1;

        List<Route> routes = Storage.routes().getAll(source, destination,
            departureDate, bookingClass, String.valueOf(maxConnections));

        assertFalse(routes.isEmpty());
        routes.forEach(route -> verifyRoute(route, maxConnections));
    }

    @Test
    public void testNonExistingTrip() throws SQLException {
        assertNull(Storage.routes().getTrip(1234566));
    }

    public static void verifyRoute(Route route, int maxConnections) {
        assertFalse(route.trips.isEmpty());
        assertTrue(route.trips.size() <= maxConnections + 1);
        route.trips.forEach(TestRoutesCollection::verifyTrip);
    }

    public static void verifyTrip(Route.Trip trip) {
        assertTrue(trip.flightId > 0);
        assertEquals(6, trip.flightNo.length());
        assertTrue(trip.scheduledDeparture.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\+\\d{2}"));
        assertTrue(trip.scheduledArrival.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\+\\d{2}"));
        assertEquals(3, trip.departureAirport.length());
        assertEquals(3, trip.arrivalAirport.length());
        assertFalse(trip.aircraftCode.isEmpty());
    }
}