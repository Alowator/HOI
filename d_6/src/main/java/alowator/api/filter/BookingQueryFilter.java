package alowator.api.filter;

import alowator.api.dto.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import static alowator.api.Common.sendJsonError;

public class BookingQueryFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            if (httpRequest.getMethod().equals("POST")) {
                BufferedReader reader = request.getReader();
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String requestBody = sb.toString();

                ObjectMapper objectMapper = new ObjectMapper();
                Booking booking = objectMapper.readValue(requestBody, Booking.class);
                if (!verifyBooking(httpRequest, httpResponse, booking)) {
                    return;
                }
                request.setAttribute("booking", booking);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    private boolean verifyBooking(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Booking booking) throws IOException {
        if (booking.flightIds == null) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "flightIds field must be set");
            return false;
        }
        if (booking.flightIds.length == 0) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "flightIds field must be not empty");
            return false;
        }

        if (booking.passengerName == null) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "passengerName field must be set");
            return false;
        }
        if (booking.passengerId == null) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "passengerId field must be set");
            return false;
        }

        if (booking.passengerPhone == null) {
            booking.passengerPhone = "";
        }
        if (booking.passengerEmail == null) {
            booking.passengerEmail = "";
        }

        String[] availableClasses = new String[] {"Economy", "Comfort", "Business"};
        if (booking.fareConditions == null) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "fareConditions field must be set");
            return false;
        } else if (!Arrays.asList(availableClasses).contains(booking.fareConditions)) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "bookingClass must be in " + Arrays.asList(availableClasses));
            return false;
        }

        return true;
    }
}