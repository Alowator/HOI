package alowator.api.servlet;

import alowator.storage.Storage;
import alowator.storage.entity.Route;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static alowator.api.Common.sendJsonError;
import static alowator.api.Common.sendJsonResponse;

public final class RoutesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String source = request.getParameter("source");
        String destination = request.getParameter("destination");
        String departureDate = request.getParameter("departureDate");
        String bookingClass = request.getParameter("bookingClass");
        String maxConnections = (String) request.getAttribute("maxConnections");

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
