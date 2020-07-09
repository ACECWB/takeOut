package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import takeout.control.BusinessManager;
import takeout.control.CommodityManager;
import takeout.control.CouponManager;
import takeout.control.DeliverManager;
import takeout.control.IncomeManager;
import takeout.control.IncomeStatisticManager;
import takeout.control.OrderManager;
import takeout.control.ReviewManager;
import takeout.control.UserManager;
import takeout.model.Admin;
import takeout.model.Business;
import takeout.model.ComCate;
import takeout.model.ComTitle;
import takeout.model.Commodity;
import takeout.model.Coupon;
import takeout.model.Deliver;
import takeout.model.Income;
import takeout.model.IncomeStatistic;
import takeout.model.Order;
import takeout.model.Review;
import takeout.model.User;
import takeout.ui.FrmLogin;
import takeout.util.BaseException;
import takeout.util.BusinessException;



public class FrmMain extends JFrame implements ActionListener{
	private JFrame jframe = new JFrame("外卖助手"); 
	private JMenuBar menubar=new JMenuBar();
	//以下为管理员菜单栏
	private JMenu menu_Manager=new JMenu("系统管理");
	private JMenu menu_Business = new JMenu("商家管理");
	private JMenu menu_ComCate = new JMenu("商品类管理");
	private JMenu menu_Com = new JMenu("商品管理");
	private JMenu menu_User = new JMenu("用户管理");
	private JMenu menu_Deliver = new JMenu("骑手管理");
	
	private JMenu menu_Coupon = new JMenu("优惠券管理");
	private JMenu menu_Statistic = new JMenu("统计");
	private JMenuItem menuItem_Income = new JMenuItem("骑手收入统计");

	//以下为用户菜单栏
	private JMenu menu_InfoManager = new JMenu("个人信息管理");
	private JMenu menu_CartManager = new JMenu("购物车管理");
	private JMenuItem menuItem_addgoods = new JMenuItem("添加到购物车");
	private JMenuItem menuItem_cart = new JMenuItem("查看当前购物车");
	private JMenuItem menuItem_location = new JMenuItem("地址信息编辑");
	private JMenuItem menuItem_orderInfo = new JMenuItem("订单信息查看");


	private int model = 0;//1:商家-商品，2:商品类别-商品，3:商家-优惠券，4:用户-优惠券，5:用户-订单，6:骑手-订单,7:骑手-收入统计
//	private JMenu menu_commoditycategoryManager = new JMenuItem("商品类别管理");
	private JMenuItem menuItem_ModifyPwd = new JMenuItem("修改密码");

	private JMenuItem menuItem_deleteCom = new JMenuItem("删除商家商品信息");
	private JMenuItem menuItem_addCom = new JMenuItem("添加商家商品信息");
//	private JMenuItem menuItem_userManager = new JMenuItem("用户管理");
//	private JMenuItem menuItem_deliverManager = new JMenuItem("骑手管理");
//	private JMenuItem menuItem_couponManager = new JMenuItem("优惠券管理");
	private JMenuItem menuItem_fullReductionManager = new JMenuItem("满减管理");
//	private JMenuItem menuItem_orderManager = new JMenuItem("订单管理");
	private JMenuItem menuItem_locationManager = new JMenuItem("地址信息管理");
//	private JMenuItem menuItem_adminManager = new JMenuItem("管理员信息管理");
	
	private JMenuItem menuItem_ComCate = new JMenuItem("查看商品类");
	private JMenuItem menuItem_addComCate = new JMenuItem("增加商品类");
	private JMenuItem menuItem_deleteComCate = new JMenuItem("删除商品类");
	private JMenuItem menuItem_resetComCate = new JMenuItem("上架商品类");
	
	private JMenuItem menuItem_businessManager = new JMenuItem("查看商家商品信息");
	private JMenuItem menuItem_businessReview = new JMenuItem("查看商家评论信息");
	private JMenuItem menuItem_addBus = new JMenuItem("添加商家");
	private JMenuItem menuItem_deleteBus = new JMenuItem("删除商家");
	private JMenuItem menuItem_commodityManager = new JMenuItem("商品全局管理");

	private JMenuItem menuItem_addCommodity = new JMenuItem("添加新商品");
	private JMenuItem menuItem_deleteCommodity = new JMenuItem("删除商品");
	private JMenuItem menuItem_resetCommodity = new JMenuItem("上架商品");
	
	private JMenuItem menuItem_BCoupon = new JMenuItem("查看商家-优惠券信息");
	private JMenuItem menuItem_CCoupon = new JMenuItem("查看用户-优惠券信息");

	private JMenuItem menuItem_addCoupon = new JMenuItem("添加优惠券信息");
	private JMenuItem menuItem_deleteCoupon = new JMenuItem("删除优惠券信息");

	private JMenuItem menuItem_order = new JMenuItem("查看用户信息");
	private JMenuItem menuItem_addUser = new JMenuItem("添加用户");
	private JMenuItem menuItem_deleteUser = new JMenuItem("删除用户");
	private JMenuItem menuItem_resetUser = new JMenuItem("重置用户密码");
	
	private JMenuItem menuItem_Deliver = new JMenuItem("查看骑手信息");
	private JMenuItem menuItem_addDeliver = new JMenuItem("添加骑手");
	private JMenuItem menuItem_deleteDeliver = new JMenuItem("删除骑手");

	List<Business> allBusiness = null;
	List<Commodity> allCom = null;
	List<ComCate> allCate = null;
	List<ComTitle> allComTitle = null;
	List<Coupon> allCoupon = null;
	List<User> allUser = null;
	List<Order> allOrder = null;
	List<Deliver> allDeliver = null;
	List<Income> allIncome = null;
	List<IncomeStatistic> allTotalIncome = null;
	List<Review> allReview = null;
	
	private Business curBus = null; 
	private ComCate curCate = null; 
	private User curUser = null;
	private Deliver curDeliver = null;
	
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	
	private JPanel westBar = new JPanel();
	private JPanel centerBar = new JPanel();
	
//	private Object tblData2[][];
//	DefaultTableModel tabBusinessModel=new DefaultTableModel();
//	private JTable dataTableBusiness=new JTable(tabBusinessModel);
	
