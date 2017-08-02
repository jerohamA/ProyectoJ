package proyecto;



import javax.faces.bean.ManagedBean;
@ManagedBean
public class Usuario {

	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String tipo;
	
	public Usuario() {
	}
	
	
	public Usuario(int id, String username, String password, String firstName, String lastName, String tipo) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.tipo = tipo;
	}

	
	public Usuario(int id, String firstName, String lastName, String tipo) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.tipo = tipo;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	

}
