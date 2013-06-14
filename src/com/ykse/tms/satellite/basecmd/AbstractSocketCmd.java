package com.ykse.tms.satellite.basecmd;

import javax.xml.bind.JAXBException;

import com.ykse.jaxb.satellite.SatelliteResponse;
import com.ykse.socket.SocketConnect;
import com.ykse.tms.satellite.api.SatelliteControl;

public abstract class AbstractSocketCmd<T, S extends SocketConnect> {
	private byte[] resultValue;
	
	protected SatelliteResponse er = new SatelliteResponse();
	protected S socket;
	
	/**
	 * 构造函数
	 * @param host 主机名(ip)
	 * @param tcpPort 端口
	 * @param timeout 超时值
	 */
	public AbstractSocketCmd(String host, int tcpPort, int timeout) {
		try {
			socket = createSocket(host, tcpPort, timeout);
			setStatus(SatelliteControl.RESULT_STATUS_OK);		//网络响应成功
		} catch (Exception e) {
			e.printStackTrace();
			setStatus(SatelliteControl.RESULT_STATUS_ERROR);	//网络响应失败
		}
	}

	protected abstract S createSocket(String host, int tcpPort, int timeout) throws Exception;			//建立socket	
	protected abstract byte[] createCmd(T value);				//生成命令体	
	protected abstract byte[] receiveData() throws Exception;	//接收数据	
	protected abstract boolean checkValue(byte[] value);		//校验结果
	protected abstract void setResult(byte[] value);			//取返回值

	/**
	 * 执行命令并读取反馈信息
	 * @param value
	 * @return
	 */
	public boolean execute(T value) {
		boolean result = false;
		if (er.getStatus().equals(SatelliteControl.RESULT_STATUS_OK)) {
			try {
				byte[] cmdSend = createCmd(value);
//				System.out.println(byte2HexStr(cmdSend, " "));
				
				socket.connect();
				socket.send(cmdSend);				//发送命令
				resultValue = receiveData();		//获取返回值
//				System.out.println(byte2HexStr(resultValue, " "));
				socket.close();
				
				if (checkValue(resultValue)) {
					setStatus(SatelliteControl.RESULT_STATUS_OK);	//成功
					setResult(resultValue);
					result = true;		//返回值正确
				} else {
					setStatus(SatelliteControl.RESULT_STATUS_ERROR);	//失败
				}
			} catch (Exception e) {
				setStatus(SatelliteControl.RESULT_STATUS_ERROR);	//失败
				e.printStackTrace();
			}
		}
		return result;
	}
	
	protected byte[] receive(int length) throws Exception {
		return socket.receive(length);
	}
	
	protected byte[] receive() throws Exception {
		return socket.receive();
	}
	
	/**
	 * 读取结果xml字符串
	 * @return
	 */
	public String toXML() {
		try {
			return er.toXmlString();
		} catch (JAXBException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 设置返回状态
	 * @param status OK|ERROR
	 */
	protected void setStatus(String status) {
		er.setStatus(status);
	}
	
	/**
	 * 设置操作成功
	 * @param success TRUE|FALSE
	 */
	protected void setResultSuccess(String success) {
		er.setSuccess(success);
	}

	protected void setCpluuid(String value) {
		er.setCpluuid(value);
    }

	protected void setAssetType(String value) {
    	er.setAssetType(value);
    }

	protected void setSource(String value) {
    	er.setSource(value);
    }

	protected void setUsername(String value) {
    	er.setUsername(value);
    }

	protected void setPassword(String value) {
    	er.setPassword(value);
    }

	protected void setPath(String value) {
    	er.setPath(value);
    }
    
	/**
	 * 字节数组合并
	 * @param src
	 * @return
	 */
	protected byte[] byteArrayCopy(byte[][] src) {
		int len = 0;
		for (int i=0; i<src.length; i++) {
			len = len + src[i].length;
		}
		byte[] result = new byte[len];
		int pos = 0;
		for (int i=0; i<src.length; i++) {
			System.arraycopy(src[i], 0, result, pos, src[i].length);
			pos = pos + src[i].length;
		}
		return result;
	}
	
    /**
     * bytes转换成十六进制字符串
     */
	protected String byte2HexStr(byte[] b, String split) {
        String hs="";
        String stmp="";
        for (int n=0;n<b.length;n++) {
            stmp=(Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1)
            	hs=hs+"0"+stmp;
            else
            	hs=hs+stmp;
            //if (n<b.length-1)  hs=hs+":";
            hs = hs + split;
        }
        return hs.toUpperCase();
    }
	
	protected byte[] hexStringToBytes(String hexString) {
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for(int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
	
	protected byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

	protected String addZeroForNum(String str, int strLength, boolean isLeft) {
		int strLen = str.length();
		StringBuffer sb = new StringBuffer();
		if (strLen < strLength) {
			while (strLen < strLength) {
				sb.append("0");//补0
				strLen ++;
			}
			str = isLeft ? sb.append(str).toString() : (str + sb.toString());
		}
		return str;
	}
	
	protected String hexTran(String hexstr){
        int length = hexstr.length()/2;
        String [] strs = new String[length];
        int s = 0;
        for(int i=0;i<length;i++){
            strs[length-i-1] = hexstr.substring(s,s+2);
            s+=2;
        }
        StringBuilder sb = new StringBuilder(hexstr.length());
        for(String str:strs){
            sb.append(str);
        }
        return sb.toString();
    }
    
	protected int hexToLength(String hexstr){
        return Integer.parseInt(hexTran(hexstr),16);
    }
	
	/**
	 * @param content
	 * @param number 校验和字符串长度
	 * @return
	 */
	protected byte[] checkSum(byte[] content, int number) {
		long sum = 0L;
		for (byte b : content) {
			sum += b;
		}
		String checkSumStr = addZeroForNum(Long.toHexString(sum), number, true);
		return hexStringToBytes(checkSumStr);
	}
}
