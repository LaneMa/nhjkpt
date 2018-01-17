package org.framework.core.util;

import java.io.Serializable;

public class LicenseSign implements Serializable{

	private String license;
	private String sign;
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
