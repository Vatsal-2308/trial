package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {
	
	private static Connection connection = null;
	
	//This function is used to set connection between the sql database and java server.
	public static Connection getConnection() throws SQLException{
		
        if (connection != null)
            return connection;
        else {

                String driver = "com.postgresql.cj.jdbc.Driver";//prop.getProperty("driver");
                String url = "jdbc:postgresql://ec2-54-156-110-139.compute-1.amazonaws.com:5432/d9og30j9emfg1v";//prop.getProperty("url");
                String user ="wrkhsvjgqpmilx";// prop.getProperty("user");
                String password = "6057826637dbb5bcb9a9b8a093465e385815f84d24d48e04d81dfc9ca9c472ae";//prop.getProperty("password");

                //Class.forName(driver);

                Connection connection = DriverManager.getConnection(url,user,password);
                if(connection!=null)
                {
                    System.out.println("Connection DOne");
                }
                

            }

            return connection;
        }

    }
