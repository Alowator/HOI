package alowator.api.servlet;

import alowator.storage.Storage;
import alowator.storage.entity.Flight;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static alowator.api.Common.sendJsonError;
import static alowator.api.Common.sendJsonResponse;

public final class FlightsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String airport = request.getParameter("airport");

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
