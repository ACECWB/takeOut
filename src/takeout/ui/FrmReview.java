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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import takeout.util.*;
import takeout.control.DeliverManager;
import takeout.control.IncomeManager;
import takeout.control.OrderManager;
import takeout.control.ReviewManager;
import takeout.model.Income;
import takeout.model.Order;
import takeout.model.Review;
import takeout.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FrmReview extends JDialog implements ActionListener{
	private Review review = null;
	private Income income = null;
	private Order order = null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelOrderId = new JLabel("订单编号：");
	private JLabel labelBusinessId = new JLabel("商家编号：");
	private JLabel labelContent = new JLabel("评论内容：");
	private JLabel labelStars = new JLabel("星级：");
	private JLabel labelDeliverId = new JLabel("骑手编号：");
	private JLabel labelReview = new JLabel("评价：");
	

	private JComboBox cmbStars= new JComboBox(new String[] { "0", "1", "2","3","4","5"});
	private JComboBox cmbReview= new JComboBox(new String[] { "好评", "差评", "未评价"});
	FrmOrderManager fom = null;
	private JTextField edtOrderId = new JTextField(20);
	private JTextField edtBusinessId = new JTextField(20);
	private JTextField edtDeliverId = new JTextField(20);

	private JTextField edtContent = new JTextField(20);
	
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable orderTable=new JTable(tablmod);
	List<Order>	allOrders = null;

	private void reloadOrderTable(){
		try {
			allOrders=(new OrderManager()).loadAllOrders(User.currentLoginUser.getUserId(), null, 1);
			tblData = new Object[allOrders.size()][Order.UtableTitles.length];
			for(int i=0;i<allOrders.size();i++){
				for(int j=0;j<Order.UtableTitles.length;j++) {
					tblData[i][j] = allOrders.get(i).getUCell(j); 
				}
			}
			tablmod.setDataVector(tblData, Order.UtableTitles);
			this.orderTable.validate();
			this.orderTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmReview(FrmOrderManager f, String s, boolean b, Order o) {
		super(f, s, b);
		this.order = o;
		this.fom = f;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelOrderId); workPane.add(edtOrderId);
		edtOrderId.setText(o.getOrderid());
		edtOrderId.setEditable(false);
		
		workPane.add(labelBusinessId); workPane.add(edtBusinessId);
		edtBusinessId.setText(o.getBusinessid());
		edtBusinessId.setEditable(false);
		
		workPane.add(labelStars); workPane.add(cmbStars);
		workPane.add(labelContent); workPane.add(edtContent);
		
		workPane.add(labelDeliverId); workPane.add(edtDeliverId);
		edtDeliverId.setText(o.getDeliverid());
		edtDeliverId.setEditable(false);
		
		workPane.add(labelReview); workPane.add(cmbReview);
		
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
			
			if(order.getStatus().equals("已送达")) {
				review = new Review();			
				Date date = new Date();
				income = new Income();
				
				review.setBusinessid(this.edtBusinessId.getText().toString());
				review.setUserid(User.currentLoginUser.getUserId());
				review.setContent(this.edtContent.getText().toString());
				review.setReviewtime(date);
				review.setStars(Float.parseFloat(cmbStars.getSelectedItem().toString()));
				review.setOrderid(order.getOrderid());
				
				income.setDeliverid(this.edtDeliverId.getText().toString());
				income.setOrderid(this.edtOrderId.getText().toString());
				income.setReview(this.cmbReview.getSelectedItem().toString());
				income.setReviewtime(date);
				income.setBonus(0);
				
				if(order.getReqtime().before(order.getReceiveTime())) {//未及时送达
					income.setBonus(-5);
				}
				if(income.getReview().equals("好评")) {
					income.setBonus((float)(income.getBonus() + 0.5));
				}
				if(income.getReview().equals("差评")) {
					income.setBonus((float)(income.getBonus() - 20));
				}
				

				
				try {
					(new ReviewManager()).addReview(review);
					(new IncomeManager()).addIncome(income);
					this.setVisible(false);
					this.fom.reloadOrderTable();
					
				} catch (BaseException e1) {
					this.review=null;
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "只可对已送达的订单进行评价！！！","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
		}
		
	}

	public Review getDeliver() {
		return review;
	}

	
	
	
}
