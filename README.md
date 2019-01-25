# File Encrypter
文件加密器，根据字符串Key来进行加密解密。

## 目录
* [加密原理](#加密原理)
* [使用方法](#使用方法)
* [其它](#其它)

## 加密原理
* 因为对文件或者其他数据流操作时一般都是以字节为单位进行的，为简单起见，研究的是逐字节加密的方法。  
* 考虑0 ~ 255 之间简单的一对一映射，以及系统定义的基础操作，这里考虑了常见的加法取模、乘法取模以及bit循环位移
    * **加法取模**
    ```java
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
    ```
    
    * **乘法取模**
    ```java
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
    ```
    
    * **位移取模**
    ```java
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
    ```

* 这里也考虑了一种单（多）字节对多字节的映射方法，目的是将字节加密映射到指定字符集。参考了Base64编解码原理，以下是一种1到2的加解密方法：
    ```java
    /**
	 * 一种指定加密字符集的简单加密方法(类似Base64)
	 * 
	 */
	//diction长度共26 + 26 +10 + 2 = 64 = 2^6
	// 6位bit，
	// Base 64是用6和8的公倍数24/6 = 4个字典字符表示24/8 = 3个字节
	// 现采用2个字典字符来表示一个字节
	static String diction = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()";
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
    ```
    
## 使用方法
* 一种常见的情况，是直接在代码里面调用```Encrypter```的加密/解密方法，示例如下：
```java
//...省略部分代码
int keys[] = {3,10,1,4,1,5,9,2,6};
int content, cnt = 0, round = keys.length;
while ((content = originRAF.read()) != -1) {
    destRaf.write(Encrypter.decrypt(content, method, keys[cnt]));
    cnt = (cnt + 1) % round;
}
```

* 另外，也提供了一种InputStream/OutputStream的方法，示例如下：
```java
File file1 = new File("README.md.encrypt");
FileInputStream fin = new FileInputStream(file1);
InputStreamReader reader = new InputStreamReader(new DecryperInputStream(fin));
BufferedReader buReader = new BufferedReader(reader);
String str;
while( (str = buReader.readLine()) != null) {
    System.out.println(str);
}
buReader.close();
```

* 最后，提供了一个UI界面示例，双击jar文件或者bat文件运行均可:  
[Encrypter.jar](https://raw.githubusercontent.com/nICEnnnnnnnLee/FileEncrypter/master/release/Encrypter.jar)  
[ui-run.bat](https://raw.githubusercontent.com/nICEnnnnnnnLee/FileEncrypter/master/release/ui-run.bat)  
![](https://raw.githubusercontent.com/nICEnnnnnnnLee/FileEncrypter/master/release/sample.png)

## 其它
* 要想增加一串字节序列破解的难度，不仅仅考虑字节对字节的映射方法，还可以从字节对应顺序入手进行拓展
* **LICENSE**: [Apache License v2.0](https://www.apache.org/licenses/LICENSE-2.0.html)

    
