# File Encrypter
�ļ��������������ַ���Key�����м��ܽ��ܡ�

## Ŀ¼
* [����ԭ��](#����ԭ��)
* [ʹ�÷���](#ʹ�÷���)
* [����](#����)

## ����ԭ��
* ��Ϊ���ļ�������������������ʱһ�㶼�����ֽ�Ϊ��λ���еģ�Ϊ��������о��������ֽڼ��ܵķ�����  
* ����0 ~ 255 ֮��򵥵�һ��һӳ�䣬�Լ�ϵͳ����Ļ������������￼���˳����ļӷ�ȡģ���˷�ȡģ�Լ�bitѭ��λ��
    * **�ӷ�ȡģ**
    ```java
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
    ```
    
    * **�˷�ȡģ**
    ```java
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
    ```
    
    * **λ��ȡģ**
    ```java
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
    ```

* ����Ҳ������һ�ֵ����ࣩ�ֽڶԶ��ֽڵ�ӳ�䷽����Ŀ���ǽ��ֽڼ���ӳ�䵽ָ���ַ������ο���Base64�����ԭ��������һ��1��2�ļӽ��ܷ�����
    ```java
    /**
	 * һ��ָ�������ַ����ļ򵥼��ܷ���(����Base64)
	 * 
	 */
	//diction���ȹ�26 + 26 +10 + 2 = 64 = 2^6
	// 6λbit��
	// Base 64����6��8�Ĺ�����24/6 = 4���ֵ��ַ���ʾ24/8 = 3���ֽ�
	// �ֲ���2���ֵ��ַ�����ʾһ���ֽ�
	static String diction = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()";
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
    ```
    
## ʹ�÷���
* һ�ֳ������������ֱ���ڴ����������```Encrypter```�ļ���/���ܷ�����ʾ�����£�
```java
//...ʡ�Բ��ִ���
int keys[] = {3,10,1,4,1,5,9,2,6};
int content, cnt = 0, round = keys.length;
while ((content = originRAF.read()) != -1) {
    destRaf.write(Encrypter.decrypt(content, method, keys[cnt]));
    cnt = (cnt + 1) % round;
}
```

* ���⣬Ҳ�ṩ��һ��InputStream/OutputStream�ķ�����ʾ�����£�
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

* ����ṩ��һ��UI����ʾ����˫��jar�ļ�����bat�ļ����о���:  
[Encrypter.jar](https://raw.githubusercontent.com/nICEnnnnnnnLee/FileEncrypter/master/release/Encrypter.jar)  
[ui-run.bat](https://raw.githubusercontent.com/nICEnnnnnnnLee/FileEncrypter/master/release/ui-run.bat)  
![](https://raw.githubusercontent.com/nICEnnnnnnnLee/FileEncrypter/master/release/sample.png)

## ����
* Ҫ������һ���ֽ������ƽ���Ѷȣ������������ֽڶ��ֽڵ�ӳ�䷽���������Դ��ֽڶ�Ӧ˳�����ֽ�����չ
* **LICENSE**: [Apache License v2.0](https://www.apache.org/licenses/LICENSE-2.0.html)

    
