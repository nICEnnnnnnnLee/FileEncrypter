package nicelee.ui.thread;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import nicelee.file.FileEncrypter;

public class EncryptThread extends Thread{
	//new EncryptThread(this, btnEncrypt, btnDecrypt, encryptText, fileText, consoleArea)
	JFrame jf;
	JButton btnEncrypt;
	JButton btnDecrypt;
	JTextField txtFileEncrypt;
	JTextField txtKeyEncrypt;
	JTextArea consoleArea;
	JComboBox<String> cbMethodEncrypt;
	
	public EncryptThread(JFrame jf, JButton btnEncrypt, JButton btnDecrypt, JTextField txtFileEncrypt, JComboBox<String> cbMethodEncrypt, JTextField txtKeyEncrypt, JTextArea consoleArea) {
		this.jf = jf;
		this.btnEncrypt = btnEncrypt;
		this.btnDecrypt = btnDecrypt;
		this.txtFileEncrypt = txtFileEncrypt;
		this.cbMethodEncrypt = cbMethodEncrypt;
		this.txtKeyEncrypt = txtKeyEncrypt;
		this.consoleArea = consoleArea;
		this.setName("Thread-Encrypt");
	}
	
	public void run() {
		String strFile = txtFileEncrypt.getText();
		File file = new File(strFile);
		if(!file.exists() || !file.isFile()) {
			JOptionPane.showMessageDialog(jf, "文件路径错误!", "错误",JOptionPane.WARNING_MESSAGE);
			return;
		}
		FileEncrypter fEncrypter = new FileEncrypter(consoleArea);
		btnEncrypt.setEnabled(false);
		btnDecrypt.setEnabled(false);
		try {
			fEncrypter.encrypt(file, cbMethodEncrypt.getSelectedIndex(), txtKeyEncrypt.getText());
		}catch (Exception e) {
			JOptionPane.showMessageDialog(jf, "加密文件时出了问题...", "错误",JOptionPane.WARNING_MESSAGE);
		}
		JOptionPane.showMessageDialog(jf, "文件加密成功!", "成功",JOptionPane.PLAIN_MESSAGE);
		btnEncrypt.setEnabled(true);
		btnDecrypt.setEnabled(true);
	}
}
