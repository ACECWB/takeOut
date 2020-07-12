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
import takeout.control.CartManager;
import takeout.model.Business;
import takeout.model.Cart;
import takeout.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmCartManager_Add extends JDialog implements ActionListener{
	private Cart cart = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	public String businessid;
	public String comid;
	public float price;
	public String comname;
	public String businessname;
	public int counts;
	public String comcateid;
	public String comcatename;
	public float vipprice;
	
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
//	private JLabel labelBusinessId = new JLabel("商家编号：");
	private JLabel labelCounts = new JLabel("数量：");
//	private JLabel labelStars = new JLabel("商家星级：");

//	private JTextField edtBusinessId = new JTextField(20);
	private JTextField edtCounts = new JTextField(20);
//	private JTextField edtStars = new JTextField(20);
	
	public FrmCartManager_Add(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
		this.setFocusable(true);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		

//		workPane.add(labelBusinessId); workPane.add(edtBusinessId);
		workPane.add(labelCounts); workPane.add(edtCounts);
//		workPane.add(labelStars); workPane.add(edtStars);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 180);
		this.setLocationRelativeTo(null);
		
		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
	}
	
	public FrmCartManager_Add(FrmSearchCommoditys frmSearchCommoditys, String s, boolean b) {
		// TODO Auto-generated constructor stub
		super(frmSearchCommoditys, s, b);
		this.setFocusable(true);

		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
//		workPane.add(labelBusinessId); workPane.add(edtBusinessId);
		workPane.add(labelCounts); workPane.add(edtCounts);
//		workPane.add(labelStars); workPane.add(edtStars);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 180);
		this.setLocationRelativeTo(null);
		
		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
	}

	public FrmCartManager_Add(FrmRecommend frmRecommend, String s, boolean b) {
		// TODO Auto-generated constructor stub
		super(frmRecommend, s, b);
		this.setFocusable(true);

		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
//		workPane.add(labelBusinessId); workPane.add(edtBusinessId);
		workPane.add(labelCounts); workPane.add(edtCounts);
//		workPane.add(labelStars); workPane.add(edtStars);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 180);
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
			
			if(counts < Integer.parseInt(this.edtCounts.getText().toString())) {
				System.out.println(counts + "  " + this.edtCounts.getText().toString());
				JOptionPane.showMessageDialog(null, "购买数量大于库存数量！！！","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			cart=new Cart();

			cart.setUserid(User.currentLoginUser.getUserId());
			cart.setBusinessid(businessid);
			cart.setComid(comid);
			cart.setCounts(Integer.parseInt(this.edtCounts.getText().toString()));				
			cart.setPrice(price * cart.getCounts());
			cart.setBusinessname(businessname);
			cart.setComname(comname);
			try {
				(new CartManager()).addCart(cart);
				this.setVisible(false);
				
			} catch (BaseException e1) {
				this.cart=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	public Cart getBusiness() {
		return cart;
	}

	
	
	
}
