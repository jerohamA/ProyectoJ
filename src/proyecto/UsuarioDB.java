package proyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UsuarioDB {

	private static UsuarioDB instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/competicion";
	
	public static UsuarioDB getInstance() throws Exception {
		if (instance == null) {
			instance = new UsuarioDB();
		}
		
		return instance;
	}
	
	private UsuarioDB() throws Exception {		
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}
		
	public List<Usuario> getUsuarios() throws Exception {

		List<Usuario> usuarios = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from usuarios where tipo = 2 order by id";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				int tipo = myRs.getInt("tipo");
				String username = myRs.getString("username");
				String password = myRs.getString("password");

				// create new user object
				Usuario tempUsuario = new Usuario(id, username, password, firstName, lastName,
						tipo);

				// add it to the list of users
				usuarios.add(tempUsuario);
			}
			
			return usuarios;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void addUsuario(Usuario us) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into usuarios (first_name, last_name, username, password, tipo) values (?, ?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, us.getFirstName());
			myStmt.setString(2, us.getLastName());
			myStmt.setString(3, us.getUsername());
			myStmt.setString(4, us.getPassword());
			myStmt.setInt(5, us.getTipo());
			
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public Usuario getUsuario(int usuarioID) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from usuarios where id=?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, usuarioID);
			
			myRs = myStmt.executeQuery();

			Usuario user = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String username = myRs.getString("username");
				//String password = myRs.getString("password");
				int tipo = myRs.getInt("tipo");
				

				user = new Usuario(id,username,null,firstName, lastName,tipo);
			}
			else {
				throw new Exception("Could not find usuario id: " + usuarioID);
			}

			return user;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public void updateUsuario(Usuario usuario) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update usuarios "
						+ " set first_name=?, last_name=?, tipo=?, username=?, password=?"
						+ " where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, usuario.getFirstName());
			myStmt.setString(2, usuario.getLastName());
			myStmt.setInt(3, usuario.getTipo());
			myStmt.setString(4, usuario.getUsername());
			myStmt.setString(5, usuario.getPassword());
			myStmt.setInt(6, usuario.getId());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public void deleteUsuario(int usuarioID) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from usuarios where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, usuarioID);
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}		
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
