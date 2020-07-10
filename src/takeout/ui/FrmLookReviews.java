package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import takeout.control.OrderManager;
import takeout.model.Review;
import takeout.model.User;
import takeout.util.BaseException;


public class FrmLookReviews extends JDialog {
//	private JPanel toolBar = new JPanel();
//	private Button btnAdd = new Button("添加地址信息");
	
//	private Button btnLook = new Button("查看订单详情");
//	private Button btnDelete = new Button("退单");
//	private Button btnReview = new Button("评价");
//	private Button btnLookReview = new Button("查看所有评价");

	
//	private Object tblData[][];
//	DefaultTableModel tablmod=new DefaultTableModel();
//	public JTable orderTable=new JTable(tablmod);
	
	
	private Object tblData1[][];
	DefaultTableModel tabModel1=new DefaultTableModel();
	public JTable dataTable1=new JTable(tabModel1);
	
	List<Review> allReviews = null;
	
	
	public void reloadReviewsTable(){
		try {
			allReviews=(new OrderManager()).loadAllOrders(User.currentLoginUser.getUserId(), null, 1);//1：简要内容2：详细内容
			tblData1 = new Object[allReviews.size()][Review.UtableTitles.length];
			for(int i=0;i<allReviews.size();i++){
				for(int j=0;j<Review.UtableTitles.length;j++) {
					tblData1[i][j] = allReviews.get(i).getUCell(j); 
				}
			}
			tabModel1.setDataVector(tblData1, Review.UtableTitles);
			this.dataTable1.validate();
			this.dataTable1.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmLookReviews(Frame f, String s, boolean b) {
		super(f, s, b);
//		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
//		toolBar.add(btnAdd);
//		toolBar.add(btnLook);
//		toolBar.add(this.btnDelete);
//		toolBar.add(btnReview);
		
//		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadReviewsTable();
		this.getContentPane().add(new JScrollPane(this.dataTable1), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		this.validate();
		
//		this.btnAdd.addActionListener(this);
//		this.btnLook.addActionListener(this);
//		this.btnDelete.addActionListener(this);
//		this.btnReview.addActionListener(this);
		
//		this.orderTable.addMouseListener(new MouseAdapter (){
//				
//				@Override
//				public void mouseClicked(MouseEvent e) {
//					int i=FrmOrderManager.this.orderTable.getSelectedRow();
//					int j=FrmOrderManager.this.orderTable.getSelectedColumn();
//					if(i<0) {
//						return;
//					}
//					FrmOrderManager.this.reloadInfoTable(i);
//					
//				}
//				
//
//				});
//		this.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				//System.exit(0);
//			}
//		});
		
		
	}
	
//	public void actionPerformed(ActionEvent e) {
////		if(e.getSource()==this.btnAdd){
////			FrmLocationManager_AddLocation dlg=new FrmLocationManager_AddLocation(this,"添加地址信息",true);
////			dlg.setVisible(true);
////			if(dlg.getLocation1()!=null){//刷新表格
////				this.reloadLocationTable();
////			}
////		}
////		else 
//		if(e.getSource()==this.btnDelete){
//			int i=this.dataTable1.getSelectedRow();
//			String businessid = tblData1[i][1].toString();
//			if(i<0) {
//				JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			if(JOptionPane.showConfirmDialog(this,"确定退掉该订单吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
//				try {
//					(new OrderManager()).deleteOrder(User.currentLoginUser.getUserId(), tblData1[i][0].toString(),businessid);
//					this.reloadReviewsTable();
//				} catch (BaseException e1) {
//					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
//				}
//				
//			}
//		}else if(e.getSource()==this.btnLook) {
//			int i=this.dataTable1.getSelectedRow();
//			if(i<0) {
//				JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			FrmOrderInfo fom = new FrmOrderInfo(this, "订单详情",true, i, allOrders);
//			fom.setVisible(true);
//			
//		}else if(e.getSource()==this.btnReview) {
//			int i=this.dataTable1.getSelectedRow();
//			if(i<0) {
//				JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			if(!allOrders.get(i).getStatus().equals("已送达")) {
//				JOptionPane.showMessageDialog(null,  "只可评价已送达的订单！！！","提示",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			if(allOrders.get(i).getIsReviewed()!=0) {
//				JOptionPane.showMessageDialog(null,  "已评论过该订单！！！","提示",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			curOrder = allOrders.get(i);
//			FrmReview fr = new FrmReview(this, "评价订单", true, curOrder);
//			fr.setVisible(true);
//			this.reloadReviewsTable();
//		}else if(e.getSource() == this.btnLookReview) {
//			
//			
//		}
//	}
		
		
	

}
	
