package alowator.storage;

import alowator.storage.entity.Flight;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestFlightsCollection {
    @ParameterizedTest
    @ValueSource(strings = {"OVB", "DME", "PEZ"})
    public void testInbound(String airport) throws Exception {
        List<Flight> flights = Storage.flights().getInbound(airport);

        assertFalse(flights.isEmpty());
        flights.forEach(TestFlightsCollection::verifyFlightEntity);
    }

    @ParameterizedTest
    @ValueSource(strings = {"OVB", "DME", "PEZ"})
    public void testOutbound(String airport) throws Exception {
        List<Flight> flights = Storage.flights().getOutbound(airport);

        assertFalse(flights.isEmpty());
        flights.forEach(TestFlightsCollection::verifyFlightEntity);
    }

    @ParameterizedTest
    @ValueSource(strings = {"OVB", "DME", "PEZ"})
    public void testInboundUniqueness(String airport) throws Exception {
        Set<String> flightNumbers = new HashSet<>();
        List<Flight> flights = Storage.flights().getInbound(airport);

        flights.forEach(flight -> flightNumbers.add(flight.flightNo));
        assertEquals(flights.size(), flightNumbers.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"OVB", "DME", "PEZ"})
    public void testOutboundUniqueness(String airport) throws Exception {
        Set<String> flightNumbers = new HashSet<>();
        List<Flight> flights = Storage.flights().getOutbound(airport);

        flights.forEach(flight -> flightNumbers.add(flight.flightNo));
        assertEquals(flights.size(), flightNumbers.size());
    }

    @Test
    public void testExistFlight() throws Exception {
        assertTrue(Storage.flights().isFlightExists(12345));
    }

    @Test
    public void testNotExistFlight() throws Exception {
        assertFalse(Storage.flights().isFlightExists(1234666));
    }

    public static void verifyFlightEntity(Flight flight) {
        assertEquals(6, flight.flightNo.length());
        assertFalse(flight.daysOfWeek.isEmpty());
        flight.daysOfWeek.forEach(value -> assertTrue(1 <= value && value <= 7));
        assertEquals(8, flight.time.length());
        assertEquals(3, flight.origin.length());
        assertEquals(3, flight.destination.length());
    }
}