import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	/*
	 * OLD CODE, IGNORE
	//public void init() {
		//try {
			//Class.forName("com.mysql.cj.jdbc.Driver");
		//} catch (ClassNotFoundException e) {e.printStackTrace();}
	//}
	 * 
	 */
	
	
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	// get user registration info
        String email = request.getParameter("email");
        String displayName = request.getParameter("displayName");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        // add the user info to database
        try (Connection connection = DatabaseUtil.getConnection()){
        	/*
        	 * OLD CODE, IGNORE
        	// Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            String url = "jdbc:mysql://localhost:3306/mydb";
            String username = "root";
            String dbPassword = "";
            Connection connection = DriverManager.getConnection(url, username, dbPassword);
             */
        	
        	
        	// SQL to INSERT user info
            String sql = ""
            		+ "INSERT INTO user "
            		+ "(emailU, displayNameU, firstNameU, lastNameU, passwordU) "
            		+ "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, displayName);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, password);

            statement.executeUpdate();
            //OLD connection.close();

            // Redirect to show success message in url
            response.sendRedirect("/MealQuest/registration-result?status=success");
            
        } catch (SQLException e) {e.printStackTrace();
        
            // Redirect to show failure message in url
            response.sendRedirect("/MealQuest/registration-result?status=failure");
        }
    }
    
    
    
    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
    		throws ServletException, IOException {
    	// display registration form
    	resp.setContentType("text/html");
    	resp.getWriter().println("<html><body>");
    	resp.getWriter().println("<h1>Please Enter Your Information Below to "
    			+ "Register for MealQuest</h1>");
    	resp.getWriter().println("<form method='post' action='/MealQuest/register'>");
    	resp.getWriter().println("Email: <input type='text' name='email'><br>");
    	resp.getWriter().println("Display Name: <input type='text' "
    			+ "name='displayName'><br>");
    	resp.getWriter().println("First Name: <input type='text' name='firstName'><br>");
    	resp.getWriter().println("Last Name: <input type='text' name='lastName'><br>");
    	resp.getWriter().println("Password: <input type='password' name='password'><br>");
    	resp.getWriter().println("<input type='submit' value='Register'>");
    	resp.getWriter().println("</form>");
    	resp.getWriter().println("</body></html>");
    }
}
