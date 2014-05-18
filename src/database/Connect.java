 package database;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

class Connect { // TODO
	private static Connection con;
	static String dbUser;
	static String dbPw;
	static String dbPort;
	static String dbHost;
	static String dbName;
	
	private Connect(){}
	
	static void load_data(){
		Properties prop = new Properties();
		InputStream input = null;
		try{
			input = new FileInputStream("WebContent/config/db.config");
			prop.load(input);
			dbUser = prop.getProperty("dbUser");
			dbPw = prop.getProperty("dbPw");
			dbPort = prop.getProperty("dbPort");
			dbHost = prop.getProperty("dbHost");
			dbName = prop.getProperty("dbName");
			
		}catch(IOException e){
			System.err.println("Connect.java konnte die Datenbankkonfigurationsdatei nicht finden / lesen");
		}finally{
			if(input != null){
				try{
					input.close();
				}catch(IOException e){
					System.err.println("Fehler beim Schließen der Datei. Bitte kontrollieren Sie die Datei und fahren fort.");
				}
			}
		}
	}
	
	/**
	 * Stellt eine Verbindung zur Datenbank her.
	 * @return  Verbindungsobjekt der Datenbank
	 * @exception SQLException Wird geworfen bei Fehlern mit der Verbindung.
	 */
	public static Connection getConnection() throws SQLException{
		load_data();
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
	/**
	 * Schließt die Verbindung zur Datenbank
	 * @throws SQLException
	 */
	public static void releaseConnection() throws SQLException{
		con.close();
	}
}
