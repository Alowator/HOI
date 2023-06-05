package alowator.storage.collection;

public interface BookingsCollection {
    String booking(Integer[] flightIds, String passengerName, String passengerPhone, String passengerEmail, String passengerId, String fareConditions, boolean commit) throws Exception;

    boolean checkin(String ticketNo, String flightId) throws Exception;
}
