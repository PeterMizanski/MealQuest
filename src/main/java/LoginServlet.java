import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
    	/*
        // OLD CODE... this was replaced with the use of DatabaseUtil & 
       					database.properties file
        // init UserDAO with jdbc
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {e.printStackTrace();}
        String jdbcURL = "jdbc:mysql://localhost:3306/mydb";
        String jdbcUsername = "root";
        String jdbcPassword = "";
        userDAO = new UserDAO(jdbcURL, jdbcUsername, jdbcPassword);
         * 
         */
    	
    	
    	userDAO = new UserDAO();
    }

    
    
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // request for user authentication
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // user is authenticated with email and password
        User authenticatedUser = userDAO.authenticateUser(email, password);

        if (authenticatedUser != null) {
            HttpSession session = request.getSession();
            
            // get user's full name to be used in the welcome message
            String firstNameU = authenticatedUser.getFirstName();
            String lastNameU = authenticatedUser.getLastName();
            String fullName = firstNameU + " " + lastNameU;
            
            // session stores user info
            session.setAttribute("user", authenticatedUser);
            session.setAttribute("userName", fullName);

            // if authenticated, redirect to the generator
            response.sendRedirect(request.getContextPath() + "/meal_gen");
        } else {
        	// if failed, display login error in url
            response.sendRedirect(request.getContextPath() + "/login?error=true");
        }
    }

    
    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    	// show the login form
        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Welcome to MealQuest!</h1>");
        response.getWriter().println("<h3>Please Enter Your Login Information Below</h3>");
        response.getWriter().println("<form method='post' action=''>");
        response.getWriter().println("Email: <input type='text' name='email'><br>");
        response.getWriter().println("Password: <input type='password' name='password'><br>");
        response.getWriter().println("<input type='submit' value='Login'>");
        response.getWriter().println("</form>");
        response.getWriter().println("</body></html>");
    }
}
