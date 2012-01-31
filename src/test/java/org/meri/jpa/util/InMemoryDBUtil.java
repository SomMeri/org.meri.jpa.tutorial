package org.meri.jpa.util;

import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;

import javax.sql.DataSource;

import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.DerbyConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;

import org.apache.derby.jdbc.EmbeddedDataSource;

public class InMemoryDBUtil {

	private static final String DATABASE_NOT_FOUND = "XJ004";
	public static final String DEFAULT_DATASOURCE_JNDI_NAME = "jdbc/jpaDemoDB";
	public static final String DEFAULT_DATABASE_NAME = "jpaDemoDB";
	private static final String DB_INITIALIZATION_ERROR = "Could not initialize database. Original exception: ";
	
	private final JNDIUtil jndi = new JNDIUtil();

	private String dataSourceJndiName = DEFAULT_DATASOURCE_JNDI_NAME;
	private String databaseName = DEFAULT_DATABASE_NAME;

	public InMemoryDBUtil() {
	}

	/**
	 * The method does three thing:
	 * * creates an embedded in-memory database if it does not exists yet,
	 * * stores data source to the JNDI,  
	 * * applies change log to the database.
	 * 
	 * The method assumes that JNDI is properly installed. If it is not, 
	 * the exception is thrown.
	 *  
	 * @param changelogPath a path to change log.
	 * @throws ConfigurationException 
	 */
	public void initializeDatabase(String changelogPath) {
		DataSource dataSource = getDataSource();
		createDatabaseStructure(changelogPath, dataSource);
	}

	/**
	 * Drops the database and removes data source from the JNDI.
	 * 
	 * The method assumes that JNDI is properly installed. If it is not, 
	 * the exception is thrown.
	 *  
	 * @throws ConfigurationException 
	 */
	public void removeDatabase() {
		dropDatabase();
		jndi.cleanJNDI(DEFAULT_DATASOURCE_JNDI_NAME);
	}

	/**
	 * Convenience method, it does two things:
	 * * drops database using {@link #removeDatabase()} method,
	 * * initializes an embedded in-memory database using {@link #initializeDatabase(String changelogPath)} method
	 * 
	 * The method assumes that JNDI is properly installed. If it is not, 
	 * the exception is thrown.
	 *  
	 * @param changelogPath a path to change log.
	 * @throws ConfigurationException 
	 */
	public void resetDatabase(String changelogPath) {
		removeDatabase();
		initializeDatabase(changelogPath);
	}

	/**
	 * Returns data source stored in JNDI. If the JNDI does not 
	 * contain the data source, the data source is created and stored.
	 * 
	 * The method assumes that JNDI is properly installed. If it is not, 
	 * the exception is thrown.
	 * 
	 * @return active data source
	 */
	public DataSource getDataSource() {
		DataSource dataSource = (DataSource) jndi.lookup(DEFAULT_DATASOURCE_JNDI_NAME);
		if (dataSource==null) {
			dataSource = createDataSource();
			jndi.bindToJNDI(DEFAULT_DATASOURCE_JNDI_NAME, dataSource);
		}
		return dataSource;
	}

	protected void createDatabaseStructure(String changelogPath, DataSource dataSource) {
		try {
			//create new liquibase instance
			Connection sqlConnection = dataSource.getConnection();
			DatabaseConnection db = new DerbyConnection(sqlConnection);
			Liquibase liquibase = new Liquibase(changelogPath, new FileSystemResourceAccessor(), db);

			//update the database
            liquibase.update("test", new OutputStreamWriter(System.out));
            liquibase.update("test");
		} catch (SQLException e) {
			// We can not solve the problem. We will let it go up without
			// having to declare the exception every time.
			throw new ConfigurationException(DB_INITIALIZATION_ERROR, e);
		} catch (LiquibaseException e) {
			// We can not solve the problem. We will let it go up without
			// having to declare the exception every time.
			throw new ConfigurationException(DB_INITIALIZATION_ERROR, e);
		}
	}

	private EmbeddedDataSource createDataSource() {
		EmbeddedDataSource dataSource = new EmbeddedDataSource();
		dataSource.setDataSourceName(dataSourceJndiName);
		dataSource.setDatabaseName("memory:" + databaseName);
		dataSource.setCreateDatabase("create");

		return dataSource;
	}

	private void dropDatabase() {
		EmbeddedDataSource dataSource = createDataSource();
		dataSource.setCreateDatabase(null);
		dataSource.setConnectionAttributes("drop=true");

		try {
			//drop the database; not the nicest solution, but works
			dataSource.getConnection();
		} catch (SQLNonTransientConnectionException e) {
			//this is OK, database was dropped
		} catch (SQLException e) {
			if (DATABASE_NOT_FOUND.equals(e.getSQLState())) {
				//attempt to drop non-existend database
				//we will ignore this error
				return ; 
			}
			throw new ConfigurationException("Could not drop database.", e);
		}
	}

}
