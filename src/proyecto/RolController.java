package proyecto;

import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class RolController {
	private Rol rol;
	private Logger logger = Logger.getLogger(getClass().getName());
	
}
