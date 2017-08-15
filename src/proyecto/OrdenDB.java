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

public class OrdenDB {

	private static OrdenDB instance;
	private DataSource dataSource;
	private String jndiName= "java:comp/env/jdbc/competicion";
	
	
	
	public static OrdenDB getInstance() throws Exception {
		if (instance == null) {
			instance = new OrdenDB();
		}
		
		return instance;
	}
	
	
	
	public OrdenDB( ) throws NamingException {
		super();
		this.dataSource = getDataSource();
	}



	public void addOrden(Orden orden) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
	
		try {
			myConn = getConnection();
	
			String sql = "insert into ordenes (fecha,producto,cliente,cantidad) values (NOW(), ?, ?,?)";
	
			myStmt = myConn.prepareStatement(sql);
	
			// set params
			
			myStmt.setInt(2, orden.getProducto().getId_producto());
			myStmt.setInt(3, orden.getCliente().getId());
			myStmt.setInt(3, orden.getCantidad());
			
			
			myStmt.execute();			
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public List<Orden> getOrdenes() throws Exception {
		
		List<Orden> ordenes = new ArrayList<>();
	
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
	
			String sql = "select * from ordenes order by id_orden";
	
			myStmt = myConn.createStatement();
	
			myRs = myStmt.executeQuery(sql);
	
			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("id_orden");
				String fecha = myRs.getString("fecha");
				int id_producto = myRs.getInt("id_producto");
				int id_usuario = myRs.getInt("id_usuario");
				int cantidad = myRs.getInt("cantidad");
				
				Producto tempProducto = ProductoDB.getInstance().getProducto(id_producto);	
				Usuario tempUsuario = UsuarioDB.getInstance().getUsuario(id_usuario);
				// create new user object
				Orden tempOrden = new Orden(id,fecha,tempProducto,tempUsuario,cantidad);
				// add it to the list of users
				ordenes.add(tempOrden);
			}
			
			return ordenes;		
		}
		finally {
			close (myConn, myStmt, myRs);
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