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
import takeout.model.ComTitle;
import takeout.util.BaseException;


public class FrmCommodityManager_1 extends JDialog implements ActionListener{
	private FrmCommodityManager a = null;
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加新商品");
	private Button btnDelete = new Button("删除商品");
	private Button btnReset = new Button("上架商品");

	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable comTable=new JTable(tablmod);
	
	private void reloadComTable(){
		try {
			List<ComTitle> comtitles=(new CommodityManager()).loadAllComTitles();
			tblData = new Object[comtitles.size()][ComTitle.tableTitles.length];
			for(int i=0;i<comtitles.size();i++){
				for(int j=0;j<ComTitle.tableTitles.length;j++) {
					tblData[i][j] = comtitles.get(i).getCell(j); 
				}
			}
			tablmod.setDataVector(tblData, ComTitle.tableTitles);
			this.comTable.validate();
			this.comTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmCommodityManager_1(FrmCommodityManager frmCommodityManager, String s, boolean b) {
		super(frmCommodityManager, s, b);
		this.a = frmCommodityManager;
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
		toolBar.add(btnReset);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadComTable();
		this.getContentPane().add(new JScrollPane(this.comTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.validate();
		
		this.btnAdd.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnReset.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnAdd){
			FrmCommodityManager_AddCommodity_1 dlg=new FrmCommodityManager_AddCommodity_1(this,"添加商品",true);
			dlg.setVisible(true);
			if(dlg.getComTitle()!=null){//刷新表格
				this.reloadComTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.comTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该商品吗？(若是则将删除所有该商品信息！)","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String commodityid=this.tblData[i][0].toString();
				try {
					(new CommodityManager()).deleteComTitle(commodityid);
					this.reloadComTable();
					this.a.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==this.btnReset) {
			int i=this.comTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定重新上架该商品吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String commodityid=this.tblData[i][0].toString();
				try {
					(new CommodityManager()).resetComTitle(commodityid);
					this.reloadComTable();
					this.a.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
		
		
	

}
	
