package alowator.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public abstract class RestServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    protected void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        setJsonHeaders(response);
        response.getWriter().println(objectMapper.writeValueAsString(data));
    }

    protected void sendResponse(HttpServletResponse response, int errorCode) throws IOException {
        setJsonHeaders(response);
        response.setStatus(errorCode);
    }

    protected void sendJsonError(HttpServletResponse response, int errorCode, String errorMessage) throws IOException {
        setJsonHeaders(response);
        response.setStatus(errorCode);
        response.getWriter().println(objectMapper.writeValueAsString(Map.of("message", errorMessage)));
    }

    private void setJsonHeaders(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
    }
}