	DefaultTableModel tabModel1=new DefaultTableModel();
	private JTable dataTable1=new JTable(tabModel1);
	
	DefaultTableModel tabModel2=new DefaultTableModel();
	private JTable dataTable2=new JTable(tabModel2);
	
//	DefaultTableModel tabModelCate = new DefaultTableModel() {
//		public boolean isCellEditable(int rowIndex, int columnIndex) {
//			if
//		}
//	};
	
	private Object tblData1[][];
	private Object tblData2[][];
	
	private void reloadBusinessTable(){//这是测试数据，需要用实际数替换
		try {
			if(User.currentLoginUser!=null)
				allBusiness = new BusinessManager().loadAllBusiness(false);
			else
				allBusiness = new BusinessManager().loadAllBusiness(true);

		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblData2 =  new Object[allBusiness.size()][Business.tableTitles.length];
		for(int i=0;i<allBusiness.size();i++){
			for(int j=0;j<Business.tableTitles.length;j++)
				tblData2[i][j]=allBusiness.get(i).getCell(j);
		}
		tabModel2.setDataVector(tblData2,Business.tableTitles);
		this.dataTable2.validate();
		this.dataTable2.repaint();
		tabModel1.setRowCount(0);
	}
	
	
	private void reloadUserTable(){//这是测试数据，需要用实际数替换
		try {
			allUser = new UserManager().loadAllUsers(true);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblData2 =  new Object[allUser.size()][User.tableTitles.length];
		for(int i=0;i<allUser.size();i++){
			for(int j=0;j<User.tableTitles.length;j++)
				tblData2[i][j]=allUser.get(i).getCell(j);
		}
		tabModel2.setDataVector(tblData2,User.tableTitles);
		this.dataTable2.validate();
		this.dataTable2.repaint();
		tabModel1.setRowCount(0);
	}
	

	
//	private Object tblComData[][];
//	DefaultTableModel tabComModel=new DefaultTableModel();
//	private JTable dataTableCom=new JTable(tabComModel);
	
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
	
	
//	private Object tblComCateData[][];
//	DefaultTableModel tablComCateMod=new DefaultTableModel();
//	public JTable comCateTable=new JTable(tablComCateMod);//商品类别
	
	private void reloadComTable(){
		try {
			allCate = (new CommodityManager()).loadAllComCates();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblData2 = new Object[allCate.size()][ComCate.tableTitles.length];
		for(int i=0;i<allCate.size();i++){
			for(int j=0;j<ComCate.tableTitles.length;j++) {
				tblData2[i][j] = allCate.get(i).getCell(j); 
			}
		}
		tabModel2.setDataVector(tblData2, ComCate.tableTitles);
		this.dataTable2.validate();
		this.dataTable2.repaint();
		tabModel1.setRowCount(0);
	}
	
	
//	private Object tblComTitleDate[][];
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
		
		tblData1 = new Object[allComTitle.size()][ComTitle.tableTitles.length];
		for(int i=0;i<allComTitle.size();i++){
			for(int j=0;j<ComTitle.tableTitles.length;j++) {
				tblData1[i][j] = allComTitle.get(i).getCell(j); 
			}
		}
		tabModel1.setDataVector(tblData1, ComTitle.tableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
	
//	private Object tblBCouponData[][];
	
	private void reloadBCouponTable(int businessIdx){
		if(businessIdx<0) return;
			curBus = allBusiness.get(businessIdx);
		try {
			allCoupon = new CouponManager().loadAllBCoupons(curBus);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tblData1 = new Object[allCoupon.size()][Coupon.BtableTitles.length];
		for(int i=0;i<allCoupon.size();i++){
			for(int j=0;j<Coupon.BtableTitles.length;j++) {
				tblData1[i][j] = allCoupon.get(i).getBCell(j); 
			}
		}
		tabModel1.setDataVector(tblData1, Coupon.BtableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
//	private Object tblCCouponData[][];
//	private Object realdata[][];
	
	private void reloadCCouponTable(int userIdx){
		if(userIdx<0) return;
			curUser = allUser.get(userIdx);
		try {
			allCoupon = new CouponManager().loadAllCCoupons(curUser);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tblData1 = new Object[allCoupon.size()][Coupon.CtableTitles1.length];
//		realdata = new Object[allCoupon.size()][Coupon.CtableTitles.length];
		for(int i=0;i<allCoupon.size();i++){
			for(int j=0;j<Coupon.CtableTitles1.length;j++) {
//				realdata[i][j] = allCoupon.get(i).getCCell(j);
//				if(j<Coupon.CtableTitles.length-1) {
					tblData1[i][j] = allCoupon.get(i).getCCell(j);
//				}
			}
		}
		tabModel1.setDataVector(tblData1, Coupon.CtableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
//	private Object tblOrderData[][];
	
	public void reloadOrderTable(int userIdx){
		if(userIdx<0) return;
			curUser = allUser.get(userIdx);
		try {
			allOrder = new OrderManager().loadAllOrders(curUser.getUserId());
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tblData1 = new Object[allOrder.size()][Order.tableTitles.length];
		for(int i=0;i<allOrder.size();i++){
			for(int j=0;j<Order.tableTitles.length;j++) {
				tblData1[i][j] = allOrder.get(i).getCell(j); 
			}
		}
		tabModel1.setDataVector(tblData1, Order.tableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
	
//	private Object tblDeliverData[][];
	
	private void reloadDeliverTable(){
		try {
			allDeliver = (new DeliverManager()).loadAllDelivers();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblData2 = new Object[allDeliver.size()][Deliver.tableTitles.length];
		for(int i=0;i<allDeliver.size();i++){
			for(int j=0;j<Deliver.tableTitles.length;j++) {
				tblData2[i][j] = allDeliver.get(i).getCell(j); 
			}
		}
		tabModel2.setDataVector(tblData2, Deliver.tableTitles);
		this.dataTable2.validate();
		this.dataTable2.repaint();
		tabModel1.setRowCount(0);
	}
	
//	private Object tblIncomeData[][];
	
	private void reloadIncomeTable(int deliverIdx){
		if(deliverIdx<0) return;
			curDeliver = allDeliver.get(deliverIdx);
		try {
			allIncome = new IncomeManager().loadAllIncomes(curDeliver.getDeliverId());
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tblData1 = new Object[allIncome.size()][Income.tableTitles.length];
		for(int i=0;i<allIncome.size();i++){
			for(int j=0;j<Income.tableTitles.length;j++) {
				tblData1[i][j] = allIncome.get(i).getCell(j); 
			}
		}
		tabModel1.setDataVector(tblData1, Income.tableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
	
	private void reloadIncomeStatisticTable(int deliverIdx){
		if(deliverIdx<0) return;
			curDeliver = allDeliver.get(deliverIdx);
		try {
			allTotalIncome = new IncomeStatisticManager().loadAllIncomeStatistics(curDeliver.getDeliverId());
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tblData1 = new Object[allTotalIncome.size()][IncomeStatistic.tableTitles.length];
		for(int i=0;i<allTotalIncome.size();i++){
			for(int j=0;j<IncomeStatistic.tableTitles.length;j++) {
				tblData1[i][j] = allTotalIncome.get(i).getCell(j); 
			}
		}
		tabModel1.setDataVector(tblData1, IncomeStatistic.tableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
	
	private void reloadReviewTable(int businessIdx){
		if(businessIdx<0) return;
			curBus = allBusiness.get(businessIdx);
		try {
			allReview = new ReviewManager().loadAllReviews(curBus.getBusinessId());
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tblData1 = new Object[allReview.size()][Review.tableTitles.length];
		for(int i=0;i<allReview.size();i++){
			for(int j=0;j<Review.tableTitles.length;j++) {
				tblData1[i][j] = allReview.get(i).getCell(j); 
			}
		}
		tabModel1.setDataVector(tblData1, Review.tableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
	public FrmMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("外卖助手");
		
		dlgLogin=new FrmLogin(this,"登陆",true);
		dlgLogin.setVisible(true);
		
		if(Admin.currentLoginUser!=null) {
			
		
	//		menu_Manager.add(menuItem_userManager); menuItem_userManager.addActionListener(this);
	//		menu_Manager.add(menuItem_deliverManager); menuItem_deliverManager.addActionListener(this);
	//		menu_Manager.add(menuItem_commoditycategoryManager); menuItem_commoditycategoryManager.addActionListener(this);
	//		menu_Manager.add(menuItem_couponManager); menuItem_couponManager.addActionListener(this);
			menu_Manager.add(menuItem_fullReductionManager); menuItem_fullReductionManager.addActionListener(this);
	//		menu_Manager.add(menuItem_orderManager); menuItem_orderManager.addActionListener(this);
			menu_Manager.add(menuItem_locationManager); menuItem_locationManager.addActionListener(this);
	//		menu_Manager.add(menuItem_adminManager); menuItem_adminManager.addActionListener(this);
			menu_Manager.add(menuItem_ModifyPwd); menuItem_ModifyPwd.addActionListener(this);
			
			menu_Business.add(menuItem_businessManager); menuItem_businessManager.addActionListener(this);
			menu_Business.add(menuItem_addBus); menuItem_addBus.addActionListener(this);
			menu_Business.add(menuItem_deleteBus);menuItem_deleteBus.addActionListener(this);
			menu_Business.add(menuItem_addCom); menuItem_addCom.addActionListener(this);
			menu_Business.add(menuItem_deleteCom); menuItem_deleteCom.addActionListener(this);
			menu_Business.add(menuItem_commodityManager); menuItem_commodityManager.addActionListener(this);
			menu_Business.add(menuItem_businessReview); menuItem_businessReview.addActionListener(this);
	
			menu_ComCate.add(menuItem_ComCate); menuItem_ComCate.addActionListener(this);
			menu_ComCate.add(menuItem_addComCate); menuItem_addComCate.addActionListener(this);
			menu_ComCate.add(menuItem_deleteComCate); menuItem_deleteComCate.addActionListener(this);
			menu_ComCate.add(menuItem_resetComCate); menuItem_resetComCate.addActionListener(this);
	
			menu_Com.add(menuItem_addCommodity); menuItem_addCommodity.addActionListener(this);
			menu_Com.add(menuItem_deleteCommodity); menuItem_deleteCommodity.addActionListener(this);
			menu_Com.add(menuItem_resetCommodity); menuItem_resetCommodity.addActionListener(this);
			
			menu_Coupon.add(menuItem_BCoupon); menuItem_BCoupon.addActionListener(this);
			menu_Coupon.add(menuItem_CCoupon); menuItem_CCoupon.addActionListener(this);
			menu_Coupon.add(menuItem_addCoupon); menuItem_addCoupon.addActionListener(this);
			menu_Coupon.add(menuItem_deleteCoupon); menuItem_deleteCoupon.addActionListener(this);
	
			menu_User.add(menuItem_order); menuItem_order.addActionListener(this);
			menu_User.add(menuItem_addUser); menuItem_addUser.addActionListener(this);
			menu_User.add(menuItem_deleteUser); menuItem_deleteUser.addActionListener(this);
			menu_User.add(menuItem_resetUser); menuItem_resetUser.addActionListener(this);
	
			menu_Deliver.add(menuItem_Deliver); menuItem_Deliver.addActionListener(this);
			menu_Deliver.add(menuItem_addDeliver); menuItem_addDeliver.addActionListener(this);
			menu_Deliver.add(menuItem_deleteDeliver); menuItem_deleteDeliver.addActionListener(this);
			
			menu_Statistic.add(menuItem_Income); menuItem_Income.addActionListener(this);
			
			menubar.add(menu_Manager);
			menubar.add(menu_Business);
			menubar.add(menu_ComCate);
			menubar.add(menu_Com);
			menubar.add(menu_Coupon);
			menubar.add(menu_User);
			menubar.add(menu_Deliver);
			menubar.add(menu_Statistic);
	//		this.btnModifyPwd.addActionListener(this);
	//		menu_Business.addActionListener(this);
	//		menu_Com.addActionListener(this);
			this.setJMenuBar(menubar);
			this.dataTable1.getModel().addTableModelListener(new TableModelListener() {
				
				@Override
				public void tableChanged(TableModelEvent e) {
					// TODO Auto-generated method stub
					int i = dataTable1.getSelectedRow();
					int j = dataTable2.getSelectedRow();
					int c = e.getColumn();
					int r = e.getFirstRow();
					if(c>=0 && r>=0) {
						if(model == 1) {
							if(c == 5) {
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
									com.setCounts(Integer.parseInt(tblData1[r][5].toString()));
									com.setEachPrice(Float.parseFloat(tblData1[r][6].toString()));
									
									(new CommodityManager()).modifyCom2bus(com);
									
								}catch(Exception e2) {
									JOptionPane.showMessageDialog(null,  "请输入正确值！！！","提示",JOptionPane.ERROR_MESSAGE);
									tabModel1.setDataVector(tblData1, ComTitle.tableTitles);
									dataTable1.validate();
									dataTable1.repaint();
								}

//								FrmMain.this.reloadComTable();
								FrmMain.this.reloadComTable(j);
								
							}else if(c == 6) {
								try {
									if(dataTable1.getValueAt(r, c).toString() == null || "".equals(dataTable1.getValueAt(r, c).toString())) {
										throw new BusinessException("单价不可为空！！！");
									}
									if(Float.parseFloat(dataTable1.getValueAt(r, c).toString())<0) {
										throw new BusinessException("单价不可为负数！！！");
									}

									tblData1[r][c] = dataTable1.getValueAt(r, c);
									Commodity com = new Commodity();
									com.setComId(tblData1[r][0].toString());
									com.setBusinessId(tblData2[j][0].toString());
									com.setCounts(Integer.parseInt(tblData1[r][5].toString()));
									com.setEachPrice(Float.parseFloat(tblData1[r][6].toString()));
									
									(new CommodityManager()).modifyCom2bus(com);
									
								}catch(Exception e1) {
									JOptionPane.showMessageDialog(null,  "请输入正确值！！！","提示",JOptionPane.ERROR_MESSAGE);
									tabModel1.setDataVector(tblData1, ComTitle.tableTitles);
									dataTable1.validate();
									dataTable1.repaint();
								}
								
//								FrmMain.this.reloadComTable();
								FrmMain.this.reloadComTable(j);
								
							}else {
								JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);
								tabModel1.setDataVector(tblData1, Commodity.tableTitles);
								dataTable1.validate();
								dataTable1.repaint();
							}
						}else if(model == 2) {
							if(c == 2) {
								tblData1[r][c] = dataTable1.getValueAt(r, c);
								Commodity com = new Commodity();
								com.setCategoryId(tblData1[r][c].toString());
								com.setComName(tblData1[r][c-1].toString());
								com.setComId(tblData1[r][0].toString());
								try {
									(new CommodityManager()).modifyCom(com);
								} catch (BaseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								FrmMain.this.reloadComTable();
								FrmMain.this.reloadComTitleTable(j);
							}else if(c == 1) {
								tblData1[r][c] = dataTable1.getValueAt(r, c);
								Commodity com = new Commodity();
								com.setCategoryId(tblData1[r][c+1].toString());
								com.setComName(tblData1[r][c].toString());
								com.setComId(tblData1[r][0].toString());
								try {
									(new CommodityManager()).modifyCom(com);
								} catch (BaseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								FrmMain.this.reloadComTitleTable(j);

							}else {
								JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);
								tabModel1.setDataVector(tblData1, ComTitle.tableTitles);
								dataTable1.validate();
								dataTable1.repaint();
							}
						}else if(model == 3) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							try {
								if(c == 1) {
									if(dataTable1.getValueAt(r, c).toString() == null || "".equals(dataTable1.getValueAt(r, c).toString())) {
										throw new BusinessException("优惠金额不可为空！！！");
									}
									if(Float.parseFloat(dataTable1.getValueAt(r, c).toString())<0) {
										throw new BusinessException("优惠金额不可为负数！！！");
									}
									
								}else if(c == 2) {
									if(dataTable1.getValueAt(r, c).toString() == null || "".equals(dataTable1.getValueAt(r, c).toString())) {
										throw new BusinessException("订单数不可为空！！！");
									}
									if(Integer.parseInt(dataTable1.getValueAt(r, c).toString())<0) {
										throw new BusinessException("订单数不可为负数！！！");
									}
								}else if(c == 3) {
									if(dataTable1.getValueAt(r, c).toString() == null || "".equals(dataTable1.getValueAt(r, c).toString())) {
										throw new BusinessException("活动开始时间不可为空！！！");
									}
									if(sdf.parse(dataTable1.getValueAt(r, c).toString()).after(sdf.parse(dataTable1.getValueAt(r, c+1).toString()))) {
										throw new BusinessException("活动开始时间不可晚于结束时间！！！");
									}
								}else if(c == 4) {
									if(dataTable1.getValueAt(r, c).toString() == null || "".equals(dataTable1.getValueAt(r, c).toString())) {
										throw new BusinessException("活动结束时间不可为空！！！");
									}
									if(sdf.parse(dataTable1.getValueAt(r, c-1).toString()).after(sdf.parse(dataTable1.getValueAt(r, c).toString()))) {
										throw new BusinessException("活动结束时间不可早于开始时间！！！");
									}
								}else if(c == 5) {
									if(dataTable1.getValueAt(r, c).toString() == null || "".equals(dataTable1.getValueAt(r, c).toString())) {
										throw new BusinessException("有效天数不可为空！！！");
									}
									if(Float.parseFloat(dataTable1.getValueAt(r, c).toString())<0) {
										throw new BusinessException("有效天数不可为负数！！！");
									}
								}else {
									JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);
									tabModel1.setDataVector(tblData1, Coupon.BtableTitles);
									dataTable1.validate();
									dataTable1.repaint();
									return;
								}
								
								tblData1[r][c] = dataTable1.getValueAt(r, c);

							}catch(Exception e3) {
								System.out.println("543");
								JOptionPane.showMessageDialog(null,  "请输入正确值！！！","提示",JOptionPane.ERROR_MESSAGE);
								tabModel1.setDataVector(tblData1, Coupon.BtableTitles);
								dataTable1.validate();
								dataTable1.repaint();
								FrmMain.this.reloadBCouponTable(j);
								return;
							}
							
							Coupon bc = new Coupon();
							bc.setBusinessId(tblData2[j][0].toString());
							bc.setCouponId(tblData1[i][0].toString());
							bc.setDiscountMoney(Float.parseFloat(tblData1[i][1].toString()));
							bc.setNeedOrders(Integer.parseInt(tblData1[i][2].toString()));
							try {
								bc.setStartTime(sdf.parse(tblData1[i][3].toString()));
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								bc.setEndTime(sdf.parse(tblData1[i][4].toString()));
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							bc.setEffectDays(Integer.parseInt(tblData1[i][5].toString()));
							try {
								(new CouponManager()).modifyBcoupon(bc);
							} catch (BaseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}else if(model == 4) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

							if(c == 3) {
								try {
									if(dataTable1.getValueAt(r, c).toString() == null || "".equals(dataTable1.getValueAt(r, c).toString())) {
										throw new BusinessException("优惠券有效截止期不可为空！！！");
									}
								}catch(Exception e4) {
									JOptionPane.showMessageDialog(null,  "请输入正确信息！！！","提示",JOptionPane.ERROR_MESSAGE);
									tabModel1.setDataVector(tblData1, Coupon.CtableTitles);
									dataTable1.validate();
									dataTable1.repaint();
									return;
								}

							}else {
								JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);
								tabModel1.setDataVector(tblData1, Coupon.CtableTitles);
								dataTable1.validate();
								dataTable1.repaint();
								return;
							}
							tblData1[r][c] = dataTable1.getValueAt(r, c);

							Coupon coupon = new Coupon();
							coupon.setUserId(tblData1[j][0].toString());
							coupon.setBusinessId(tblData1[i][0].toString());
							coupon.setCouponId(tblData1[i][2].toString());
							coupon.setOwnOrder(Integer.parseInt(tblData1[i][4].toString()));
							try {
								coupon.setRemoveTime(sdf.parse(tblData1[i][3].toString()));
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								(new CouponManager()).modifyCcoupon(coupon);
							} catch (BaseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							FrmMain.this.reloadCCouponTable(j);
						}else if(model == 5){//用户-订单信息
							JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);
							tabModel1.setDataVector(tblData1, Order.tableTitles);
							dataTable1.validate();
							dataTable1.repaint();
							return;
						}else if(model == 6) {//骑手-订单信息
							JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);
							tabModel1.setDataVector(tblData1, Income.tableTitles);
							dataTable1.validate();
							dataTable1.repaint();
							return;
						}else if(model == 7) {//骑手-统计信息
							JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);
							tabModel1.setDataVector(tblData1, IncomeStatistic.tableTitles);
							dataTable1.validate();
							dataTable1.repaint();
							return;
						}else if(model == 8) {//商家-评论
							JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);
							tabModel1.setDataVector(tblData1, Review.tableTitles);
							dataTable1.validate();
							dataTable1.repaint();
							return;
						}
					}
				}
			});
			
			
			this.dataTable2.getModel().addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					int i = dataTable1.getSelectedRow();
					int j = dataTable2.getSelectedRow();
					int c = e.getColumn();
					int r = e.getFirstRow();
					if(c>=0 && r>=0) {
						if(model == 1 || model == 3 || model == 8) {
							if(c != 1) {
								JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);
								tabModel2.setDataVector(tblData2, Business.tableTitles);
								dataTable2.validate();
								dataTable2.repaint();
							}else {
								try {
									if(dataTable2.getValueAt(r, c).toString() == null || "".equals(dataTable2.getValueAt(r, c).toString()))
										throw new BusinessException("商家名称不可为空！！！");
									tblData2[r][c] = dataTable2.getValueAt(r, c);
									(new BusinessManager()).modifyBusinessName(tblData2[r][c].toString(),dataTable2.getValueAt(r, 0).toString());
								} catch (BaseException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(null,  "商家名不可为空！！！","提示",JOptionPane.ERROR_MESSAGE);
									tabModel2.setDataVector(tblData2, Business.tableTitles);
									dataTable2.validate();
									dataTable2.repaint();
								}
							}
						}else if(model == 2) {//商品类别
							
							if(c != 1) {
								JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);

								tabModel2.setDataVector(tblData2, ComCate.tableTitles);
//								System.out.println(dataTable2.getValueAt(r, c).toString());
//								tabModel2.setValueAt("c", r, c);
//								System.out.println(dataTable2.getValueAt(r, c).toString());
								dataTable2.validate();
								dataTable2.repaint();
//								System.out.println(dataTable2.getValueAt(r, c).toString());

//								System.out.println(dataTable2.getValueAt(r, c).toString().length());
	//
//								dataTable2.setValueAt(tblData2[r][c].toString(), r, c);
//								dataTable2.validate();
//								dataTable2.repaint();
//								if(dataTable2.isEditing()) {
//									System.out.println("正在编辑");
//									dataTable2.getCellEditor().stopCellEditing();
//								}
//									dataTable2.getCellEditor().stopCellEditing();
//								dataTable2.setValueAt(tblData2[r][c].toString(), r, c);
								return;
							}else {
								tblData2[r][c] = dataTable2.getValueAt(r, c);
								try {
									(new CommodityManager()).modifyCateName(dataTable2.getValueAt(r, 0).toString(),dataTable2.getValueAt(r, c).toString());
								} catch (BaseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							}
						}else if(model == 4 || model == 5) {//用户信息
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							try {
								if(c == 1) {//用户名
									if(dataTable2.getValueAt(r, c).toString() == null || "".equals(dataTable2.getValueAt(r, c).toString())) {
										throw new BusinessException("用户名不可为空！！！");
									}
									
								}else if(c == 2) {//性别
									String[] sex = {"男", "女", "无" }; 
									int c1 = 0;
									if(dataTable2.getValueAt(r, c).toString() == null || "".equals(dataTable2.getValueAt(r, c).toString())) {
										throw new BusinessException("性别不可为空！！！");
									}
									for(String s:sex) {
										if(s.equals(dataTable2.getValueAt(r, c).toString())) {
											c1+=1;
										}
									}
									if(c1 == 0) {
										throw new BusinessException("性别内容添加错误！！！");
									}
								}else if(c == 3) {//密码
									if(dataTable2.getValueAt(r, c).toString() == null || "".equals(dataTable2.getValueAt(r, c).toString())) {
										throw new BusinessException("密码不可为空！！！");
									}
								}else if(c == 4) {//绑定手机号码
									if(dataTable2.getValueAt(r, c).toString() == null || "".equals(dataTable2.getValueAt(r, c).toString())) {
										throw new BusinessException("绑定手机号码不可为空！！！");
									}
								}else if(c == 5) {
									
								}else if(c == 6) {//城市
									if(dataTable2.getValueAt(r, c).toString() == null || "".equals(dataTable2.getValueAt(r, c).toString())) {
										throw new BusinessException("所在城市不可为空！！！");
									}

								}else {
									JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);
									tabModel2.setDataVector(tblData2, User.tableTitles);
									dataTable2.validate();
									dataTable2.repaint();
									return;
								}
								
								tblData2[r][c] = dataTable2.getValueAt(r, c);

							}catch(Exception e3) {
								JOptionPane.showMessageDialog(null,  "请输入正确值！！！","提示",JOptionPane.ERROR_MESSAGE);
								tabModel2.setDataVector(tblData2, User.tableTitles);
								dataTable2.validate();
								dataTable2.repaint();
								FrmMain.this.reloadUserTable();
								return;
							}
							
							User user = new User();
							user.setUserId(tblData2[j][0].toString());
							user.setUserName(tblData2[j][1].toString());
							user.setSex(tblData2[j][2].toString());
							user.setPwd(tblData2[j][3].toString());
							user.setPhone(tblData2[j][4].toString());
							user.setEmail(tblData2[j][5].toString());
							user.setCity(tblData2[j][6].toString());
							try {
								(new UserManager()).modifyUser(user);
							} catch (BaseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}else if(model == 6 || model == 7) {//骑手信息
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							try {
								if(c == 1) {//骑手姓名
									if(dataTable2.getValueAt(r, c).toString() == null || "".equals(dataTable2.getValueAt(r, c).toString())) {
										throw new BusinessException("骑手名不可为空！！！");
									}

								}else if(c == 4) {//身份
									String[] identify = {"新人", "正式员工", "单王" }; 
									int c1 = 0;
									if(dataTable2.getValueAt(r, c).toString() == null || "".equals(dataTable2.getValueAt(r, c).toString())) {
										throw new BusinessException("身份不可为空！！！");
									}
									for(String s:identify) {
										if(s.equals(dataTable2.getValueAt(r, c).toString())) {
											c1+=1;
										}
									}
									if(c1 == 0) {
										throw new BusinessException("身份内容添加错误！！！");
									}
								}else if(c == 2) {//就职时间
									if(dataTable2.getValueAt(r, c).toString() == null || "".equals(dataTable2.getValueAt(r, c).toString())) {
										System.out.println("here");
										throw new BusinessException("就职时间不可为空！！！");
									}
									if(dataTable2.getValueAt(r, c+1).toString() != null && !"".equals(dataTable2.getValueAt(r, c+1).toString()) && !"null".equals(dataTable2.getValueAt(r, c+1).toString())) {
										if(sdf.parse(dataTable2.getValueAt(r, c).toString()).after(sdf.parse(dataTable2.getValueAt(r, c+1).toString()))) {
											throw new BusinessException("就职时间不可晚于辞职时间！！！");
										}
									}
									
									
								}else if(c == 3) {//辞职时间
//									if(dataTable2.getValueAt(r, c).toString() == null || "".equals(dataTable2.getValueAt(r, c).toString())) {
//										throw new BusinessException("辞职时间不可为空！！！");
//									}
									if(dataTable2.getValueAt(r, c).toString() != null && !"".equals(dataTable2.getValueAt(r, c).toString())){
										if(sdf.parse(dataTable2.getValueAt(r, c-1).toString()).after(sdf.parse(dataTable2.getValueAt(r, c).toString()))) {
											throw new BusinessException("活动开始时间不可晚于结束时间！！！");
										}
									}
									
								}else {
									JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);
									tabModel2.setDataVector(tblData2, Deliver.tableTitles);
									dataTable2.validate();
									dataTable2.repaint();
									return;
								}
								
								tblData2[r][c] = dataTable2.getValueAt(r, c);

							}catch(Exception e3) {
								System.out.println("123456");
								JOptionPane.showMessageDialog(null,  "请输入正确值！！！","提示",JOptionPane.ERROR_MESSAGE);
								tabModel2.setDataVector(tblData2, Deliver.tableTitles);
								dataTable2.validate();
								dataTable2.repaint();
								FrmMain.this.reloadDeliverTable();
								return;
							}
							
							Deliver deliver = new Deliver();
							deliver.setDeliverId(tblData2[j][0].toString());
							deliver.setDeliverName(tblData2[j][1].toString());
							deliver.setEmployTime(tblData2[j][2].toString());
							deliver.setQuitTime(tblData2[j][3].toString());
							deliver.setIdentity(tblData2[j][4].toString());
							try {
								(new DeliverManager()).modifyDeliver(deliver);
							} catch (BaseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
					}
					
				}
			});
		}else if(User.currentLoginUser!=null) {
			
			menu_InfoManager.add(menuItem_location); menuItem_location.addActionListener(this);
			menu_InfoManager.add(menuItem_orderInfo); menuItem_orderInfo.addActionListener(this);
			menu_InfoManager.add(menuItem_ModifyPwd); menuItem_ModifyPwd.addActionListener(this);

			menu_CartManager.add(menuItem_cart); menuItem_cart.addActionListener(this);
			menu_CartManager.add(menuItem_addgoods); menuItem_addgoods.addActionListener(this);
			
			menubar.add(menu_InfoManager);
			menubar.add(menu_CartManager);
			
			this.setJMenuBar(menubar);
			
		}
		JScrollPane jsp2 = new JScrollPane(this.dataTable2);
		jsp2.setPreferredSize(new Dimension(550,740));
		westBar.add(jsp2);
//		westBar.setPreferredSize(new Dimension(600,800));
		
//	    this.getContentPane().add(new JScrollPane(this.dataTable2), BorderLayout.WEST);
		JScrollPane jsp1 = new JScrollPane(this.dataTable1);
		jsp1.setPreferredSize(new Dimension(950,740));
	    centerBar.add(jsp1);
	    
	    this.add(westBar,BorderLayout.WEST);
	    this.add(centerBar,BorderLayout.CENTER);
//	    centerBar.setPreferredSize(new );

//	    this.dataTable2.addlis
		this.dataTable2.addMouseListener(new MouseAdapter (){
				
				@Override
				public void mouseClicked(MouseEvent e) {
					int i=FrmMain.this.dataTable2.getSelectedRow();
					int j=FrmMain.this.dataTable2.getSelectedColumn();
					if(i<0) {
						return;
					}
					
					if(model == 1)
						FrmMain.this.reloadComTable(i);
					else if(model == 2)
						FrmMain.this.reloadComTitleTable(i);
					else if(model == 3) {
						FrmMain.this.reloadBCouponTable(i);
					}else if(model == 4)
						FrmMain.this.reloadCCouponTable(i);
					else if(model == 5)
						FrmMain.this.reloadOrderTable(i);
					else if(model == 6)
						FrmMain.this.reloadIncomeTable(i);
					else if(model == 7)
						FrmMain.this.reloadIncomeStatisticTable(i);
					else if(model == 8)
						FrmMain.this.reloadReviewTable(i);
				}
				

				}
		
		    );
		
//		this.getContentPane().add(new JScrollPane(this.dataTable1), BorderLayout.CENTER);
	  

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
		if(Admin.currentLoginUser!=null) {
			JLabel label=new JLabel("您好!"+Admin.currentLoginUser.getName());
		    statusBar.add(label);
		}else if(User.currentLoginUser!=null) {
			JLabel label=new JLabel("您好!"+User.currentLoginUser.getUserName());
		    statusBar.add(label);
		}

	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    if(User.currentLoginUser!=null) {
	    	this.reloadBusinessTable();
	    	this.model = 1;
	    }
	    this.setVisible(true);
	
		
	}
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource()==this.menuItem_userManager){
//			FrmUserManager dlg = new FrmUserManager(this,"用户管理",true);
//			dlg.setVisible(true);
//		}else 
		if(e.getSource()==this.menuItem_locationManager) {
			FrmLocationManager dlg = new FrmLocationManager(this,"地址管理",true);
			dlg.setVisible(true);
		}
//		else if(e.getSource()==this.menuItem_deliverManager) {
//			FrmDeliverManager dlg = new FrmDeliverManager(this,"骑手管理",true);
//			dlg.setVisible(true);
//		}
		else if(e.getSource()==this.menuItem_businessManager) {
//			FrmBusinessManager dlg = new FrmBusinessManager(this,"商家管理",true);
//			dlg.setVisible(true);
			
			
		    this.reloadBusinessTable();
		    this.model = 1;
		   
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
				String businessid=this.tblData2[i][0].toString();
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
				String commodityid=this.tblData1[i][0].toString();
				String businessid = this.tblData2[j][0].toString();
				try {
					(new CommodityManager()).deleteCommodity(commodityid, businessid);
					this.reloadBusinessTable();
					this.reloadComTable(j);
					dataTable2.setRowSelectionInterval(j,j);

				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==this.menuItem_ComCate) {
			this.reloadComTable();
			this.model = 2;
		}else if(e.getSource()==this.menuItem_addComCate) {
			FrmCommodityManager_AddCommodity_2 dlg=new FrmCommodityManager_AddCommodity_2(this,"添加商品类型",true);
			dlg.setVisible(true);
			if(dlg.getComTitle()!=null){//刷新表格
				this.reloadComTable();
			}
		}else if(e.getSource()==this.menuItem_deleteComCate) {
			int i=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品类型","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该商品类型吗？(若是则删除所有该类的所有商品信息)","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String comcateid=this.tblData2[i][0].toString();
				try {
					(new CommodityManager()).deleteComCate(comcateid);
					this.reloadComTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		}else if(e.getSource()==this.menuItem_resetComCate) {
			int i=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品类型","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定重新上架该类商品吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String comcateid=this.tblData2[i][0].toString();
				try {
					(new CommodityManager()).resetComCate(comcateid);
					this.reloadComTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==this.menuItem_addCommodity) {
			int i=this.dataTable2.getSelectedRow();
			FrmCommodityManager_AddCommodity_1 dlg = null;
			if(i<0 || dataTable2.getColumnName(0)!="类型编号") {
				dlg=new FrmCommodityManager_AddCommodity_1(this,"添加商品",true,1);
//				JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
//				return;
			}				
			else {
				dlg=new FrmCommodityManager_AddCommodity_1(this,"添加商品",true);
				dlg.curCate = curCate;
			}
	
			dlg.setVisible(true);
			if(dlg.getComTitle()!=null){//刷新表格
				this.reloadComTitleTable(i);
			}
		}else if(e.getSource()==this.menuItem_deleteCommodity) {
			int i=this.dataTable1.getSelectedRow();
			int j=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该商品吗？(若是则将删除所有该商品信息！)","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String commodityid=this.tblData1[i][0].toString();
				try {
					(new CommodityManager()).deleteComTitle(commodityid);
					this.reloadComTable();
					this.reloadComTitleTable(j);
					dataTable2.setRowSelectionInterval(j,j);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		}else if(e.getSource()==this.menuItem_resetCommodity) {
			int i=this.dataTable1.getSelectedRow();
			int j=this.dataTable2.getSelectedRow();

			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定重新上架该商品吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String commodityid=this.tblData1[i][0].toString();
				try {
					(new CommodityManager()).resetComTitle(commodityid);
					this.reloadComTable();
					this.reloadComTitleTable(j);
					dataTable2.setRowSelectionInterval(j,j);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==this.menuItem_BCoupon) {
			this.model = 3;
			this.reloadBusinessTable();
//			dataTable2.setRowSelectionInterval(1,1);
			
			
		}else if(e.getSource()==this.menuItem_CCoupon) {
			this.model = 4;
			this.reloadUserTable();
//			dataTable2.setRowSelectionInterval(1,1);
			
		}else if(e.getSource()==this.menuItem_addCoupon) {
			int i=this.dataTable2.getSelectedRow();
			int j=this.dataTable1.getSelectedRow();

			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商家或用户","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			FrmAddCoupon dlg = null;
			if(model == 3) {
				 dlg = new FrmAddCoupon(this,"添加商家优惠券信息",true,3);
				 dlg.id = this.tblData2[i][0].toString();
				 dlg.setVisible(true);
					if(dlg.getCoupon()!=null){//刷新表格
						this.reloadBCouponTable(i);
					}
			}else if(model == 4) {
				 dlg = new FrmAddCoupon(this,"添加用户优惠券信息",true,4);
				 dlg.id = this.tblData2[i][0].toString();
				 dlg.setVisible(true);
					if(dlg.getCoupon()!=null){//刷新表格
						this.reloadCCouponTable(i);
					}
			}
	
		}else if(e.getSource()==this.menuItem_deleteCoupon) {
			int i=this.dataTable2.getSelectedRow();
			int j=this.dataTable1.getSelectedRow();

			if(j<0) {
				JOptionPane.showMessageDialog(null,  "请选择优惠券","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			if(model == 3) {
				if(JOptionPane.showConfirmDialog(this,"确定删除该优惠券吗？(若是则将删除所有该优惠券信息！)","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					String couponid=this.tblData1[j][0].toString();
					try {
						(new CouponManager()).deleteBCoupon(couponid);
						this.reloadBusinessTable();
						this.reloadBCouponTable(j);
						dataTable2.setRowSelectionInterval(j,j);
					} catch (BaseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}else if(model == 4) {
				if(JOptionPane.showConfirmDialog(this,"确定删除该用户的优惠券吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					int order = Integer.parseInt(tblData1[j][4].toString());
					
					try {
						(new CouponManager()).deleteCCoupon(order);
						this.reloadUserTable();
						this.reloadCCouponTable(i);
						dataTable2.setRowSelectionInterval(i,i);
					} catch (BaseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
			
		}else if(e.getSource()==this.menuItem_fullReductionManager) {
			FrmFullReductionManager dlg = new FrmFullReductionManager(this,"满减活动",true);
			dlg.setVisible(true);
		}else if(e.getSource()==this.menuItem_order) {
			this.reloadUserTable();
			this.model = 5;
		}else if(e.getSource()==this.menuItem_addUser) {
			FrmUserManager_AddUser dlg=new FrmUserManager_AddUser(this,"添加账号",true);
			dlg.setVisible(true);
			if(dlg.getUser()!=null){//刷新表格
				this.reloadUserTable();
			}
		}else if(e.getSource()==this.menuItem_deleteUser) {
			int i=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择账号","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除账号吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String userid=this.tblData2[i][0].toString();
				try {
					(new UserManager()).deleteUser(userid);
					this.reloadUserTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==this.menuItem_resetUser) {
			int i=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择账号","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定重置密码吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String userid=this.tblData2[i][0].toString();
				try {
					(new UserManager()).resetUserPwd(userid);
					this.reloadUserTable();
					JOptionPane.showMessageDialog(null,  "密码重置完成","提示",JOptionPane.INFORMATION_MESSAGE);
					
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==this.menuItem_Deliver) {
			this.model = 6;
			this.reloadDeliverTable();
		}else if(e.getSource()==this.menuItem_addDeliver) {
			FrmDeliverManager_AddDeliver dlg=new FrmDeliverManager_AddDeliver(this,"添加骑手",true);
			dlg.setVisible(true);
			if(dlg.getDeliver()!=null){//刷新表格
				this.reloadDeliverTable();
			}
			
		}else if(e.getSource()==this.menuItem_deleteDeliver) {
			int i=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择骑手","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该骑手信息吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String deliverid=this.tblData2[i][0].toString();
				try {
					(new DeliverManager()).deleteDeliver(deliverid);
					this.reloadDeliverTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==menuItem_ModifyPwd) {
			int m = 0;//1：管理员，2：用户
			if(User.currentLoginUser!=null)
				m = 2;
			else 
				m = 1;
			System.out.println(m);
			FrmModifyPwd dlg=new FrmModifyPwd(this,"密码修改",true, m);
			dlg.setVisible(true);
		}else if(e.getSource()==menuItem_Income) {
			this.model = 7;
			this.reloadDeliverTable();
		}else if(e.getSource()==menuItem_businessReview) {
			this.model = 8;
			this.reloadBusinessTable();
		}else if(e.getSource()==menuItem_cart) {
			int i=this.dataTable2.getSelectedRow();

			FrmCartManager dlg = new FrmCartManager(this,"购物车管理",true, i);
			dlg.setVisible(true);
		}else if(e.getSource()==menuItem_addgoods) {
			int i=this.dataTable2.getSelectedRow();
			int j=this.dataTable1.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择一个商品！！！","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			FrmCartManager_Add dlg = new FrmCartManager_Add(this,"添加购物车",true);
			dlg.businessid = tblData2[i][0].toString();
			dlg.comid = tblData1[j][0].toString();
			dlg.price = Float.parseFloat(tblData1[j][6].toString());
			dlg.businessname = tblData2[i][1].toString();
			dlg.comname = tblData1[j][1].toString();
			dlg.counts = Integer.parseInt(tblData1[j][5].toString());
			dlg.setVisible(true);
		}else if(e.getSource()==menuItem_location) {
			FrmLocationManager dlg = new FrmLocationManager(this,"地址信息管理",true);
			dlg.setVisible(true);
		}else if(e.getSource()==menuItem_orderInfo) {
			FrmOrderManager dlg = new FrmOrderManager(this,"订单查看",true);
			dlg.setVisible(true);
		}
	}
}
