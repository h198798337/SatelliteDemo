package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.CRC32;

import com.ykse.pub.FileOperate;

public class TestByte {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String sendXml = FileOperate.readFileToString("filminforeturn.xml");
//		String sendXml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"+
//				"<response status=\"OK\" version=\"2\" >"+
//				"<uuid>ppp</uuid>"+
//				"</response>";
		byte[] xmlb = sendXml.getBytes();
		System.out.println(byte2HexStr(xmlb, " "));
		String ls = Integer.toHexString(7 + xmlb.length);
		ls = addZeroForNum(ls, 8, true);
		byte[] length = hexStringToBytes(hexTran(ls));
		System.out.println(byte2HexStr(length, " "));
		
		byte[] cmd = new byte[xmlb.length + 7];
		System.arraycopy(new byte[]{0x55, 0x22, 0x00}, 0, cmd, 0, 3);
		System.arraycopy(length, 0, cmd, 3, length.length);
		System.arraycopy(xmlb, 0, cmd, 7, xmlb.length);
		CRC32 crc32 = new CRC32();
		crc32.update(cmd);
		byte[] crc = hexStringToBytes(Long.toHexString(crc32.getValue()));
		System.out.println(byte2HexStr(crc, " "));
		
//		byte[] lengtht = {0x07, 0x00, 0x00, 0x00};
//		System.out.println(hexToLength(byte2HexStr(lengtht, "")));
//		
//		String lengtht2 = "00000007";
//		System.out.println(hexTran(lengtht2));
//		byte[] lengtht3 = hexStringToBytes(hexTran(lengtht2));
//		System.out.println(byte2HexStr(lengtht3, " "));
	}
	
	public static String addZeroForNum(String str, int strLength, boolean isLeft) {
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
}
