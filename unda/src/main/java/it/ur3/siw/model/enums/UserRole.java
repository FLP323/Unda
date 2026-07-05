package it.ur3.siw.model.enums;

public enum UserRole {
	ROLE_USER("USER"),			// Utente registrato
	ROLE_ADMIN("ADMIN");		// Utente registrato con privilegi da amministratore
	
	private String role;
	
	UserRole(String string) {
		this.role = string;
	}
	
	public String getRole() {
		return role;
	}
}
