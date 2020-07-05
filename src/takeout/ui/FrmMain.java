package takeout.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeout.control.BusinessManager;
import takeout.control.CommodityManager;
import takeout.model.Admin;
import takeout.model.Business;
import takeout.model.ComCate;
import takeout.model.ComTitle;
import takeout.model.Commodity;
import takeout.ui.FrmLogin;
import takeout.util.BaseException;



public class FrmMain extends JFrame implements ActionListener{
	private JMenuBar menubar=new JMenuBar();
	private JMenu menu_Manager=new JMenu("系统管理");
	private JMenu menu_Business = new JMenu("商家管理");
	private JMenu menu_Com = new JMenu("商品管理");
//	private JMenu menu_commoditycategoryManager = new JMenuItem("商品类别管理");
	
	private JMenuItem menuItem_ComCate = new JMenuItem("查看商品类");
	private JMenuItem menuItem_deleteCom = new JMenuItem("删除商品");
	private JMenuItem menuItem_addCom = new JMenuItem("添加商品");
	private JMenuItem menuItem_userManager = new JMenuItem("用户管理");
	private JMenuItem menuItem_deliverManager = new JMenuItem("骑手管理");
	private JMenuItem menuItem_commodityManager = new JMenuItem("商品管理");
	private JMenuItem menuItem_businessManager = new JMenuItem("查看商家信息");
	private JMenuItem menuItem_couponManager = new JMenuItem("优惠券管理");
	private JMenuItem menuItem_fullReductionManager = new JMenuItem("满减管理");
	private JMenuItem menuItem_orderManager = new JMenuItem("订单管理");
	private JMenuItem menuItem_locationManager = new JMenuItem("地址信息管理");
	private JMenuItem menuItem_adminManager = new JMenuItem("管理员信息管理");
	
	private JMenuItem menuItem_addBus = new JMenuItem("添加商家");
	private JMenuItem menuItem_deleteBus = new JMenuItem("删除商家");
	
	
	List<Business> allBusiness = null;
	List<Commodity> allCom = null;
	List<ComCate> allCate = null;
	List<ComTitle> allComTitle = null;
	private Business curBus = null; 
	private ComCate curCate = null; 
	
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	
	private Object tblBusinessData[][];
//	DefaultTableModel tabBusinessModel=new DefaultTableModel();
//	private JTable dataTableBusiness=new JTable(tabBusinessModel);
	
	DefaultTableModel tabModel1=new DefaultTableModel();
	private JTable dataTable1=new JTable(tabModel1);
	
	DefaultTableModel tabModel2=new DefaultTableModel();
	private JTable dataTable2=new JTable(tabModel2);
	
