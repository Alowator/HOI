package alowator.storage.impl.sql;

import alowator.storage.collection.CityCollection;
import alowator.storage.entity.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SqlCityCollection extends BaseSqlCollection implements CityCollection {
    @Override
    public List<City> getAll() throws Exception {
        String sql = "SELECT city FROM airports_data";
        ResultSet resultSet = execute(sql);
        Set<String> citiesJson = new HashSet<>();

        while (resultSet.next()) {
            citiesJson.add(resultSet.getString("city"));
        }

        List<City> cities = new ArrayList<>();
        for (String value : citiesJson) {
            cities.add(mapper.readValue(value, City.class));
        }
        return cities;
    }
}
