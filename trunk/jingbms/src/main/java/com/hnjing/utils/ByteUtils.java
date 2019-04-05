package com.hnjing.utils;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 字节工具类
 * 
 * @author yhb
 * 
 */
public class ByteUtils {
	/**
	 * 比较两个字符串数据
	 * 
	 * @param src
	 *            比较的第一个字符串
	 * @param dest
	 *            比较的第二个字符串
	 * @return 如果内容相同，则返回true，包括指向同一对象， 或者同为null的情况，否则为false
	 */
	public static boolean compare(byte[] src, byte[] dest) {
		if (src == null && dest == null)
			return true;
		if (src == null || dest == null)
			return false;
		if (src == dest)
			return true;
		if (src.length == dest.length) {
			int i = 0;
			for (; i < src.length; i++) {
				if (src[i] != dest[i])
					break;
			}
			if (i != src.length)
				return false;
			return true;
		} else
			return false;
	}

	/**
	 * 在字节串中搜索指定字节串 <br/>
	 * <code>
	 * --|---HEAD-------------------------------   <br/>
	 * ->|---^----------------------------------   <br/></code>
	 * 
	 * @param source
	 *            被搜索的源字节串
	 * @param offset
	 *            搜索开始偏移
	 * @param len
	 *            搜索源数据的长度
	 * @param target
	 *            搜索的源字节串
	 * @return 第一次出现的字节串位置,错误返回-1
	 */
	public static int searchBytes(byte[] source, int offset, int len,
			byte[] target) {
		if (source == null || target == null)
			return -1;
		int targetpos = -1;
		if (len > source.length)
			len = source.length;
		int endpos = offset + len - target.length + 1;
		for (int i = offset; i < endpos; i++) {
			int j;
			for (j = 0; j < target.length; j++) {
				if (source[i + j] != target[j])
					break;
			}
			if (j == target.length) {
				targetpos = i;
				break;
			}
		}

		return targetpos;
	}

	/**
	 * 在字节串中搜索指定字节串 <br/>
	 * <code>
	 * --|---HEAD-------------------------------   <br/>
	 * ->|---^----------------------------------   <br/></code>
	 * 
	 * @param source
	 *            被搜索的源字节串
	 * @param target
	 *            搜索的源字节串
	 * @return 第一次出现的字节串位置,错误返回-1
	 */
	public static int searchBytes(byte[] source, byte[] target) {
		if (source == null || target == null)
			return -1;
		return searchBytes(source, 0, source.length, target);
	}

	/**
	 * 在字节串中搜索指定字节串,反向搜索<br/>
	 * <code>
	 * |---------------------------------END--|   <br/>
	 * |---------------------------------^----|<- <br/></code>
	 * 
	 * @param source
	 *            被搜索的源字节串
	 * @param offset
	 *            搜索开始偏移
	 * @param len
	 *            搜索源数据的长度
	 * @param target
	 *            搜索的源字节串
	 * @return 第一次出现的字节串位置,错误返回-1
	 */
	public static int searchBytesRev(byte[] source, int offset, int len,
			byte[] target) {
		if (source == null || target == null)
			return -1;

		int startpos = source.length - offset - target.length;
		if (startpos < 0)
			return -1;

		int endpos = source.length - offset - len;
		int targetpos = -1;
		if (endpos < 0)
			endpos = 0;

		for (int i = startpos; i >= endpos; i--) {
			int j;
			for (j = 0; j < target.length; j++) {
				if (source[i + j] != target[j])
					break;
			}
			if (j == target.length) {
				targetpos = i;
				break;
			}
		}

		return targetpos;
	}

	/**
	 * 在字节串中搜索指定字节串,反向搜索<br/>
	 * <code>
	 * |---------------------------------END--|   <br/>
	 * |---------------------------------^----|<- <br/></code>
	 * 
	 * @param source
	 *            被搜索的源字节串
	 * @param target
	 *            搜索的源字节串
	 * @return 第一次出现的字节串位置,错误返回-1
	 */
	public static int searchBytesRev(byte[] source, byte[] target) {
		if (source == null || target == null)
			return -1;
		return searchBytes(source, 0, source.length, target);
	}

	/**
	 * 将短整形值按本地字节序转换到字节数组
	 * 
	 * @param value
	 *            短整形值
	 * @return 字节数组(本地字节序)
	 */
	public static byte[] short2Bytes(short value) {
		return short2Bytes(value, ByteOrder.nativeOrder());
	}

