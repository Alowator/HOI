package alowator.api.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static alowator.api.Common.sendJsonError;

public class FlightsQueryFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        if (response instanceof HttpServletResponse httpResponse) {
            if (request.getParameter("airport") == null) {
                sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST, "Airport code must be set");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}