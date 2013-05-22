package com.test;

import java.util.zip.CRC32;

public class TestCRC {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CRC32 crc32 = new CRC32();
		byte[] t = new byte[]{0x55, 0x28, 0x00, 0x07, 0x00, 0x00, 0x00};
		System.out.println(new String(t));
		crc32.update(t);
		System.out.println(Long.toHexString(crc32.getValue()));
		
		byte[] t2 = hexStringToBytes(Long.toHexString(crc32.getValue()));
		System.out.println(byte2HexStr(t2, " "));
		
		byte[] t3 = {0x00, 0x00, 0x00, 0x07};
		System.out.println(Integer.parseInt(byte2HexStr(t3, ""),16));
		/*byte[] REQUEST_CMD = {0x55, 0x11, 0x00, 0x07, 0x00, 0x00, 0x00, (byte)0xb9, (byte)0xdc, (byte)0xf0, (byte)0xe9};
		byte[] temp = new byte[7];
		byte[] crcByte = new byte[4];
		System.out.println(byte2HexStr(REQUEST_CMD, " "));
		System.arraycopy(REQUEST_CMD, 0, temp, 0, 7);
		System.arraycopy(REQUEST_CMD, 7, crcByte, 0, 4);
		CRC32 crc322 = new CRC32();
		crc322.update(temp);
		System.out.println(Long.toHexString(crc322.getValue()));
		System.out.println(byte2HexStr(crcByte, " "));
		if(byte2HexStr(crcByte, "").toLowerCase().equals(Long.toHexString(crc322.getValue()))) {
			System.out.println("true");
		}*/
	}
	
	/**
     * bytes转换成十六进制字符串
     */
	public static String byte2HexStr(byte[] b, String split) {
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
	
	public static String hexTran(String hexstr){
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
    
    public static int hexToLength(String hexstr){
        return Integer.parseInt(hexTran(hexstr),16);
    }
	
	public static byte[] hexStringToBytes(String hexString) {
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
	
	public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
