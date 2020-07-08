package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import StartTakeout.takeOutUtil;
import takeout.model.Admin;
import takeout.ui.FrmRegister;
import takeout.util.BaseException;


public class FrmLogin extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private JPanel workBar = new JPanel();
	private JButton btnLogin = new JButton("µÇÂ½");
	private JButton btnCancel = new JButton("ÍË³ö");
	private JButton btnRegister = new JButton("×¢²áÕË»§");
	private JButton btnBack = new JButton("·µ»ØÉÏÒ»²½");
	
	private JLabel labelUser = new JLabel("ÓÃ»§£º");
	private JLabel labelPwd = new JLabel("ÃÜÂë£º");
	private JTextField edtUserId = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	
	FrmChoice fs =null;
	
	public FrmLogin(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnRegister);
		toolBar.add(btnLogin);
		toolBar.add(btnBack);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workBar.add(labelUser);
		workBar.add(edtUserId);
		workBar.add(labelPwd);
		workBar.add(edtPwd);
		this.getContentPane().add(workBar, BorderLayout.CENTER);
		this.setSize(300,200);
//		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//		this.setLocation((int)(width/2), (int)(height/2));
		this.setLocationRelativeTo(null);
		
		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		btnBack.addActionListener(this);
		this.btnRegister.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public FrmLogin(FrmChoice f, String s, boolean b) {
		super(f, s, b);
		fs = f;
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnRegister);
		toolBar.add(btnLogin);
		toolBar.add(btnBack);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workBar.add(labelUser);
		workBar.add(edtUserId);
		workBar.add(labelPwd);
		workBar.add(edtPwd);
		this.getContentPane().add(workBar, BorderLayout.CENTER);
		this.setSize(300,200);
//		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//		this.setLocation((int)(width/2), (int)(height/2));
		this.setLocationRelativeTo(null);
		
		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		btnBack.addActionListener(this);
		this.btnRegister.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnLogin) {
			String userid=this.edtUserId.getText();
			String pwd=new String(this.edtPwd.getPassword());
			try {
				Admin.currentLoginUser= takeOutUtil.adminManager.login(userid, pwd);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "´íÎó",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
			FrmMain dlg = new FrmMain();
			dlg.setVisible(true);
			
			
		} else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		} else if(e.getSource()==this.btnRegister){
			FrmRegister dlg=new FrmRegister(this,"×¢²á",true);
			dlg.setVisible(true);
		}else if(e.getSource()==this.btnBack) {
			
			this.fs.setVisible(true);
			this.setVisible(false);
			
		}
		
		
		
	}
	
	
	
}
