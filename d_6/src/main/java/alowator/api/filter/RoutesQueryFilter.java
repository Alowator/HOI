package alowator.api.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static alowator.api.Common.sendJsonError;

public class RoutesQueryFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse httpResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            if (!processSource(httpRequest, httpResponse)) {
                return;
            }
            if (!processDestination(httpRequest, httpResponse)) {
                return;
            }
            if (!processDepartureDate(httpRequest, httpResponse)) {
                return;
            }
            if (!processBookingClass(httpRequest, httpResponse)) {
                return;
            }
            processMaxConnections(httpRequest, httpResponse);
        }
        chain.doFilter(request, response);
    }

    private boolean processSource(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        if (httpRequest.getParameter("source") == null) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "source field must be set");
            return false;
        }
        return true;
    }

    private boolean processDestination(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        if (httpRequest.getParameter("destination") == null) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "destination field must be set");
            return false;
        }
        return true;
    }

    private boolean processDepartureDate(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        String departureDate = httpRequest.getParameter("departureDate");
        if (departureDate == null) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "departureDate field must be set");
            return false;
        } else if (!departureDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "departureDate must be in YYYY-MM-DD format");
            return false;
        }
        return true;
    }

    private boolean processBookingClass(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        String bookingClass = httpRequest.getParameter("bookingClass");
        String[] availableClasses = new String[] {"Economy", "Comfort", "Business"};
        if (bookingClass == null) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "bookingClass field must be set");
            return false;
        } else if (!Arrays.asList(availableClasses).contains(bookingClass)) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "bookingClass must be in " + Arrays.asList(availableClasses));
            return false;
        }
        return true;
    }

    private void processMaxConnections(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        String maxConnections = httpRequest.getParameter("maxConnections");
        httpRequest.setAttribute("maxConnections",
            maxConnections == null ? String.valueOf(Integer.MAX_VALUE) : maxConnections);
    }

    @Override
    public void destroy() {}
}