	/**
	 * 将短整形值按指定字节序转换到字节数组
	 * 
	 * @param value
	 *            短整形值
	 * @param bo
	 *            指定字节序
	 * @return 字节数组(指定字节序)
	 */
	public static byte[] short2Bytes(short value, ByteOrder bo) {
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.order(bo);
		bb.asShortBuffer().put(value);
		return bb.array();
	}

	/**
	 * 将整形值按本地字节序转换到字节数组
	 * 
	 * @param value
	 *            整形值
	 * @return 字节数组(本地字节序)
	 */
	public static byte[] int2Bytes(int value) {
		return int2Bytes(value, ByteOrder.nativeOrder());
	}

	/**
	 * 将整形值按指定字节序转换到字节数组
	 * 
	 * @param value
	 *            整形值
	 * @param bo
	 *            指定字节序
	 * @return 字节数组(指定字节序)
	 */
	public static byte[] int2Bytes(int value, ByteOrder bo) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(bo);
		bb.asIntBuffer().put(value);
		return bb.array();
	}
	
	/**
	 * 将长整形值按本地字节序转换到字节数组(仅高四位)
	 * @param 长整形值
	 * @return 字节数组(本地字节序，高四位)
	 */
	public static byte[] long2BytesLen4(long value) {
		byte[] ret = new byte[4];
		System.arraycopy(long2Bytes(value, ByteOrder.nativeOrder()), 0, ret, 0, 4);
		return ret;
	}

	/**
	 * 将长整形值按本地字节序转换到字节数组
	 * 
	 * @param value
	 *            长整形值
	 * @return 字节数组(本地字节序)
	 */
	public static byte[] long2Bytes(long value) {
		return long2Bytes(value, ByteOrder.nativeOrder());
	}

	/**
	 * 将长整形值按指定字节序转换到字节数组
	 * 
	 * @param value
	 *            长整形值
	 * @param bo
	 *            指定字节序
	 * @return 字节数组(指定字节序)
	 */
	public static byte[] long2Bytes(long value, ByteOrder bo) {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(bo);
		bb.asLongBuffer().put(value);
		return bb.array();
	}

	/**
	 * 将浮点值按本地字节序转换到字节数组
	 * 
	 * @param value
	 *            浮点值
	 * @return 字节数组(本地字节序)
	 */
	public static byte[] float2Bytes(float value) {
		return float2Bytes(value, ByteOrder.nativeOrder());
	}

	/**
	 * 将浮点值按指定字节序转换到字节数组
	 * 
	 * @param value
	 *            浮点形值
	 * @param bo
	 *            指定字节序
	 * @return 字节数组(指定字节序)
	 */
	public static byte[] float2Bytes(float value, ByteOrder bo) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(bo);
		bb.asFloatBuffer().put(value);
		return bb.array();
	}

	/**
	 * 将双精度浮点值按本地字节序转换到字节数组
	 * 
	 * @param value
	 *            双精度浮点值
	 * @return 字节数组(本地字节序)
	 */
	public static byte[] double2Bytes(double value) {
		return double2Bytes(value, ByteOrder.nativeOrder());
	}

	/**
	 * 将双精度浮点值按指定字节序转换到字节数组
	 * 
	 * @param value
	 *            双精度浮点值
	 * @param bo
	 *            指定字节序
	 * @return 字节数组(指定字节序)
	 */
	public static byte[] double2Bytes(double value, ByteOrder bo) {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.order(bo);
		bb.asDoubleBuffer().put(value);
		return bb.array();
	}

	/**
	 * 将字节数组按本地字节序转换到短整形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 短整形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static short bytes2Short(byte[] bytes) throws Exception {
		return bytes2Short(bytes, ByteOrder.nativeOrder());
	}

	/**
	 * 将字节数组按指定字节序转换到短整形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param bo
	 *            指定字节序
	 * @return 短整形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static short bytes2Short(byte[] bytes, ByteOrder bo)
			throws Exception {
		if (bytes == null)
			throw new NullPointerException();
		if (bytes.length < 2)
			throw new Exception("长度不符合要求");
		return bytes2Short(bytes, 0, bo);
	}

	/**
	 * 将字节数组按指定字节序转换到短整形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param offset
	 *            数据偏移
	 * @return 短整形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static short bytes2Short(byte[] bytes, int offset) throws Exception {
		return bytes2Short(bytes, offset, ByteOrder.nativeOrder());
	}

	/**
	 * 将字节数组按指定字节序转换到短整形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param offset
	 *            数据偏移
	 * @param bo
	 *            指定字节序
	 * @return 短整形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static short bytes2Short(byte[] bytes, int offset, ByteOrder bo)
			throws Exception {
		if (bytes == null)
			throw new NullPointerException();
		if (bytes.length - offset < 2)
			throw new Exception("长度不符合要求");
		ByteBuffer bb = ByteBuffer.wrap(bytes, offset, 4);
		bb.order(bo);
		return bb.asShortBuffer().get();
	}

	/**
	 * 将字节数组按本地字节序转换到整形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 整形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static int bytes2Int(byte[] bytes) throws Exception {
		return bytes2Int(bytes, ByteOrder.nativeOrder());
	}
	
	/**
	 * 将字节数组按本地字节序转换到Float()
	 * 
	 * @param bytes
	 *            字节数组
	 * @return Float
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static Float bytes2FloatAsC(byte[] bytes) throws Exception {
		if(bytes==null || bytes.length!=4) return 0.00f;
		byte[] by = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
		System.arraycopy(bytes, 0, by, 0, 4);		
		return  bytes2Float(by, ByteOrder.nativeOrder());
	}

	/**
	 * 将字节数组按指定字节序转换到整形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param bo
	 *            指定字节序
	 * @return 整形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static int bytes2Int(byte[] bytes, ByteOrder bo) throws Exception {
		if (bytes == null)
			throw new NullPointerException();
		if (bytes.length < 4)
			throw new Exception("长度不符合要求");
		return bytes2Int(bytes, 0, bo);
	}

	/**
	 * 将字节数组按指定字节序转换到整形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param offset
	 *            数据偏移
	 * @return 整形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static int bytes2Int(byte[] bytes, int offset) throws Exception {
		return bytes2Int(bytes, offset, ByteOrder.nativeOrder());
	}

	/**
	 * 将字节数组按指定字节序转换到整形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param offset
	 *            数据偏移
	 * @param bo
	 *            指定字节序
	 * @return 整形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static int bytes2Int(byte[] bytes, int offset, ByteOrder bo)
			throws Exception {
		if (bytes == null)
			throw new NullPointerException();
		if (bytes.length - offset < 4)
			throw new Exception("长度不符合要求");
		ByteBuffer bb = ByteBuffer.wrap(bytes, offset, 4);
		bb.order(bo);
		return bb.asIntBuffer().get();
	}

	/**
	 * 将字节数组按本地字节序转换到长整形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 长整形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static long bytes2Long(byte[] bytes) throws Exception {
		return bytes2Long(bytes, ByteOrder.nativeOrder());
	}
	
	/**
	 * 将字节数组按本地字节序转换到长整形值(四位长度)
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 长整形值
	 * @throws Exception
	 *             字节数据不符合要求(四位长度)
	 */
	public static long bytesLen42Long(byte[] bytes) throws Exception {		
		if (bytes == null)
			throw new NullPointerException();
		if (bytes.length !=4 )
			throw new Exception("长度不符合要求");
		byte[] bs = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
		System.arraycopy(bytes, 0, bs, 0, 4);
		//System.out.println("bytesLen42Long:"+StringUtils.byteArray2String(bs, " "));
		return bytes2Long(bs, ByteOrder.nativeOrder());
	}

	/**
	 * 将字节数组按指定字节序转换到长整形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param bo
	 *            指定字节序
	 * @return 长整形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static long bytes2Long(byte[] bytes, ByteOrder bo) throws Exception {
		if (bytes == null)
			throw new NullPointerException();
		if (bytes.length < 8)
			throw new Exception("长度不符合要求");
		return bytes2Long(bytes, 0, bo);
	}

	/**
	 * 将字节数组按指定字节序转换到长整形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param offset
	 *            数据偏移
	 * @return 长整形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static long bytes2Long(byte[] bytes, int offset) throws Exception {
		return bytes2Long(bytes, offset, ByteOrder.nativeOrder());
	}

	/**
	 * 将字节数组按指定字节序转换到长整形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param offset
	 *            数据偏移
	 * @param bo
	 *            指定字节序
	 * @return 长整形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static long bytes2Long(byte[] bytes, int offset, ByteOrder bo)
			throws Exception {
		if (bytes == null)
			throw new NullPointerException();
		if (bytes.length - offset < 8)
			throw new Exception("长度不符合要求");
		ByteBuffer bb = ByteBuffer.wrap(bytes, offset, bytes.length);
		bb.order(bo);
		return bb.asLongBuffer().get();
	}

	/**
	 * 将字节数组按本地字节序转换到浮点形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 浮点形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static float bytes2Float(byte[] bytes) throws Exception {
		return bytes2Float(bytes, ByteOrder.nativeOrder());
	}

	/**
	 * 将字节数组按指定字节序转换到浮点值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param bo
	 *            指定字节序
	 * @return 浮点形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static float bytes2Float(byte[] bytes, ByteOrder bo)
			throws Exception {
		if (bytes == null)
			throw new NullPointerException();
		if (bytes.length < 8)
			throw new Exception("长度不符合要求");
		return bytes2Float(bytes, 0, bo);
	}

	/**
	 * 将字节数组按指定字节序转换到浮点形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param offset
	 *            数据偏移
	 * @return 浮点形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static float bytes2Float(byte[] bytes, int offset) throws Exception {
		return bytes2Float(bytes, offset, ByteOrder.nativeOrder());
	}

	/**
	 * 将字节数组按指定字节序转换到浮点形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param offset
	 *            数据偏移
	 * @param bo
	 *            指定字节序
	 * @return 浮点形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static float bytes2Float(byte[] bytes, int offset, ByteOrder bo)
			throws Exception {
		if (bytes == null)
			throw new NullPointerException();
		if (bytes.length - offset < 4)
			throw new Exception("长度不符合要求");
		ByteBuffer bb = ByteBuffer.wrap(bytes, offset, 4);
		bb.order(bo);
		return bb.asFloatBuffer().get();
	}

	/**
	 * 将字节数组按本地字节序转换到双精度浮点形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 双精度浮点形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static double bytes2Double(byte[] bytes) throws Exception {
		return bytes2Double(bytes, ByteOrder.nativeOrder());
	}

	/**
	 * 将字节数组按指定字节序转换到双精度浮点值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param bo
	 *            指定字节序
	 * @return 双精度浮点形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static double bytes2Double(byte[] bytes, ByteOrder bo)
			throws Exception {
		if (bytes == null)
			throw new NullPointerException();
		if (bytes.length < 8)
			throw new Exception("长度不符合要求");
		return bytes2Double(bytes, 0, bo);
	}

	/**
	 * 将字节数组按指定字节序转换到双精度浮点形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param offset
	 *            数据偏移
	 * @return 双精度浮点形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static double bytes2Double(byte[] bytes, int offset)
			throws Exception {
		return bytes2Double(bytes, offset, ByteOrder.nativeOrder());
	}

	/**
	 * 将字节数组按指定字节序转换到双精度浮点形值
	 * 
	 * @param bytes
	 *            字节数组
	 * @param offset
	 *            数据偏移
	 * @param bo
	 *            指定字节序
	 * @return 双精度浮点形值
	 * @throws Exception
	 *             字节数据不符合要求
	 */
	public static double bytes2Double(byte[] bytes, int offset, ByteOrder bo)
			throws Exception {
		if (bytes == null)
			throw new NullPointerException();
		if (bytes.length - offset < 8)
			throw new Exception("长度不符合要求");
		ByteBuffer bb = ByteBuffer.wrap(bytes, offset, 4);
		bb.order(bo);
		return bb.asDoubleBuffer().get();
	}

	/**
	 * 将一个整形转换成一个字节的BCD码，如果需要两位十进制表示，使用压缩BCD编码
	 * 
	 * @param value
	 *            数值 0~99, 大于99时，不会编码
	 * @return 所表示的BCD编码
	 */
	public static byte int2bcd(int value) {
		return (byte) ((((value / 10) % 10) << 4) | (value % 10));
	}

	/**
	 * 将一个字节的BCD码转移成整形，支持压缩BCD编码
	 * 
	 * @param data
	 *            BCD编码的字节数据
	 * @return BCD编码表示的数据,范围0~99
	 */
	public static int bcd2int(byte data) {
		return ((data >> 4) & 0x0F) * 10 + (data & 0xF);
	}

	/**
	 * 截取字节串指定位置起的一定长度
	 * 
	 * @param src
	 *            要截取的字节串
	 * @param begin
	 *            开始位置
	 * @param count
	 *            长度
	 * @return
	 */
	public static byte[] subBytes(byte[] src, int begin, int count) {
		byte[] bs = new byte[count];
		for (int i = begin; i < begin + count; i++)
			bs[i - begin] = src[i];
		return bs;
	}

	/**
	 * 字符到字节转换
	 * 
	 * @param ch
	 * @return
	 */
	public static void putChar(byte[] bb, char ch, int index) {
		int temp = (int) ch;
		// byte[] b = new byte[2];
		for (int i = 0; i < 2; i++) {
			// 将最高位保存在最低位
			bb[index + i] = new Integer(temp & 0xff).byteValue();
			temp = temp >> 8; // 向右移8位
		}
	}

	/**
	 * 字节到字符转换
	 * 
	 * @param b
	 * @return
	 */
	public static char getChar(byte[] b, int index) {
		int s = 0;
		if (b[index + 1] > 0)
			s += b[index + 1];
		else
			s += 256 + b[index + 0];
		s *= 256;
		if (b[index + 0] > 0)
			s += b[index + 1];
		else
			s += 256 + b[index + 0];
		char ch = (char) s;
		return ch;
	}

