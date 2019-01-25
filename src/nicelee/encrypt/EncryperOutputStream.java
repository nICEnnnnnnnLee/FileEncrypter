package nicelee.encrypt;

import java.io.IOException;
import java.io.OutputStream;

public class EncryperOutputStream extends OutputStream {

	OutputStream out;
	int[] keys;
	int cnt;
	int round;

	public EncryperOutputStream(OutputStream out) {
		this.out = out;
		this.out = out;
		this.keys = convertKeys("3.141926535");
		this.cnt = 0;
		this.round = this.keys.length;
	}

	public EncryperOutputStream(OutputStream out, String keys) {
		this.out = out;
		this.keys = convertKeys(keys);
		this.cnt = 0;
		this.round = this.keys.length;
	}

	int[] convertKeys(String keys) {
		int[] intKeys = new int[keys.length()];
		char[] chKeys = keys.toCharArray();
		for (int i = 0; i < intKeys.length; i++) {
			if (Character.isDigit(chKeys[i])) {
				int num = (int) chKeys[i] - (int) ('0');
				intKeys[i] = num;
			} else {
				intKeys[i] = 10;
			}
		}
		return intKeys;
	}

	@Override
	public void write(int data) throws IOException {
			
		if (data ==10 || data ==13) {
			out.write(data);
		} else {
			int result = Encrypter.encrypt(data, 0, keys[cnt]);
			while (result ==10 || result ==13) {
				result = Encrypter.encrypt(result, 0, keys[cnt]);
			}
			out.write(result);
		}
		cnt = (cnt + 1) % round;
	}

}
