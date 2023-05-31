package alowator.storage.collection;

import alowator.storage.entity.Airport;

import java.util.List;

public interface AirportCollection {
    List<Airport> getAll() throws Exception;

    List<Airport> getAll(String city) throws Exception;
}
