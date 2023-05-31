package alowator.storage.impl.sql;

import alowator.storage.collection.RoutesCollection;
import alowator.storage.entity.Route;

import java.sql.ResultSet;
import java.util.*;

import static java.util.Map.entry;

public class SqlRoutesCollection extends BaseSqlCollection implements RoutesCollection {

    @Override
    public List<Route> getAll(String source, String destination, String departureDate, String bookingClass, String maxConnections) throws Exception {
        String sql = """
            WITH RECURSIVE r AS (
                SELECT\s
            \t\tdeparture_airport,\s
            \t\tarrival_airport,\s
            \t\tscheduled_arrival,\s
            \t\t0 AS lvl,\s
            \t\tARRAY[flight_id] AS route,
            \t\tfare_conditions
                FROM flights
            \tJOIN seats ON flights.aircraft_code = seats.aircraft_code
            \tWHERE (departure_airport IN (SELECT airport_code FROM airports_data WHERE city ->> 'en' = ? OR city ->> 'ru' = ?) OR departure_airport = ?)
            \t\tAND scheduled_departure::date = ?::date\s
            \t\tAND status = 'Scheduled'
            \tGROUP BY flight_id, seats.fare_conditions
            \tHAVING seats.fare_conditions = ?
            \t
                UNION
               \s
                SELECT\s
            \t\tflights.departure_airport,\s
            \t\tflights.arrival_airport,\s
            \t\tflights.scheduled_arrival,\s
            \t\tr.lvl + 1 AS lvl,
            \t\tARRAY_APPEND(r.route, flights.flight_id) AS route,
            \t\tseats.fare_conditions
                FROM flights
                JOIN r ON r.arrival_airport = flights.departure_airport
            \tJOIN seats ON flights.aircraft_code = seats.aircraft_code AND r.fare_conditions = seats.fare_conditions
            \tWHERE flights.scheduled_departure::date = ?::date\s
            \t\tAND flights.status = 'Scheduled'\s
            \t\tAND r.scheduled_arrival < flights.scheduled_departure
            )
            SELECT *\s
            FROM r
            WHERE\s
            \t(arrival_airport IN (SELECT airport_code FROM airports_data WHERE city ->> 'en' = ? OR city ->> 'ru' = ?) OR arrival_airport = ?)\s
            \tAND lvl <= ?::integer;""";
        ResultSet result = execute(sql, Map.ofEntries(
            entry(1, source),
            entry(2, source),
            entry(3, source),
            entry(4, departureDate),
            entry(5, bookingClass),
            entry(6, departureDate),
            entry(7, destination),
            entry(8, destination),
            entry(9, destination),
            entry(10, maxConnections)
        ));
        List<Route> routes = new ArrayList<>();
        while (result.next()) {
            routes.add(new Route(Arrays.asList((Integer[]) result.getArray("route").getArray())));
        }
        return routes;
    }
}
