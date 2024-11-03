import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ConfigContextServlet12a extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String initParam = config.getInitParameter("exampleParam");
        System.out.println("ServletConfig Parameter: " + initParam);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String contextParam = context.getInitParameter("globalParam");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Context Parameter</h1>");
        out.println("<p>" + contextParam + "</p>");
        out.println("</body></html>");
    }
}
