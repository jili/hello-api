package com.joy.api.device;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "device")
public class Device {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String sn;
	private int uid;

	public int getId() {
		return id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
}
