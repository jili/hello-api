package com.joy.api.security;

import static com.joy.api.security.SecurityConstants.HEADER_STRING;
import static com.joy.api.security.SecurityConstants.SECRET;
import static com.joy.api.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	public JwtAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(HEADER_STRING);

		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		// If everything is in place, we set the user in the {@link SecurityContext} and
		// allow the request to move on.
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	/**
	 * This method reads the JWT from the Authorization header, and then uses
	 * {@link Jwts} to validate the token.
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			// parse the token.
			Claims tokenBody = Jwts.parser().setSigningKey(SECRET.getBytes())
					.parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
			String username = tokenBody.getSubject();
			int uid = (int) tokenBody.get("id");

			@SuppressWarnings("unchecked")
			String auth = ((LinkedHashMap<String, String>) tokenBody.get("role", List.class).get(0)).get("authority");

			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(auth);
			auths.add(grantedAuthority);

			// TODO: Get user by username from repository
			if (username != null) {
				return new UsernamePasswordAuthenticationToken(new Principal(uid, username), null, auths);
			}
		}
		return null;
	}
}
