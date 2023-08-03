package dto;

import java.io.Serializable;

public class CommObj implements Serializable{
	private static final long serialVersionUID = -1L;
	private String errorMessage;
	private String warnMessage;
	private String role;
	private String username;
	
	public CommObj() {
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getWarnMessage() {
		return warnMessage;
	}
	public void setWarnMessage(String warnMessage) {
		this.warnMessage = warnMessage;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
