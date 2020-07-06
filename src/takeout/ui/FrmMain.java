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
import takeout.control.CouponManager;
import takeout.control.OrderManager;
import takeout.control.UserManager;
import takeout.model.Admin;
import takeout.model.Business;
import takeout.model.ComCate;
import takeout.model.ComTitle;
import takeout.model.Commodity;
import takeout.model.Coupon;
import takeout.model.Deliver;
import takeout.model.Order;
import takeout.model.User;
import takeout.ui.FrmLogin;
import takeout.util.BaseException;



public class FrmMain extends JFrame implements ActionListener{
	private JMenuBar menubar=new JMenuBar();
	private JMenu menu_Manager=new JMenu("ϵͳ����");
	private JMenu menu_Business = new JMenu("�̼ҹ���");
	private JMenu menu_ComCate = new JMenu("��Ʒ�����");
	private JMenu menu_Com = new JMenu("��Ʒ����");
	private JMenu menu_User = new JMenu("�û�����");
	
	private JMenu menu_Coupon = new JMenu("�Ż�ȯ����");
	private int model = 0;//1:�̼�-��Ʒ��2:��Ʒ���-��Ʒ��3:�̼�-�Ż�ȯ��4:�û�-�Ż�ȯ��5:�û�-������6:����-����
//	private JMenu menu_commoditycategoryManager = new JMenuItem("��Ʒ������");
	
	private JMenuItem menuItem_deleteCom = new JMenuItem("ɾ���̼���Ʒ��Ϣ");
	private JMenuItem menuItem_addCom = new JMenuItem("����̼���Ʒ��Ϣ");
//	private JMenuItem menuItem_userManager = new JMenuItem("�û�����");
	private JMenuItem menuItem_deliverManager = new JMenuItem("���ֹ���");
//	private JMenuItem menuItem_couponManager = new JMenuItem("�Ż�ȯ����");
	private JMenuItem menuItem_fullReductionManager = new JMenuItem("��������");
	private JMenuItem menuItem_orderManager = new JMenuItem("��������");
	private JMenuItem menuItem_locationManager = new JMenuItem("��ַ��Ϣ����");
	private JMenuItem menuItem_adminManager = new JMenuItem("����Ա��Ϣ����");
	
	private JMenuItem menuItem_ComCate = new JMenuItem("�鿴��Ʒ��");
	private JMenuItem menuItem_addComCate = new JMenuItem("������Ʒ��");
	private JMenuItem menuItem_deleteComCate = new JMenuItem("ɾ����Ʒ��");
	private JMenuItem menuItem_resetComCate = new JMenuItem("�ϼ���Ʒ��");
	
	private JMenuItem menuItem_businessManager = new JMenuItem("�鿴�̼���Ϣ");
	private JMenuItem menuItem_addBus = new JMenuItem("����̼�");
	private JMenuItem menuItem_deleteBus = new JMenuItem("ɾ���̼�");
	private JMenuItem menuItem_commodityManager = new JMenuItem("��Ʒȫ�ֹ���");

	private JMenuItem menuItem_addCommodity = new JMenuItem("�������Ʒ");
	private JMenuItem menuItem_deleteCommodity = new JMenuItem("ɾ����Ʒ");
	private JMenuItem menuItem_resetCommodity = new JMenuItem("�ϼ���Ʒ");
	
	private JMenuItem menuItem_BCoupon = new JMenuItem("�鿴�̼�-�Ż�ȯ��Ϣ");
	private JMenuItem menuItem_CCoupon = new JMenuItem("�鿴�û�-�Ż�ȯ��Ϣ");

	private JMenuItem menuItem_addCoupon = new JMenuItem("����Ż�ȯ��Ϣ");
	private JMenuItem menuItem_deleteCoupon = new JMenuItem("ɾ���Ż�ȯ��Ϣ");

	private JMenuItem menuItem_order = new JMenuItem("�鿴�û���Ϣ");
	private JMenuItem menuItem_addUser = new JMenuItem("����û�");
	private JMenuItem menuItem_deleteUser = new JMenuItem("ɾ���û�");

	
	List<Business> allBusiness = null;
	List<Commodity> allCom = null;
	List<ComCate> allCate = null;
	List<ComTitle> allComTitle = null;
	List<Coupon> allCoupon = null;
	List<User> allUser = null;
	List<Order> allOrder = null;
	
	
	private Business curBus = null; 
	private ComCate curCate = null; 
	private User curUser = null;
	private Deliver curdeliver = null;
	
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	
	private Object tblBusinessData[][];
//	DefaultTableModel tabBusinessModel=new DefaultTableModel();
//	private JTable dataTableBusiness=new JTable(tabBusinessModel);
	
