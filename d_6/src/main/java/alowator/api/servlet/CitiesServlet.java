package alowator.api.servlet;

import alowator.storage.Storage;
import alowator.storage.entity.City;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static alowator.api.Common.sendJsonError;
import static alowator.api.Common.sendJsonResponse;

public final class CitiesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<City> cities = Storage.city().getAll();
            sendJsonResponse(response, Map.of("cities", cities));
        } catch (Exception e) {
            sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ":(");
        }
    }
}
