package alowator.storage.impl.sql;

import alowator.storage.collection.AirportCollection;
import alowator.storage.entity.Airport;
import alowator.storage.entity.City;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlAirportsCollection extends BaseSqlCollection implements AirportCollection {
    @Override
    public List<Airport> getAll() throws Exception {
        String sql = "SELECT * FROM airports_data";
        ResultSet result = execute(sql);
        return collectAirports(result);
    }

    @Override
    public List<Airport> getAll(String city) throws Exception {
        String sql = "SELECT * FROM airports_data WHERE city ->> 'en' = ? OR city ->> 'ru' = ?;";
        ResultSet result = execute(sql, Map.of(1, city, 2, city));
        return collectAirports(result);
    }

    private List<Airport> collectAirports(ResultSet result) throws SQLException, JsonProcessingException {
        List<Airport> airports = new ArrayList<>();
        while (result.next()) {
            airports.add(new Airport(
                result.getString("airport_code"),
                mapper.readValue(result.getString("airport_name"), Airport.AirportName.class),
                mapper.readValue(result.getString("city"), City.class),
                result.getString("timezone")
            ));
        }
        return airports;
    }
}
