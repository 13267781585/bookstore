package cn.itcast.bookstore.user.domin;

public class User {
	//	CREATE TABLE tb_user(
	//  uid CHAR(32) PRIMARY KEY,/*����*/
	//  username VARCHAR(50) NOT NULL,/*�û���*/
	//  `password` VARCHAR(50) NOT NULL,/*����*/
	//  email VARCHAR(50) NOT NULL,/*����*/
	//  `code` CHAR(64) NOT NULL,/*������*/
	//  state BOOLEAN/*�û�״̬���������Ƿ񼤻�*/
	//);
	 
	private String uid;
	private String username;
	private String password;
	private String email;
	private String code;
	private boolean state;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", email=" + email + ", code="
				+ code + ", state=" + state + "]";
	}
	public User(String uid, String username, String password, String email, String code, boolean state) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.email = email;
		this.code = code;
		this.state = state;
	}
	public User() {
		super();
	}
	
	
}