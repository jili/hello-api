package com.joy.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AuthUser extends User{

	private static final long serialVersionUID = 2935065271403714638L;
	
	private int id;

	public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public AuthUser(String username, String password, Collection<GrantedAuthority> authorities, int id) {
		this(username, password, authorities);
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
