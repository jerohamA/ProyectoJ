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

@ManagedBean
@SessionScoped	
public class UsuariosController {

	private List<Usuario> usuarios;
	private UsuarioDB usuariodb;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public UsuariosController() throws Exception {
		usuarios = new ArrayList<>();
		
		usuariodb = UsuarioDB.getInstance();
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void loadUsuarios() {

		logger.info("Cargando Usuarios");
		
		usuarios.clear();

		try {
			
			// get all users from database
			usuarios = usuariodb.getUsuarios();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error cargando Usuario", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
		
	public String addUsuario(Usuario us) {

		logger.info("Agregando Usuario: " + us);

		try {
			
			// add user to the database
			//agregar aqui comprobacion para username repetido
			usuariodb.addUsuario(us);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error agregando usuario", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "login?faces-redirect=true";
	}

	public String loadUsuario(int usuarioid) {
		
		logger.info("Cargando usuario" + usuarioid);
		
		try {
			// get user from database
			Usuario user = usuariodb.getUsuario(usuarioid);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("usuario", user);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading usuario id:" + usuarioid, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "updateUsuario.xhtml";
	}	
	
	public String updateUsuario(Usuario user) {

		logger.info("updating user: " + user);
		
		try {
			
			// update student in the database
			usuariodb.updateUsuario(user);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error actualizando usuario: " + user, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "administrador?faces-redirect=true";		
	}
	
	public String deleteUser(int userid) {

		logger.info("Deleting user id: " + userid);
		
		try {

			// delete the user from the database
			usuariodb.deleteUsuario(userid);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error borrando usuario id: " + userid, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "administrador";	
	}	
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}
