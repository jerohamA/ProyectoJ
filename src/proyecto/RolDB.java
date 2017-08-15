package proyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class RolDB {
	
	private static RolDB instance;
	private DataSource dataSource;
	private String jndiName= "java:comp/env/jdbc/competicion";
	
	public static RolDB getInstance() throws Exception {
		if (instance == null) {
			instance = new RolDB();
		}
		
		return instance;
	}
		
	public RolDB() throws NamingException {
		super();
		this.dataSource = getDataSource();
	}
	
	public void addRol(Rol rol) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into roles (tipo) values (?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, rol.getTipo());
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}
	
	private Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();
		
		return theConn;
	}
	
	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}
	
	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}