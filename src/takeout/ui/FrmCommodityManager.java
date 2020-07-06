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


import takeout.control.CommodityManager;
import takeout.model.Commodity;
import takeout.util.BaseException;


public class FrmCommodityManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加商品信息");
	private Button btnCom = new Button("管理商品");
	private Button btnCate = new Button("管理商品类别");
	private Button btnDelete = new Button("删除商品信息");
	
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable commodityTable=new JTable(tablmod);
	
	public void reloadCommodityTable(){
		try {
			List<Commodity> commoditys=(new CommodityManager()).loadAllCommoditys();
			tblData = new Object[commoditys.size()][Commodity.tableTitles.length];
			for(int i=0;i<commoditys.size();i++){
				for(int j=0;j<Commodity.tableTitles.length;j++) {
					tblData[i][j] = commoditys.get(i).getCell(j); 
				}
			}
			tablmod.setDataVector(tblData, Commodity.tableTitles);
			this.commodityTable.validate();
			this.commodityTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmCommodityManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnCom);
		toolBar.add(btnCate);
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadCommodityTable();
		this.getContentPane().add(new JScrollPane(this.commodityTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		this.validate();
		
		this.btnAdd.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnCate.addActionListener(this);
		this.btnCom.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnAdd){
			FrmCommodityManager_AddCommodity dlg=new FrmCommodityManager_AddCommodity(this,"添加商品信息",true);
			dlg.setVisible(true);
			if(dlg.getCommodity()!=null){//刷新表格
				this.reloadCommodityTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.commodityTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品信息","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该商品信息吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String commodityid=this.tblData[i][0].toString();
				String businessid = this.tblData[i][4].toString();
				try {
					(new CommodityManager()).deleteCommodity(commodityid, businessid);
					this.reloadCommodityTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==this.btnCom) {
			FrmCommodityManager_1 dlg = new FrmCommodityManager_1(this,"管理商品",true);
			
			dlg.setVisible(true);
			
		}
			else if(e.getSource()==this.btnCate) {
			FrmCommodityManager_2 dlg = new FrmCommodityManager_2(this,"管理商品类别",true);
			dlg.setVisible(true);
		}
	}
		
		
	

}
	
