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


import takeout.model.Admin;
import takeout.ui.FrmLogin;



public class FrmMain extends JFrame implements ActionListener{
	private JMenuBar menubar=new JMenuBar();
	private JMenu menu_Manager=new JMenu("ϵͳ����");
	
	private JMenuItem menuItem_userManager = new JMenuItem("�û�����");
	private JMenuItem menuItem_deliverManager = new JMenuItem("���ֹ���");
	private JMenuItem menuItem_commodityManager = new JMenuItem("��Ʒ����");
	private JMenuItem menuItem_commoditycategoryManager = new JMenuItem("��Ʒ������");
	private JMenuItem menuItem_businessManager = new JMenuItem("�̼ҹ���");
	private JMenuItem menuItem_couponManager = new JMenuItem("�Ż�ȯ����");
	private JMenuItem menuItem_fullReductionManager = new JMenuItem("��������");
	private JMenuItem menuItem_orderManager = new JMenuItem("��������");
	private JMenuItem menuItem_locationManager = new JMenuItem("��ַ��Ϣ����");
	private JMenuItem menuItem_adminManager = new JMenuItem("����Ա��Ϣ����");
	
	
	
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	public FrmMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("��������");
		
		dlgLogin=new FrmLogin(this,"��½",true);
		dlgLogin.setVisible(true);
		
		menu_Manager.add(menuItem_userManager); menuItem_userManager.addActionListener(this);
		menu_Manager.add(menuItem_deliverManager); menuItem_deliverManager.addActionListener(this);
		menu_Manager.add(menuItem_commodityManager); menuItem_commodityManager.addActionListener(this);
		menu_Manager.add(menuItem_commoditycategoryManager); menuItem_commoditycategoryManager.addActionListener(this);
		menu_Manager.add(menuItem_businessManager); menuItem_businessManager.addActionListener(this);
		menu_Manager.add(menuItem_couponManager); menuItem_couponManager.addActionListener(this);
		menu_Manager.add(menuItem_fullReductionManager); menuItem_fullReductionManager.addActionListener(this);
		menu_Manager.add(menuItem_orderManager); menuItem_orderManager.addActionListener(this);
		menu_Manager.add(menuItem_locationManager); menuItem_locationManager.addActionListener(this);
		menu_Manager.add(menuItem_adminManager); menuItem_adminManager.addActionListener(this);
		menubar.add(menu_Manager);
		this.setJMenuBar(menubar);
		
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
		if(e.getSource()==this.menuItem_userManager){
			FrmUserManager dlg = new FrmUserManager(this,"�û�����",true);
			dlg.setVisible(true);
		}
	}
}
