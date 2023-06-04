package alowator.storage;

import alowator.storage.entity.City;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestCityCollection {
    @Test
    public void testGetAll() throws Exception {
        List<City> cities = Storage.city().getAll();

        assertFalse(cities.isEmpty());
        cities.forEach(TestCityCollection::verifyCityEntry);
    }

    @Test
    public void testGetAllUniqueness() throws Exception {
        Set<String> names = new HashSet<>();
        List<City> cities = Storage.city().getAll();

        cities.forEach(city -> names.add(city.ru));
        assertEquals(cities.size(), names.size());
    }

    public static void verifyCityEntry(City city) {
        assertFalse(city.ru.isEmpty());
        assertFalse(city.en.isEmpty());
    }
}