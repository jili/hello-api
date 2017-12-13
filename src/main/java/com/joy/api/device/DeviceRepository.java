package com.joy.api.device;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
	@Cacheable("devices")
	@Query("SELECT d FROM device d, user u WHERE d.uid = u.id AND u.username = ?")
	public Collection<Device> findByUsername(String username);
}
