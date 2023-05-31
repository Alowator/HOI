package alowator.servlet;

import alowator.storage.Storage;
import alowator.storage.entity.Airport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AirportsServlet extends RestServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String city = request.getParameter("city");
        try {
            List<Airport> airports;
            if (city == null) {
                airports = Storage.airports().getAll();
            } else {
                airports = Storage.airports().getAll(city);
            }

            sendJsonResponse(response, Map.of("airports", airports));
        } catch (Exception e) {
            sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ":(");
        }
    }
}
