package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import takeout.control.BusinessManager;
import takeout.control.CartManager;
import takeout.control.CommodityManager;
import takeout.control.CartManager;
import takeout.control.CartManager;
import takeout.model.Business;
import takeout.model.Cart;
import takeout.model.Commodity;
import takeout.model.Cart;
import takeout.model.User;
import takeout.util.BaseException;


public class FrmCartManager extends JDialog implements ActionListener{
	private FrmCommodityManager a = null;
	private int row;
	public FrmMain fm = null;
	private JPanel toolBar = new JPanel();
	
	private JPanel westBar = new JPanel();
	private JPanel centerBar = new JPanel();
	
	private Button btnBuy = new Button("下单");
	private Button btnDelete = new Button("删除商品项目");
//	private Button btnReset = new Button("上架商品");
	private Object tblData1[][];
	DefaultTableModel tabModel1=new DefaultTableModel();
	public JTable dataTable1=new JTable(tabModel1);
	
	private Object tblData2[][];
	DefaultTableModel tabModel2=new DefaultTableModel();
	public JTable dataTable2=new JTable(tabModel2);
	
	private List<Business> allBusiness; 
	private List<Cart> allCart;
	private List<Commodity> allCom;
	
	private Business curBus;
	public void reloadComTable(int businessIdx){
		if(businessIdx<0) return;
			curBus = allBusiness.get(businessIdx);
		try {
			allCom = new CommodityManager().loadAllCommodity(curBus);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblData1 =  new Object[allCom.size()][Commodity.tableTitles.length];
		for(int i=0;i<allCom.size();i++){
			for(int j=0;j<Commodity.tableTitles.length;j++)
				tblData1[i][j]=allCom.get(i).getCell(j);
		}
		tabModel1.setDataVector(tblData1,Commodity.tableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
	public void reloadCartTable(int businessIdx){
		if(businessIdx<0) return;
			curBus = allBusiness.get(businessIdx);
		try {
			allCart = new CartManager().loadAllCarts(User.currentLoginUser.getUserId(),curBus.getBusinessId());
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblData1 =  new Object[allCart.size()][Cart.tableTitles.length];
		for(int i=0;i<allCart.size();i++){
			for(int j=0;j<Cart.tableTitles.length;j++)
				tblData1[i][j]=allCart.get(i).getCell(j);
		}
		tabModel1.setDataVector(tblData1,Cart.tableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
	
	public void reloadBusinessTable(){//这是测试数据，需要用实际数替换
		try {
			allBusiness = new BusinessManager().loadAllBusiness(User.currentLoginUser.getUserId());
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblData2 =  new Object[allBusiness.size()][Business.CtableTitles.length];
		for(int i=0;i<allBusiness.size();i++){
			for(int j=0;j<Business.CtableTitles.length;j++)
				tblData2[i][j]=allBusiness.get(i).getCell(j);
		}
		tabModel2.setDataVector(tblData2,Business.CtableTitles);
		this.dataTable2.validate();
		this.dataTable2.repaint();
		tabModel1.setRowCount(0);
	}
	
	
	public FrmCartManager(FrmMain frmMain, String s, boolean b, int row) {
		super(frmMain, s, b);
//		this.a = frmMain;
		this.setSize(750, 480);
		this.setLocationRelativeTo(null);
		this.validate();
		this.fm = frmMain;
		this.row = row;
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnBuy);
		toolBar.add(this.btnDelete);
//		toolBar.add(btnReset);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		
		JScrollPane jsp2 = new JScrollPane(this.dataTable2);
		jsp2.setPreferredSize(new Dimension(200,400));
		westBar.add(jsp2);
		
		JScrollPane jsp1 = new JScrollPane(this.dataTable1);
		jsp1.setPreferredSize(new Dimension(500,400));
	    centerBar.add(jsp1);
	    this.add(westBar,BorderLayout.WEST);
	    this.add(centerBar,BorderLayout.CENTER);
	    
//		this.getContentPane().add(new JScrollPane(this.dataTable2), BorderLayout.WEST);
		this.dataTable2.addMouseListener(new MouseAdapter (){

			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmCartManager.this.dataTable2.getSelectedRow();
				if(i<0) {
					return;
				}
				FrmCartManager.this.reloadCartTable(i);
			}
	    	
	    });
//	    this.getContentPane().add(new JScrollPane(this.dataTable1), BorderLayout.CENTER);
		this.reloadBusinessTable();

		// 屏幕居中显示
		
		
		this.btnBuy.addActionListener(this);
		this.btnDelete.addActionListener(this);
//		this.btnReset.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnBuy){
			int i=this.dataTable2.getSelectedRow();
			
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商家","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			String businessid = tblData2[i][0].toString();
			FrmBuyManager dlg = new FrmBuyManager(this, "购买", true, businessid, this.row, fm);
			dlg.setVisible(true);
//			this.reloadComTable(i);
//			String businessid=this.tblData2[i][0].toString();
//			try {
//				(new CartManager()).Buy(User.currentLoginUser.getUserId(),businessid);
//				this.reloadBusinessTable();
//			} catch (BaseException e1) {
//				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
//			}
				
			
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.dataTable1.getSelectedRow();
			int j=this.dataTable2.getSelectedRow();

			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品项目","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该商品项目吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String businessid=this.tblData2[j][0].toString();
				String comid = this.tblData1[i][0].toString();
				try {
					(new CartManager()).deleteCart(User.currentLoginUser.getUserId(),businessid,comid);
					this.reloadCartTable(j);
					this.reloadBusinessTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
		
		
	

}
	
