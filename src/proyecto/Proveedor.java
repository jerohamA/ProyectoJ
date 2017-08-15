package proyecto;


import javax.faces.bean.ManagedBean;


@ManagedBean
public class Proveedor {
	private int id_proveedor;
	private String nombre;
	private String direccion;
	private int telefono;
	private String mail;
	
	public Proveedor() {
		
	}
	
	public Proveedor(int id_proveedor, String nombre, String direccion, int telefono, String mail) throws Exception {
		super();
		this.id_proveedor = id_proveedor;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.mail = mail;
		
	}


	public int getId_proveedor() {
		return id_proveedor;
	}

	public void setId_proveedor(int id_proveedor) {
		this.id_proveedor = id_proveedor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
}