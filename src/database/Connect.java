package database;
import java.sql.*;

class Connect {
	private static Connection con;
	private static String dbUser ="%";
	private static String dbPw = "%";
	private static String dbPort = "3306";
	private static String dbHost="localhost";
	private static String dbName="tasks";
	
	private Connect(){}
	
	public static Connection getConnection() throws SQLException{
		if (con == null){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (Exception e) {
				return null;
			} 
			con = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName, dbUser, dbPw);
		}
		return con;
	}
	public static void releaseConnection() throws SQLException{
		con.close();
	}
}
