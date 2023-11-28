import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RandomMealGenConcreteStrategy implements MealGenStrategy {

	@Override
	public List<String> genFoods(Connection connection) throws SQLException {
		List<String> foodList = new ArrayList<>();
		
		// using the original logic from MealGen class
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
			String flavorCategory = flavorCategoryResultSet.getString("flavorCategory");
			
			// get 2 foods for each flavorCategory
						String foodQuery = ""
								+ "SELECT idFoods, food, flavorCategory "
								+ "FROM foods "
								+ "WHERE flavorCategory = ? "
								+ "ORDER BY RAND() "
								+ "LIMIT 1";
			PreparedStatement preparedStatement = connection.prepareStatement(foodQuery);
			// set value of '?' below:
			preparedStatement.setString(1, flavorCategory);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				String food = resultSet.getString("food");
				// add the food items to the List
				foodList.add(food);
			}
			
			i++;
		}
		
		return foodList;

	}

	@Override
	public List<String> genMethods(Connection connection) throws SQLException {
		List<String> methodList = new ArrayList<>();
		
		// using original logic
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
		
		return methodList;
	}

}
