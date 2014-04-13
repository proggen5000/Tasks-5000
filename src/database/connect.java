package database;
import java.sql.*;

class connect {
	private static Connection con;
	
	private connect(){}
	
	public static Connection getConnection(){
		if (con == null){
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost/tasks", "adminbsTSA3H", "adminbsTSA3H");
			} catch (SQLException e) {
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
