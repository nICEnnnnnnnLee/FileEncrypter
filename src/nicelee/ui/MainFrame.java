package nicelee.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import nicelee.ui.thread.DecryptThread;
import nicelee.ui.thread.EncryptThread;

//import nicelee.ui.thread.DecryptThread;
//import nicelee.ui.thread.EncryptThread;

public class MainFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnEncrypt = new JButton("����");
	JButton btnDecrypt = new JButton("����");
	JButton btnEncryptFileChooser = new JButton("...");
	JButton btnDecryptFileChooser = new JButton("...");
	String []ct= {"0","1","2"};
	JComboBox cbMethodEncrypt = new JComboBox<String>(ct);
	JComboBox cbMethodDecrypt = new JComboBox<String>(ct);
	JTextField txtFileEncrypt = new JTextField();
	JTextField txtFileDecrypt = new JTextField();
	JTextField txtKeyEncrypt = new JTextField();
	JTextField txtKeyDecrypt = new JTextField();
	JTextArea consoleArea = new JTextArea(10, 55);

	public static void main(String[] args) {
		MainFrame log = new MainFrame();
		log.InitUI();
	}

	public void InitUI() {

		// ���ô�������
		this.setTitle("�ļ�������");
		// ���ô��ڴ�С
		this.setSize(860, 427);
		this.setResizable(false);
		// ���ô���λ����Ļ����
		this.setLocationRelativeTo(null);
		// ����Ϊ3ʱ����ʾ�رմ���������˳�
		this.setDefaultCloseOperation(3);
		URL iconURL=this.getClass().getResource("/resources/favicon.png");
		ImageIcon icon=new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());

		// �˴�ʹ����ʽ����FlowLayout����ʽ����������word�Ĳ���
		FlowLayout f1 = new FlowLayout(FlowLayout.LEFT);
		// this��������Ϊf1����ʽ�����
		this.setLayout(f1);

		JLabel tmp0 = new JLabel();
		// ���ÿ�JLabel���ȴ�С.
		tmp0.setPreferredSize(new Dimension(130, 90));
		// ����JLabel����봰��
		this.add(tmp0);
		
		URL fileURL=this.getClass().getResource("/resources/title.png"); 
		ImageIcon imag1 = new ImageIcon(fileURL);
		JLabel pic1 = new JLabel(imag1, SwingConstants.LEFT);
		pic1.setPreferredSize(new Dimension(630, 90));
		// ��������ͼƬ������ӵ� ������
		this.add(pic1);

		// ����һ���յ�JLabel�����ĳ��ȿ��Ϊ110,30
		JLabel name1 = new JLabel();
		// ���ÿ�JLabel���ȴ�С
		name1.setPreferredSize(new Dimension(90, 30));
		// ����JLabel����봰��
		this.add(name1);

		JLabel name = new JLabel("�����ļ���");
		name.setPreferredSize(new Dimension(70, 30));
		this.add(name);

		// JTextField�ڴ��������һ��������ɼ��ı����ı�����Ҫ��ӵİ���Ϊjavax.swing.JTextField.
		// �����ı����С
		txtFileEncrypt.setPreferredSize(new Dimension(220, 30));
		// ��ӵ�������
		this.add(txtFileEncrypt);

		// JButton����һ���ɵ���İ�ť
		btnEncryptFileChooser.addActionListener(this);
		btnEncryptFileChooser.setPreferredSize(new Dimension(20, 30));
		this.add(btnEncryptFileChooser);
		
		JLabel tl0 = new JLabel("����:");
		tl0.setPreferredSize(new Dimension(30, 30));
		this.add(tl0);
		this.add(cbMethodEncrypt);
		
		JLabel tl1 = new JLabel("��Կ: ");
		tl1.setPreferredSize(new Dimension(50, 30));
		this.add(tl1);
		
		txtKeyEncrypt.setPreferredSize(new Dimension(130, 30));
		txtKeyEncrypt.setText("3.141592653");
		this.add(txtKeyEncrypt);
		
		btnEncrypt.addActionListener(this);
		btnEncrypt.setPreferredSize(new Dimension(60, 30));
		this.add(btnEncrypt);
		
		// ͬname1
		JLabel name2 = new JLabel();
		name2.setPreferredSize(new Dimension(60, 30));
		this.add(name2);

		// ͬname1
		JLabel name3 = new JLabel();
		name3.setPreferredSize(new Dimension(90, 30));
		this.add(name3);

		// ͬname
		JLabel password = new JLabel("�����ļ���");
		password.setPreferredSize(new Dimension(70, 30));
		this.add(password);
		
		txtFileDecrypt.setPreferredSize(new Dimension(220, 30));
		this.add(txtFileDecrypt);
		
		btnDecryptFileChooser.addActionListener(this);
		btnDecryptFileChooser.setPreferredSize(new Dimension(20, 30));
		this.add(btnDecryptFileChooser);
		
		JLabel tl3 = new JLabel("����:");
		tl3.setPreferredSize(new Dimension(30, 30));
		this.add(tl3);
		this.add(cbMethodDecrypt);
		JLabel tl2 = new JLabel("��Կ: ");
		tl2.setPreferredSize(new Dimension(50, 30));
		this.add(tl2);
		
		txtKeyDecrypt.setPreferredSize(new Dimension(130, 30));
		txtKeyDecrypt.setText("3.141592653");
		this.add(txtKeyDecrypt);
		
		btnDecrypt.addActionListener(this);
		btnDecrypt.setPreferredSize(new Dimension(60, 30));
		this.add(btnDecrypt);
		// ͬname1
		JLabel name4 = new JLabel();
		name4.setPreferredSize(new Dimension(20, 30));
		this.add(name4);

		// ͬname1
		JLabel name5 = new JLabel();
		name5.setPreferredSize(new Dimension(100, 30));
		this.add(name5);
		consoleArea.setEditable(false);
		JScrollPane js=new JScrollPane(consoleArea);
		//�ֱ�����ˮƽ�ʹ�ֱ���������ַ�ʽ
		js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(js);
		
		// ���ô��ڿɼ����˾�һ��Ҫ�ڴ����������ú���֮�������ӣ���Ȼ�޷�������ʾ
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEncryptFileChooser) {
			JFileChooser fileChooser = new JFileChooser(".");
			fileChooser.setDialogTitle("��ѡ��Ҫ���ܵ��ļ�...");
			fileChooser.setApproveButtonText("ȷ��");
			fileChooser.showOpenDialog(this);// ��ʾ�򿪵��ļ��Ի���
			File f = fileChooser.getSelectedFile();// ʹ���ļ����ȡѡ����ѡ����ļ�
			if (f != null) {
				String s = f.getAbsolutePath();// ����·����
				txtFileEncrypt.setText(s);
				consoleArea.setText("��ǰѡ���ļ���СԼ");
				if( f.length() > 1024 ) {
					consoleArea.append(f.length()/1024 + "KB\r\n");
				}else {
					consoleArea.append(f.length() + "B\r\n");
				}
			}
		} else if (e.getSource() == btnDecryptFileChooser) {
			JFileChooser fileChooser = new JFileChooser(".");
			fileChooser.setDialogTitle("��ѡ��Ҫ���ܵ��ļ�...");
			fileChooser.setApproveButtonText("ȷ��");
			fileChooser.showOpenDialog(this);// ��ʾ�򿪵��ļ��Ի���
			File f = fileChooser.getSelectedFile();// ʹ���ļ����ȡѡ����ѡ����ļ�
			if (f != null) {
				String s = f.getAbsolutePath();// ����·����
				txtFileDecrypt.setText(s);
				consoleArea.setText("��ǰѡ���ļ���СԼ");
				if( f.length() > 1024 ) {
					consoleArea.append(f.length()/1024 + "KB\r\n");
				}else {
					consoleArea.append(f.length() + "B\r\n");
				}
			}
		}else if (e.getSource() == btnEncrypt) {
			String.valueOf(cbMethodEncrypt.getSelectedItem());
			new EncryptThread(this, btnEncrypt, btnDecrypt, txtFileEncrypt, cbMethodEncrypt, txtKeyEncrypt, consoleArea).start();
		}else if (e.getSource() == btnDecrypt) {
			new DecryptThread(this, btnEncrypt, btnDecrypt, txtFileDecrypt, cbMethodDecrypt, txtKeyDecrypt, consoleArea).start();
		}
	}
}
