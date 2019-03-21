package death_slash13.doctorlagbe.login;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login")
public class Login {
	@Id
	@Column( name = "id")
	private String id;
	@Column( name = "password")
    private String password;
	@Column( name = "type")
    private String type;
	@Column( name = "token")
    private String token;
    
    public Login() {
		
	}
    
    public Login(String id, String password, String type, String token) {
		this.id = id;
		this.password = password;
		this.type = type;
		this.token = token;
	}

	public Login(Login l) {
		this.id = l.id;
		this.password = l.password;
		this.type = l.type;
		this.token = l.token;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
}
