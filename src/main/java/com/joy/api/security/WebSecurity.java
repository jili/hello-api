package com.joy.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Extends {@link WebSecurityConfigurerAdapter} to take advantage of the default
 * web security configuration provided by Spring Security.This allows us to
 * fine-tune the framework to our needs by defining three methods:
 * 
 * <ul>
 * <li>configure(HttpSecurity http)</li>
 * <li>configure(AuthenticationManagerBuilder auth)</li>
 * <li>corsConfigurationSource()</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurity(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 * A method where we can define which resources are public and which are
	 * secured. In our case, we set the SIGN_UP_URL endpoint as being public and
	 * everything else as being secured. We also configure CORS (Cross-Origin
	 * Resource Sharing) support through http.cors() and we add a custom security
	 * filter in the Spring Security filter chain.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/api/**").hasAnyRole("ADMIN", "USER").and()
				.addFilter(new JwtAuthenticationFilter(authenticationManager()))
				.addFilter(new JwtAuthorizationFilter(authenticationManager()))
				// this disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	/**
	 * A method where we defined a custom implementation of UserDetailsService to
	 * load user-specific data in the security framework. We have also used this
	 * method to set the encrypt method used by our application
	 * (BCryptPasswordEncoder).
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	/**
	 * A method where we can allow/restrict our CORS support. In our case we left it
	 * wide open by permitting requests from any source (/**).
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.applyPermitDefaultValues();
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", config);

		return source;
	}
}
