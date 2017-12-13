package com.joy.api.user;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Cacheable("user")
	public User findByUsername(String username);
	
	@Cacheable("roles")
	public Collection<GrantedAuthority> loadUserAuthorities(String username);
}
