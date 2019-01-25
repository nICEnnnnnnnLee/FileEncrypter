package nicelee;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nicelee.encrypt.DecryperInputStream;
import nicelee.encrypt.EncryperOutputStream;
import nicelee.encrypt.Encrypter;

public class EncryptTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAlgAdd() {
		for (int i = 0; i < 256; i++) {
			for (int key = 0; key < 1000; key++) {
				if (i != Encrypter.decryptAlgAdd(Encrypter.encryptAlgAdd(i, key), key)) {
					fail("Add¼ÓÃÜËã·¨Ê§°Ü: ³¢ÊÔ¼ÓÃÜi: " + i + ", key: " + key);
				}
			}
		}

	}

	@Test
	public void testAlgMultiple() {
		for (int i = 0; i < 256; i++) {
			for (int key = 0; key < 1000; key++) {
				if (i != Encrypter.decryptAlgMultiple(Encrypter.encryptAlgMultiple(i, key), key)) {
					fail("Multiple¼ÓÃÜËã·¨Ê§°Ü: ³¢ÊÔ¼ÓÃÜi: " + i + ", key: " + key);
				}
			}
		}
	}
	
	@Test
	public void testAlgMove() {
		for (int i = 0; i < 256; i++) {
			for (int key = 0; key < 8; key++) {
				// System.out.println("Multiple¼ÓÃÜËã·¨ key: " + key);
				if (i != Encrypter.decryptAlgMove(Encrypter.encryptAlgMove(i, key), key)) {
					fail("Move¼ÓÃÜËã·¨Ê§°Ü: ³¢ÊÔ¼ÓÃÜi: " + i + ", key: " + key);
				}
			}
		}
	}

	@Test
	public void testAlgDic() {
		Encrypter.generateDictionary();
		for (int i = 0; i < 256; i++) {
			if (i != Encrypter.decryptAlgDic(Encrypter.encryptAlgDic(i))) {
				fail("×Öµä¼ÓÃÜËã·¨Ê§°Ü: ³¢ÊÔ¼ÓÃÜi: " + i);
			}
		}
	}
	
	@Test
	public void testStream() throws IOException {
		File file = new File("README.md");
		File file1 = new File("README.md.encrypt");
		
		FileReader fileReader = new FileReader(file);
		BufferedReader buReader = new BufferedReader(fileReader);
		
		FileOutputStream fout = new FileOutputStream(file1);
		OutputStreamWriter writer = new OutputStreamWriter(new EncryperOutputStream(fout));
		BufferedWriter buWriter = new BufferedWriter(writer);
		
		String oringin = buReader.readLine();
		while(oringin != null){
			buWriter.write(oringin);
			buWriter.write("\r\n");
			oringin = buReader.readLine();
		}
		buReader.close();
		buWriter.close();
		
		FileInputStream fin = new FileInputStream(file1);
		InputStreamReader reader = new InputStreamReader(new DecryperInputStream(fin));
		buReader = new BufferedReader(reader);
		String str;
		while( (str = buReader.readLine()) != null) {
			System.out.println(str);
		}
		buReader.close();
	}
	
}
