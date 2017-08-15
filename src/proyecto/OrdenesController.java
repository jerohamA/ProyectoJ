package proyecto;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class OrdenesController {

	private List<Orden> ordenes;
	private OrdenDB ordendb;
	private Logger logger = Logger.getLogger(getClass().getName());

	public OrdenesController() throws Exception {
		ordenes = new ArrayList<>();
		
		ordendb = OrdenDB.getInstance();
	}

	public List<Orden> getOrdenes() {
		return ordenes;
	}

	public void loadOrdenes() {
	
		logger.info("Cargando ordenes");
		
		ordenes.clear();
	
		try {
			
			// get all users from database
			ordenes = ordendb.getOrdenes();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error cargando Productos", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	public void generarOrden (Orden orden) throws Exception{
		
		try {
			
			// get all users from database
			ordenes = ordendb.getOrdenes();
			
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
