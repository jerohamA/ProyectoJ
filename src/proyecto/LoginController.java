package proyecto;

import java.sql.SQLException;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.flow.FlowScoped;

@ManagedBean
@SessionScoped
public class LoginController {
	
		
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@ManagedProperty(value="#{login}")	private Login log;	

	public LoginController() {
		super();
	}

	public Login getLog() {
		return log;
	}

	public void setLog(Login log) {
		this.log = log;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String autenticar() throws SQLException{
		
		logger.info("autenticando usuario");		
		String pag = log.verificar();
		return pag;		
	}	
}
