package nicelee.file;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Date;

import javax.swing.JTextArea;

import nicelee.encrypt.Encrypter;

public class FileEncrypter {

	
	public FileEncrypter() {
	}
	
	
	/**
	 * UI显示相关
	 */
	JTextArea consoleArea;
	public FileEncrypter(JTextArea consoleArea) {
		this.consoleArea = consoleArea;
	}
	
	void print(String str) {
		System.out.println(str);
		if (consoleArea != null) {
			consoleArea.append(str);
			consoleArea.append("\r\n");
		}
	}
	
	int[] convertKeys(String keys) {
		int[] intKeys = new int[keys.length()];
		char[] chKeys = keys.toCharArray();
		for(int i=0; i<intKeys.length; i++) {
			if (Character.isDigit(chKeys[i] )){
			    int num = (int)chKeys[i] - (int)('0');
			    intKeys[i] = num;
			}else {
				intKeys[i] = 10;
			}
		}
		return intKeys;
	}
	/**
	 * 
	 * @param file	待解密文件
	 * @param key  密钥
	 * @return
	 */
	public int encrypt(File file, int method, String key) {
		int[] keys = convertKeys(key);
		File folder = file.getParentFile();
		Date start = new Date();
		print("当前加密" + file.getName());
		try {
			// 读取文件
			RandomAccessFile originRAF = new RandomAccessFile(file, "r");
			File dstFile = new File(folder, file.getName() + ".encrypt");
			if (dstFile.exists()) {
				dstFile.delete();
			}
			// 写加密文件
			RandomAccessFile raf = new RandomAccessFile(dstFile, "rw");
			// 逐字节加密
			int content, cnt = 0, round = keys.length;
			while ((content = originRAF.read()) != -1) {
				raf.write(Encrypter.encrypt(content, method, keys[cnt]));
				cnt = (cnt + 1) % round;
			}
			raf.close();
			originRAF.close();
			Date end = new Date();
			long duration = (end.getTime() - start.getTime());
			if (duration > 1000000) {
				print("用时" + (duration) / 1000 + "s");
			} else {
				print("用时" + (duration) + "ms");
			}
			return 1;
		} catch (Exception e) {
			return 0;// 未知错误
		}
	}
	
	/**
	 * 
	 * @param file	待解密文件
	 * @param key  密钥
	 * @return
	 */
	public int decrypt(File file, int method, String key) {
		int[] keys = convertKeys(key);
		File folder = file.getParentFile();
		Date start = new Date();
		print("当前解密" + file.getName());
		try {
			// 读取文件
			RandomAccessFile originRAF = new RandomAccessFile(file, "r");
			String fName = file.getName().replaceAll(".[^.]+$", "");
			File dstFile = new File(folder, fName + ".decrypt");
			if (dstFile.exists()) {
				dstFile.delete();
			}
			// 写加密文件
			RandomAccessFile raf = new RandomAccessFile(dstFile, "rw");
			// 逐字节解密
			int content, cnt = 0, round = keys.length;
			while ((content = originRAF.read()) != -1) {
				raf.write(Encrypter.decrypt(content, method, keys[cnt]));
				cnt = (cnt + 1) % round;
			}
			raf.close();
			originRAF.close();
			Date end = new Date();
			long duration = (end.getTime() - start.getTime());
			if (duration > 1000000) {
				print("用时" + (duration) / 1000 + "s");
			} else {
				print("用时" + (duration) + "ms");
			}
			return 1;
		} catch (Exception e) {
			return 0;// 未知错误
		}
	}
	

}
