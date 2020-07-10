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
import takeout.control.OrderManager;
import takeout.model.Order;
import takeout.model.Order;
import takeout.model.User;
import takeout.util.BaseException;


public class FrmOrderManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
//	private Button btnAdd = new Button("��ӵ�ַ��Ϣ");
	private FrmMain fm = null;
	private Button btnLook = new Button("�鿴��������");
	private Button btnDelete = new Button("�˵�");
	private Button btnReview = new Button("����");
	private Button btnLookReview = new Button("�鿴��������");

	
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable orderTable=new JTable(tablmod);
	
	
	private Object tblData1[][];
	DefaultTableModel tabModel1=new DefaultTableModel();
	public JTable dataTable1=new JTable(tabModel1);
	
	Order curOrder = null; 
	List<Order>	allOrders = null;
	
	
	public void reloadOrderTable(){
		try {
			allOrders=(new OrderManager()).loadAllOrders(User.currentLoginUser.getUserId(), null, 1);//1����Ҫ����2����ϸ����
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
	
	public FrmOrderManager(FrmMain f, String s, boolean b) {
		super(f, s, b);
		this.fm = f;
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
//		toolBar.add(btnAdd);
		toolBar.add(btnLook);
		toolBar.add(this.btnDelete);
		toolBar.add(btnReview);
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������
		this.reloadOrderTable();
		this.getContentPane().add(new JScrollPane(this.orderTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		this.validate();
		
//		this.btnAdd.addActionListener(this);
		this.btnLook.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnReview.addActionListener(this);
		
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
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource()==this.btnAdd){
//			FrmLocationManager_AddLocation dlg=new FrmLocationManager_AddLocation(this,"��ӵ�ַ��Ϣ",true);
//			dlg.setVisible(true);
//			if(dlg.getLocation1()!=null){//ˢ�±��
//				this.reloadLocationTable();
//			}
//		}
//		else 
		if(e.getSource()==this.btnDelete){
			int i=this.orderTable.getSelectedRow();
			String businessid = tblData[i][1].toString();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ�񶩵�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ���˵��ö�����","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					(new OrderManager()).deleteOrder(User.currentLoginUser.getUserId(), tblData[i][0].toString(),businessid);
					this.reloadOrderTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==this.btnLook) {
			int i=this.orderTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ�񶩵�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			FrmOrderInfo fom = new FrmOrderInfo(this, "��������",true, i, allOrders);
			fom.setVisible(true);
			
		}else if(e.getSource()==this.btnReview) {
			int i=this.orderTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ�񶩵�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!allOrders.get(i).getStatus().equals("���ʹ�")) {
				JOptionPane.showMessageDialog(null,  "ֻ���������ʹ�Ķ���������","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(allOrders.get(i).getIsReviewed()!=0) {
				JOptionPane.showMessageDialog(null,  "�����۹��ö���������","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			curOrder = allOrders.get(i);
			FrmReview fr = new FrmReview(this, "���۶���", true, curOrder);
			fr.setVisible(true);
			this.reloadOrderTable();
			fm.reloadBusinessTable();
		}else if(e.getSource() == this.btnLookReview) {
			
			
		}
	}
		
		
	

}
	
