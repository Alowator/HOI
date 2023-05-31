package alowator.storage.impl.sql;

import alowator.storage.collection.FlightCollection;
import alowator.storage.entity.Flight;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlFlightsCollection extends BaseSqlCollection implements FlightCollection {
    @Override
    public List<Flight> getInbound(String airport) throws Exception {
        String sql =
            "SELECT flight_no, EXTRACT(ISODOW FROM scheduled_arrival) as day_of_week, " +
                "scheduled_arrival::time as time, departure_airport, arrival_airport " +
            "FROM flights " +
            "GROUP BY flight_no, day_of_week, time, departure_airport, arrival_airport " +
            "HAVING arrival_airport = ? " +
            "ORDER BY flight_no;";
        ResultSet result = execute(sql, Map.of(1, airport));
        return collectFlights(result);
    }

    @Override
    public List<Flight> getOutbound(String airport) throws Exception {
        String sql =
            "SELECT flight_no, EXTRACT(ISODOW FROM scheduled_departure) as day_of_week, " +
                "scheduled_departure::time as time, departure_airport, arrival_airport " +
                "FROM flights " +
                "GROUP BY flight_no, day_of_week, time, departure_airport, arrival_airport " +
                "HAVING departure_airport = ? " +
                "ORDER BY flight_no;";
        ResultSet result = execute(sql, Map.of(1, airport));
        return collectFlights(result);
    }

    private List<Flight> collectFlights(ResultSet result) throws SQLException {
        Map<String, Flight> flights = new HashMap<>();
        while (result.next()) {
            String flightNo = result.getString("flight_no");
            if (flights.containsKey(flightNo)) {
                flights.get(flightNo).daysOfWeek.add(result.getInt("day_of_week"));
            } else {
                flights.put(flightNo, new Flight(
                    flightNo,
                    result.getInt("day_of_week"),
                    result.getString("time"),
                    result.getString("departure_airport"),
                    result.getString("arrival_airport")
                ));
            }
        }
        return flights.values().stream().toList();
    }
}
