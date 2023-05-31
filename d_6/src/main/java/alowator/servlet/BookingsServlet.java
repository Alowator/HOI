package alowator.servlet;

import alowator.storage.Storage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookingsServlet extends RestServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ticketNo = request.getParameter("ticket");

        try {
            if (Storage.bookings().checkin(ticketNo)) {
                sendResponse(response, HttpServletResponse.SC_CREATED);
            } else {
                sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, "Already registered");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonError(response,HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ":(");
        }
    }
}
