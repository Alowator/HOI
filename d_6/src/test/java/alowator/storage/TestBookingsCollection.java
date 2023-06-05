package alowator.storage;

import alowator.storage.entity.Route;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBookingsCollection {
    @Test
    public void testBooking() throws Exception {
        Integer[] flightIds = {12345, 12346};
        String passengerName = "";
        String passengerPhone = "79991230821";
        String passengerEmail = "fedoruk@nsu.ru";
        String passengerId = "5050 123123";
        String fareConditions = "Economy";

        Storage.bookings().booking(flightIds, passengerName, passengerPhone, passengerEmail, passengerId, fareConditions, true);
    }

    @Test
    public void  testCheckin() throws Exception {
        String ticketNo = "1000000000039";
        String flightId = "15402";

        assertFalse(Storage.bookings().checkin(ticketNo, flightId));
    }
}