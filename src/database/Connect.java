package database;
import com.mysql.jdbc.*;

class Connect {
	private static Connection con;
	private static String dbUser ="adminbsTSA3H";
	private static String dbPw = "6_76lpJkIE8V";
	private static String dbPort = "3306";
	private static String dbHost="localhost";
	private static String dbName="tasks";
	
	private Connect(){}
	
	public static Connection getConnection(){
		if (con == null){
			try {
				con = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName, dbUser, dbPw);
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
