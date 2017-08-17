package proyecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class ProductosController {

	private List<Producto> productos;
	private ProductoDB productodb;
	private Logger logger = Logger.getLogger(getClass().getName());

	public ProductosController() throws Exception {
		productos = new ArrayList<>();
		
		productodb = ProductoDB.getInstance();
	}

	public List<Producto> getProductos() {
		return productos;
	}
	
	@PostConstruct
	public void loadProductos() {
	
		logger.info("Cargando productos");
		
		productos.clear();
	
		try {
			
			// get all users from database
			productos = productodb.getProductos();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error cargando Productos", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	

	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
