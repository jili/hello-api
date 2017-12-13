/**
 * Hold all the code related to application's security.
 * 
 * To support both authentication and authorization in our application, we are
 * going to:
 * 
 * <ul>
 * <li>implement an authentication filter to issue JWTS to users sending
 * credentials</li>
 * <li>implement an authorization filter to validate requests containing
 * JWTs</li>
 * <li>create a custom implementation of UserDetailsService to help Spring
 * Security loading user-specific data in the framework</li>
 * <li>and extend the WebSecurityConfigurerAdapter class to customize the
 * security framework to our needs</li>
 * </ul>
 */
package com.joy.api.security;