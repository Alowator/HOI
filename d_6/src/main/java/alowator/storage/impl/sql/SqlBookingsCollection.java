package alowator.storage.impl.sql;

import alowator.storage.collection.BookingsCollection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class SqlBookingsCollection extends BaseSqlCollection implements BookingsCollection {

    @Override
    public boolean checkin(String ticketNo) throws Exception {
        try {
            connection.setAutoCommit(false);
            String sql = """
            SELECT *
            FROM ticket_flights AS tf
            LEFT JOIN boarding_passes bp ON tf.ticket_no = bp.ticket_no
            WHERE bp.ticket_no IS NULL AND tf.ticket_no = ?
            """;
            ResultSet result = execute(sql, Map.of(1, ticketNo));
            if (result.next()) {
                sql = """
                INSERT INTO boarding_passes\s
                SELECT tf.ticket_no, tf.flight_id, floor(random() * 10000000)::int, MIN(s.seat_no)
                FROM ticket_flights AS tf
                JOIN flights f ON tf.flight_id = f.flight_id
                JOIN seats s ON f.aircraft_code = s.aircraft_code AND tf.fare_conditions = s.fare_conditions
                LEFT JOIN boarding_passes AS bp ON f.flight_id = bp.flight_id AND bp.seat_no = s.seat_no
                WHERE tf.ticket_no = ?
                GROUP BY tf.ticket_no, tf.flight_id
                """;
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, ticketNo);
                st.executeUpdate();
                connection.commit();
                connection.setAutoCommit(true);
                return true;
            } else {
                connection.setAutoCommit(true);
                return false;
            }
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