	private void reloadBusinessTable(){//这是测试数据，需要用实际数替换
		try {
			allBusiness = new BusinessManager().loadAllBusiness(true);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblBusinessData =  new Object[allBusiness.size()][Business.tableTitles.length];
		for(int i=0;i<allBusiness.size();i++){
			for(int j=0;j<Business.tableTitles.length;j++)
				tblBusinessData[i][j]=allBusiness.get(i).getCell(j);
		}
		tabModel2.setDataVector(tblBusinessData,Business.tableTitles);
		this.dataTable2.validate();
		this.dataTable2.repaint();
	}
	

	
	private Object tblComData[][];
//	DefaultTableModel tabComModel=new DefaultTableModel();
//	private JTable dataTableCom=new JTable(tabComModel);
	
	private void reloadComTable(int businessIdx){
		if(businessIdx<0) return;
			curBus = allBusiness.get(businessIdx);
		try {
			allCom = new CommodityManager().loadAllCommodity(curBus);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblComData =  new Object[allCom.size()][Commodity.tableTitles.length];
		for(int i=0;i<allCom.size();i++){
			for(int j=0;j<Commodity.tableTitles.length;j++)
				tblComData[i][j]=allCom.get(i).getCell(j);
		}
		tabModel1.setDataVector(tblComData,Commodity.tableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
	
	
	private Object tblComCateData[][];
//	DefaultTableModel tablComCateMod=new DefaultTableModel();
//	public JTable comCateTable=new JTable(tablComCateMod);//商品类别
	
	private void reloadComTable(){
		try {
			allCate = (new CommodityManager()).loadAllComCates();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblComCateData = new Object[allCate.size()][ComCate.tableTitles.length];
		for(int i=0;i<allCate.size();i++){
			for(int j=0;j<ComCate.tableTitles.length;j++) {
				tblComCateData[i][j] = allCate.get(i).getCell(j); 
			}
		}
		tabModel2.setDataVector(tblComCateData, ComCate.tableTitles);
		this.dataTable2.validate();
		this.dataTable2.repaint();
		
	}
	
	
	private Object tblComTitleDate[][];
//	DefaultTableModel tabComTitleModel=new DefaultTableModel();
//	private JTable dataTableComTitle=new JTable(tabComTitleModel);
	
	private void reloadComTitleTable(int cateIdx){
		if(cateIdx<0) return;
			curCate = allCate.get(cateIdx);
		try {
			allComTitle = new CommodityManager().loadAllComTitles(curCate);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tblComTitleDate = new Object[allComTitle.size()][ComTitle.tableTitles.length];
		for(int i=0;i<allComTitle.size();i++){
			for(int j=0;j<ComTitle.tableTitles.length;j++) {
				tblComTitleDate[i][j] = allComTitle.get(i).getCell(j); 
			}
		}
		tabModel1.setDataVector(tblComTitleDate, ComTitle.tableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
		
	}
	public FrmMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("外卖助手");
		
		dlgLogin=new FrmLogin(this,"登陆",true);
		dlgLogin.setVisible(true);
		
		menu_Manager.add(menuItem_userManager); menuItem_userManager.addActionListener(this);
		menu_Manager.add(menuItem_deliverManager); menuItem_deliverManager.addActionListener(this);
		menu_Manager.add(menuItem_commodityManager); menuItem_commodityManager.addActionListener(this);
//		menu_Manager.add(menuItem_commoditycategoryManager); menuItem_commoditycategoryManager.addActionListener(this);
		menu_Manager.add(menuItem_couponManager); menuItem_couponManager.addActionListener(this);
		menu_Manager.add(menuItem_fullReductionManager); menuItem_fullReductionManager.addActionListener(this);
		menu_Manager.add(menuItem_orderManager); menuItem_orderManager.addActionListener(this);
		menu_Manager.add(menuItem_locationManager); menuItem_locationManager.addActionListener(this);
		menu_Manager.add(menuItem_adminManager); menuItem_adminManager.addActionListener(this);
		
		menu_Business.add(menuItem_businessManager); menuItem_businessManager.addActionListener(this);
		menu_Business.add(menuItem_addBus); menuItem_addBus.addActionListener(this);
		menu_Business.add(menuItem_deleteBus);menuItem_deleteBus.addActionListener(this);
		menu_Business.add(menuItem_addCom); menuItem_addCom.addActionListener(this);
		menu_Business.add(menuItem_deleteCom); menuItem_deleteCom.addActionListener(this);

		menu_Com.add(menuItem_ComCate); menuItem_ComCate.addActionListener(this);
		
		menubar.add(menu_Manager);
		menubar.add(menu_Business);
		menubar.add(menu_Com);
//		menu_Business.addActionListener(this);
//		menu_Com.addActionListener(this);
		this.setJMenuBar(menubar);
	    this.getContentPane().add(new JScrollPane(this.dataTable2), BorderLayout.WEST);

		this.dataTable2.addMouseListener(new MouseAdapter (){
				@Override
				public void mouseClicked(MouseEvent e) {
					int i=FrmMain.this.dataTable2.getSelectedRow();
					if(i<0) {
						return;
					}
					
					if("商家名称".equals(tabModel2.getColumnName(1)))
						FrmMain.this.reloadComTable(i);
					else if("分类名".equals(tabModel2.getColumnName(1)))
						FrmMain.this.reloadComTitleTable(i);
					
				}
		
		    });
		this.getContentPane().add(new JScrollPane(this.dataTable1), BorderLayout.CENTER);
	
	  

//		this.getContentPane().add(new JScrollPane(this.comCateTable), BorderLayout.WEST);
//
//	    this.comCateTable.addMouseListener(new MouseAdapter (){
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				int i=FrmMain.this.comCateTable.getSelectedRow();
//				if(i<0) {
//					return;
//				}
//				FrmMain.this.reloadComTitleTable(i);
//			}
//	
//	    });
//	    this.getContentPane().add(new JScrollPane(this.dataTableComTitle), BorderLayout.CENTER);

		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("您好!"+Admin.currentLoginUser.getName());
	    statusBar.add(label);
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    this.setVisible(true);
	
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.menuItem_userManager){
			FrmUserManager dlg = new FrmUserManager(this,"用户管理",true);
			dlg.setVisible(true);
		}else if(e.getSource()==this.menuItem_locationManager) {
			FrmLocationManager dlg = new FrmLocationManager(this,"地址管理",true);
			dlg.setVisible(true);
		}else if(e.getSource()==this.menuItem_deliverManager) {
			FrmDeliverManager dlg = new FrmDeliverManager(this,"骑手管理",true);
			dlg.setVisible(true);
		}else if(e.getSource()==this.menuItem_businessManager) {
//			FrmBusinessManager dlg = new FrmBusinessManager(this,"商家管理",true);
//			dlg.setVisible(true);
			

		    this.reloadBusinessTable();
		    
		   
		}else if(e.getSource()==this.menuItem_commodityManager) {
			FrmCommodityManager dlg = new FrmCommodityManager(this, "商品管理",true);
			dlg.setVisible(true);
		}else if(e.getSource()==this.menu_Business) {
			
		}else if(e.getSource()==this.menuItem_addBus) {
			FrmBusinessManager_AddBusiness dlg=new FrmBusinessManager_AddBusiness(this,"添加商家",true);
			dlg.setVisible(true);
			if(dlg.getBusiness()!=null){//刷新表格
				this.reloadBusinessTable();
			}
		}else if(e.getSource()==menuItem_deleteBus) {
			int i=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商家","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该商家信息吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String businessid=this.tblBusinessData[i][0].toString();
				try {
					(new BusinessManager()).deleteBusiness(businessid);
					this.reloadBusinessTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==menuItem_addCom) {
			int i=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商家","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			FrmCommodityManager_AddCommodity dlg=new FrmCommodityManager_AddCommodity(this,"添加商品信息",true);
			dlg.bus = curBus;
			
			dlg.setVisible(true);
			if(dlg.getCommodity()!=null){//刷新表格
				this.reloadComTable(i);
			}

		}else if(e.getSource()==menuItem_deleteCom) {
			int i=this.dataTable1.getSelectedRow();
			int j=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品信息","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该商品信息吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String commodityid=this.tblComData[i][0].toString();
				String businessid = this.tblBusinessData[j][4].toString();
				try {
					(new CommodityManager()).deleteCommodity(commodityid, businessid);
					this.reloadBusinessTable();
					this.reloadComTable(j);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==this.menuItem_ComCate) {
			this.reloadComTable();
		}
	}
}
