package nicelee.encrypt;

import java.util.HashMap;
import java.util.Map;

public class Encrypter {
	public static void main(String args[]) {
		for (int i = 0; i < 256; i++) {
			for (int key = 0; key < 256; key++) {
				if (i != decryptAlgAdd(encryptAlgAdd(i, key), key)) {
					System.out.println("�����㷨ʧ��: ���Լ���i: " + i + ", key: " + key);
				}
			}
		}
	}

	/**
	 * ���ֽڼ��ܷ���: ��Ӻ�ȡģ - ����
	 * 
	 * @param source 0~255
	 * @param key �������κ���Ȼ����
	 * 	��Ҫע��ֻҪkey%256���,�ӽ�����һ��Ч����
	 *  ����,key 0 ������key 256������
	 * @return
	 */
	public static int encryptAlgAdd(int source, int key) {
		return (source + key) & 0xff;
	}
	
	/**
	 * ���ֽڼ��ܷ���: ��Ӻ�ȡģ - ����
	 * 
	 * @param source 0~255
	 * @param key �������κ���Ȼ����
	 * 	��Ҫע��ֻҪkey%256���,�ӽ�����һ��Ч����
	 *  ����,key 0 ������key 256������
	 * @return
	 */
	public static int decryptAlgAdd(int source, int key) {
		return (source - key + 256) & 0xff;
	}
	
	/**
	 * ���ֽڼ��ܷ���: bitλ�� - ����
	 * ���� keyλ
	 * @param source 0~255
	 * @param key ���0~7����ȡģ����
	 * @return
	 */
	public static int encryptAlgMove(int source, int key) {
		key = key % 8;
		int leftPart = (source << key)& 0xff;
		int rightPart = source >> (8-key);
		return leftPart | rightPart;
	}
	
	/**
	 * ���ֽڼ��ܷ���: bitλ�� - ����
	 * 
	 * @param source 0~255
	 * @param key ���0~7����ȡģ����
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
	 * ���ֽڼ��ܷ��� - ԭʼ: ��˺�ȡģ - ����
	 * 
	 * @param source 0~255
	 * @param key ���ܸ�256�й�Լ��,������������
	 *   ע���ֵ������ܻᵼ��ӳ�����ͬ������������������޵Ŀ��Լ򵥵ó�
	 * @return
	 */
	private static int encryptAlgMultipleOringin(int source, int key) {
		return (source * key) & 0xff;
	}
	
	/**
	 * ���ֽڼ��ܷ��� - ԭʼ: ��˺�ȡģ - ����
	 * 
	 * @param source 0~255
	 * @param key ���ܸ�256�й�Լ��,������������
	 *   ע���ֵ������ܻᵼ��ӳ�����ͬ������������������޵Ŀ��Լ򵥵ó�
	 * @return
	 */
	private static int decryptAlgMultipleOringin(int source, int key) {
		//�Ƚ���һ��ӳ���
		int[] dics = new int[256];
		for(int i=0; i<256; i++) {
			dics[(i * key) & 0xff ] = i;
		}
		//��ӳ�����ȡ����ֵ
		return dics[source];
	}
	
	/**
	 * ���ֽڼ��ܷ��� - �ƹ�: ��˺�ȡģ - ����
	 * 	encryptAlgMultipleOringin(source, key*2 + 1);
	 * @param source 0~255
	 * @param key �������κ���Ȼ��
	 * @return
	 */
	public static int encryptAlgMultiple(int source, int key) {
		return encryptAlgMultipleOringin(source, key*2 + 1);
	}
	
	/**
	 * ���ֽڼ��ܷ���  - �ƹ�: ��˺�ȡģ - ����
	 * 	decryptAlgMultipleOringin(source, key*2 + 1);
	 * @param source 0~255
	 * @param key �������κ���Ȼ��
	 * @return
	 */
	public static int decryptAlgMultiple(int source, int key) {
		return decryptAlgMultipleOringin(source, key*2 + 1);
	}
	
	/**
	 * һ��ָ�������ַ����ļ򵥼��ܷ���(����Base64)
	 * 
	 */
	//diction���ȹ�26 + 26 +10 + 2 = 64 = 2^6
	// 6λbit��
	// Base 64����6��8�Ĺ�����24/6 = 4���ֵ��ַ���ʾ24/8 = 3���ֽ�
	// �ֲ���2���ֵ��ַ�����ʾһ���ֽ�
	public static String diction = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()";
	static int[] encryptDics;
	static Map<Integer, Integer>  decryptDics;
	
	/**
	 * �����ַ��������ֵ�
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
	 * �����ṩ���ַ��������ֵ�
	 * @param keyString �ַ�����������ͬ�ַ���ɣ��ҳ��ȱ���Ҫ���ڵ���64��ʵ���Ͻ�ȡ����ǰ64���ַ���
	 */
	public static void generateDictionary(String keyString) {
		diction = keyString;
		generateDictionary(); 
	}
	
	/**
	 * ����ȷ��generateDictionary()ִ�й�һ��
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
	 * ����ȷ��generateDictionary()ִ�й�һ��
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
	 * @param method 0���ӷ�  1���˷�
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
	 * @param method 0���ӷ�  1���˷� 2��ѭ�����Ʒ���0~7��
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
