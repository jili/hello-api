package com.joy.api.security;

public class Principal {
	
	private int uid;
	private String username;

	public Principal(int uid, String username) {
		this.setUid(uid);
		this.setUsername(username);
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
