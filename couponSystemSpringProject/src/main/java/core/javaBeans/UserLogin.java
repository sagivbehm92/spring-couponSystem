package core.javaBeans;


import org.springframework.stereotype.Component;

/**
 * @author user1 this class represent the user detail when he trying login to
 *         the system
 */
@Component
public class UserLogin {

	private String type;
	private String name;
	private String password;
	private Long id;
	private boolean rememberMe;
	public UserLogin() {
		super();
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getType() {
		return type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserLogin [type=" + type + ", name=" + name + ", password=" + password + ", id=" + id + ", rememberMe="
				+ rememberMe + "]";
	}

}
