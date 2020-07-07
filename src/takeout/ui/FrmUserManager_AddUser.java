package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import takeout.util.*;
import takeout.control.UserManager;
import takeout.model.User;

public class FrmUserManager_AddUser extends JDialog implements ActionListener{
	private User user = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelUsername = new JLabel("������");
	private JLabel labelUserId = new JLabel("�û���ţ�");
	private JLabel labelUserpwd = new JLabel("���룺");
	private JLabel labelUserSex = new JLabel("�Ա�");
	private JComboBox cmbUserSex= new JComboBox(new String[] { "��", "Ů", "��"});
	private JLabel labelPhone = new JLabel("���ֻ����룺");
	private JLabel labelEmail = new JLabel("������");
	private JLabel labelCity = new JLabel("���У�");
	
	private JTextField edtUserId = new JTextField(20);
	private JTextField edtUsername = new JTextField(20);
	private JTextField edtUserpwd = new JTextField(20);
	private JTextField edtPhone = new JTextField(20);
	private JTextField edtEmail = new JTextField(20);
	private JTextField edtCity = new JTextField(20);
	
	
	public FrmUserManager_AddUser(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelUserId); workPane.add(edtUserId);
		workPane.add(labelUsername); workPane.add(edtUsername);
		workPane.add(labelUserpwd); workPane.add(edtUserpwd);
		workPane.add(labelUserSex); workPane.add(cmbUserSex);
		workPane.add(labelPhone); workPane.add(edtPhone);
		workPane.add(labelEmail); workPane.add(edtEmail);
		workPane.add(labelCity); workPane.add(edtCity);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(600, 180);
		this.setLocationRelativeTo(null);
		
		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
	}
	
	public FrmUserManager_AddUser(FrmMain f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelUserId); workPane.add(edtUserId);
		workPane.add(labelUsername); workPane.add(edtUsername);
		workPane.add(labelUserpwd); workPane.add(edtUserpwd);
		workPane.add(labelUserSex); workPane.add(cmbUserSex);
		workPane.add(labelPhone); workPane.add(edtPhone);
		workPane.add(labelEmail); workPane.add(edtEmail);
		workPane.add(labelCity); workPane.add(edtCity);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(600, 180);
		this.setLocationRelativeTo(null);
		
		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			if(this.cmbUserSex.getSelectedIndex()<0){
				JOptionPane.showMessageDialog(null,  "��ѡ���Ա�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			String id = this.edtUserId.getText();
			String name=this.edtUsername.getText();
			String pwd = this.edtUserpwd.getText();
			String sex=this.cmbUserSex.getSelectedItem().toString();
			String city = this.edtCity.getText();
			String phone = this.edtPhone.getText();
			String email = this.edtEmail.getText();
			
			user=new User();
			user.setUserId(id);
			user.setUserName(name);
			user.setSex(sex);
			user.setPwd(pwd);
			user.setCity(city);
			user.setPhone(phone);
			user.setEmail(email);
			try {
				(new UserManager()).createUser(user);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.user=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public User getUser() {
		return user;
	}
	
}
