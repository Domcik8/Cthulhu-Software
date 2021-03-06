package lt.vu.mif.labanoro_draugai.authentication;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;

/**
 *
 * @author Ernest J
 */
@WebServlet("/error")
public class ErrorHandlerServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private DatabaseManager dbm;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        //customize error message
        Throwable throwable = (Throwable) request
                .getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request
                .getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }
        String requestUri = (String) request
                .getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }
        
//        request.setAttribute("error", "Servlet " + servletName
//                + " has thrown an exception " + throwable.getClass().getName()
//                + " : " + throwable.getMessage());
        
//        request.getRequestDispatcher("WEB-INF/other_pages" + "/someError.html").forward(request, response);
        request.getRequestDispatcher(dbm.getSystemParameter("SystemParameter.Redirect.GlobalError").getValue()).forward(request, response);
    }
}
