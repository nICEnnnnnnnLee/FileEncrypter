package nicelee.encrypt;

import java.util.HashMap;
import java.util.Map;

public class Encrypter {
	public static void main(String args[]) {
		for (int i = 0; i < 256; i++) {
			for (int key = 0; key < 256; key++) {
				if (i != decryptAlgAdd(encryptAlgAdd(i, key), key)) {
					System.out.println("加密算法失败: 尝试加密i: " + i + ", key: " + key);
				}
			}
		}
	}

	/**
	 * 单字节加密方法: 相加后取模 - 加密
	 * 
	 * @param source 0~255
	 * @param key 可以是任何自然数。
	 * 	但要注意只要key%256相等,加解密是一个效果。
	 *  比如,key 0 可以用key 256来解码
	 * @return
	 */
	public static int encryptAlgAdd(int source, int key) {
		return (source + key) & 0xff;
	}
	
	/**
	 * 单字节加密方法: 相加后取模 - 解密
	 * 
	 * @param source 0~255
	 * @param key 可以是任何自然数。
	 * 	但要注意只要key%256相等,加解密是一个效果。
	 *  比如,key 0 可以用key 256来解码
	 * @return
	 */
	public static int decryptAlgAdd(int source, int key) {
		return (source - key + 256) & 0xff;
	}
	
	/**
	 * 单字节加密方法: bit位移 - 加密
	 * 左移 key位
	 * @param source 0~255
	 * @param key 最好0~7，有取模操作
	 * @return
	 */
	public static int encryptAlgMove(int source, int key) {
		key = key % 8;
		int leftPart = (source << key)& 0xff;
		int rightPart = source >> (8-key);
		return leftPart | rightPart;
	}
	
	/**
	 * 单字节加密方法: bit位移 - 加密
	 * 
	 * @param source 0~255
	 * @param key 最好0~7，有取模操作
	 * @return
	 */
	public static int decryptAlgMove(int source, int key) {
		key = key % 8;
		int part = 128, sum =0;
		for(int i=0; i<key; i++) {
			sum |= part;
			part = part >> 1;
		}

		int leftPart = (source << (8-key)) & sum;
		int rightPart = source >> key;
		return leftPart | rightPart;
	}
	
	/**
	 * 单字节加密方法 - 原始: 相乘后取模 - 加密
	 * 
	 * @param source 0~255
	 * @param key 不能跟256有公约数,即必须是奇数
	 *   注意该值过大可能会导致映射表相同，这点从排列组合是有限的可以简单得出
	 * @return
	 */
	private static int encryptAlgMultipleOringin(int source, int key) {
		return (source * key) & 0xff;
	}
	
	/**
	 * 单字节加密方法 - 原始: 相乘后取模 - 解密
	 * 
	 * @param source 0~255
	 * @param key 不能跟256有公约数,即必须是奇数
	 *   注意该值过大可能会导致映射表相同，这点从排列组合是有限的可以简单得出
	 * @return
	 */
	private static int decryptAlgMultipleOringin(int source, int key) {
		//先建立一个映射表
		int[] dics = new int[256];
		for(int i=0; i<256; i++) {
			dics[(i * key) & 0xff ] = i;
		}
		//从映射表中取出数值
		return dics[source];
	}
	
	/**
	 * 单字节加密方法 - 推广: 相乘后取模 - 加密
	 * 	encryptAlgMultipleOringin(source, key*2 + 1);
	 * @param source 0~255
	 * @param key 可以是任何自然数
	 * @return
	 */
	public static int encryptAlgMultiple(int source, int key) {
		return encryptAlgMultipleOringin(source, key*2 + 1);
	}
	
	/**
	 * 单字节加密方法  - 推广: 相乘后取模 - 解密
	 * 	decryptAlgMultipleOringin(source, key*2 + 1);
	 * @param source 0~255
	 * @param key 可以是任何自然数
	 * @return
	 */
	public static int decryptAlgMultiple(int source, int key) {
		return decryptAlgMultipleOringin(source, key*2 + 1);
	}
	
	/**
	 * 一种指定加密字符集的简单加密方法(类似Base64)
	 * 
	 */
	//diction长度共26 + 26 +10 + 2 = 64 = 2^6
	// 6位bit，
	// Base 64是用6和8的公倍数24/6 = 4个字典字符表示24/8 = 3个字节
	// 现采用2个字典字符来表示一个字节
	public static String diction = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()";
	static int[] encryptDics;
	static Map<Integer, Integer>  decryptDics;
	
	/**
	 * 根据字符串生成字典
	 */
	public static void generateDictionary() {
		encryptDics = new int[64];
		byte[] tempDic = diction.getBytes();
		for(int i=0; i<64; i++) {
			encryptDics[i] = tempDic[i];
		}
		
		decryptDics = new HashMap<Integer, Integer>();
		for(int i=0; i<64; i++) {
			decryptDics.put(encryptDics[i] & 0xff, i);
		}
	}
	
	/**
	 * 根据提供的字符串生成字典
	 * @param keyString 字符串不能由相同字符组成，且长度必须要大于等于64（实际上仅取用了前64个字符）
	 */
	public static void generateDictionary(String keyString) {
		diction = keyString;
		generateDictionary(); 
	}
	
	/**
	 * 必须确保generateDictionary()执行过一次
	 * @param source
	 * @return
	 */
	public static int[] encryptAlgDic(int source) {
		int result[] = new int[2];
		int firstOrder = (source>>2) & 0xff;
		result[0] = encryptDics[firstOrder];
		
		int secondOrder = source & 0x03;
		result[1] = encryptDics[secondOrder];
		
		return result;
	}
	
	/**
	 * 必须确保generateDictionary()执行过一次
	 * @param source
	 * @return
	 */
	public static int decryptAlgDic(int source[]) {
		int first6bit = decryptDics.get(source[0]) << 2;
		int last2bit = decryptDics.get(source[1]);
		
		return first6bit | last2bit;
	}
	
	/**
	 * 
	 * @param content
	 * @param method 0：加法  1：乘法
	 * @param key
	 * @return
	 */
	public static int encrypt(int content, int method, int key) {
		int value = 0;
		switch(method){
		case 0:
			value = encryptAlgAdd(content, key);
		    break;
		case 1:
			value = encryptAlgMultiple(content, key);
			break;
		case 2:
			value = encryptAlgMove(content, key);
			break;
		default:
		    break;
		}
		return value;
	}
	
	/**
	 * 
	 * @param content
	 * @param method 0：加法  1：乘法 2：循环左移法（0~7）
	 * @param key
	 * @return
	 */
	public static int decrypt(int content, int method, int key) {
		int value = 0;
		switch(method){
		case 0:
			value = decryptAlgAdd(content, key);
			break;
		case 1:
			value = decryptAlgMultiple(content, key);
			break;
		case 2:
			value = decryptAlgMove(content, key);
			break;
		default:
			break;
		}
		return value;
	}
}
