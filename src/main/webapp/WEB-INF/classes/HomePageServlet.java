import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	public void init() {
		try {
			// load jdbc driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {e.printStackTrace();}
	}
	
	
	
	

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // content type is html
        response.setContentType("text/html");

        // PrintWriter writes html response
        PrintWriter out = response.getWriter();

        // home page content
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>MealQuest - Welcome</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Welcome to MealQuest</h1>");
        out.println("<p>Choose an option:</p>");
        out.println("<ul>");
        
        // create link to the login page
        out.println("<li><a href='" + request.getContextPath() + 
        		"/login'>Login</a></li>");
        
        //create link to the registration page
        out.println("<li><a href='" + request.getContextPath() + 
        		"/register'>Register</a></li>");
        
        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
    }

    
    
    // END OF HOME PAGE SERVLET
}