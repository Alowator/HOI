package alowator.storage.collection;

import alowator.storage.entity.Flight;

import java.util.List;

public interface FlightCollection {
    List<Flight> getInbound(String airport) throws Exception;

    List<Flight> getOutbound(String airport) throws Exception;
}
