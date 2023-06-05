package alowator.storage.impl.sql;

import alowator.storage.collection.BookingsCollection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class SqlBookingsCollection extends BaseSqlCollection implements BookingsCollection {
    private static int LAST_BOOKING_ID = 10000;
    private static long LAST_TICKET_ID = 1000000000001L;

    public SqlBookingsCollection() {
        try {
            initBookingId();
            initTicketId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initBookingId() throws SQLException {
        String sql = """
            SELECT book_ref
            FROM bookings
            ORDER BY book_ref DESC
            LIMIT 1;
            """;

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String lastId = resultSet.getString("book_ref");
            if (lastId.startsWith("X")) {
                LAST_BOOKING_ID = Integer.parseInt(lastId.substring(1));
            }
        }
    }

    private static void initTicketId() throws SQLException {
        String sql = """
            SELECT ticket_no
            FROM tickets
            ORDER BY ticket_no DESC
            LIMIT 1;
            """;

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String lastId = resultSet.getString("ticket_no");
            LAST_TICKET_ID = Long.parseLong(lastId);
        }
    }

    @Override
    public String booking(Integer[] flightIds, String passengerName, String passengerPhone, String passengerEmail, String passengerId, String fareConditions, boolean commit) throws Exception {
        try {
            String sql = """
                BEGIN;
                INSERT INTO bookings VALUES (?, bookings.now(), 0.00);
                INSERT INTO tickets VALUES (?, ?, ?, ?, ?::jsonb);
                """;

            for (Integer flightId : flightIds) {
                sql += """
                INSERT INTO ticket_flights VALUES (?, ?, ?,
                								  	(SELECT pricing_rule.amount
                									FROM flights
                									JOIN pricing_rule ON flights.flight_no = pricing_rule.flight_no
                									WHERE flights.flight_id = ? AND pricing_rule.fare_conditions = ?
                									LIMIT 1));
                """;
            }
            sql += """
                UPDATE bookings SET total_amount = (SELECT SUM(amount) as total
                    FROM ticket_flights
                    WHERE ticket_no = ?
                    GROUP BY ticket_no) WHERE book_ref = ?;
                """;
            if (commit) {
                sql += "COMMIT;";
            }
            System.out.println(sql);

            String bookId = getNextBookingId();
            String ticketId = getNextTicketId();

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, bookId);
            st.setString(2, ticketId);
            st.setString(3, bookId);
            st.setString(4, passengerId);
            st.setString(5, passengerName);
            st.setString(6, "{\"email\": \"" + passengerEmail + "\", \"phone\": \"" + passengerPhone + "\"}");
            int index = 7;
            for (Integer flightId : flightIds) {
                st.setString(index, ticketId); index += 1;
                st.setInt(index, flightId); index += 1;
                st.setString(index, fareConditions); index += 1;
                st.setInt(index, flightId); index += 1;
                st.setString(index, fareConditions); index += 1;
            }
            st.setString(index, ticketId); index += 1;
            st.setString(index, bookId);
            st.executeUpdate();
            return ticketId;
        } catch (SQLException e) {
            connection.rollback();
            return null;
        }
    }

    @Override
    public boolean checkin(String ticketNo, String flightId) throws Exception {
        try {
            connection.setAutoCommit(false);
            String sql = """
            SELECT *
            FROM ticket_flights AS tf
            LEFT JOIN boarding_passes bp ON tf.ticket_no = bp.ticket_no
            WHERE bp.ticket_no IS NULL AND tf.ticket_no = ? AND tf.flight_id = ?::integer
            """;
            ResultSet result = execute(sql, Map.of(1, ticketNo, 2, String.valueOf(flightId)));
            if (result.next()) {
                sql = """
                INSERT INTO boarding_passes\s
                SELECT tf.ticket_no, tf.flight_id, floor(random() * 10000000)::int, MIN(s.seat_no)
                FROM ticket_flights AS tf
                JOIN flights f ON tf.flight_id = f.flight_id
                JOIN seats s ON f.aircraft_code = s.aircraft_code AND tf.fare_conditions = s.fare_conditions
                LEFT JOIN boarding_passes AS bp ON f.flight_id = bp.flight_id AND bp.seat_no = s.seat_no
                WHERE tf.ticket_no = ? AND tf.flight_id = ?::integer
                GROUP BY tf.ticket_no, tf.flight_id
                """;
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, ticketNo);
                st.setString(2, flightId);
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

    private String getNextBookingId() {
        LAST_BOOKING_ID += 1;
        return "X" + LAST_BOOKING_ID;
    }

    private String getNextTicketId() {
        LAST_TICKET_ID += 1;
        return String.valueOf(LAST_TICKET_ID);
    }
}
