package com.hnjing.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
/**
 * 字符串工具类
 * @author yhb
 *
 */
public class StringUtils {
	public static final String SPACE_SPLITER = " ";
	public static final String NO_SPLITER = "";
	static final String HEXES = "0123456789ABCDEF";

	/**
	 * 转换一个字节数组到十六进制文本形式
	 * 
	 * @param raw
	 *            字节数组
	 * @return 十六进制文本形式
	 */
	public static String byteArray2String(byte[] raw) {
		return byteArray2String(raw, NO_SPLITER);
	}

	/**
	 * 转换一个字节数组到十六进制文本形式,可提供字节之间的分隔符
	 * 
	 * @param raw
	 *            字节数组
	 * @param spliter
	 *            字节之间的分隔符
	 * @return 十六进制文本形式
	 */
	public static String byteArray2String(byte[] raw, String spliter) {
		if (raw == null) {
			return null;
		}
		return byteArray2String(raw, 0, raw.length, spliter);
	}
	
	/**
	 * 转换一个字节数组到十六进制文本形式,可提供字节之间的分隔符，
	 * 可指定偏移值和长度
	 * @param raw 字节数组
	 * @param offset 偏移值
	 * @param len 长度
	 * @param spliter 分隔符
	 * @return 十六进制文本形式
	 */
	public static String byteArray2String(byte[] raw, int offset, int len, String spliter) {
		if (raw == null) {
			return null;
		}
		if (spliter == null) spliter = NO_SPLITER;
		final StringBuilder hex = new StringBuilder((2+spliter.length()) * len);
		boolean first = true;
		for (int i = offset; i < offset+len; i++) {
			if (!first)
				hex.append(spliter);
			hex.append(HEXES.charAt((raw[i] & 0xF0) >> 4)).append(
					HEXES.charAt((raw[i] & 0x0F)));
			first = false;
		}
		return hex.toString();
	}
	
	public static byte[] string2ByteArray(String hex){
		return string2ByteArray(hex, NO_SPLITER);
	}
	
	public static byte[] string2ByteArray(String hex, String spliter){
		if (hex == null || hex.length() == 0) return null;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (spliter != null && spliter.length() > 0){
			// 按分隔符分隔字符串
			String[] hexbytes = hex.split(spliter);
			for(int i = 0; i < hexbytes.length; i++){
				if (hexbytes[i].length() == 2){
					baos.write(string2Byte(hexbytes[i]));
				}else if(hexbytes[i].length() > 2){
					// 替换所有换行符
					try {
						baos.write(string2ByteArray(hexbytes[i].replace("\r", "").replace("\n", "")));
					} catch (IOException e) {
					}
					
				}
			}
		}else{
			int beginIndex = 0;
			do{
				baos.write(string2Byte(
						hex.substring(beginIndex, 
								Math.min(beginIndex + 2, hex.length())
								)));
				beginIndex += 2;
			}while(beginIndex < hex.length());
		}
		
		return baos.toByteArray();
	}

	/**
	 * 转换一个字符串到一个字符
	 * @param string 字符串
	 * @return 一个byte的整形
	 */
	private static int string2Byte(String string) {
		if (string != null && string.length() > 0) 
			return Integer.parseInt(string, 16) & 0xFF;
		else return 0;
	}

	/**
	 * 将单独的字节转换成一个两字节的十六进制字符串
	 * 
	 * @param b
	 *            单独的字节
	 * @return 两字节的十六进制字符串
	 */
	public static String byte2String(byte b) {
		final StringBuilder hex = new StringBuilder(2);
		hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(
				HEXES.charAt((b & 0x0F)));
		return hex.toString();
	}
	
	public static void main(String args[]){
		System.out.println("测试 StringUtils 类实现......");
		if (171 != StringUtils.string2Byte("ab")){
			System.out.println("StringUtils.string2Byte 【不】支持小写十六进制字符.");
		}else System.out.println("StringUtils.string2Byte 支持小写十六进制字符.");
		if (171 != StringUtils.string2Byte("AB")){
			System.out.println("StringUtils.string2Byte 【不】支持大写十六进制字符.");
		}else System.out.println("StringUtils.string2Byte 支持大写十六进制字符.");
		if (171 != StringUtils.string2Byte("aB")){
			System.out.println("StringUtils.string2Byte 【不】支持大小写混合十六进制字符.");
		}else System.out.println("StringUtils.string2Byte 支持大小写混合十六进制字符.");
		try{
			if (171 == StringUtils.string2Byte("Ab ")){
				System.out.println("StringUtils.string2Byte 支持带空格结束的字符转换");
			}
		}catch(Exception e){
			System.out.println("StringUtils.string2Byte 【不】支持带空格结束的字符转换");
		}
		try{
			if (11 == StringUtils.string2Byte("b")){
				System.out.println("StringUtils.string2Byte 支持不完整十六进制字符转换");
			}
		}catch(Exception e){
			System.out.println("StringUtils.string2Byte 【不】支持不完整十六进制字符转换");
		}
		
		//string2ByteArray
		byte[] brv;
		brv = StringUtils.string2ByteArray("DDBBCCAA");
		if (brv.length == 4 
				&& brv[0] == (byte)0xDD && brv[1] == (byte)0xBB 
				&& brv[2] == (byte)0xCC && brv[3] == (byte)0xAA){
			System.out.println("StringUtils.string2ByteArray 支持大写十六进制字符串转换");
		}else System.out.println("StringUtils.string2ByteArray 【不】支持带大写十六进制字符串转换");
		
		brv = StringUtils.string2ByteArray("ddbbccaa");
		if (brv.length == 4 
				&& brv[0] == (byte)0xDD && brv[1] == (byte)0xBB 
				&& brv[2] == (byte)0xCC && brv[3] == (byte)0xAA){
			System.out.println("StringUtils.string2ByteArray 支持小写十六进制字符串转换");
		}else System.out.println("StringUtils.string2ByteArray 【不】支持带小写十六进制字符串转换");
		
		brv = StringUtils.string2ByteArray("DD BB CC AA", StringUtils.SPACE_SPLITER);
		if (brv.length == 4 
				&& brv[0] == (byte)0xDD && brv[1] == (byte)0xBB 
				&& brv[2] == (byte)0xCC && brv[3] == (byte)0xAA){
			System.out.println("StringUtils.string2ByteArray 支持带空格分隔符大写十六进制字符串转换");
		}else System.out.println("StringUtils.string2ByteArray 【不】支持带空格分隔符大写十六进制字符串转换");
		
		brv = StringUtils.string2ByteArray("dd bb  cc aa", StringUtils.SPACE_SPLITER);
		if (brv.length == 4 
				&& brv[0] == (byte)0xDD && brv[1] == (byte)0xBB 
				&& brv[2] == (byte)0xCC && brv[3] == (byte)0xAA){
			System.out.println("StringUtils.string2ByteArray 支持带空格分隔符小写十六进制字符串转换");
		}else System.out.println("StringUtils.string2ByteArray 【不】支持带空格分隔符小写十六进制字符串转换");
		
		brv = StringUtils.string2ByteArray("dd bb\n   cc aa", StringUtils.SPACE_SPLITER);
		if (brv.length == 4 
				&& brv[0] == (byte)0xDD && brv[1] == (byte)0xBB 
				&& brv[2] == (byte)0xCC && brv[3] == (byte)0xAA){
			System.out.println("StringUtils.string2ByteArray 支持带空格分隔符小写十六进制含换行字符串转换");
		}else System.out.println("StringUtils.string2ByteArray 【不】支持带空格分隔符小写十六进制含换行字符串转换");
	}
}
