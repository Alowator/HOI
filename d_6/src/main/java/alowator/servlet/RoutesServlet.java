package alowator.servlet;

import alowator.storage.Storage;
import alowator.storage.entity.Flight;
import alowator.storage.entity.Route;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class RoutesServlet extends RestServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String source = request.getParameter("source");
        String destination = request.getParameter("destination");
        String departureDate = request.getParameter("departureDate");
        String bookingClass = request.getParameter("bookingClass");
        String maxConnections = request.getParameter("maxConnections");

        if (source == null || destination == null || departureDate == null || bookingClass == null) {
            sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, "Args code must be set");
            return;
        }
        if (maxConnections == null) {
            maxConnections = String.valueOf(Integer.MAX_VALUE);
        }

        List<Route> routes;
        try {
            routes = Storage.routes().getAll(source, destination, departureDate, bookingClass, maxConnections);
            sendJsonResponse(response, Map.of("routes", routes));
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ":(");
        }
    }
}
