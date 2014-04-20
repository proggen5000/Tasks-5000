package database;

import java.sql.*;

public class Queries {
	private Queries(){}
	
	public static Object scalarQuery(String table, String column, String where) throws SQLException{
			PreparedStatement query;
			query = Connect.getConnection().prepareStatement("SELECT "+column+" FROM "+table+" WHERE "+where);
			ResultSet rs = query.executeQuery();
			return rs.getObject(0);	}
	
	public static Object  scalarQuery(String sql) throws SQLException{
			Statement query = Connect.getConnection().createStatement();
			ResultSet rs = query.executeQuery(sql);
			return rs.getObject(0);
	}
	
	public static ResultSet rowQuery(String column, String table, String where) throws SQLException{
		PreparedStatement query;
		query = Connect.getConnection().prepareStatement("SELECT "+column+" FROM "+table+" WHERE "+where);
		ResultSet rs = query.executeQuery();
		return rs;
	}
	
	public static ResultSet rowQuery(String sql) throws SQLException{
		Statement query = Connect.getConnection().createStatement();
		ResultSet rs = query.executeQuery(sql);
		return rs;	
	}
	
	public static boolean updateQuery(String table, String updateString, String where) throws SQLException{
		PreparedStatement query = Connect.getConnection().prepareStatement("UPDATE "+table+" SET "+updateString+" WHERE "+where);
		query.executeQuery();
		return(query.getUpdateCount()>0);
	}
	
	public static boolean updateQuery(String sql) throws SQLException{
		Statement query = Connect.getConnection().createStatement();
		query.executeUpdate(sql);
		return (query.getUpdateCount()>0);
	}

	public static boolean deleteQuery(String table, String where) throws SQLException{
		PreparedStatement query = Connect.getConnection().prepareStatement("DELETE FROM "+table+" WHERE "+where);
		query.executeQuery();
		return true;
	}
	 
	public static boolean deleteQuery(String sql) throws SQLException{
		Statement query = Connect.getConnection().createStatement();
		query.executeUpdate(sql);
		return (query.getUpdateCount()>0);
	}
	
	public static int insertQuery(String table, String columns, String values) throws SQLException{
		PreparedStatement query = Connect.getConnection().prepareStatement("INSERT INTO "+table+" ("+columns+") VALUES ("+values+")");
		query.executeQuery();
		ResultSet generatedKeys = query.getGeneratedKeys();
		if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("Creating row failed, no generated key obtained.");
        }
	}
	
	public static int insertQuery(String sql) throws SQLException{
		Statement query = Connect.getConnection().createStatement();
		query.executeUpdate(sql);
		ResultSet generatedKeys = query.getGeneratedKeys();
		if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("Creating row failed, no generated key obtained.");
        }
	}
}
