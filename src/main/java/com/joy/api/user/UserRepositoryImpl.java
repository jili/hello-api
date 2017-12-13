package com.joy.api.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	public Collection<GrantedAuthority> loadUserAuthorities(String username) {
		Role role = getRoleByUsername(username);
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getAuthority());
		auths.add(grantedAuthority);

		return auths;
	}

	private Role getRoleByUsername(String username) {
		String sql = "SELECT role FROM user WHERE username = ?1";
		Query query = this.em.createNativeQuery(sql);
		query.setParameter(1, username);

		@SuppressWarnings("unchecked")
		List<Integer> rs = query.getResultList();		
		if(rs.isEmpty() || rs.get(0) == null) {
			return Role.UNKNOWN;
		}
		
		Role role = Role.getRole(rs.get(0));
		return role;
	}
}
