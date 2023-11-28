import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MealGenStrategy {
	List<String> genFoods(Connection connection) throws SQLException;
	List<String> genMethods(Connection connection) throws SQLException;
}
