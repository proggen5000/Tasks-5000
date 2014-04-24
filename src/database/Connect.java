 package database;
import java.sql.*;

class Connect {
	private static Connection con;
	
	private Connect(){}
	
	public static Connection getConnection() throws SQLException{
		if (con == null){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (Exception e) {
				return null;
			} 
			con = DriverManager.getConnection("jdbc:mysql://"+ConnectionData.dbHost+":"+ConnectionData.dbPort+"/"+ConnectionData.dbName, ConnectionData.dbUser, ConnectionData.dbPw);
		}
		return con;
	}
	public static void releaseConnection() throws SQLException{
		con.close();
	}
}
