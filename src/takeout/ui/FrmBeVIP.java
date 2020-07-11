package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import takeout.util.*;
import takeout.control.CartManager;
import takeout.control.UserManager;
import takeout.model.Business;
import takeout.model.Cart;
import takeout.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FrmBeVIP extends JDialog implements ActionListener{
	
	private JPanel toolBar = new JPanel();
	private JPanel toolBar1 = new JPanel();

	private JPanel workPane = new JPanel();
	
	private int month = 1;
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");

//	private JTextField edtCounts = new JTextField(20);
	private JRadioButton btnOneMonth = new JRadioButton("一个月  $15");
	private JRadioButton btnThreeMonth = new JRadioButton("三个月 $40");
	private JRadioButton btnSixMonth = new JRadioButton("六个月 $75");
	private JRadioButton btnYear = new JRadioButton("一年 $130");
	private ButtonGroup btnGroup = new ButtonGroup();

	private JTextArea text = new JTextArea(5,10);
	
	public FrmBeVIP(FrmUserInfo frmUserInfo, String s, boolean b) {
		super(frmUserInfo, s, b);
		toolBar1.setLayout(new FlowLayout(FlowLayout.CENTER));

		btnGroup.add(btnOneMonth);
		btnGroup.add(btnThreeMonth);
		btnGroup.add(btnSixMonth);
		btnGroup.add(btnYear);
		toolBar1.add(btnOneMonth);
		toolBar1.add(btnThreeMonth);
		toolBar1.add(btnSixMonth);
		toolBar1.add(btnYear);
		this.getContentPane().add(toolBar1, BorderLayout.CENTER);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
//		workPane.add(labelCounts); workPane.add(edtCounts);
		workPane.add(text);
		text.setText("成为VIP的福利："+"\n"+"全场享受会员价");
		text.setEditable(false);
		this.getContentPane().add(workPane, BorderLayout.WEST);
		this.setSize(270, 210);
		this.setLocationRelativeTo(null);
		
		this.validate();
		btnOneMonth.addActionListener(this);
		btnThreeMonth.addActionListener(this);
		btnSixMonth.addActionListener(this);
		btnYear.addActionListener(this);
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		btnOneMonth.setSelected(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
				
			try {
				(new UserManager()).vip(month);
				
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
			this.setVisible(false);
		}else if(e.getSource()==this.btnOneMonth) {
			this.month = 1;
		}else if(e.getSource()==this.btnThreeMonth) {
			this.month = 3;
		}else if(e.getSource()==this.btnSixMonth) {
			this.month = 6;
		}else if(e.getSource()==this.btnYear) {
			this.month = 12;
		}
		
	}


	
	
}
