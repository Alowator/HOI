package alowator.storage;

import alowator.storage.entity.Airport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static alowator.storage.TestCityCollection.verifyCityEntry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestAirportCollection {
    @Test
    public void testGetAll() throws Exception {
        List<Airport> airports = Storage.airports().getAll();

        assertFalse(airports.isEmpty());
        airports.forEach(TestAirportCollection::verifyAirportEntry);
    }

    @Test
    public void testGetAllUniqueness() throws Exception {
        Set<String> codes = new HashSet<>();
        List<Airport> airports = Storage.airports().getAll();

        airports.forEach(airport -> codes.add(airport.code));
        assertEquals(airports.size(), codes.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Moscow", "Москва", "Novokuznetsk", "Novokuznetsk"})
    public void testGetAllWithinCity(String city) throws Exception {
        List<Airport> airports = Storage.airports().getAll(city);

        assertFalse(airports.isEmpty());
        airports.forEach(TestAirportCollection::verifyAirportEntry);
    }

    public static void verifyAirportEntry(Airport airport) {
        assertEquals(3, airport.code.length());
        verifyAirportNameEntry(airport.name);
        verifyCityEntry(airport.city);
        assertFalse(airport.timezone.isEmpty());
    }

    public static void verifyAirportNameEntry(Airport.AirportName airportName) {
        assertFalse(airportName.ru.isEmpty());
        assertFalse(airportName.en.isEmpty());
    }
}