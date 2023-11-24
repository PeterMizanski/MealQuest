import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegistrationResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	public void init() {
		try {
			// init jdbc driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {e.printStackTrace();}
	}
	
	
	
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws IOException {
    	// create a var to be used to match against "success" or "failure"
        String status = request.getParameter("status");

        
        if ("success".equals(status)) {
            // Display a success message
            response.getWriter().println("Registration successful!");
        } else if ("failure".equals(status)) {
            // Display a failure message
            response.getWriter().println("Registration failed.");
        }
        
        
    }
    
    
    
// END OF RESISTRATION RESULT SERVLET
}