//	@SuppressWarnings("deprecation")
//	public static char[] bytes2chars(byte[] bytes)
//			throws MalformedInputException, UnsupportedEncodingException {
//		ByteToCharConverter converter = ByteToCharConverter
//				.getConverter("8859_1");
//		char[] ret = converter.convertAll(bytes);
//		return ret;
//	}
//
//	@SuppressWarnings("deprecation")
//	public static byte[] chars2bytes(char[] chars)
//			throws UnsupportedEncodingException, MalformedInputException {
//		CharToByteConverter converter = CharToByteConverter
//				.getConverter("8859_1");
//		byte[] ret = converter.convertAll(chars);
//		return ret;
//	}

	// ----------------------------------------------------------------
	// 测试用例
	// ----------------------------------------------------------------
	public static void main(String[] args) {
		String testcase1 = "DD BB 14 00 05 12 CD AB 02 12 CD AB 90 80 A2 00 79 06 CC AA";
		String testcase2 = "00 DD BB 14 00 05 12 CD AB 02 12 CD AB 90 80 A2 00 79 06 CC AA";
		String testcase3 = "DD BB 14 00 05 12 CD AB 02 12 CD AB 90 80 A2 00 79 06 CC AA 00";
		String testcase4 = "00 DD BB 14 00 05 12 CD AB 02 12 CD AB 90 80 A2 00 79 06 CC AA 00";

		byte[] tcbyte1 = StringUtils.string2ByteArray(testcase1,
				StringUtils.SPACE_SPLITER);
		byte[] tcbyte2 = StringUtils.string2ByteArray(testcase2,
				StringUtils.SPACE_SPLITER);
		byte[] tcbyte3 = StringUtils.string2ByteArray(testcase3,
				StringUtils.SPACE_SPLITER);
		byte[] tcbyte4 = StringUtils.string2ByteArray(testcase4,
				StringUtils.SPACE_SPLITER);

		byte[] head = new byte[] { (byte) 0xDD, (byte) 0xBB };
		byte[] tail = new byte[] { (byte) 0xCC, (byte) 0xAA };

		TestCase(tcbyte1, head, tail, 0, 18);
		TestCase(tcbyte2, head, tail, 1, 19);
		TestCase(tcbyte3, head, tail, 0, 18);
		TestCase(tcbyte4, head, tail, 1, 19);

		System.out.println("-----------------------------------------");
		System.out.println("测试用例完成信息, 没有输出信息表示测试成功。");
		System.out.println("-----------------------------------------");

		System.out.println("-----------BCD Test----------------");
		System.out.println(ByteUtils.int2bcd(20));
		System.out.println(ByteUtils.int2bcd(2012));
	}

	private static void TestCase(byte[] tcbyte1, byte[] head, byte[] tail,
			int headpos, int tailpos) {
		int pos;

		pos = ByteUtils.searchBytes(tcbyte1, 0, tcbyte1.length, head);
		if (pos != headpos) {
			System.out.println("ByteUtils.searchBytes 函数搜索开始位置有异常!");
		}
		pos = ByteUtils.searchBytes(tcbyte1, 0, tcbyte1.length, tail);
		if (pos != tailpos) {
			System.out.println("ByteUtils.searchBytes 函数搜索结尾位置有异常!");
		}

		pos = ByteUtils.searchBytesRev(tcbyte1, 0, tcbyte1.length, head);
		if (pos != headpos) {
			System.out
					.println("ByteUtils.searchBytesRev 函数搜索结尾搜索开始位置有异常! pos = "
							+ pos);
		}
		pos = ByteUtils.searchBytesRev(tcbyte1, 0, tcbyte1.length, tail);
		if (pos != tailpos) {
			System.out.println("ByteUtils.searchBytesRev 函数搜索开始位置有异常! pos = "
					+ pos);
		}
	}

}
