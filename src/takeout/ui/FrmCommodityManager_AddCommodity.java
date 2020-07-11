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
import takeout.model.Business;
import takeout.model.Commodity;

import java.text.SimpleDateFormat;

public class FrmCommodityManager_AddCommodity extends JDialog implements ActionListener{
	public Commodity commodity = null;
	public Business bus = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelCommodityId = new JLabel("商品编号：");
	private JLabel labelBusinessId = new JLabel("商家编号：");
	private JLabel labelCounts = new JLabel("数量：");
	private JLabel labelPrice = new JLabel("单价：");
	private JLabel labelVIPPrice = new JLabel("会员价：");

	
	private JTextField edtCommodityId = new JTextField(20);
	private JTextField edtBusinessId = new JTextField(20);
	private JTextField edtCounts = new JTextField(20);
	private JTextField edtPrice = new JTextField(20);
	private JTextField edtVIPPrice = new JTextField(20);

	public int model = 0;//页面启动模式
	
	public FrmCommodityManager_AddCommodity(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
		model = 1;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelCommodityId); workPane.add(edtCommodityId);
//		workPane.add(labelBusinessId); workPane.add(edtBusinessId);
		workPane.add(labelCounts); workPane.add(edtCounts);
		workPane.add(labelPrice); workPane.add(edtPrice);
		workPane.add(labelVIPPrice); workPane.add(edtVIPPrice);

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(400, 180);
		this.setLocationRelativeTo(null);
		
		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
	}
	
	public FrmCommodityManager_AddCommodity(FrmCommodityManager frmCommodityManager, String s, boolean b) {
		super(frmCommodityManager, s, b);
		model = 2;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelCommodityId); workPane.add(edtCommodityId);
		workPane.add(labelBusinessId); workPane.add(edtBusinessId);
		workPane.add(labelCounts); workPane.add(edtCounts);
		workPane.add(labelPrice); workPane.add(edtPrice);
		workPane.add(labelVIPPrice); workPane.add(edtVIPPrice);

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
			
			
			
			String commodityid = this.edtCommodityId.getText();
			String counts = this.edtCounts.getText();
			String price = this.edtPrice.getText();
			String vipprice = this.edtVIPPrice.getText();
			
			commodity=new Commodity();
			commodity.setComId(commodityid);
			
			String businessid = null;
			
			if(this.model==2) {
				businessid = this.edtBusinessId.getText();
			}else if(this.model==1){
				businessid = bus.getBusinessId();
			}
			commodity.setBusinessId(businessid);
			
			try{
				commodity.setEachPrice(Float.parseFloat(price));
				
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "请正确输入单价！！！","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			commodity.setVipprice(Float.parseFloat(vipprice));
			try {
				commodity.setCounts(Integer.parseInt(counts));
			}catch(Exception ei) {
				JOptionPane.showMessageDialog(null, "请正确输入数量！！！","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			try {
				(new CommodityManager()).addCommodity(commodity);
				this.setVisible(false);
				
			} catch (BaseException e1) {
				this.commodity=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	public Commodity getCommodity() {
		return commodity;
	}

	
	
	
}
