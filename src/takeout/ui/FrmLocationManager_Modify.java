package takeout.ui;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
public class FrmLocationManager_Modify extends JDialog implements ActionListener {
	private Location location=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelLocationid = new JLabel("地址编号：");
	private JLabel labelLocation = new JLabel("具体地址：");
	private JLabel labelPhone = new JLabel("手机号码：");
	private JLabel labelConnection = new JLabel("联系人：");

	private JTextField edtId = new JTextField(20);
	private JTextField edtLocation = new JTextField(20);
	private JTextField edtPhone = new JTextField(20);
	private JTextField edtConnection = new JTextField(20);

	
	public FrmLocationManager_Modify(JDialog f, String s, boolean b,Location l) {
		super(f, s, b);
		this.location=l;
		
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelLocationid);
		this.edtId.setEnabled(false);
		this.edtId.setText(""+this.location.getLocaId());
		workPane.add(edtId);
		workPane.add(labelLocation);
		this.edtLocation.setText(l.getLoca());
		workPane.add(edtLocation);
		workPane.add(labelPhone);
		this.edtPhone.setText(l.getPhone());
		workPane.add(edtPhone);
		workPane.add(labelConnection);
		this.edtConnection.setText(l.getConnUser());
		workPane.add(edtConnection);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 180);

		this.setLocationRelativeTo(null);
		
		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FrmLocationManager_Modify.this.location=null;
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.location=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			try {
				String loca = this.edtLocation.getText();
				String phone = this.edtPhone.getText();
				String con = this.edtConnection.getText();
				
				if(!loca.equals(location.getLoca())) {
					(new LocationManager()).modifyLoca(location.getLocaId(), loca);
				}
				if(!phone.equals(location.getPhone())) {
					(new LocationManager()).modifyPhone(location.getLocaId(), phone);
				}
				if(!con.equals(location.getConnUser())) {
					(new LocationManager()).modifyCon(location.getLocaId(), con);
				}
				
				this.setVisible(false);
			}catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);

			}
			
			
		}
		
	}
	public Location getLocation2() {
		return location;
	}
	
}
