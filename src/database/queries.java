package database;

import java.sql.*;

public class queries {
	private queries(){}
	
	public static Object scalarQuery2(String column, String table, String where, String param, String condition) throws SQLException{
		PreparedStatement query;
		if(where == ""){
			query = connect.getConnection().prepareStatement("SELECT "+column+" FROM "+table+" WHERE "+param+" LIKE ?");
			
		}else{
			query = connect.getConnection().prepareStatement("SELECT "+column+" FROM "+table+" WHERE "+where +" AND "+param+" LIKE ?");
		}
		ResultSet rs = query.executeQuery();
		return rs.getObject(0);
	}
	
	public static Object  scalarQuery(String sql) throws SQLException{
		Statement query = connect.getConnection().createStatement();
		ResultSet rs = query.executeQuery(sql);
		return rs.getObject(0);
	}
	
	public static ResultSet rowQuery(String sql) throws SQLException{
		Statement query = connect.getConnection().createStatement();
		ResultSet rs = query.executeQuery(sql);
		return rs;		
	}
	
	public static boolean updateQuery(String sql){
		try{
			Statement query = connect.getConnection().createStatement();
			query.executeUpdate(sql);
			return (query.getUpdateCount()>0);
		}catch (SQLException e){
			return false;
		}	
	}

	public static boolean deleteQuery(String sql) throws SQLException{
		try{
			Statement query = connect.getConnection().createStatement();
			query.executeUpdate(sql);
			return (query.getUpdateCount()>0);
		}catch (SQLException e){
			return false;
		}		
	}
	
	public static boolean insertQuery(String sql) throws SQLException{
		try{
			Statement query = connect.getConnection().createStatement();
			query.executeUpdate(sql);
			return (query.getUpdateCount()>0);
		}catch (SQLException e){
			return false;
		}	
	}
	
	

}
