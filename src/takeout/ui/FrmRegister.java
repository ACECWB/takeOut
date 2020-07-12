package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import StartTakeout.takeOutUtil;
import takeout.model.Admin;
import takeout.model.Business;
import takeout.model.Deliver;
import takeout.model.User;
import takeout.util.BaseException;


public class FrmRegister extends JDialog implements ActionListener {
	
	private int model = 0;//1������Ա��2���û�
	private JPanel toolBar = new JPanel();
	private JPanel workPane1 = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ע��");
	private Button btnCancel = new Button("ȡ��");
	
	private JLabel labelUser = new JLabel("�û��˺ţ�");
	private JLabel labelUserName = new JLabel("�û�����");
	private JLabel labelSex = new JLabel("�Ա�");
	private JLabel labelPhone = new JLabel("���ֻ����룺");
	private JLabel labelEmail = new JLabel("�����䣺");
	private JLabel labelCity = new JLabel("���ڳ��У�");
	private JComboBox cmbSex= new JComboBox(new String[] { "��", "Ů", "��"});

	private JLabel labelBusName = new JLabel("�̼�����");
	private JLabel labelBusId = new JLabel("�̼ұ�ţ�");

	private JLabel labelDeliverName = new JLabel("����������");
	private JLabel labelDeliverId = new JLabel("���ֱ�ţ�");

	private JLabel labelAdmin = new JLabel("����Ա�˺ţ�");
	private JLabel labelAdminName = new JLabel("����Ա������");
	private JLabel labelPwd = new JLabel("�������룺");
	private JLabel labelPwd2 = new JLabel("ȷ�����룺");
	
	private JTextField edtPhone = new JTextField(20);
	private JTextField edtCity = new JTextField(20);
	private JTextField edtEmail = new JTextField(20);

	private JTextField edtUserId = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	private JPasswordField edtPwd2 = new JPasswordField(20);
	
	public FrmRegister(Dialog f, String s, boolean b, int m) {
		super(f, s, b);
		model = m;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		if(m == 1) {//����Ա
			workPane.add(labelAdmin); workPane.add(edtUserId);
			workPane.add(labelAdminName); workPane.add(edtName);
			workPane.add(labelPwd); workPane.add(edtPwd);
			workPane.add(labelPwd2); workPane.add(edtPwd2);
			this.getContentPane().add(workPane, BorderLayout.CENTER);
//			this.getContentPane().add(workPane1, BorderLayout.CENTER);
			this.setSize(320, 200);
		}else if(m == 2) {//�û�
			workPane.add(labelUser); workPane.add(edtUserId);
			workPane.add(labelUserName); workPane.add(edtName);
			workPane.add(labelSex); workPane.add(cmbSex);
			workPane.add(labelPhone); workPane.add(edtPhone);
			workPane.add(labelEmail); workPane.add(edtEmail);
			workPane.add(labelCity); workPane.add(edtCity);
			workPane.add(labelPwd); workPane.add(edtPwd);
			workPane.add(labelPwd2); workPane.add(edtPwd2);
			this.getContentPane().add(workPane, BorderLayout.CENTER);
//			this.getContentPane().add(workPane1, BorderLayout.CENTER);
			this.setSize(320, 500);

		}else if(m == 3) {//�̼�
			workPane.add(labelBusId); workPane.add(edtUserId);
			workPane.add(labelBusName); workPane.add(edtName);
			workPane.add(labelPwd); workPane.add(edtPwd);
			workPane.add(labelPwd2); workPane.add(edtPwd2);
			this.getContentPane().add(workPane, BorderLayout.CENTER);
//			this.getContentPane().add(workPane1, BorderLayout.CENTER);
			this.setSize(320, 500);
			
		}else if(m == 4) {//����
			workPane.add(labelDeliverId); workPane.add(edtUserId);
			workPane.add(labelDeliverName); workPane.add(edtName);
			workPane.add(labelPwd); workPane.add(edtPwd);
			workPane.add(labelPwd2); workPane.add(edtPwd2);
			this.getContentPane().add(workPane, BorderLayout.CENTER);
//			this.getContentPane().add(workPane1, BorderLayout.CENTER);
			this.setSize(320, 500);
		}
		
		
		this.setLocationRelativeTo(null);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnOk){
			String userid=this.edtUserId.getText();
			String username=this.edtName.getText();
			String pwd1=new String(this.edtPwd.getPassword());
			String pwd2=new String(this.edtPwd2.getPassword());
			if(model == 1) {
				try {
					Admin admin=takeOutUtil.adminManager.reg(userid,username,pwd1,pwd2);
					this.setVisible(false);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}else if(model == 2) {
				
				if(!pwd1.equals(pwd2)) {
					JOptionPane.showMessageDialog(null, "�������벻һ�£�����","����",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				User user = new User();
				user.setSex(this.cmbSex.getSelectedItem().toString());
				user.setUserId(userid);
				user.setUserName(username);
				user.setPwd(pwd1);
				user.setPhone(this.edtPhone.getText().toString());
				user.setCity(this.edtCity.getText().toString());
				user.setEmail(this.edtEmail.getText().toString());

				try {
					takeOutUtil.userManager.reg(user);
					this.setVisible(false);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}else if(model == 3) {
				if(!pwd1.equals(pwd2)) {
					JOptionPane.showMessageDialog(null, "�������벻һ�£�����","����",JOptionPane.ERROR_MESSAGE);
					return;
				}
				Business bus = new Business();
				bus.setBusinessId(userid);
				bus.setBusinessName(username);
				bus.setPwd(pwd1);
				try {
					Business b=takeOutUtil.businessManager.reg(bus);
					this.setVisible(false);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
					return;
				}

		}else if(model == 4) {
			if(!pwd1.equals(pwd2)) {
				JOptionPane.showMessageDialog(null, "�������벻һ�£�����","����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			Deliver d = new Deliver();
			d.setDeliverName(username);
			d.setDeliverId(userid);
			d.setPwd(pwd1);
			
			try {
				d = takeOutUtil.deliverManager.reg(d);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
		}
			
		
	}


}}
