package com.ykse.tms.satellite.api;


public class SatelliteDevice {
	
	public static enum SatelliteDeviceType
	{
		CRIFST;
	}

	public static final SatelliteDeviceType[] SATELLITEDEVICETYPES = { SatelliteDeviceType.CRIFST}; 
	public static final String[] SATELLITEDEVICENAMES = { SatelliteDeviceType.CRIFST.name()};
	
	private String ipaddr;
	private SatelliteDeviceType satelliteDeviceType;

	public SatelliteDevice(String ipaddr, SatelliteDeviceType satelliteDeviceType) {
		this.ipaddr = ipaddr;
		this.satelliteDeviceType = satelliteDeviceType;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public SatelliteDeviceType getSatelliteDeviceType() {
		return satelliteDeviceType;
	}

	public void setSatelliteDeviceType(SatelliteDeviceType satelliteDeviceType) {
		this.satelliteDeviceType = satelliteDeviceType;
	}

}
