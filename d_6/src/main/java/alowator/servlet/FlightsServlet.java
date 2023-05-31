package alowator.servlet;

import alowator.storage.Storage;
import alowator.storage.entity.City;
import alowator.storage.entity.Flight;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class FlightsServlet extends RestServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String airport = request.getParameter("airport");
        if (airport == null) {
            sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, "Airport code must be set");
            return;
        }

        List<Flight> flights;
        try {
            if (request.getServletPath().equals("/inbound-schedule")) {
                flights = Storage.flights().getInbound(airport);
            } else {
                flights = Storage.flights().getOutbound(airport);
            }
            sendJsonResponse(response, Map.of("flights", flights));
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ":(");
        }
    }
}
