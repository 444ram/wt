// ConfigContextServlet.java
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet("/configContext")
public class ConfigContextServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // You can retrieve ServletConfig parameters here if needed
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get ServletConfig parameters
        ServletConfig servletConfig = getServletConfig();
        String servletParam = servletConfig.getInitParameter("servletParam");

        // Get ServletContext parameters
        ServletContext servletContext = getServletContext();
        String contextParam = servletContext.getInitParameter("contextParam");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>Servlet and Context Parameters</h1>");
        out.println("<p>Servlet Parameter: " + servletParam + "</p>");
        out.println("<p>Context Parameter: " + contextParam + "</p>");
    }
}


<!-- web.xml -->
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee" 
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
          http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
          version="3.1">

    <servlet>
        <servlet-name>ConfigContextServlet</servlet-name>
        <servlet-class>ConfigContextServlet</servlet-class>
        <init-param>
            <param-name>servletParam</param-name>
            <param-value>This is a servlet parameter</param-value>
        </init-param>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>ConfigContextServlet</servlet-name>
        <url-pattern>/configContext</url-pattern>
    </servlet-mapping>

    <context-param>
        <param-name>contextParam</param-name>
        <param-value>This is a context parameter</param-value>
    </context-param>

</web-app>
