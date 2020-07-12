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
import takeout.control.LocationManager;
import takeout.model.Location;
import takeout.model.User;

public class FrmLocationManager_AddLocation extends JDialog implements ActionListener{
	private Location location = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelUserId = new JLabel("用户编号：");
	private JLabel labelLocation = new JLabel("详细地址：");
	private JLabel labelPhone = new JLabel("手机号码：");
	private JLabel labelConnUser = new JLabel("联系人：");
	
	private JTextField edtUserId = new JTextField(20);
	private JTextField edtLocation = new JTextField(20);
	private JTextField edtPhone = new JTextField(20);
	private JTextField edtConnUser = new JTextField(20);	
	
	public FrmLocationManager_AddLocation(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		if(User.currentLoginUser == null) {
			workPane.add(labelUserId); workPane.add(edtUserId);

		}
		workPane.add(labelLocation); workPane.add(edtLocation);
		workPane.add(labelPhone); workPane.add(edtPhone);
		workPane.add(labelConnUser); workPane.add(edtConnUser);
		
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
			String userid;
			if(User.currentLoginUser==null) {
				userid = this.edtUserId.getText();
			}else {
				userid = User.currentLoginUser.getUserId();
			}
				
			String loca = this.edtLocation.getText();
			String connUser = this.edtConnUser.getText();
			String phone = this.edtPhone.getText();
			System.out.println(loca	);

			location=new Location();
			location.setUserId(userid);
			location.setLoca(loca);
			location.setConnUser(connUser);
			location.setPhone(phone);
			try {
				(new LocationManager()).addLocation(location);
				this.setVisible(false);
				
			} catch (BaseException e1) {
				this.location=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	public Location getLocation1() {
		return location;
	}

	
	
	
}
