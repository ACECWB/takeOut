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
import takeout.control.CommodityManager;
import takeout.model.ComCate;

import java.text.SimpleDateFormat;

public class FrmCommodityManager_AddCommodity_2 extends JDialog implements ActionListener{
	private ComCate comcate = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelCateName = new JLabel("商品名称：");
	private JLabel labelCateId = new JLabel("类别编号：");

	
	private JTextField edtCateId = new JTextField(20);
	private JTextField edtCateName = new JTextField(20);

	public FrmCommodityManager_AddCommodity_2(FrmMain f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelCateId); workPane.add(edtCateId);
		workPane.add(labelCateName); workPane.add(edtCateName);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(350, 120);
		this.setLocationRelativeTo(null);
		
		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
	}
	public FrmCommodityManager_AddCommodity_2(FrmCommodityManager_2 f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelCateId); workPane.add(edtCateId);
		workPane.add(labelCateName); workPane.add(edtCateName);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(500, 180);
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
			
			String cateid = this.edtCateId.getText();
			String catename = this.edtCateName.getText();
			
			comcate=new ComCate();
			comcate.setCategoryId(cateid);;
			comcate.setCategoryName(catename);;
			
			try {
				(new CommodityManager()).addComCate(comcate);
				this.setVisible(false);
				
			} catch (BaseException e1) {
				this.comcate=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	public ComCate getComTitle() {
		return comcate;
	}

	
	
	
}