	DefaultTableModel tabModel1=new DefaultTableModel();
	private JTable dataTable1=new JTable(tabModel1);
	
	DefaultTableModel tabModel2=new DefaultTableModel();
	private JTable dataTable2=new JTable(tabModel2);
	
	private void reloadBusinessTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			allBusiness = new BusinessManager().loadAllBusiness(true);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
		tabModel1.setRowCount(0);
	}
	private Object tblUserData[][];
	
	private void reloadUserTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			allUser = new UserManager().loadAllUsers(true);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblUserData =  new Object[allUser.size()][User.tableTitles.length];
		for(int i=0;i<allUser.size();i++){
			for(int j=0;j<User.tableTitles.length;j++)
				tblUserData[i][j]=allUser.get(i).getCell(j);
		}
		tabModel2.setDataVector(tblUserData,User.tableTitles);
		this.dataTable2.validate();
		this.dataTable2.repaint();
		tabModel1.setRowCount(0);
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
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
//	public JTable comCateTable=new JTable(tablComCateMod);//��Ʒ���
	
	private void reloadComTable(){
		try {
			allCate = (new CommodityManager()).loadAllComCates();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
		tabModel1.setRowCount(0);
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
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
	
	private Object tblBCouponData[][];
	
	private void reloadBCouponTable(int businessIdx){
		if(businessIdx<0) return;
			curBus = allBusiness.get(businessIdx);
		try {
			allCoupon = new CouponManager().loadAllBCoupons(curBus);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tblBCouponData = new Object[allCoupon.size()][Coupon.BtableTitles.length];
		for(int i=0;i<allCoupon.size();i++){
			for(int j=0;j<Coupon.BtableTitles.length;j++) {
				tblBCouponData[i][j] = allCoupon.get(i).getBCell(j); 
			}
		}
		tabModel1.setDataVector(tblBCouponData, Coupon.BtableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
	private Object tblCCouponData[][];
//	private Object realdata[][];
	
	private void reloadCCouponTable(int userIdx){
		if(userIdx<0) return;
			curUser = allUser.get(userIdx);
		try {
			allCoupon = new CouponManager().loadAllCCoupons(curUser);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tblCCouponData = new Object[allCoupon.size()][Coupon.CtableTitles1.length];
//		realdata = new Object[allCoupon.size()][Coupon.CtableTitles.length];
		for(int i=0;i<allCoupon.size();i++){
			for(int j=0;j<Coupon.CtableTitles1.length;j++) {
//				realdata[i][j] = allCoupon.get(i).getCCell(j);
//				if(j<Coupon.CtableTitles.length-1) {
					tblCCouponData[i][j] = allCoupon.get(i).getCCell(j);
//				}
			}
		}
		tabModel1.setDataVector(tblCCouponData, Coupon.CtableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
	private Object tblOrderData[][];
	
	private void reloadOrderTable(int userIdx){
		if(userIdx<0) return;
			curUser = allUser.get(userIdx);
		try {
			allOrder = new OrderManager().loadAllOrders(curUser.getUserId());
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tblOrderData = new Object[allOrder.size()][Order.tableTitles.length];
		for(int i=0;i<allOrder.size();i++){
			for(int j=0;j<Order.tableTitles.length;j++) {
				tblOrderData[i][j] = allOrder.get(i).getCell(j); 
			}
		}
		tabModel1.setDataVector(tblOrderData, Order.tableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
	
	public FrmMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("��������");
		
		dlgLogin=new FrmLogin(this,"��½",true);
		dlgLogin.setVisible(true);
		
//		menu_Manager.add(menuItem_userManager); menuItem_userManager.addActionListener(this);
		menu_Manager.add(menuItem_deliverManager); menuItem_deliverManager.addActionListener(this);
//		menu_Manager.add(menuItem_commoditycategoryManager); menuItem_commoditycategoryManager.addActionListener(this);
//		menu_Manager.add(menuItem_couponManager); menuItem_couponManager.addActionListener(this);
		menu_Manager.add(menuItem_fullReductionManager); menuItem_fullReductionManager.addActionListener(this);
		menu_Manager.add(menuItem_orderManager); menuItem_orderManager.addActionListener(this);
		menu_Manager.add(menuItem_locationManager); menuItem_locationManager.addActionListener(this);
		menu_Manager.add(menuItem_adminManager); menuItem_adminManager.addActionListener(this);
		
		menu_Business.add(menuItem_businessManager); menuItem_businessManager.addActionListener(this);
		menu_Business.add(menuItem_addBus); menuItem_addBus.addActionListener(this);
		menu_Business.add(menuItem_deleteBus);menuItem_deleteBus.addActionListener(this);
		menu_Business.add(menuItem_addCom); menuItem_addCom.addActionListener(this);
		menu_Business.add(menuItem_deleteCom); menuItem_deleteCom.addActionListener(this);
		menu_Business.add(menuItem_commodityManager); menuItem_commodityManager.addActionListener(this);

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

		menubar.add(menu_Manager);
		menubar.add(menu_Business);
		menubar.add(menu_ComCate);
		menubar.add(menu_Com);
		menubar.add(menu_Coupon);
		menubar.add(menu_User);
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
	    JLabel label=new JLabel("����!"+Admin.currentLoginUser.getName());
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
//		if(e.getSource()==this.menuItem_userManager){
//			FrmUserManager dlg = new FrmUserManager(this,"�û�����",true);
//			dlg.setVisible(true);
//		}else 
		if(e.getSource()==this.menuItem_locationManager) {
			FrmLocationManager dlg = new FrmLocationManager(this,"��ַ����",true);
			dlg.setVisible(true);
		}else if(e.getSource()==this.menuItem_deliverManager) {
			FrmDeliverManager dlg = new FrmDeliverManager(this,"���ֹ���",true);
			dlg.setVisible(true);
		}else if(e.getSource()==this.menuItem_businessManager) {
//			FrmBusinessManager dlg = new FrmBusinessManager(this,"�̼ҹ���",true);
//			dlg.setVisible(true);
			
			
		    this.reloadBusinessTable();
		    this.model = 1;
		   
		}else if(e.getSource()==this.menuItem_commodityManager) {
			FrmCommodityManager dlg = new FrmCommodityManager(this, "��Ʒ����",true);
			dlg.setVisible(true);
		}else if(e.getSource()==this.menu_Business) {
			
		}else if(e.getSource()==this.menuItem_addBus) {
			FrmBusinessManager_AddBusiness dlg=new FrmBusinessManager_AddBusiness(this,"����̼�",true);
			dlg.setVisible(true);
			if(dlg.getBusiness()!=null){//ˢ�±��
				this.reloadBusinessTable();
			}
		}else if(e.getSource()==menuItem_deleteBus) {
			int i=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���̼�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ�����̼���Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String businessid=this.tblBusinessData[i][0].toString();
				try {
					(new BusinessManager()).deleteBusiness(businessid);
					this.reloadBusinessTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==menuItem_addCom) {
			int i=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���̼�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			FrmCommodityManager_AddCommodity dlg=new FrmCommodityManager_AddCommodity(this,"�����Ʒ��Ϣ",true);
			dlg.bus = curBus;
			
			dlg.setVisible(true);
			if(dlg.getCommodity()!=null){//ˢ�±��
				this.reloadComTable(i);
			}

		}else if(e.getSource()==menuItem_deleteCom) {
			int i=this.dataTable1.getSelectedRow();
			int j=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ����Ʒ��Ϣ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ������Ʒ��Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String commodityid=this.tblComData[i][0].toString();
				String businessid = this.tblBusinessData[j][0].toString();
				try {
					(new CommodityManager()).deleteCommodity(commodityid, businessid);
					this.reloadBusinessTable();
					this.reloadComTable(j);
					dataTable2.setRowSelectionInterval(j,j);

				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==this.menuItem_ComCate) {
			this.reloadComTable();
			this.model = 2;
		}else if(e.getSource()==this.menuItem_addComCate) {
			FrmCommodityManager_AddCommodity_2 dlg=new FrmCommodityManager_AddCommodity_2(this,"�����Ʒ����",true);
			dlg.setVisible(true);
			if(dlg.getComTitle()!=null){//ˢ�±��
				this.reloadComTable();
			}
		}else if(e.getSource()==this.menuItem_deleteComCate) {
			int i=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ����Ʒ����","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ������Ʒ������(������ɾ�����и����������Ʒ��Ϣ)","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String comcateid=this.tblComCateData[i][0].toString();
				try {
					(new CommodityManager()).deleteComCate(comcateid);
					this.reloadComTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		}else if(e.getSource()==this.menuItem_resetComCate) {
			int i=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ����Ʒ����","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ�������ϼܸ�����Ʒ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String comcateid=this.tblComCateData[i][0].toString();
				try {
					(new CommodityManager()).resetComCate(comcateid);
					this.reloadComTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==this.menuItem_addCommodity) {
			int i=this.dataTable2.getSelectedRow();
			FrmCommodityManager_AddCommodity_1 dlg = null;
			if(i<0 || dataTable2.getColumnName(0)!="���ͱ��") {
				dlg=new FrmCommodityManager_AddCommodity_1(this,"�����Ʒ",true,1);
//				JOptionPane.showMessageDialog(null,  "��ѡ����Ʒ","��ʾ",JOptionPane.ERROR_MESSAGE);
//				return;
			}				
			else {
				dlg=new FrmCommodityManager_AddCommodity_1(this,"�����Ʒ",true);
				dlg.curCate = curCate;
			}
	
			dlg.setVisible(true);
			if(dlg.getComTitle()!=null){//ˢ�±��
				this.reloadComTitleTable(i);
			}
		}else if(e.getSource()==this.menuItem_deleteCommodity) {
			int i=this.dataTable1.getSelectedRow();
			int j=this.dataTable2.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ����Ʒ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ������Ʒ��(������ɾ�����и���Ʒ��Ϣ��)","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String commodityid=this.tblComTitleDate[i][0].toString();
				try {
					(new CommodityManager()).deleteComTitle(commodityid);
					this.reloadComTable();
					this.reloadComTitleTable(j);
					dataTable2.setRowSelectionInterval(j,j);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		}else if(e.getSource()==this.menuItem_resetCommodity) {
			int i=this.dataTable1.getSelectedRow();
			int j=this.dataTable2.getSelectedRow();

			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ����Ʒ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ�������ϼܸ���Ʒ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String commodityid=this.tblComTitleDate[i][0].toString();
				try {
					(new CommodityManager()).resetComTitle(commodityid);
					this.reloadComTable();
					this.reloadComTitleTable(j);
					dataTable2.setRowSelectionInterval(j,j);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null,  "��ѡ���̼һ��û�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			FrmAddCoupon dlg = null;
			if(model == 3) {
				 dlg = new FrmAddCoupon(this,"����̼��Ż�ȯ��Ϣ",true,3);
				 dlg.id = this.tblBusinessData[i][0].toString();
				 dlg.setVisible(true);
					if(dlg.getCoupon()!=null){//ˢ�±��
						this.reloadBCouponTable(i);
					}
			}else if(model == 4) {
				 dlg = new FrmAddCoupon(this,"����û��Ż�ȯ��Ϣ",true,4);
				 dlg.id = this.tblBusinessData[i][0].toString();
				 dlg.setVisible(true);
					if(dlg.getCoupon()!=null){//ˢ�±��
						this.reloadCCouponTable(i);
					}
			}
	
		}else if(e.getSource()==this.menuItem_deleteCoupon) {
			int i=this.dataTable2.getSelectedRow();
			int j=this.dataTable1.getSelectedRow();

			if(j<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���Ż�ȯ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			if(model == 3) {
				if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ�����Ż�ȯ��(������ɾ�����и��Ż�ȯ��Ϣ��)","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					String couponid=this.tblBCouponData[j][0].toString();
					try {
						(new CouponManager()).deleteBCoupon(couponid);
						this.reloadBusinessTable();
						this.reloadBCouponTable(j);
						dataTable2.setRowSelectionInterval(j,j);
					} catch (BaseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}else if(model == 4) {
				if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ�����û����Ż�ȯ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					int order = Integer.parseInt(tblCCouponData[j][4].toString());
					
					try {
						(new CouponManager()).deleteCCoupon(order);
						this.reloadUserTable();
						this.reloadCCouponTable(i);
						dataTable2.setRowSelectionInterval(i,i);
					} catch (BaseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
			
		}else if(e.getSource()==this.menuItem_fullReductionManager) {
			FrmFullReductionManager dlg = new FrmFullReductionManager(this,"�����",true);
			dlg.setVisible(true);
		}else if(e.getSource()==this.menuItem_order) {
			this.reloadUserTable();
			this.model = 5;
		}
	}
}
