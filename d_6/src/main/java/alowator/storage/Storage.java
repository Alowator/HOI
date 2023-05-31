package alowator.storage;

import alowator.storage.collection.*;
import alowator.storage.impl.sql.*;

public class Storage {
    public static CityCollection city() {
        return new SqlCityCollection();
    }

    public static AirportCollection airports() {
        return new SqlAirportsCollection();
    }

    public static FlightCollection flights() {
        return new SqlFlightsCollection();
    }

    public static RoutesCollection routes() {
        return new SqlRoutesCollection();
    }

    public static BookingsCollection bookings() {
        return new SqlBookingsCollection();
    }
}
