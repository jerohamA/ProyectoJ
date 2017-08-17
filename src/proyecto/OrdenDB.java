package proyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
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

	public void generarOrden(HtmlDataTable datatable) throws Exception {		
			
			FacesContext context = FacesContext.getCurrentInstance();
			Producto data = (Producto) datatable.getRowData();	
			Orden orden = context.getApplication().evaluateExpressionGet(context, "#{orden}", Orden.class);		
			Login log = context.getApplication().evaluateExpressionGet(context, "#{login}", Login.class);
			
			
			orden.setTotal(this.calcularTotal(data));
			orden.setCantidad(data.getCantidadOrden());
			orden.setProducto(data);
			orden.setUsuario(log.getUs().getId());
			
			this.addOrden(orden);		
	}

	private float calcularTotal(Producto pr) {
		return pr.getPrecio()*pr.getCantidadOrden();
	}

	public void addOrden(Orden orden) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
	
		try {
			myConn = getConnection();
	
			String sql = "insert into ordenes (fecha,id_producto,id_usuario,cantidad,total) values (NOW(),?,?,?,?)";
			myStmt = myConn.prepareStatement(sql);
	
			// set params
			
			myStmt.setInt(1, orden.getProducto().getId_producto());
			myStmt.setInt(2, orden.getUsuario());
			myStmt.setInt(3, orden.getCantidad());	
			myStmt.setFloat(4, orden.getTotal());	
			myStmt.execute();			
			
			
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public List<Orden> getOrdenes() throws Exception {
		
		List<Orden> ordenes = new ArrayList<>();
		Login log = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{login}", Login.class);
		
		Connection myConn = null;
		Statement myStmt = null;
		PreparedStatement myPstmt = null;
		ResultSet myRs = null;
		String sql;
		try {
			myConn = getConnection();
			if(log.getUs().getTipo()==1){
				sql = "select * from ordenes order by id_orden";
				myStmt = myConn.createStatement();			
				myRs = myStmt.executeQuery(sql);
			}
			else{
				sql = "select * from ordenes where id_usuario=? order by fecha";
				myPstmt=  myConn.prepareStatement(sql);
				myPstmt.setInt(1, log.getUs().getId()); 
				myRs = myPstmt.executeQuery();
			}
			
			
	
			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("id_orden");
				String fecha = myRs.getString("fecha");
				int id_producto = myRs.getInt("id_producto");
				int id_usuario = myRs.getInt("id_usuario");
				int cantidad = myRs.getInt("cantidad");
				float total = myRs.getFloat("total");
				Producto pr = ProductoDB.getInstance().getProducto(id_producto);
				// create new user object
				Orden tempOrden = new Orden(id,fecha,pr,id_usuario,cantidad,total);
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
