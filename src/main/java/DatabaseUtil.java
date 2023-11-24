import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
	// db connection params
	private static String jdbcURL;
	private static String jdbcUsername;
	private static String jdbcPassword;
	// the following 'properties' object stores the info from the config file
    private static Properties properties = new Properties();

    static {
    	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	// YOU NEED TO SPECIFY THE CORRECT FILE PATH FOR YOUR DEVICE HERE
        String propertiesFilePath = "C:\\Users\\pmiza\\"
        		+ "Documents\\% - Semester 3\\"
        		+ "CST8288 - OOP Dsgn\\Capstone II\\"
        		+ "database.properties mine\\database.properties";

        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            // assign the info from properties file to properties object
        	properties.load(input);
            
            jdbcURL = properties.getProperty("jdbc.url");
            jdbcUsername = properties.getProperty("jdbc.username");
            jdbcPassword = properties.getProperty("jdbc.password");
            
            // load jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    
    // get the database connection:
    public static Connection getConnection() throws SQLException {
        //OLD String jdbcUrl = properties.getProperty("jdbc.url");
        //OLD String jdbcUsername = properties.getProperty("jdbc.username");
        //OLD String jdbcPassword = properties.getProperty("jdbc.password");
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

	public static String getJdbcURL() {return jdbcURL;}
	public static String getJdbcUsername() {return jdbcUsername;}
	public static String getJdbcPassword() {return jdbcPassword;}
	
}
