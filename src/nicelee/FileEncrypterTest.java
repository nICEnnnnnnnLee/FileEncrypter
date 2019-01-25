package nicelee;


import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nicelee.file.FileEncrypter;

public class FileEncrypterTest {

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
	public void testMethod0() {
		FileEncrypter fEncrypter = new FileEncrypter();
		String key = "3.1415926535";
		fEncrypter.encrypt(new File("README0.md"), 0, key);
		fEncrypter.decrypt(new File("README0.md.encrypt"), 0, key);
	}
	@Test
	public void testMethod1() {
		FileEncrypter fEncrypter = new FileEncrypter();
		String key = "3.1415926535";
		fEncrypter.encrypt(new File("README1.md"), 1, key);
		fEncrypter.decrypt(new File("README1.md.encrypt"), 1, key);
	}
	@Test
	public void testMethod2() {
		FileEncrypter fEncrypter = new FileEncrypter();
		String key = "3.1415926535";
		fEncrypter.encrypt(new File("README2.md"), 2, key);
		fEncrypter.decrypt(new File("README2.md.encrypt"), 2, key);
	}

}
