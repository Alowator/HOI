package alowator.api.servlet;

import alowator.api.dto.Checkin;
import alowator.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static alowator.api.Common.sendJsonError;
import static alowator.api.Common.sendNoBodyResponse;

public class CheckinServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Checkin checkin = (Checkin) request.getAttribute("checkin");

        try {
            if (Storage.bookings().checkin(checkin.ticketId, String.valueOf(checkin.flightId))) {
                sendNoBodyResponse(response, HttpServletResponse.SC_CREATED);
            } else {
                sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, "Already registered");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonError(response,HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ":(");
        }
    }
}
