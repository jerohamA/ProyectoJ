package proyecto;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Producto {
	
	private int id_producto;
	private String nombre;
	private String tipo;
	private float precio;
	private Proveedor id_proveedor;
	private int cantidadOrden;	
	
	public Producto() {
		super();
	}

	public Producto(int id_producto, String nombre, String tipo, float precio, Proveedor proveedor) {
		super();
		this.id_producto = id_producto;
		this.nombre = nombre;
		this.tipo = tipo;
		this.precio = precio;
		this.id_proveedor = proveedor;
	}
	
	public int getCantidadOrden() {
		return cantidadOrden;
	}

	public void setCantidadOrden(int cantidadOrden) {
		this.cantidadOrden = cantidadOrden;
	}

	public int getId_producto() {
		return id_producto;
	}
	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public Proveedor getId_proveedor() {
		return id_proveedor;
	}
	public void setId_proveedor(Proveedor id_proveedor) {
		this.id_proveedor = id_proveedor;
	}
	
	
}
