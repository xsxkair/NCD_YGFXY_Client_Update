package org.com.xsx.Data;

public class SoftVersionData {
	
	private static SoftVersionData S_SoftVersionData = null;
	
	private int version = 0;
	
	private SoftVersionData() {
		
	}
	
	public static SoftVersionData GetInstance() {
		if(S_SoftVersionData == null)
			S_SoftVersionData = new SoftVersionData();
		
		return S_SoftVersionData;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public void setVersion(String version) {
		Integer ver = null;
		
		try {
			ver = Integer.valueOf(version);
		} catch (Exception e) {
			// TODO: handle exception
			ver = 0;
		}
		
		this.version = ver;
	}
}
