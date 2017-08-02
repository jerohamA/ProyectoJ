package proyecto;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Orden {
	public String nombre;
	public String apellido;
	public String mail;
	
	
	public Orden(){
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	

	
	
	
	
}