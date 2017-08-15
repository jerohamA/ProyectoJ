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

public class ProveedorDB {
	
	private static ProveedorDB instance;
	private DataSource dataSource;
	private String jndiName= "java:comp/env/jdbc/competicion";
	
	public static ProveedorDB getInstance() throws Exception {
		if (instance == null) {
			instance = new ProveedorDB();
		}
		
		return instance;
	}

	public ProveedorDB() throws NamingException {
		super();
		this.dataSource = getDataSource();
	}
	
	public List<Proveedor> getProveedores() throws Exception {

		List<Proveedor> proveedores = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from proveedores order by id_proveedor";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("id_proveedor");
				String nombre = myRs.getString("nombre");
				String direccion = myRs.getString("direccion");
				int telefono = myRs.getInt("telefono");
				String mail = myRs.getString("mail");
				

				// create new user object
				Proveedor tempProveedor = new Proveedor(id, nombre, direccion, telefono, mail);

				// add it to the list of users
				proveedores.add(tempProveedor);
			}
			
			return proveedores;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public void addProveedor(Proveedor prov) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into proveedores (nombre, direccion, telefono, mail) values (?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, prov.getNombre());
			myStmt.setString(2, prov.getDireccion());
			myStmt.setInt(3, prov.getTelefono());
			myStmt.setString(4, prov.getMail());
			
			
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public Proveedor getProveedor(int id_proveedor) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from proveedores where id_proveedor=?";

			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, id_proveedor);
			
			myRs = myStmt.executeQuery();

			Proveedor prov = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("id_proveedor");
				String nombre = myRs.getString("nombre");
				String direccion = myRs.getString("direccion");
				int telefono = myRs.getInt("telefono");
				String mail = myRs.getString("mail");
				
				

				prov = new Proveedor(id,nombre,direccion,telefono, mail);
			}
			else {
				throw new Exception("Could not find Proveedor id: " + id_proveedor);
			}

			return prov;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	public void updateProveedor(Proveedor prov) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update proveedores "
						+ " set nombre=?, direccion=?, telefono=?, mail=?"
						+ " where id_proveedor=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, prov.getNombre());
			myStmt.setString(2, prov.getDireccion());
			myStmt.setInt(3, prov.getTelefono());
			myStmt.setString(4, prov.getMail());
			myStmt.setInt(5, prov.getId_proveedor());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public void deleteProveedor(int id_proveedor) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from proveedores where id_proveedor=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, id_proveedor);
			
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