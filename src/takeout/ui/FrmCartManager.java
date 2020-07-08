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


import takeout.control.CartManager;
import takeout.model.Cart;
import takeout.model.User;
import takeout.util.BaseException;


public class FrmCartManager extends JDialog implements ActionListener{
	private FrmCommodityManager a = null;
	private JPanel toolBar = new JPanel();
	private Button btnBuy = new Button("�µ�");
	private Button btnDelete = new Button("ɾ����Ʒ��Ŀ");
//	private Button btnReset = new Button("�ϼ���Ʒ");
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable cartsTable=new JTable(tablmod);
	
	private void reloadCartsTable(){
		try {
			System.out.println("123");
			List<Cart> carts=(new CartManager()).loadAllCarts(User.currentLoginUser.getUserId());
			tblData = new Object[carts.size()][Cart.tableTitles.length];
			for(int i=0;i<carts.size();i++){
				for(int j=0;j<Cart.tableTitles.length;j++) {
					tblData[i][j] = carts.get(i).getCell(j); 
				}
			}
			tablmod.setDataVector(tblData, Cart.tableTitles);
			this.cartsTable.validate();
			this.cartsTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmCartManager(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
//		this.a = frmMain;
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnBuy);
		toolBar.add(this.btnDelete);
//		toolBar.add(btnReset);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������
		this.reloadCartsTable();
		this.getContentPane().add(new JScrollPane(this.cartsTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.validate();
		
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
			FrmBuyManager dlg = new FrmBuyManager();
			dlg.setVisible(true);
			if(dlg.getFullReduction()!=null){//ˢ�±��
				this.reloadCartsTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.cartsTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ����Ʒ��Ŀ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ������Ʒ��Ŀ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String businessid=this.tblData[i][0].toString();
				String comid = this.tblData[i][2].toString();
				try {
					(new CartManager()).deleteCart(User.currentLoginUser.getUserId(),businessid,comid);
					this.reloadCartsTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
		
		
	

}
	
