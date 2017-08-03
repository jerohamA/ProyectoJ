package proyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean
@SessionScoped
public class Rol {
    int id;
    String tipo;
    private DataSource dataSource;
    private String jndiName= "java:comp/env/jdbc/competicion";
        
    public Rol(String tipo) throws Exception {
        super();        
        this.tipo = tipo;            
        dataSource = getDataSource();
    }
    
    
    public Rol() throws Exception {        
        dataSource = getDataSource();
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }    

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();
        
        DataSource theDataSource = (DataSource) context.lookup(jndiName);
        
        return theDataSource;
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