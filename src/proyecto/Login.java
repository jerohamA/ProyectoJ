package proyecto;

import java.sql.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class Login {
	
	@ManagedProperty(value="#{usuario}") Usuario us;
	Boolean isLogged = false;
	
	
	public Login() {
			
	}
		
	
	public Usuario getUs() {
		return us;
	}


	public void setUs(Usuario us) {
		this.us = us;
	}
	
	private void setUs(ResultSet rs) throws SQLException {
		this.getUs().setId(rs.getInt("usuarios.id"));
		this.getUs().setFirstName(rs.getString("usuarios.first_name"));
		this.getUs().setLastName(rs.getString("usuarios.last_name"));
		this.getUs().setTipo(rs.getInt("usuarios.tipo"));
	}

	public Boolean getIsLogged() {
		return isLogged;
	}


	public void setIsLogged(Boolean isLogged) {
		this.isLogged = isLogged;
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
			if(this.us.getUsername().equals(resultSet.getString("username")) && this.us.getPassword().equals(resultSet.getString("password"))){
				setUs(resultSet);
				this.isLogged=true;
				return resultSet.getString("roles.tipo");				
			}else{
				return "login_error";
			}	
		}
		return "login_error";
	}

	private ResultSet getUsuarios(Connection conn) {
		String Query = "SELECT * from usuarios,roles where usuarios.username like '"+us.getUsername()+"'AND usuarios.tipo = roles.id_roles" ;
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
	
	/*public String autenticar(){
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
						return "competidor";				
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
	}*/

}
	

