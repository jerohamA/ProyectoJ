package proyecto;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Orden {
	private int id_orden;
	private String fecha;
	private Producto producto;
	private Usuario cliente;
	private int cantidad;
	
	public Orden(){
		super();
	}	
	
	public Orden(int id, String fecha, Producto producto, Usuario cliente,int cantidad) {
		super();
		this.id_orden = id;
		this.fecha = fecha;
		this.producto = producto;
		this.cliente = cliente;	
		this.cantidad = cantidad;
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

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}
	
	
}