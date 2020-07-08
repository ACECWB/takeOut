package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import takeout.control.AdminManager;
import takeout.control.UserManager;
import takeout.model.Admin;
import takeout.model.User;
import takeout.util.BaseException;



public class FrmModifyPwd extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	
	private int model = 0;//1:����Ա��2���û�
	
	private JLabel labelPwdOld = new JLabel("ԭ���룺");
	private JLabel labelPwd = new JLabel("�����룺");
	private JLabel labelPwd2 = new JLabel("�����룺");
	private JPasswordField edtPwdOld = new JPasswordField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	private JPasswordField edtPwd2 = new JPasswordField(20);
	public FrmModifyPwd(FrmMain f, String s, boolean b, int m) {
		super(f, s, b);
		model = m;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelPwdOld);
		workPane.add(edtPwdOld);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		workPane.add(labelPwd2);
		workPane.add(edtPwd2);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 180);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnOk){
			if(model == 1) {
				try {
					new AdminManager().changePwd(Admin.currentLoginUser, new String(edtPwdOld.getPassword()), new String(edtPwd.getPassword()), new String(edtPwd2.getPassword()));
					this.setVisible(false);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}else if(model == 2) {
				try {
					new UserManager().changeUserPwd(User.currentLoginUser.getUserId(), new String(edtPwdOld.getPassword()), new String(edtPwd.getPassword()), new String(edtPwd2.getPassword()));
					this.setVisible(false);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
		}
			
		
	}


}
