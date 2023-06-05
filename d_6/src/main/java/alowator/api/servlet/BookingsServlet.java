package alowator.api.servlet;

import alowator.api.dto.Booking;
import alowator.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static alowator.api.Common.sendJsonError;
import static alowator.api.Common.sendJsonResponse;

public class BookingsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Booking booking = (Booking) request.getAttribute("booking");

        try {
            for (Integer flightId : booking.flightIds) {
                if (!Storage.flights().isFlightExists(flightId)) {
                    sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, "flight " + flightId + " doesnt exist");
                    return;
                }
            }
            String ticketId = Storage.bookings().booking(booking.flightIds, booking.passengerName, booking.passengerPhone, booking.passengerEmail, booking.passengerId, booking.fareConditions, true);
            sendJsonResponse(response, Map.of("ticketId", ticketId));
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ":(");
        }
    }
}
