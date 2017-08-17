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

public class ProductoDB {

	private static ProductoDB instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/competicion";

	private ProductoDB() throws Exception {		
		dataSource = getDataSource();
	}

	public static ProductoDB getInstance() throws Exception {
		if (instance == null) {
			instance = new ProductoDB();
		}		
		return instance;
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}

	public List<Producto> getProductos() throws Exception {
	
		List<Producto> productos = new ArrayList<>();
	
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
	
			String sql = "select * from productos order by id_producto";
	
			myStmt = myConn.createStatement();
	
			myRs = myStmt.executeQuery(sql);
	
			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("id_producto");
				String nombre = myRs.getString("nombre");
				String tipo = myRs.getString("tipo");
				float precio = myRs.getInt("precio");
				
				int id_proveedor = myRs.getInt("id_proveedor");
				
				Proveedor prov  = ProveedorDB.getInstance().getProveedor(id_proveedor);
				
	
				// create new user object
				Producto tempProducto = new Producto(id, nombre, tipo, precio, prov);
	
				// add it to the list of users
				productos.add(tempProducto);
			}
			
			return productos;		
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	
	public Producto getProducto(int id_producto) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
	
			String sql = "select * from productos where id_producto=?";
	
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1,id_producto);
			
			myRs = myStmt.executeQuery();
	
			Producto producto = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("id_producto");
				String nombre = myRs.getString("nombre");
				String tipo = myRs.getString("tipo");
				float precio = myRs.getInt("precio");
				
				int id_proveedor = myRs.getInt("id_proveedor");
				
				Proveedor prov  = ProveedorDB.getInstance().getProveedor(id_proveedor);
				
	
				// create new user object
				producto = new Producto(id, nombre, tipo, precio, prov);
			}
			else {
				throw new Exception("Could not find Producto id: " + id_producto);
			}
	
			return producto;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	//
	

	

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
