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
import takeout.control.DeliverManager;
import takeout.model.Deliver;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FrmDeliverManager_AddDeliver extends JDialog implements ActionListener{
	private Deliver deliver = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelDeliverId = new JLabel("骑手编号：");
	private JLabel labelDeliverName = new JLabel("骑手姓名：");
	private JLabel labelEmployTime = new JLabel("就职时间：");
	private JLabel labelIdentity = new JLabel("身份：");
	private JComboBox cmbDeliverIdentity= new JComboBox(new String[] { "兼职", "正式员工", "单王"});

	private JTextField edtDeliverId = new JTextField(20);
	private JTextField edtDeliverName = new JTextField(20);
	private JTextField edtEmployTime = new JTextField(20);
	
	public FrmDeliverManager_AddDeliver(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelDeliverId); workPane.add(edtDeliverId);
		workPane.add(labelDeliverName); workPane.add(edtDeliverName);
		workPane.add(labelEmployTime); workPane.add(edtEmployTime);
		workPane.add(labelIdentity); workPane.add(cmbDeliverIdentity);
		
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
			
			String deliverid = this.edtDeliverId.getText();
			String delivername = this.edtDeliverName.getText();
			String employtime = this.edtEmployTime.getText();
			String identity = this.cmbDeliverIdentity.getSelectedItem().toString();
			
			deliver=new Deliver();
			deliver.setDeliverId(deliverid);
			deliver.setDeliverName(delivername);
			deliver.setIdentity(identity);
			deliver.setEmployTime(employtime);
			
			try {
				(new DeliverManager()).addDeliver(deliver);
				this.setVisible(false);
				
			} catch (BaseException e1) {
				this.deliver=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	public Deliver getDeliver() {
		return deliver;
	}

	
	
	
}
