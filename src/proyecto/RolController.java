package proyecto;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class RolController {
    private Rol roldb;
    private Logger logger = Logger.getLogger(getClass().getName());
    
    
    public RolController() throws Exception {
        this.roldb=new Rol();
    }

    public String addRol(Rol rol) {

        logger.info("Agregando Rol: " + rol);

        try {
            
            // add user to the database
            //agregar aqui comprobacion para rol repetido
            roldb.addRol(rol);
            
        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error agregando rol", exc);
            
            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }
        
        return "administrador?faces-redirect=true";
    }
    
    private void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
}