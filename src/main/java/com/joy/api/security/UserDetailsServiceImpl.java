package com.joy.api.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joy.api.user.User;
import com.joy.api.user.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	/**
	 * When a user tries to authenticate, this method receives the username,
	 * searches the database for a record containing it, and (if found) returns an
	 * instance of User. The properties of this instance (username and password) are
	 * then checked against the credentials passed by the user in the login request.
	 * This last process is executed outside this class, by the Spring Security
	 * framework.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		Collection<GrantedAuthority> auths = userRepository.loadUserAuthorities(username);

		return new AuthUser(user.getUsername(), user.getPassword(), auths, user.getId());
	}
}
