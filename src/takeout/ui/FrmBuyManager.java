package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import takeout.util.*;
import takeout.control.CartManager;
import takeout.model.Business;
import takeout.model.Cart;
import takeout.model.Order;
import takeout.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmBuyManager extends JDialog implements ActionListener{
	private Cart cart = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private int row;
	private FrmMain fm;
	private FrmCartManager fcm = null;
//	public String businessid;
//	public String comid;
//	public float price;
//	public String comname;
//	public String businessname;
	String afterFull = null;
	String afterCoupon = null;
	String [] coupons = null;
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelOrigin = new JLabel("原价：");
	private JLabel labelAfterCoupon = new JLabel("使用优惠券后价格(结算价格)：");
	private JLabel labelAfterFull = new JLabel("满减后价格：");
	
	private JLabel labelCoupon = new JLabel("使用优惠券：");
	private JLabel labelLoc = new JLabel("地址联系信息：");
	private JLabel labelTime = new JLabel("要求送达时间：");

	private JComboBox cmbCoupon=null;

	private String businessid = null;
	private JTextField edtOrigin = new JTextField(20);
	private JTextField edtAfterCoupon = new JTextField(20);
	private JTextField edtAfterFull = new JTextField(20);
	private JTextField edtLoc = new JTextField(20);
	private JTextField edtTime = new JTextField(20);

	public FrmBuyManager(FrmCartManager frmCartManager, String s, boolean b, String businessid, int row, FrmMain fm) {
		super(frmCartManager, s, b);
		this.businessid = businessid; 
		this.fcm = frmCartManager;
		this.row = row;
		this.fm = fm;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelOrigin); workPane.add(edtOrigin);
		try {
			edtOrigin.setText((new CartManager().getOrigin(User.currentLoginUser.getUserId(), businessid)));
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		edtOrigin.setEditable(false);
		
		workPane.add(labelAfterFull); workPane.add(edtAfterFull);
		try {
			afterFull = (new CartManager().getAfterFull(User.currentLoginUser.getUserId(), businessid));
		} catch (BaseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		edtAfterFull.setText(afterFull);
		
		
		try {
			coupons = (new CartManager()).getCoupons(User.currentLoginUser.getUserId(), businessid);
			for(String ss:coupons) {
				System.out.println(ss);
			}
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmbCoupon = new JComboBox(coupons);
		cmbCoupon.setSelectedIndex(0);
		
		edtAfterFull.setEditable(false);
		workPane.add(labelCoupon);
		workPane.add(cmbCoupon);
		workPane.add(labelAfterCoupon); workPane.add(edtAfterCoupon);
		workPane.add(labelLoc); workPane.add(edtLoc);
		workPane.add(labelTime); workPane.add(edtTime);

		System.out.println("select:"+this.cmbCoupon.getSelectedItem().toString());
		try {
			afterCoupon = (new CartManager()).getAfterCoupon(this.cmbCoupon.getSelectedItem().toString(), afterFull);
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		edtAfterCoupon.setText(afterCoupon);
		edtAfterCoupon.setEditable(false);
		
		
		cmbCoupon.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED) {
					try {
						afterCoupon = (new CartManager()).getAfterCoupon(cmbCoupon.getSelectedItem().toString(), afterFull);
					} catch (BaseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					edtAfterCoupon.setText(afterCoupon);

				}
			}
		});
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(250, 350);
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
			

			Order o = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			Date date = new Date();
			o.setOrderid(sdf.format(date));
			o.setUserid(User.currentLoginUser.getUserId());
			o.setLocaid(this.edtLoc.getText().toString());
			if(this.edtTime.getText().toString() == null || "".equals(this.edtTime.getText().toString())) {
				JOptionPane.showMessageDialog(null, "要求送达时间不可为空！！！","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				o.setReqtime(sdf1.parse(this.edtTime.getText().toString()));
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				JOptionPane.showMessageDialog(null, "请输入正确的时间！！！","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			o.setBusinessid(businessid);
			o.setCouponid(this.cmbCoupon.getSelectedItem().toString());
			o.setOriginamount(Float.parseFloat(this.edtOrigin.getText().toString()));
			o.setFinalamount(Float.parseFloat(this.edtAfterCoupon.getText().toString()));
			o.setOrderTime(date);
			o.setStatus("等待商家处理");
			
			
			try {
				(new CartManager()).purchase(o);
				this.setVisible(false);
				
			} catch (BaseException e1) {
				this.cart=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			fcm.reloadBusinessTable();
			this.fm.reloadComTable(row);
		}
		
	}

	public Cart getBusiness() {
		return cart;
	}

	
	
	
}
