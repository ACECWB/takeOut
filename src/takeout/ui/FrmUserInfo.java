package takeout.ui;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import takeout.model.*;
import takeout.util.BaseException;
import takeout.control.*;
public class FrmUserInfo extends JDialog implements ActionListener {
	
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private Button btnVIP = new Button("成为VIP");

	private JLabel labelUserId = new JLabel("用户账号：");
	private JLabel labelUserName = new JLabel("用户名：");

	private JLabel labelSex = new JLabel("性别：");
	private JLabel labelPhone = new JLabel("绑定手机号码：");
	private JLabel labelEmail = new JLabel("绑定邮箱：");
	private JLabel labelCity = new JLabel("所在城市：");
	private JLabel labelIsVip = new JLabel("是否为VIP：");
	private JLabel labelStart = new JLabel("VIP开始时间：");
	
	private JLabel labelEnd = new JLabel("VIP结束时间：");
	
//	private JLabel labelVip = new JLabel("成为VIP");

	private JComboBox cmbUserSex= new JComboBox(new String[] { "男", "女", "无"});

	private JTextField edtUserId = new JTextField(20);
	private JTextField edtUserName = new JTextField(20);
	private JTextField edtPhone = new JTextField(20);
	private JTextField edtEmail = new JTextField(20);
	private JTextField edtCity = new JTextField(20);
	private JTextField edtIsVip = new JTextField(20);
	private JTextField edtStart = new JTextField(20);
	private JTextField edtEnd = new JTextField(20);
	 

	
	public FrmUserInfo(FrmMain f, String s, boolean b) {
		super(f, s, b);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		if(User.currentLoginUser.getIsVip() == 1)
			btnVIP.setLabel("VIP续费");
		
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnVIP);
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUserId); workPane.add(edtUserId);
		edtUserId.setText(User.currentLoginUser.getUserId());
		edtUserId.setEditable(false);
		workPane.add(labelUserName); workPane.add(edtUserName);
		edtUserName.setText(User.currentLoginUser.getUserName());
		workPane.add(labelSex); workPane.add(cmbUserSex);
		cmbUserSex.setSelectedItem(User.currentLoginUser.getSex());
		workPane.add(labelPhone); workPane.add(edtPhone);
		edtPhone.setText(User.currentLoginUser.getPhone());
		workPane.add(labelEmail); workPane.add(edtEmail);
		edtEmail.setText(User.currentLoginUser.getEmail());
		workPane.add(labelCity); workPane.add(edtCity);
		edtCity.setText(User.currentLoginUser.getCity());
		workPane.add(labelIsVip); workPane.add(edtIsVip);
		edtIsVip.setText(""+(User.currentLoginUser.getIsVip()==1?"是":"否"));
		edtIsVip.setEditable(false);
		workPane.add(labelStart); workPane.add(edtStart);
		edtStart.setText(User.currentLoginUser.getVipStartTime()==null?"无":sdf.format(User.currentLoginUser.getVipStartTime()));
		edtStart.setEditable(false);
		workPane.add(labelEnd); workPane.add(edtEnd);
		edtEnd.setText(User.currentLoginUser.getVipEndTime()==null?"无":sdf.format(User.currentLoginUser.getVipEndTime()));
		edtEnd.setEditable(false);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(250, 500);

		this.setLocationRelativeTo(null);
		
		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.btnVIP.addActionListener(this);
//		this.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				FrmUserInfo.this.location=null;
//			}
//		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			try {
				User user = new User();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				user.setUserId(User.currentLoginUser.getUserId());
				user.setPwd(User.currentLoginUser.getPwd());
				user.setUserName(edtUserName.getText().toString());
				user.setSex(cmbUserSex.getSelectedItem().toString());
				user.setCity(edtCity.getText().toString());
				user.setEmail(edtEmail.getText().toString());
				user.setPhone(edtPhone.getText().toString());
				user.setIsVip((edtIsVip.getText().toString()).equals("是")?1:0);
				user.setVipStartTime(User.currentLoginUser.getVipStartTime());
				user.setVipEndTime(User.currentLoginUser.getVipEndTime());
				
				(new UserManager()).modifyUser(user);
				this.setVisible(false);
			}catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);

			}
			
			
		}else if(e.getSource()==this.btnVIP) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			FrmBeVIP fbv = new FrmBeVIP(this, btnVIP.getLabel(),true);
			fbv.setVisible(true);
			edtIsVip.setText(""+(User.currentLoginUser.getIsVip()==1?"是":"否"));
			edtStart.setText(User.currentLoginUser.getVipStartTime()==null?"无":sdf.format(User.currentLoginUser.getVipStartTime()));
			edtEnd.setText(User.currentLoginUser.getVipEndTime()==null?"无":sdf.format(User.currentLoginUser.getVipEndTime()));

			
		}
		
	}
	
	
}
