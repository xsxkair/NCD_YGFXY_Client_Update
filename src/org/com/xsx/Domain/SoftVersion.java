package org.com.xsx.Domain;

public class SoftVersion {
	
	private String softname;
	private int version;
	private String versiondesc;
	private String MD5;
	
	public String getSoftname() {
		return softname;
	}
	public void setSoftname(String softname) {
		this.softname = softname;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getVersiondesc() {
		return versiondesc;
	}
	public void setVersiondesc(String versiondesc) {
		this.versiondesc = versiondesc;
	}
	public String getMD5() {
		return MD5;
	}
	public void setMD5(String mD5) {
		MD5 = mD5;
	}
}
