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
import takeout.control.BusinessManager;
import takeout.model.Business;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FrmBusinessManager_AddBusiness extends JDialog implements ActionListener{
	private Business business = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelBusinessId = new JLabel("商家编号：");
	private JLabel labelBusinessName = new JLabel("商家名称：");
//	private JLabel labelStars = new JLabel("商家星级：");

	private JTextField edtBusinessId = new JTextField(20);
	private JTextField edtBusinessName = new JTextField(20);
//	private JTextField edtStars = new JTextField(20);
	
	public FrmBusinessManager_AddBusiness(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelBusinessId); workPane.add(edtBusinessId);
		workPane.add(labelBusinessName); workPane.add(edtBusinessName);
//		workPane.add(labelStars); workPane.add(edtStars);
		
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
			
			String businessid = this.edtBusinessId.getText();
			String businessname = this.edtBusinessName.getText();
//			String stars = this.edtStars.getText();
			
			business=new Business();
			business.setBusinessId(businessid);
			business.setBusinessName(businessname);
//			business.setStars(Integer.parseInt(stars));
			
			try {
				(new BusinessManager()).addBusiness(business);
				this.setVisible(false);
				
			} catch (BaseException e1) {
				this.business=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	public Business getBusiness() {
		return business;
	}

	
	
	
}
