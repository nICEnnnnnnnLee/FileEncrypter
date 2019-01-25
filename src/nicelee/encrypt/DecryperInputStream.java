package nicelee.encrypt;

import java.io.IOException;
import java.io.InputStream;

public class DecryperInputStream extends InputStream{
	
	InputStream in;
	int[] keys;
	int cnt;
	int round;
	public DecryperInputStream(InputStream in) {
		this.in = in;
		this.in = in;
		this.keys = convertKeys("3.141926535");
		this.cnt = 0;
		this.round = this.keys.length;
	}
	
	public DecryperInputStream(InputStream in,String keys) {
		this.in = in;
		this.keys = convertKeys(keys);
		this.cnt = 0;
		this.round = this.keys.length;
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
	
	@Override
	public int read() throws IOException {
		int result, read = in.read();
		if( read == -1 || read ==10 || read ==13) {
			result = read;
		}else {
			result = Encrypter.decrypt(read, 0, keys[cnt]);
			//为了兼容某些readLine操作, \r\n不加密, 而为了保证一对一映射,加密后指向\r或\n的,分别指向原来\r\n的映射结果
			while( result ==10 || result ==13) {
				result = Encrypter.decrypt(result, 0, keys[cnt]);
			}
		}
		
		cnt = (cnt+1) % round;
		return result;
	}

}
