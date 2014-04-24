 package database;
import java.sql.*;

class Connect {
	private static Connection con;
	
	private Connect(){}
	
	/**
	 * Stellt eine Verbindung zur Datenbank her.
	 * @return  Verbindungsobjekt der Datenbank
	 * @exception SQLException Wird geworfen bei Fehlern mit der Verbindung.
	 */
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
	/**
	 * Schlieﬂt die Verbindung zur Datenbank
	 * @throws SQLException
	 */
	public static void releaseConnection() throws SQLException{
		con.close();
	}
}
