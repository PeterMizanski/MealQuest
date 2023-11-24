import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MealGen extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	List<String> foodList = new ArrayList<>();
	List<String> methodList = new ArrayList<>();
	String[] flavorCategories = new String[2];
	
	
	
	/*
	 * THIS IS OLD CODE, IGNORE
	public void init() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {e.printStackTrace();}
	}
	 * 
	 */
	
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws 
	ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		// session to get user's name
		HttpSession session = req.getSession();
		String userName = (String) session.getAttribute("userName");
		
		
		// need to clear the lists, otherwise 
		// when the user clicks the "generate" button,
		// new items are continuously added rather than
		// limiting the generation to 2 foods & methods
		// as intended
		foodList.clear();
		methodList.clear();
		
		
		/*
		 * OLD CODE, IGNORE, replaced with DatabaseUtil
		database connection parameters
		String url = "jdbc:mysql://localhost:3306/mydb";
		String username = "root";
		String password = "!";
		Connection connection = null;
		 * 
		 */
		
		
		
		// load database connection from the DatabaseUtil class
		try (Connection connection = DatabaseUtil.getConnection()){
			
			/*
			 * OLD CODE, IGNORE
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {e.printStackTrace();}
			// create database connection
			connection = DriverManager.getConnection(url, username, password);
			 * 
			 */
			
			
			
			
			
			// get 2 random, but different, flavorCategories
			String flavorCategoryQuery = ""
					+ "SELECT DISTINCT flavorCategory "
					+ "FROM foods "
					+ "ORDER BY RAND() LIMIT 2";
			
			// use a PreparedStatement to do SQL queries
			PreparedStatement flavorCategoryStatement = 
					connection.prepareStatement(flavorCategoryQuery);
			ResultSet flavorCategoryResultSet = flavorCategoryStatement.executeQuery();
			
			int i = 0;
			// while-loop limits results to 2 rows
			while (flavorCategoryResultSet.next() && i < 2) {
				// add the distinct flavor categories to the array
				flavorCategories[i] = flavorCategoryResultSet.getString("flavorCategory");
				i++;
			}
			
			
			
			
			
			
			// get 2 foods for each flavorCategory
			String foodQuery = ""
					+ "SELECT idFoods, food, flavorCategory "
					+ "FROM foods "
					+ "WHERE flavorCategory = ? "
					+ "ORDER BY RAND() "
					+ "LIMIT 1";
			
			// a for-each loop iterates thru the flavorCatergories array
			for (String flavorCategory : flavorCategories) {
				PreparedStatement preparedStatement = connection.prepareStatement(foodQuery);
				// set value of '?' below:
				preparedStatement.setString(1, flavorCategory);
				ResultSet resultSet = preparedStatement.executeQuery();
				
				while (resultSet.next()) {
					String food = resultSet.getString("food");
					// add the food items to the List
					foodList.add(food);
				}
			}
			
			
			
			/*
			 * THIS WAS THE OG QUERY, BUT I COULDN'T GET IT TO WORK
			 * IT WAS STILL GENERATING FOODS FROM THE SAME FLAVORCATEGORY
			 * 
			// Query to get 2 random ingredients/foods
			// this assumes a MySQL DB with table "ingredients" and columns "id" "name"
			String sql = "SELECT f1.idFoods, f1.food, f1.flavorCategory "
					+ "FROM foods AS f1 "
					+ "JOIN ("
					+ "SELECT DISTINCT flavorCategory "
					+ "FROM foods "
					+ "ORDER BY RAND() LIMIT 2) "
					+ "AS f2 ON f1.flavorCategory = f2.flavorCategory "
					+ "ORDER BY f1.flavorCategory, RAND() LIMIT 2";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			*/
			
			resp.setContentType("text/html");
			
			out.println("<html><head><title>Meal Quest</title></head>");
			out.println("<body>");
			// Custom welcome message:
			out.println("<h1>Welcome to MealQuest, " + userName + "!</h1>");
			
			// "generate" button
			out.println("<form method='GET'>");
			out.println("<button type='submit' id=generateButton'>"
					+ "Generate Meal</button>");
			out.println("</form>");
			
			out.println("<h4>Your Ingredients</h4>");
			out.println("<ul>");
			
			// below was part of OG query
			//while (resultSet.next()) {
				//String food = resultSet.getString("food");
				//String flavorCategory = resultSet.getString("flavorCategory");
				//foodList.add(food);
			//}
			
			if (!foodList.isEmpty()) {
				out.println("<li>How about a meal that uses: " +
						String.join(" and ", foodList) + 
						"</li>");
			}
			
			
			
			
			
			// get random cooking methods
			String methodSql = ""
					+ "SELECT method "
					+ "FROM cook_methods "
					+ "ORDER BY RAND() LIMIT 2";
			PreparedStatement methodStatement = connection.prepareStatement(methodSql);
			ResultSet methodResultSet = methodStatement.executeQuery();
		
			while (methodResultSet.next()) {
				String method = methodResultSet.getString("method");
				methodList.add(method);
			}
			
			if (!methodList.isEmpty()) {
				out.println("<li>Prepare by: " + 
			String.join(" and ", methodList) + "</li>");
			}
			
			out.println("</ul>");
			out.println("</body></html>");	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		/*
		 * OLD CODE, IGNORE
		//finally {
			//try {
				//if (connection != null) {
					//connection.close();
				//}
			//} catch (SQLException e) {
				//e.printStackTrace();
			//}
		//}
		 * 
		 */
	}

	
	
	
	// end of MealGen class
}