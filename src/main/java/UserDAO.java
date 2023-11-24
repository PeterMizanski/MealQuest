import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	// URL, db username & db password
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;

    
    
    // init db connection
    public UserDAO() {
    	// get db connection info from DatabaseUtil class
        jdbcURL = DatabaseUtil.getJdbcURL();
        jdbcUsername = DatabaseUtil.getJdbcUsername();
        jdbcPassword = DatabaseUtil.getJdbcPassword();
    }
    
    
    
    // insert user into db
    private static final String INSERT_USER_SQL = ""
    		+ "INSERT INTO user "
    		+ "(emailU, passwordU) "
    		+ "VALUES (?, ?)";
    
    // select user by email and password for user authentication
    private static final String SELECT_USER_BY_EMAIL_PASSWORD_SQL = ""
    		+ "SELECT * "
    		+ "FROM user "
    		+ "WHERE emailU = ? AND passwordU = ?";

    
    
    
    
    public boolean registerUser(User user) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, 
        		jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement
            		 (INSERT_USER_SQL)) {
        	// set user data in SQL
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());

            // check if rows were inserted
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
            
        } catch (SQLException e) {e.printStackTrace();}
        	return false;
    }

    
    
    
    public User authenticateUser(String email, String password) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, 
        		jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement
            		 (SELECT_USER_BY_EMAIL_PASSWORD_SQL)) {
        	// set user data in SQL, same as above
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	// if user exists, create User object & set properties
                User user = new User();
                user.setId(resultSet.getInt("idUser"));
                user.setEmail(resultSet.getString("emailU"));
                user.setPassword(resultSet.getString("passwordU"));
                user.setFirstName(resultSet.getString("firstNameU"));
                user.setLastName(resultSet.getString("lastNameU"));
                return user;
            }
        } catch (SQLException e) {e.printStackTrace();}
        return null;
    }
}
