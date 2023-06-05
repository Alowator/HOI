package alowator.storage.impl.sql;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class BaseSqlCollection {
    protected final static Connection connection;
    protected final ObjectMapper mapper = new ObjectMapper();

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/demo",
                "postgres",
                "ultrgmngdb");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected ResultSet execute(String sql) throws SQLException {
        return execute(sql, new HashMap<>());
    }

    protected ResultSet execute(String sql, Map<Integer, String> params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        for (Entry<Integer, String> entry : params.entrySet()) {
            statement.setString(entry.getKey(), entry.getValue());
        }
        return statement.executeQuery();
    }
}
