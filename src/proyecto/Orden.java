package proyecto;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Orden {
	private int id_orden;
	private String fecha;
	@ManagedProperty(value="#{producto}") 	private Producto producto;
	private int usuario;
	private int cantidad;
	private float total;
	
	public Orden(){
		super();
	}	
	
	public Orden(int id, String fecha, Producto producto, int usuario,int cantidad,float total) {
		super();
		this.id_orden = id;
		this.fecha = fecha;
		this.producto = producto;
		this.usuario = usuario;	
		this.cantidad = cantidad;
		this.total = total;
	}

	public Orden(Producto producto, int usuario,int cantidad,float total) {
		super();
		this.producto = producto;
		this.usuario = usuario;	
		this.cantidad = cantidad;
		this.total = total;
	}
	
	
	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getId_orden() {
		return id_orden;
	}

	public void setId_orden(int id_orden) {
		this.id_orden = id_orden;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int us) {
		this.usuario = us;
	}
	
	
}