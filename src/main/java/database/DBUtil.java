package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {
	
	private static Connection connection = null;
	
	//This function is used to set connection between the sql database and java server.
	public static Connection getConnection() {
		
        if (connection != null)
            return connection;
        else {
            try {
                String driver = "com.mysql.cj.jdbc.Driver";//prop.getProperty("driver");
                String url = "jdbc:mysql://localhost:3306/project1";//prop.getProperty("url");
                String user ="root";// prop.getProperty("user");
                String password = "Vatsal@2308";//prop.getProperty("password");

                Class.forName(driver);

                connection = DriverManager.getConnection(url, user, password);
                
                
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } 

            return connection;
        }

    }
}