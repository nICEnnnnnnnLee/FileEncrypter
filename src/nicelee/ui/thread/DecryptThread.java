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
			JOptionPane.showMessageDialog(jf, "�ļ�·������!", "����",JOptionPane.WARNING_MESSAGE);
			return;
		}
		FileEncrypter fEncrypter = new FileEncrypter(consoleArea);
		btnEncrypt.setEnabled(false);
		btnDecrypt.setEnabled(false);
		try {
			fEncrypter.decrypt(file, cbMethodDecrypt.getSelectedIndex(), txtKeyDecrypt.getText());
		}catch (Exception e) {
			JOptionPane.showMessageDialog(jf, "�����ļ�ʱ��������...", "����",JOptionPane.WARNING_MESSAGE);
		}
		JOptionPane.showMessageDialog(jf, "�ļ����ܳɹ�!", "�ɹ�",JOptionPane.PLAIN_MESSAGE);
		btnEncrypt.setEnabled(true);
		btnDecrypt.setEnabled(true);
	}
}
