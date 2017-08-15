package proyecto;

import java.sql.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class Login {
	private static Login instance;
	private String usr;
	private String pwd;
	
	public Login() {
		super();
		usr = null;
		pwd = null;
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String verificar() throws SQLException{
				
		Connection conn = this.getConn();
		java.sql.ResultSet resultSet = this.getUsuarios(conn);
		String paginaResult = this.verificarUsuario(resultSet);
		conn.close();
		
		return paginaResult;			
			
	}

	private String verificarUsuario(ResultSet resultSet) throws SQLException{
		while(resultSet.next()){
			if(this.usr.equals(resultSet.getString("username")) && this.pwd.equals(resultSet.getString("password"))){
				if(resultSet.getString("tipo").equals("administrador"))
					return "administrador";
				else
					return "consumidor";				
			}else{
				return "login_error";
			}	
		}
		return "login_error";
	}

	private ResultSet getUsuarios(Connection conn) {
		String Query = "SELECT username,password,roles.tipo from usuarios,roles where username like '"+usr+"'AND usuarios.tipo = roles.id_roles" ;
		Statement st;
		try {
			st = conn.createStatement();
			java.sql.ResultSet resultSet;
			resultSet = st.executeQuery(Query);
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	private Connection getConn() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection Conexion = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/competicion","root","alejandrot");
			return Conexion;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}			
	}
	
	public String autenticar(){
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection Conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/competicion","root","alejandrot");
			String Query = "SELECT * from usuarios where username like '"+usr+"'";
			Statement st = Conexion.createStatement();
			java.sql.ResultSet resultSet;
			resultSet = st.executeQuery(Query);
				
			while(resultSet.next()){
				if(this.usr.equals(resultSet.getString("username")) && this.pwd.equals(resultSet.getString("password"))){
					if(resultSet.getString("tipo").equals("administrador"))
						return "administrador";
					else
						return "consumidor";				
				}else{
					return "login_error";
				}	
			}
			Conexion.close();
		} catch (Exception e) {
		   e.printStackTrace();
		   return "login_error2";
		}
		return "login_error2";
	}

	public static Login getInstance() {
		if (instance == null) {
			instance = new Login();
		}
		return instance;
	}
	
}
	

