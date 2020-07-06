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
import takeout.control.*;
import takeout.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FrmAddCoupon extends JDialog implements ActionListener{
	private Coupon coupon = null;
	public String id = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	
	private JLabel labelCouponId = new JLabel("�Ż�ȯ��ţ�");
	private JLabel labelDiscount = new JLabel("�Żݽ�");
	private JLabel labelRequire = new JLabel("����Ҫ������");
	private JLabel labelStart = new JLabel("���ʼʱ�䣺");
	private JLabel labelEnd = new JLabel("�����ʱ�䣺");
	private JLabel labelEffect = new JLabel("�Ż�ȯ��Чʱ�䣺");

	
	private JLabel labelBusinessId = new JLabel("�̼ұ�ţ�");
	private JLabel labelInEffect = new JLabel("��Ч��ֹʱ�䣺");

	
	private JTextField edtCouponId = new JTextField(20);
	private JTextField edtDiscount = new JTextField(20);
	private JTextField edtRequire = new JTextField(20);
	private JTextField edtStart = new JTextField(20);
	private JTextField edtEnd = new JTextField(20);
	private JTextField edtEffect = new JTextField(20);
	private JTextField edtBusinessId = new JTextField(20);
	private JTextField edtInEffect = new JTextField(20);
	

	private int model = 0;
	
//	public FrmAddCoupon(JDialog f, String s, boolean b) {
//		super(f, s, b);
//		model = 1;
//		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
//		toolBar.add(btnOk);
//		toolBar.add(btnCancel);
//		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
//		
//		workPane.add(labelCommodityId); workPane.add(edtCommodityId);
//		workPane.add(labelCommodityName); workPane.add(edtCommodityName);
//		workPane.add(labelCommodityCate); workPane.add(edtCommodityCate);
//		
//		this.getContentPane().add(workPane, BorderLayout.CENTER);
//		this.setSize(500, 180);
//		this.setLocationRelativeTo(null);
//		
//		this.validate();
//		this.btnOk.addActionListener(this);
//		this.btnCancel.addActionListener(this);
//	}
//	public FrmAddCoupon(FrmMain f, String s, boolean b) {
//		super(f, s, b);
//		model = 2;
//		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
//		toolBar.add(btnOk);
//		toolBar.add(btnCancel);
//		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
//		
//		workPane.add(labelCommodityId); workPane.add(edtCommodityId);
//		workPane.add(labelCommodityName); workPane.add(edtCommodityName);
////		workPane.add(labelCommodityCate); workPane.add(edtCommodityCate);
//		
//		this.getContentPane().add(workPane, BorderLayout.CENTER);
//		this.setSize(500, 180);
//		this.setLocationRelativeTo(null);
//		
//		this.validate();
//		this.btnOk.addActionListener(this);
//		this.btnCancel.addActionListener(this);
//	}
	public FrmAddCoupon(FrmMain f, String s, boolean b,int model) {
		super(f, s, b);
		this.model = model;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		if(model == 3) {
			workPane.add(labelCouponId); workPane.add(edtCouponId);
			workPane.add(labelDiscount); workPane.add(edtDiscount);
			workPane.add(labelRequire); workPane.add(edtRequire);
			workPane.add(labelStart); workPane.add(edtStart);
			workPane.add(labelEnd); workPane.add(edtEnd);
			workPane.add(labelEffect); workPane.add(edtEffect);

		}else if(model == 4) {
			workPane.add(labelBusinessId); workPane.add(edtBusinessId);
			workPane.add(labelCouponId); workPane.add(edtCouponId);
			workPane.add(labelInEffect); workPane.add(edtInEffect);
		}
		
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(500, 380);
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
			System.out.println(this.model);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			coupon = new Coupon();
			if(model == 3) {
				coupon.setBusinessId(this.id);
				coupon.setCouponId(this.edtCouponId.getText());
				if("".equals(this.edtDiscount.getText())) {
					JOptionPane.showMessageDialog(null, "�������Żݽ�����", "����",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if("".equals(this.edtRequire.getText())) {
					JOptionPane.showMessageDialog(null, "������Ҫ�󶩵���������", "����",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if("".equals(this.edtStart.getText())) {
					JOptionPane.showMessageDialog(null, "��������ʼʱ�䣡����", "����",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if("".equals(this.edtEnd.getText())) {
					JOptionPane.showMessageDialog(null, "����������ʱ�䣡����", "����",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if("".equals(this.edtEffect.getText())) {
					JOptionPane.showMessageDialog(null, "�������Ż�ȯ��Чʱ�䣡����", "����",JOptionPane.ERROR_MESSAGE);
					return;
				}
				coupon.setDiscountMoney(Float.parseFloat(this.edtDiscount.getText()));
				

				coupon.setNeedOrders(Integer.parseInt(this.edtRequire.getText()));
				
				try {
					coupon.setStartTime(sdf.parse(this.edtStart.getText()));
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					coupon.setEndTime(sdf.parse(this.edtEnd.getText()));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				coupon.setEffectDays(Integer.parseInt(this.edtEffect.getText()));
				
			}else if(model == 4) {
				coupon.setUserId(this.id);
				coupon.setBusinessId(this.edtBusinessId.getText());
				coupon.setCouponId(this.edtCouponId.getText());
				
				if("".equals(this.edtInEffect.getText())) {
					JOptionPane.showMessageDialog(null, "��������Чʱ�䣡����", "����",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {
					coupon.setIneffectDate(sdf.parse(this.edtInEffect.getText()));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
			try {
				if(model == 3) {
					(new CouponManager()).addBCoupon(coupon);
					this.setVisible(false);
				}else if(model == 4) {
					(new CouponManager()).addCCoupon(coupon);
					this.setVisible(false);
				}
				
				
			} catch (BaseException e1) {
				this.coupon=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	public Coupon getCoupon() {
		return coupon;
	}

	
	
	
}
