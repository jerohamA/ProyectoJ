package proyecto;

import java.sql.SQLException;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



@ManagedBean
@SessionScoped
public class LoginController {
	
	private String usr;
	private String pwd;
	private Logger logger = Logger.getLogger(getClass().getName());
	private Login log;
	
	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public LoginController() {
		super();
		this.log = new Login();
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
		log.setUsr(usr);
		log.setPwd(pwd);
		String pag = log.verificar();			
		return pag;		
	}	
}
