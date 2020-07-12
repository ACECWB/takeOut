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
import java.util.Date;
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
import takeout.control.CommodityManager;
import takeout.control.UserManager;
import takeout.model.Commodity;
import takeout.model.User;
import takeout.util.BaseException;


public class FrmRecommend extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加到购物车");

	
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable goodsTable=new JTable(tablmod);
	
	
//	private Object tblData1[][];
//	DefaultTableModel tabModel1=new DefaultTableModel();
//	public JTable dataTable1=new JTable(tabModel1);
	
	List<Commodity> allComs = null;
	
	private void reloadRecomsTable(){
		try {
			allComs=(new CommodityManager()).loadAllRecoms();
			tblData = new Object[allComs.size()][Commodity.RtableTitles.length];
			for(int i=0;i<allComs.size();i++){
				for(int j=0;j<Commodity.RtableTitles.length;j++) {
					tblData[i][j] = allComs.get(i).getRCell(j); 
				}
			}
			tablmod.setDataVector(tblData, Commodity.RtableTitles);
			this.goodsTable.validate();
			this.goodsTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmRecommend(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);

		//提取现有数据
		this.reloadRecomsTable();
		this.getContentPane().add(new JScrollPane(this.goodsTable), BorderLayout.CENTER);
		this.btnAdd.addActionListener(this);
		// 屏幕居中显示
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.validate();

}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd) {
			int i=this.goodsTable.getSelectedRow();

			FrmCartManager_Add dlg = new FrmCartManager_Add(this,"添加购物车",true);
			dlg.businessid = tblData[i][0].toString();
			dlg.businessname = tblData[i][1].toString();
			dlg.counts = Integer.parseInt(tblData[i][5].toString());
			if(User.currentLoginUser.getVipEndTime()==null || User.currentLoginUser.getVipEndTime().before(new Date()))
				dlg.price = Float.parseFloat(tblData[i][6].toString());
			else
				dlg.price = Float.parseFloat(tblData[i][7].toString());			
			dlg.vipprice = Float.parseFloat(tblData[i][7].toString());
			dlg.comid = tblData[i][2].toString();
			dlg.comname = tblData[i][3].toString();
			
			dlg.setVisible(true);
			
		}
	}
}
	
