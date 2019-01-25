package nicelee.ui.thread;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import nicelee.file.FileEncrypter;

public class DecryptThread extends Thread{
	//new EncryptThread(this, btnEncrypt, btnDecrypt, encryptText, fileText, consoleArea)
	JFrame jf;
	JButton btnEncrypt;
	JButton btnDecrypt;
	JTextField txtFileDecrypt;
	JTextField txtKeyDecrypt;
	JComboBox<String> cbMethodDecrypt;
	JTextArea consoleArea;
	
	public DecryptThread(JFrame jf, JButton btnEncrypt, JButton btnDecrypt, JTextField txtFileDecrypt, JComboBox<String> cbMethodDecrypt, JTextField txtKeyDecrypt, JTextArea consoleArea) {
		this.jf = jf;
		this.btnEncrypt = btnEncrypt;
		this.btnDecrypt = btnDecrypt;
		this.txtFileDecrypt = txtFileDecrypt;
		this.cbMethodDecrypt = cbMethodDecrypt;
		this.txtKeyDecrypt = txtKeyDecrypt;
		this.consoleArea = consoleArea;
		this.setName("Thread-Decrypt");
	}
	public void run() {
		String strFile = txtFileDecrypt.getText();
		File file = new File(strFile);
		if(!file.exists() || !file.isFile()) {
			JOptionPane.showMessageDialog(jf, "文件路径错误!", "错误",JOptionPane.WARNING_MESSAGE);
			return;
		}
		FileEncrypter fEncrypter = new FileEncrypter(consoleArea);
		btnEncrypt.setEnabled(false);
		btnDecrypt.setEnabled(false);
		try {
			fEncrypter.decrypt(file, cbMethodDecrypt.getSelectedIndex(), txtKeyDecrypt.getText());
		}catch (Exception e) {
			JOptionPane.showMessageDialog(jf, "解密文件时出了问题...", "错误",JOptionPane.WARNING_MESSAGE);
		}
		JOptionPane.showMessageDialog(jf, "文件解密成功!", "成功",JOptionPane.PLAIN_MESSAGE);
		btnEncrypt.setEnabled(true);
		btnDecrypt.setEnabled(true);
	}
}
