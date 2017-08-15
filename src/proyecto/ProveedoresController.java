package proyecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

@ManagedBean
@SessionScoped
public class ProveedoresController {
	
	private List<Proveedor> proveedores;
	private ProveedorDB proveedordb;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	
	
	public List<Proveedor> getProveedores() {
		return proveedores;
	}

	public ProveedoresController() throws Exception {
		proveedores = new ArrayList<>();
		proveedordb = ProveedorDB.getInstance();
	}

	public void loadProveedores(ComponentSystemEvent event) {

		logger.info("Cargando Proveedores");
		
		proveedores.clear();

		try {
			
			// get all users from database
			proveedores = proveedordb.getProveedores();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error cargando Proveedor", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
		
	public String addProveedor(Proveedor prov) {

		logger.info("Agregando Proveedor: " + prov);

		try {
			
			// add user to the database
			//agregar aqui comprobacion para username repetido
			proveedordb.addProveedor(prov);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error agregando Proveedor", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "proveedores?faces-redirect=true";
	}

	public String loadProveedor(int id_proveedor) {
		
		logger.info("Cargando prov" + id_proveedor);
		
		try {
			// get user from database
			Proveedor prov = proveedordb.getProveedor(id_proveedor);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("Proveedor", prov);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading Proveedor id:" + id_proveedor, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "updateProveedor.xhtml";
	}	
	
	public String updateProveedores(Proveedor prov) {

		logger.info("updating Proveedor: " + prov);
		
		try {
			
			// update student in the database
			proveedordb.updateProveedor(prov);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error actualizando proveedor: " + prov, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "proveedores?faces-redirect=true";		
	}
	
	public String deleteProveedor(int id) {

		logger.info("Deleting prov id: " +id);
		
		try {

			// delete the user from the database
			proveedordb.deleteProveedor(id);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error borrando proveedor id: " + id, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "proveedores";	
	}	
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}