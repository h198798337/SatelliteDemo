package com.ykse.tms.satellite.api;

import com.ykse.tms.satellite.api.SatelliteDevice.SatelliteDeviceType;
import com.ykse.tms.satellite.crifstdevice.CrifstSatelliteDevice;

public class SatelliteDeviceCmdFactory {

	/**
	 * 创建音频控制指令
	 * @param audioDevice 音频设备
	 * @return
	 * @throws Exception 
	 */
	public static ISatelliteControl create(SatelliteDevice satelliteDevice) throws Exception {
		ISatelliteControl satelliteCtrl = null;
		String ipaddr = satelliteDevice.getIpaddr();
		SatelliteDeviceType deviceType = satelliteDevice.getSatelliteDeviceType();
		switch (deviceType) {
		case CRIFST: {satelliteCtrl = new CrifstSatelliteDevice(ipaddr); break;}
		default:
			throw new Exception("Satellite Device Type Unkown");
		}
		return satelliteCtrl;
	}

}
