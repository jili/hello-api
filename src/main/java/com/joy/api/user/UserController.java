package com.joy.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * API endpoints
 */
@Controller
@RequestMapping(path = "/api/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserController(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody Iterable<User> getUsers() {
		return userRepository.findAll();
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseBody
	public void addUser(@RequestBody User user) {
		// Encrypt the password
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		// Save the user to database
		userRepository.save(user);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseBody
	public void updateUser(@PathVariable int id, @RequestBody User user) {
		User u = userRepository.findOne(id);
		Assert.notNull(u, "User not found");
		u.setUsername(user.getUsername());
		u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		u.setRole(user.getRole());
		userRepository.save(u);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseBody
	public void deleteUser(@PathVariable int id) {
		userRepository.delete(id);
	}
}
