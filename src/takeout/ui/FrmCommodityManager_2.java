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
import takeout.model.ComCate;
import takeout.util.BaseException;


public class FrmCommodityManager_2 extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加新商品类型");
	private Button btnReset = new Button("上架商品类型");
	private Button btnDelete = new Button("删除商品类型");
	
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable comTable=new JTable(tablmod);
	
	private void reloadComTable(){
		try {
			List<ComCate> comcates=(new CommodityManager()).loadAllComCates();
			tblData = new Object[comcates.size()][ComCate.tableTitles.length];
			for(int i=0;i<comcates.size();i++){
				for(int j=0;j<ComCate.tableTitles.length;j++) {
					tblData[i][j] = comcates.get(i).getCell(j); 
				}
			}
			tablmod.setDataVector(tblData, ComCate.tableTitles);
			this.comTable.validate();
			this.comTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmCommodityManager_2(FrmCommodityManager frmCommodityManager, String s, boolean b) {
		super(frmCommodityManager, s, b);
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
	public FrmCommodityManager_2(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
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
			FrmCommodityManager_AddCommodity_2 dlg=new FrmCommodityManager_AddCommodity_2(this,"添加商品类型",true);
			dlg.setVisible(true);
			if(dlg.getComTitle()!=null){//刷新表格
				this.reloadComTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.comTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品类型","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该商品类型吗？(若是则删除所有该类的所有商品信息)","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String comcateid=this.tblData[i][0].toString();
				try {
					(new CommodityManager()).deleteComCate(comcateid);
					this.reloadComTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==this.btnReset) {
			int i=this.comTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品类型","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定重新上架该类商品吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String comcateid=this.tblData[i][0].toString();
				try {
					(new CommodityManager()).resetComCate(comcateid);
					this.reloadComTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
		
		
	

}
	
