package alowator.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class Common {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(objectMapper.writeValueAsString(data));
    }

    public static void sendNoBodyResponse(HttpServletResponse response, int code) throws IOException {
        response.setStatus(code);
    }

    public static void sendJsonError(HttpServletResponse response, int errorCode, String errorMessage) throws IOException {
        response.setStatus(errorCode);
        response.getWriter().println(objectMapper.writeValueAsString(Map.of("message", errorMessage)));
    }
}
