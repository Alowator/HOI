package alowator.storage.collection;

import alowator.storage.entity.Route;

import java.util.List;

public interface BookingsCollection {
    boolean checkin(String ticketNo) throws Exception;
}
