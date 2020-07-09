package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import takeout.model.Order;
import takeout.model.User;
import takeout.util.BaseException;


public class FrmOrderManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
//	private Button btnAdd = new Button("添加地址信息");
	private Button btnDelete = new Button("退单");
	
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable orderTable=new JTable(tablmod);
	
	private void reloadOrderTable(){
		try {
			List<Order>	orders=(new OrderManager()).loadAllOrders(User.currentLoginUser.getUserId());
			tblData = new Object[orders.size()][Order.tableTitles.length];
			for(int i=0;i<orders.size();i++){
				for(int j=0;j<Order.tableTitles.length;j++) {
					tblData[i][j] = orders.get(i).getCell(j); 
				}
			}
			tablmod.setDataVector(tblData, Order.tableTitles);
			this.orderTable.validate();
			this.orderTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmOrderManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
//		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadOrderTable();
		this.getContentPane().add(new JScrollPane(this.orderTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		this.validate();
		
//		this.btnAdd.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource()==this.btnAdd){
//			FrmLocationManager_AddLocation dlg=new FrmLocationManager_AddLocation(this,"添加地址信息",true);
//			dlg.setVisible(true);
//			if(dlg.getLocation1()!=null){//刷新表格
//				this.reloadLocationTable();
//			}
//		}
//		else 
		if(e.getSource()==this.btnDelete){
			int i=this.orderTable.getSelectedRow();
			String businessid = tblData[i][1].toString();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定退掉该订单吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					(new OrderManager()).deleteOrder(User.currentLoginUser.getUserId(), tblData[i][0].toString(),businessid);
					this.reloadOrderTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
		
		
	

}
	
