package com.joy.api.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * API endpoints
 */
@RestController
@RequestMapping("/api/devices")
public class DeviceController {

	@Autowired
	private DeviceRepository deviceRepository;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void addDevice(@RequestBody Device device) {
		deviceRepository.save(device);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@PostFilter("hasRole('ROLE_ADMIN') || filterObject.uid == principal.uid")
	public @ResponseBody Iterable<Device> getDevices(
			@RequestParam(value = "username", required = false) String username) {
		if (username == null) {
			return deviceRepository.findAll();
		}

		return deviceRepository.findByUsername(username);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void updateDevice(@PathVariable int id, @RequestBody Device device) {
		Device d = deviceRepository.findOne(id);
		Assert.notNull(d, "Device not found");
		d.setSn(device.getSn());
		d.setUid(device.getUid());
		deviceRepository.save(d);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteDevice(@PathVariable int id) {
		deviceRepository.delete(id);
	}
}
