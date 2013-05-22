package com.ykse.tms.satellite.api;

import com.ykse.tms.satellite.api.SatelliteDevice.SatelliteDeviceType;

public class SatelliteControl implements ISatelliteControl {
	public static String RESULT_SUCCESS_TRUE = "TRUE"; // 操作成功
	public static String RESULT_SUCCESS_FALSE = "FALSE"; // 操作失败
	public static String RESULT_SUCCESS_NOTSUPPORT = "NOT SUPPORT"; // 不支持操作
	public static String RESULT_ON = "ON"; // 打开
	public static String RESULT_OFF = "OFF"; // 关闭
	public static String RESULT_STATUS_OK = "OK"; // 执行成功
	public static String RESULT_STATUS_ERROR = "ERROR"; // 执行失败

	private ISatelliteControl satelliteCtrl;
	
	/**
	 * 构造函数
	 * @param audioDevice 音频设备
	 * @throws Exception 
	 */
	public SatelliteControl(SatelliteDevice satelliteDevice) throws Exception {
		satelliteCtrl = SatelliteDeviceCmdFactory.create(satelliteDevice);			//建立控制指令
	}
	
	@Override
	public String getXml() {
		// TODO Auto-generated method stub
		return satelliteCtrl.getXml();
	}

	@Override
	public void setCompleteFlag(String uuid) {
		// TODO Auto-generated method stub
		satelliteCtrl.setCompleteFlag(uuid);
	}
	
	public static void main(String args[]) {
		SatelliteDevice satelliteDevice = new SatelliteDevice("192.168.0.91", SatelliteDeviceType.CRIFST);
		try {
			SatelliteControl satelliteControl = new SatelliteControl(satelliteDevice);
			satelliteControl.getXml();
			String uuid = "";
			satelliteControl.setCompleteFlag(uuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
