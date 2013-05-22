package com.ykse.tms.satellite.crifstdevice;

import java.io.IOException;
import java.util.Arrays;
import java.util.zip.CRC32;

import javax.xml.bind.JAXBException;

import com.ykse.jaxb.satellite.filminfo.FilmInfoResponse;

public class CMDFilmInfoRequest extends CrifstSatelliteDeviceCMD<String>{

	private FilmInfoResponse filmInfoResponse;
	
	public CMDFilmInfoRequest(String host, int tcpPort, int timeout) {
		super(host, tcpPort, timeout);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected byte[] createCmd(String value) {
		// TODO Auto-generated method stub
		return CrifstSatelliteDeviceProtocol.REQUEST_FILMINFO_CMD;
	}

	@Override
	protected byte[] receiveData() throws Exception {
		// TODO Auto-generated method stub
		byte[] result = receive();
		System.out.println("影片信息反馈报文：\n" + byte2HexStr(result, " "));
		return result;
	}

	@Override
	protected boolean checkValue(byte[] value) {
		// TODO Auto-generated method stub
		byte[] cmd = new byte[2];
		System.arraycopy(value, 1, cmd, 0, 2);
		byte[] requestSuccess = new byte[]{0x22,0x00};
		if(Arrays.equals(cmd, requestSuccess)){
			setPayloadLength(value);
			byte[] temp = new byte[payloadLength];
			System.arraycopy(value, 0, temp, 0, payloadLength);
			byte[] crcByte = new byte[4];
			System.arraycopy(value, payloadLength, crcByte, 0, 4);
			CRC32 crc32 = new CRC32();
			crc32.update(temp);
			if(byte2HexStr(crcByte, "").toLowerCase().equals(Long.toHexString(crc32.getValue()).toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void setResult(byte[] value) {
		// TODO Auto-generated method stub
		if(payloadLength != 0){
			byte[] content = new byte[payloadLength - 7];
			System.arraycopy(value, 7, content, 0, content.length);
			String xml = new String(content);
			System.out.println("影片信息:\n" + xml);
			try {
				filmInfoResponse = new FilmInfoResponse(xml);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public FilmInfoResponse getFilmInfoResponse() {
		return filmInfoResponse;
	}
	
}
