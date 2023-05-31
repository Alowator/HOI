package alowator.storage.collection;

import alowator.storage.entity.Airport;
import alowator.storage.entity.City;

import java.util.List;

public interface CityCollection {
    List<City> getAll() throws Exception;
}
