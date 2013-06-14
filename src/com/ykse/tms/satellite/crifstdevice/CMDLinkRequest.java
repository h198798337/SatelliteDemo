package com.ykse.tms.satellite.crifstdevice;


public class CMDLinkRequest extends CrifstSatelliteDeviceCMD<String>{

	public CMDLinkRequest(String host, int tcpPort, int timeout) {
		super(host, tcpPort, timeout);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected byte[] createCmd(String value) {
		// TODO Auto-generated method stub
		return CrifstSatelliteDeviceProtocol.REQUEST_CMD;
	}

	@Override
	protected byte[] receiveData() throws Exception {
		// TODO Auto-generated method stub
		byte[] result = receive(11);
		System.out.println("连接确认报文：\n" + byte2HexStr(result, " "));
		return result;
	}

	@Override
	protected boolean checkValue(byte[] value) {
		// TODO Auto-generated method stub
		byte[] temp = new byte[7];
//		byte[] crcByte = new byte[4];
		byte[] checksumFromV = new byte[4];
		System.arraycopy(value, 0, temp, 0, 7);
		System.arraycopy(value, 7, checksumFromV, 0, 4);
//		CRC32 crc32 = new CRC32();
//		crc32.update(temp);
		byte[] checksum = checkSum(temp, 8);
//		if(byte2HexStr(crcByte, "").toLowerCase().equals(Long.toHexString(crc32.getValue()).toLowerCase())) {
		if(byte2HexStr(checksumFromV, "").toLowerCase().equals(byte2HexStr(checksum, "").toLowerCase())) {	
			return true;
		}
		return false;
	}

	@Override
	protected void setResult(byte[] value) {
		// TODO Auto-generated method stub
		
	}
	
}
