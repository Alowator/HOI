package alowator.api.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        if (response instanceof HttpServletResponse httpResponse) {
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("utf-8");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}