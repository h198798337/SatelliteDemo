package com.ykse.tms.satellite.crifstdevice;


public class CMDFtpDownLoadComplete extends CrifstSatelliteDeviceCMD<String>{
	
	public CMDFtpDownLoadComplete(String host, int tcpPort, int timeout) {
		super(host, tcpPort, timeout);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected byte[] createCmd(String value) {
		// TODO Auto-generated method stub
		String sendXml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"+
				"<response status=\"OK\" version=\"2\" >"+
				"<uuid>" + value + "</uuid>"+
				"</response>";
		byte[] xmlb = sendXml.getBytes();
		byte[] temp = new byte[7 + xmlb.length];
		byte[] cmd = new byte[11 + xmlb.length];
		String lengthS = Integer.toHexString(11 + xmlb.length);
		lengthS = addZeroForNum(lengthS, 8, true);//补零
		lengthS = hexTran(lengthS);//高低位调转
		byte[] length = hexStringToBytes(lengthS);
		System.arraycopy(new byte[]{0x55, 0x27, 0x00}, 0, temp, 0, 3);
		System.arraycopy(length, 0, temp, 3, 4);
		System.arraycopy(xmlb, 0, temp, 7, xmlb.length);
		//生成校验和
		/*CRC32 crc32 = new CRC32();
		crc32.update(temp);
		byte[] crc = hexStringToBytes(Long.toHexString(crc32.getValue()));*/
		byte[] checkSum = checkSum(temp, 8);
		//拼接命令
		System.arraycopy(temp, 0, cmd, 0, temp.length);
//		System.arraycopy(crc, 0, cmd, temp.length, crc.length);
		System.arraycopy(checkSum, 0, cmd, temp.length, checkSum.length);
		System.out.println("对应影片下载完毕信息反馈报文：\n" + byte2HexStr(cmd, " "));
		return cmd;
	}

	@Override
	protected byte[] receiveData() throws Exception {
		// TODO Auto-generated method stub
		byte[] result = receive(11);
		System.out.println("对应影片下载完毕信息确认报文：\n" + byte2HexStr(result, " "));
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
