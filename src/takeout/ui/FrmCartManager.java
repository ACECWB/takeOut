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
import java.sql.Connection;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import takeout.control.BusinessManager;
import takeout.control.CartManager;
import takeout.control.CartManager;
import takeout.control.CartManager;
import takeout.control.CartManager;
import takeout.model.Business;
import takeout.model.Cart;
import takeout.model.ComTitle;
import takeout.model.Commodity;
import takeout.model.Cart;
import takeout.model.User;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;


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
//	public void reloadComTable(int businessIdx){
//		if(businessIdx<0) return;
//			curBus = allBusiness.get(businessIdx);
//		try {
//			allCom = new CartManager().loadAllCarts(User.currentLoginUser.getUserId() ,curBus.getBusinessId());
//		} catch (BaseException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//		tblData1 =  new Object[allCom.size()][Commodity.tableTitles.length];
//		for(int i=0;i<allCom.size();i++){
//			for(int j=0;j<Commodity.tableTitles.length;j++)
//				tblData1[i][j]=allCom.get(i).getCell(j);
//		}
//		tabModel1.setDataVector(tblData1,Commodity.tableTitles);
//		this.dataTable1.validate();
//		this.dataTable1.repaint();
//	}
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
		
		this.dataTable1.getModel().addTableModelListener(new TableModelListener() {
			
			public void tableChanged(TableModelEvent e) {
				int i = dataTable1.getSelectedRow();
				int j = dataTable2.getSelectedRow();
				int c = e.getColumn();
				int r = e.getFirstRow();
				
				if(c>=0 && r>=0) {
					int origincounts = Integer.parseInt(tblData1[r][2].toString());
					if(c == 2) {
						try {
							if(dataTable1.getValueAt(r, c).toString() == null || "".equals(dataTable1.getValueAt(r, c).toString())) {
								throw new BusinessException("数量不可为空！！！");
							}
							if(Integer.parseInt(dataTable1.getValueAt(r, c).toString())<0) {
								throw new BusinessException("数量不可为负数！！！");
							}

							tblData1[r][c] = dataTable1.getValueAt(r, c);
							Commodity com = new Commodity();
							com.setComId(tblData1[r][0].toString());
							com.setBusinessId(tblData2[j][0].toString());
							com.setCounts(Integer.parseInt(tblData1[r][2].toString()));
							com.setEachPrice(Float.parseFloat(tblData1[r][3].toString())/origincounts);
							System.out.println(com.getEachPrice());
							(new CartManager()).modifyCart(com);
							
						}catch(Exception e2) {
							JOptionPane.showMessageDialog(null,  "请输入正确值(或该商品现库存数不足)！！！","提示",JOptionPane.ERROR_MESSAGE);
							tabModel1.setDataVector(tblData1, Cart.tableTitles);
							dataTable1.validate();
							dataTable1.repaint();
						}

//						FrmMain.this.reloadComTable();
						FrmCartManager.this.reloadCartTable(j);
					}else {
						JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);
						tabModel1.setDataVector(tblData1, Cart.tableTitles);
						dataTable1.validate();
						dataTable1.repaint();

					}
					
					
				}

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
			
			Connection conn = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				sql = "select cb.com_Id, com_name, cb.counts , c.counts  from cart c, com2bus cb, commodity com\r\n" + 
						"where user_Id = ? and cb.business_Id = ? and cb.com_Id = c.com_Id and cb.business_Id = c.business_Id and com.com_Id = c.com_Id\r\n" + 
						"and c.counts > cb.counts";
				java.sql.PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, User.currentLoginUser.getUserId());
				pst.setString(2, businessid);
				java.sql.ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					JOptionPane.showMessageDialog(null,"购物车中商品编号为 "+rs.getString(1)+"的"+rs.getString(2)+"的购买数量为: "+rs.getInt(4)+"，但库存中仅有"+rs.getInt(3),"错误",JOptionPane.ERROR_MESSAGE);
					rs.close();
					pst.close();
					conn.close();
					return ;
					
				}
				rs.close();
				pst.close();
				conn.close();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
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
					this.reloadBusinessTable();
					if(allBusiness.size()>j) {
						this.reloadCartTable(j);
						dataTable2.setRowSelectionInterval(j,j);
					}

				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
		
		
	

}
	
