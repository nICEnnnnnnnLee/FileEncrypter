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
	JButton btnEncrypt = new JButton("加密");
	JButton btnDecrypt = new JButton("解密");
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

		// 设置窗口名称
		this.setTitle("文件加密器");
		// 设置窗口大小
		this.setSize(860, 427);
		this.setResizable(false);
		// 设置窗口位于屏幕中央
		this.setLocationRelativeTo(null);
		// 参数为3时，表示关闭窗口则程序退出
		this.setDefaultCloseOperation(3);
		URL iconURL=this.getClass().getResource("/resources/favicon.png");
		ImageIcon icon=new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());

		// 此处使用流式布局FlowLayout，流式布局类似于word的布局
		FlowLayout f1 = new FlowLayout(FlowLayout.LEFT);
		// this窗口设置为f1的流式左对齐
		this.setLayout(f1);

		JLabel tmp0 = new JLabel();
		// 设置空JLabel长度大小.
		tmp0.setPreferredSize(new Dimension(130, 90));
		// 将空JLabel添加入窗口
		this.add(tmp0);
		
		URL fileURL=this.getClass().getResource("/resources/title.png"); 
		ImageIcon imag1 = new ImageIcon(fileURL);
		JLabel pic1 = new JLabel(imag1, SwingConstants.LEFT);
		pic1.setPreferredSize(new Dimension(630, 90));
		// 将创建的图片对象添加到 窗口上
		this.add(pic1);

		// 创建一个空的JLabel，它的长度宽度为110,30
		JLabel name1 = new JLabel();
		// 设置空JLabel长度大小
		name1.setPreferredSize(new Dimension(90, 30));
		// 将空JLabel添加入窗口
		this.add(name1);

		JLabel name = new JLabel("加密文件：");
		name.setPreferredSize(new Dimension(70, 30));
		this.add(name);

		// JTextField在窗口上添加一个可输入可见文本的文本框，需要添加的包名为javax.swing.JTextField.
		// 设置文本框大小
		txtFileEncrypt.setPreferredSize(new Dimension(220, 30));
		// 添加到窗口上
		this.add(txtFileEncrypt);

		// JButton创建一个可点击的按钮
		btnEncryptFileChooser.addActionListener(this);
		btnEncryptFileChooser.setPreferredSize(new Dimension(20, 30));
		this.add(btnEncryptFileChooser);
		
		JLabel tl0 = new JLabel("方法:");
		tl0.setPreferredSize(new Dimension(30, 30));
		this.add(tl0);
		this.add(cbMethodEncrypt);
		
		JLabel tl1 = new JLabel("密钥: ");
		tl1.setPreferredSize(new Dimension(50, 30));
		this.add(tl1);
		
		txtKeyEncrypt.setPreferredSize(new Dimension(130, 30));
		txtKeyEncrypt.setText("3.141592653");
		this.add(txtKeyEncrypt);
		
		btnEncrypt.addActionListener(this);
		btnEncrypt.setPreferredSize(new Dimension(60, 30));
		this.add(btnEncrypt);
		
		// 同name1
		JLabel name2 = new JLabel();
		name2.setPreferredSize(new Dimension(60, 30));
		this.add(name2);

		// 同name1
		JLabel name3 = new JLabel();
		name3.setPreferredSize(new Dimension(90, 30));
		this.add(name3);

		// 同name
		JLabel password = new JLabel("解密文件：");
		password.setPreferredSize(new Dimension(70, 30));
		this.add(password);
		
		txtFileDecrypt.setPreferredSize(new Dimension(220, 30));
		this.add(txtFileDecrypt);
		
		btnDecryptFileChooser.addActionListener(this);
		btnDecryptFileChooser.setPreferredSize(new Dimension(20, 30));
		this.add(btnDecryptFileChooser);
		
		JLabel tl3 = new JLabel("方法:");
		tl3.setPreferredSize(new Dimension(30, 30));
		this.add(tl3);
		this.add(cbMethodDecrypt);
		JLabel tl2 = new JLabel("密钥: ");
		tl2.setPreferredSize(new Dimension(50, 30));
		this.add(tl2);
		
		txtKeyDecrypt.setPreferredSize(new Dimension(130, 30));
		txtKeyDecrypt.setText("3.141592653");
		this.add(txtKeyDecrypt);
		
		btnDecrypt.addActionListener(this);
		btnDecrypt.setPreferredSize(new Dimension(60, 30));
		this.add(btnDecrypt);
		// 同name1
		JLabel name4 = new JLabel();
		name4.setPreferredSize(new Dimension(20, 30));
		this.add(name4);

		// 同name1
		JLabel name5 = new JLabel();
		name5.setPreferredSize(new Dimension(100, 30));
		this.add(name5);
		consoleArea.setEditable(false);
		JScrollPane js=new JScrollPane(consoleArea);
		//分别设置水平和垂直滚动条出现方式
		js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(js);
		
		// 设置窗口可见，此句一定要在窗口属性设置好了之后才能添加，不然无法正常显示
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEncryptFileChooser) {
			JFileChooser fileChooser = new JFileChooser(".");
			fileChooser.setDialogTitle("请选择要加密的文件...");
			fileChooser.setApproveButtonText("确定");
			fileChooser.showOpenDialog(this);// 显示打开的文件对话框
			File f = fileChooser.getSelectedFile();// 使用文件类获取选择器选择的文件
			if (f != null) {
				String s = f.getAbsolutePath();// 返回路径名
				txtFileEncrypt.setText(s);
				consoleArea.setText("当前选择文件大小约");
				if( f.length() > 1024 ) {
					consoleArea.append(f.length()/1024 + "KB\r\n");
				}else {
					consoleArea.append(f.length() + "B\r\n");
				}
			}
		} else if (e.getSource() == btnDecryptFileChooser) {
			JFileChooser fileChooser = new JFileChooser(".");
			fileChooser.setDialogTitle("请选择要加密的文件...");
			fileChooser.setApproveButtonText("确定");
			fileChooser.showOpenDialog(this);// 显示打开的文件对话框
			File f = fileChooser.getSelectedFile();// 使用文件类获取选择器选择的文件
			if (f != null) {
				String s = f.getAbsolutePath();// 返回路径名
				txtFileDecrypt.setText(s);
				consoleArea.setText("当前选择文件大小约");
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
