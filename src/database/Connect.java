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
	
	public static Connection getConnection(){
		if (con == null){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName, dbUser, dbPw);
				System.out.println(con.isClosed());
			} catch (SQLException e) {
				return null;
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		return con;
	}
	public static void releaseConnection(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
