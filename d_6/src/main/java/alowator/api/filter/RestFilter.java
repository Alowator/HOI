package alowator.api.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import static alowator.api.Common.sendJsonError;

public class RestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        if (response instanceof HttpServletResponse httpResponse) {
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("utf-8");

            if (request instanceof HttpServletRequest httpRequest) {
                if (httpRequest.getMethod().equals("POST") && !httpRequest.getContentType().equals("application/json")) {
                    sendJsonError(httpResponse, HttpServletResponse.SC_BAD_REQUEST, "Accept only application/json content");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}