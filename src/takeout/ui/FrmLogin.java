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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import StartTakeout.takeOutUtil;
import takeout.model.Admin;
import takeout.model.Business;
import takeout.model.Deliver;
import takeout.model.User;
import takeout.ui.FrmRegister;
import takeout.util.BaseException;


public class FrmLogin extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private JPanel workBar = new JPanel();
	private JButton btnLogin = new JButton("登陆");
//	private JButton btnCancel = new JButton("退出");
	private JButton btnRegister = new JButton("注册账户");
	private JButton btnBack = new JButton("返回上一步");
	
	private JRadioButton btnAdmin = new JRadioButton("管理员");
	private JRadioButton btnUser = new JRadioButton("用户");
	private JRadioButton btnBus = new JRadioButton("商家");
	private JRadioButton btnDeliver = new JRadioButton("骑手");

	private ButtonGroup btnGroup = new ButtonGroup();
	
	private JLabel labelUser = new JLabel("用户：");
	private JLabel labelPwd = new JLabel("密码：");
	private JTextField edtUserId = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	
//	private FrmChoice fs =null;
	private int model = 1;//1:管理员, 2:用户,3:商家，4：骑手
	
	public FrmLogin(FrmMain f, String s, boolean b) {
		super(f,s,b);
		btnGroup.add(btnAdmin);
		btnGroup.add(btnUser);
		btnGroup.add(btnBus);
		btnGroup.add(btnDeliver);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnRegister);
		toolBar.add(btnLogin);
//		toolBar.add(btnBack);
		toolBar.add(btnAdmin);
		toolBar.add(btnUser);
		toolBar.add(btnBus);
		toolBar.add(btnDeliver);
//		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workBar.add(labelUser);
		workBar.add(edtUserId);
		workBar.add(labelPwd);
		labelPwd.setLocation(30, 30);
		workBar.add(edtPwd);
		this.getContentPane().add(workBar, BorderLayout.CENTER);
		this.setSize(600,150);
//		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//		this.setLocation((int)(width/2), (int)(height/2));
		this.setLocationRelativeTo(null);
		
		btnAdmin.addActionListener(this);
		btnUser.addActionListener(this);
		btnLogin.addActionListener(this);
//		btnCancel.addActionListener(this);
		btnBack.addActionListener(this);
		btnBus.addActionListener(this);
		btnDeliver.addActionListener(this);
		this.btnRegister.addActionListener(this);
		
		btnAdmin.setSelected(true);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	

	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnLogin) {
			
			if(model == 1) {
				String userid=this.edtUserId.getText();
				String pwd=new String(this.edtPwd.getPassword());
				try {
					Admin.currentLoginUser= takeOutUtil.adminManager.login(userid, pwd);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				this.setVisible(false);
			}else if(model == 2) {
				String userid=this.edtUserId.getText();
				String pwd=new String(this.edtPwd.getPassword());
				try {
					User.currentLoginUser= takeOutUtil.userManager.login(userid, pwd);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				this.setVisible(false);
			}else if(model == 3) {
				String userid=this.edtUserId.getText();
				String pwd=new String(this.edtPwd.getPassword());
				try {
					Business.currentLoginBusiness= takeOutUtil.businessManager.login(userid, pwd);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				this.setVisible(false);

			}else if(model == 4) {
				String userid=this.edtUserId.getText();
				String pwd=new String(this.edtPwd.getPassword());
				try {
					Deliver.currentLoginDeliver= takeOutUtil.deliverManager.login(userid, pwd);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				this.setVisible(false);
				
				
			}else {
				JOptionPane.showMessageDialog(null, "请选择你的身份！！！", "错误",JOptionPane.ERROR_MESSAGE);

			}
			
//			this.fs.setVisible(false);
//			fs.setVisible(false);
			
//			FrmMain dlg = new FrmMain();
//			dlg.setVisible(true);
			
			
		} 
//		else if (e.getSource() == this.btnCancel) {
//			System.exit(0);
//		}
		else if(e.getSource()==this.btnRegister){
			
			
			FrmRegister dlg=new FrmRegister(this,"注册",true, model);
			dlg.setVisible(true);
			
			
		}
//			else if(e.getSource()==this.btnBack) {
//			fs.setVisible(true);
//			this.setVisible(false);
//		}
		else if(e.getSource()==this.btnAdmin) {
			this.model = 1;
			
		}else if(e.getSource()==this.btnUser) {
			this.model = 2;
			
		}else if(e.getSource()==this.btnBus) {
			this.model = 3;
		}else if(e.getSource()==this.btnDeliver) {
			this.model = 4;
		}
		
		
	}
	
	
	
}
