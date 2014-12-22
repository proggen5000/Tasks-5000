package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {
	private Queries() {
	}

	/**
	 * Führt eine SQL-Abfrage mit eindeutigem Rückgabewert aus.
	 * 
	 * @param table
	 *            Tabelle, in der gesucht werden soll.
	 * @param column
	 *            Spalte(n), in der gesucht werden soll.
	 * @param where
	 *            Bedingung, mit der gefiltert werden soll.
	 * @return Object Eindeutiges Ergebnis der Datenbankabfrage
	 * @throws SQLException
	 *             Wird bei Fehlern bei Ausführung der Anweisung geworfen.
	 */
	public static Object scalarQuery(String table, String column, String where)
			throws SQLException {
		PreparedStatement query;
		query = Connect.getConnection().prepareStatement(
				"SELECT " + column + " FROM " + table + " WHERE " + where);
		ResultSet rs = query.executeQuery();
		rs.next();
		return rs.getObject(1);
	}

	/**
	 * Führt eine SQL-Abfrage mit eindeutigem Rückgabewert aus.
	 * 
	 * @param sql
	 *            SELECT-Abfrage mit eindeutigem Ergebnis
	 * @return Object Eindeutiges Ergebnis der Datenbankabfrage.
	 * @throws SQLException
	 *             Wird bei Fehlern bei Ausführung der SQL-Anweisung geworfen.
	 */
	public static Object scalarQuery(String sql) throws SQLException {
		Statement query = Connect.getConnection().createStatement();
		ResultSet rs = query.executeQuery(sql);
		return rs.getObject(0);
	}

	/**
	 * Führt eine SQL-Abfrage mit Zeilen als Rückgabewert aus.
	 * 
	 * @param table
	 *            Tabelle, in der gesucht werden soll.
	 * @param column
	 *            Spalte(n), in der gesucht werden soll.
	 * @param where
	 *            Bedingung, mit der gefiltert werden soll.
	 * @return ResultSet Repräsentiert das Abfrageergebnis der SQL-Abfrage
	 * @throws SQLException
	 *             Wird bei Fehlern bei Ausführung der SQL-Anweisung geworfen.
	 */
	public static ResultSet rowQuery(String column, String table, String where)
			throws SQLException {
		PreparedStatement query;
		query = Connect.getConnection().prepareStatement(
				"SELECT " + column + " FROM " + table + " WHERE " + where);
		ResultSet rs = query.executeQuery();
		return rs;
	}

	/**
	 * Führt eine SQL-Abfrage mit Zeilen als Rückgabewert aus.
	 * 
	 * @param sql
	 *            SQL-Abfrage mit Zeilen als Rückgabewert.
	 * @return ResultSet Repräsentiert das Abfrageergebnis der SQL-Abfrage
	 * @throws SQLException
	 *             Wird bei Fehlern bei Ausführung der SQL-Anweisung geworfen.
	 */
	public static ResultSet rowQuery(String sql) throws SQLException {
		Statement query = Connect.getConnection().createStatement();
		ResultSet rs = query.executeQuery(sql);
		return rs;
	}

	/**
	 * Führt ein Update-Befehl aus.
	 * 
	 * @param table
	 *            Tabelle, die aktualisiert werdensoll.
	 * @param updateString
	 *            Gibt Atrribute und Parameter an, die aktualisiert werden.
	 * @param where
	 *            Gibt eine Bedingung an, welche Datensätze aktualisiert werden.
	 * @return boolean Gibt an, ob das Update erfolgreich ausgeführt wurde.
	 * @throws SQLException
	 *             Wird bei Fehlern bei Ausführung der SQL-Anweisung geworfen.
	 */
	public static boolean updateQuery(String table, String updateString,
			String where) throws SQLException {
		Statement query = Connect.getConnection().createStatement();
		if (query.executeUpdate("UPDATE " + table + " SET " + updateString
				+ " WHERE " + where) == 1) {
			// Statement erfolgreich ausgeführt
			try {
				// Es wurde automatisch ein Schlüssel genereiert
				ResultSet generatedKeys = query.getGeneratedKeys();
				generatedKeys.next();
				return generatedKeys.getInt(1) > 0;
			} catch (SQLException e) {
				// Es wurde kein Schlüssel generiert
				return true;
			}
		}
		return false;
	}

	/**
	 * Führt ein Update-Befehl aus.
	 * 
	 * @param sql
	 *            Vollwertige SQL-Update-Anweisung
	 * @return boolean Gibt an, ob das Update erfolgreich ausgeführt wurde.
	 * @throws SQLException
	 *             Wird bei Fehlern bei Ausführung der SQL-Anweisung geworfen.
	 */
	public static boolean updateQuery(String sql) throws SQLException {
		Statement query = Connect.getConnection().createStatement();
		if (query.executeUpdate(sql) == 1) {
			// Statement erfolgreich ausgeführt
			try {
				// Es wurde automatisch ein Schlüssel genereiert
				ResultSet generatedKeys = query.getGeneratedKeys();
				generatedKeys.next();
				return generatedKeys.getInt(1) > 0;
			} catch (SQLException e) {
				// Es wurde kein Schlüssel generiert
				return true;
			}
		}
		return false;
	}

	/**
	 * Führt einen DELETE-Befehl aus.
	 * 
	 * @param table
	 *            Tabelle, in der der Datensatz gelöscht werden soll.
	 * @param where
	 *            Bedingung, mit der die zu löschenden Datensätze ausgewählt
	 *            werden sollen.
	 * @return boolean Gibt an, ob der Befehl erfolgreich ausgeführt wurde.
	 * @throws SQLException
	 *             Wird bei Fehlern bei Ausführung der SQL-Anweisung geworfen.
	 */
	public static boolean deleteQuery(String table, String where)
			throws SQLException {
		Statement query = Connect.getConnection().createStatement();
		query.executeUpdate("DELETE FROM " + table + " WHERE " + where);
		return true;
	}

	/**
	 * Führt einen DELETE-Befehl aus.
	 * 
	 * @param sql
	 *            Vollwertige SQL-Delete-Anweisung
	 * @return boolean Gibt an, ob der Befehl erfolgreich ausgeführt wurde.
	 * @throws SQLException
	 */
	public static boolean deleteQuery(String sql) throws SQLException {
		Statement query = Connect.getConnection().createStatement();
		query.executeUpdate(sql);
		return (query.getUpdateCount() > 0);
	}

	/**
	 * Führt einen INSERT-Befehl aus.
	 * 
	 * @param table
	 *            Tabelle, in der ein Datensatz eingefügt werden soll.
	 * @param columns
	 *            Spalten, in die Daten eingetragen werden sollen.
	 * @param values
	 *            Werte, die in die Spalten eingetragen werden sollen.
	 * @return int Der Primärschlüssel des eingefügten Datensatzes.
	 * @throws SQLException
	 *             Wird bei Fehlern bei Ausführung der SQL-Anweisung geworfen.
	 */
	public static int insertQuery(String table, String columns, String values)
			throws SQLException {
		Statement query = Connect.getConnection().createStatement();
		if (query.executeUpdate("INSERT INTO " + table + " (" + columns
				+ ") VALUES (" + values + ")", Statement.RETURN_GENERATED_KEYS) == 1) {
			// Statement erfolgreich ausgeführt
			try {
				// Es wurde automatisch ein Schlüssel genereiert
				ResultSet generatedKeys = query.getGeneratedKeys();
				generatedKeys.next();
				return generatedKeys.getInt(1);
			} catch (SQLException e) {
				// Es wurde kein Schlüssel generiert
				return 0;
			}
		}
		return -1;
	}

	/**
	 * Führt einen INSERT-Befehl aus.
	 * 
	 * @param sql
	 *            Vollwertige SQL-Insert-Anweisung.
	 * @return int Der Primärschlüssel des eingefügten Datensatzes.
	 * @throws SQLException
	 *             Wird bei Fehlern bei Ausführung der SQL-Anweisung geworfen.
	 */
	public static int insertQuery(String sql) throws SQLException {
		Statement query = Connect.getConnection().createStatement();
		if (query.executeUpdate(sql) == 1) {
			// Statement erfolgreich ausgeführt
			try {
				// Es wurde automatisch ein Schlüssel genereiert
				ResultSet generatedKeys = query.getGeneratedKeys();
				generatedKeys.next();
				return generatedKeys.getInt(1);
			} catch (SQLException e) {
				// Es wurde kein Schlüssel generiert
				return 0;
			}
		}
		return -1;
	}
}
