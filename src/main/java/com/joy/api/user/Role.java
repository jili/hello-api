package com.joy.api.user;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ADMIN(0), USER(1), UNKNOWN(-1);

	private static final Map<Integer, Role> lookup = new HashMap<>();

	static {
		for (Role role : EnumSet.allOf(Role.class)) {
			lookup.put(role.getRoleId(), role);
		}
	}

	private final int id;

	private Role(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return this.id;
	}

	public static Role getRole(int id) {
		return lookup.get(id);
	}

	@Override
	public String getAuthority() {
		return this.name();
	}
}
