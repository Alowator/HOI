package alowator.storage.entity;

import java.util.List;

public class Route {
    public List<Route.Trip> trips;

    public Route(List<Route.Trip> trips) {
        this.trips = trips;
    }

    public static class Trip {
        public final int flightId;

        public final String flightNo;
        public final String scheduledDeparture;
        public final String scheduledArrival;
        public final String departureAirport;
        public final String arrivalAirport;
        public final String aircraftCode;

        public Trip(int flightId, String flightNo,
                    String scheduledDeparture, String scheduledArrival,
                    String departureAirport, String arrivalAirport, String aircraftCode) {
            this.flightId = flightId;
            this.flightNo = flightNo;
            this.scheduledDeparture = scheduledDeparture;
            this.scheduledArrival = scheduledArrival;
            this.departureAirport = departureAirport;
            this.arrivalAirport = arrivalAirport;
            this.aircraftCode = aircraftCode;
        }
    }
}
