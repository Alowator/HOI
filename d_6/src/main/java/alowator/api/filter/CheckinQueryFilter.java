package alowator.api.filter;

import alowator.api.dto.Booking;
import alowator.api.dto.Checkin;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import static alowator.api.Common.sendJsonError;

public class CheckinQueryFilter implements Filter {

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
                Checkin checkin = objectMapper.readValue(requestBody, Checkin.class);
                if (!verifyCheckin(httpRequest, httpResponse, checkin)) {
                    return;
                }
                request.setAttribute("checkin", checkin);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    private boolean verifyCheckin(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Checkin checkin) throws IOException {
        if (checkin.flightId == null) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "flightId field must be set");
            return false;
        }
        if (checkin.ticketId == null) {
            sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST,
                "ticketId field must be set");
            return false;
        }
        return true;
    }
}