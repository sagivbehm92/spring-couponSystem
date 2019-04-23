package core.javaBeans;


public class PasswordBean {

	private long id;
	private String password;
	private String newPassword;

	public PasswordBean() {
		super();
	}

	public PasswordBean(long id, String password, String newPassword) {
		super();
		this.id = id;
		this.password = password;
		this.newPassword = newPassword;
	}

	public long geÃ¿tId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "PasswordBean [id=" + id + ", password=" + password + ", newPassword=" + newPassword + "]";
	}

}
