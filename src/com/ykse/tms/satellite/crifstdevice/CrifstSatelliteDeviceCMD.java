package com.ykse.tms.satellite.crifstdevice;

import com.ykse.tms.satellite.basecmd.AbstractTCPCmd;

public abstract class CrifstSatelliteDeviceCMD<T> extends AbstractTCPCmd<T>{
	protected int payloadLength = 0;
	
	public CrifstSatelliteDeviceCMD(String host, int tcpPort, int timeout) {
		super(host, tcpPort, timeout);
		// TODO Auto-generated constructor stub
	}
	
	protected void setPayloadLength(byte[] value) {
		byte[] payloadLengthByte = new byte[4];
		System.arraycopy(value, 3, payloadLengthByte, 0, 4);
//		payloadLength = Integer.parseInt(hexTran(byte2HexStr(payloadLengthByte, "")), 16);
		payloadLength = Integer.parseInt(byte2HexStr(payloadLengthByte, ""), 16);
	}
	
	public String getStatus() {
		return er.getStatus();
	}
}
