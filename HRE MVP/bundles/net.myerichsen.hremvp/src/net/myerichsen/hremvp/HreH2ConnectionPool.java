package net.myerichsen.hremvp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.h2.jdbcx.JdbcConnectionPool;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

/**
 * Singleton class that instantiates a JDBC Connection Pool and returns a
 * connection to it
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 7. mar. 2019
 */
public class HreH2ConnectionPool {
	private static IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static JdbcConnectionPool connectionPool = null;
	private static int h2TraceLevel = 1;
	private static String dbPath = null;
	private static final String GETH2VERSION = "SELECT H2VERSION() FROM DUAL";

	/**
	 * Exists only to defeat instantiation
	 */
	protected HreH2ConnectionPool() {
	}

	/**
	 * Dispose connection pool and recreate to create a new data base.
	 *
	 * @param dbName Name of the database
	 * @throws BackingStoreException Error in preferences file access
	 */
	public static void createNew(String dbName) throws BackingStoreException {
		try {
			connectionPool.dispose();
		} catch (final Exception e) {
			LOGGER.log(Level.INFO, "No connection pool to dispose");
		}
		dbPath = store.getString("DBPATH");
		h2TraceLevel = store.getInt("H2TRACELEVEL");
		final String jdbcUrl = "jdbc:h2:" + dbPath + "/" + dbName
				+ ";TRACE_LEVEL_FILE=" + h2TraceLevel
				+ ";TRACE_LEVEL_SYSTEM_OUT=" + h2TraceLevel;
		LOGGER.log(Level.INFO, "JDBC URL: {0}", jdbcUrl);
		connectionPool = JdbcConnectionPool.create(jdbcUrl, "sa", "");
		LOGGER.log(Level.INFO, "Connection pool has been created");
		connectionPool.setMaxConnections(500);
		store.setValue("DBNAME", dbName);
		LOGGER.log(Level.INFO, "Preferences dbname set to {0}", dbName);
	}

	/**
	 * Dispose the connection pool.
	 *
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	public static void dispose() throws Exception {
		final Connection conn = HreH2ConnectionPool.getConnection();
		final PreparedStatement ps = conn.prepareStatement("SHUTDOWN");
		ps.executeUpdate();

		if (connectionPool != null) {
			connectionPool.dispose();
		}
		connectionPool = null;
	}

	/**
	 * @return A JDBC connection
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	public static Connection getConnection() throws SQLException {
		final String dbName = store.getString("DBNAME");
		h2TraceLevel = store.getInt("H2TRACELEVEL");
		dbPath = store.getString("DBPATH");

		if (connectionPool == null) {
			h2TraceLevel = store.getInt("H2TRACELEVEL");
			final String jdbcUrl = "jdbc:h2:" + dbPath + "/" + dbName
					+ ";IFEXISTS=TRUE;TRACE_LEVEL_FILE=" + h2TraceLevel
					+ ";TRACE_LEVEL_SYSTEM_OUT=" + h2TraceLevel;
			LOGGER.log(Level.INFO, "JDBC URL: {0}", jdbcUrl);
			connectionPool = JdbcConnectionPool.create(jdbcUrl,
					store.getString("USERID"), store.getString("PASSWORD"));
			connectionPool.setMaxConnections(500);
			String h2Version = store.getString("H2VERSION");

			try {
				final Connection conn = connectionPool.getConnection();
				final PreparedStatement ps = conn
						.prepareStatement(GETH2VERSION);
				final ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					h2Version = rs.getString(1);
					store.setValue("H2VERSION", h2Version);

					LOGGER.log(Level.INFO, "H2 Version is {0}", h2Version);
				}
			} catch (final Exception e) {
				LOGGER.log(Level.INFO, e.getMessage());
				store.setValue("H2VERSION", h2Version);

				LOGGER.log(Level.INFO, "H2 Version defaults to {0}", h2Version);
			}
		}

		LOGGER.log(Level.FINE,
				"Reusing connection pool, Max: "
						+ connectionPool.getMaxConnections() + ", Active: "
						+ connectionPool.getActiveConnections());

		return connectionPool.getConnection();
	}

	/**
	 * Get a JDBCconnection
	 *
	 * @param dbName Name of database
	 * @return A JDBC Connection
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	public static Connection getConnection(String dbName) throws Exception {
		h2TraceLevel = store.getInt("H2TRACELEVEL");
		dbPath = store.getString("DBPATH");
		final String jdbcUrl = "jdbc:h2:" + dbPath + "/" + dbName
				+ ";IFEXISTS=TRUE;TRACE_LEVEL_FILE=" + h2TraceLevel
				+ ";TRACE_LEVEL_SYSTEM_OUT=" + h2TraceLevel;
		LOGGER.log(Level.INFO, "JDBC URL: " + jdbcUrl);
		connectionPool = JdbcConnectionPool.create(jdbcUrl, "sa", "");
		connectionPool.setMaxConnections(500);
		LOGGER.log(Level.INFO, "Connection pool has been created");

		return connectionPool.getConnection();
	}
}