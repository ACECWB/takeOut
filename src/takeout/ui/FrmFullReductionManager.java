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


import takeout.control.FullReductionManager;
import takeout.model.FullReduction;
import takeout.util.BaseException;


public class FrmFullReductionManager extends JDialog implements ActionListener{
	private FrmCommodityManager a = null;
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加新满减活动");
	private Button btnDelete = new Button("删除满减活动");
//	private Button btnReset = new Button("上架商品");

	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable comTable=new JTable(tablmod);
	
	private void reloadFullTable(){
		try {
			List<FullReduction> fulls=(new FullReductionManager()).loadAllFullReductions();
			tblData = new Object[fulls.size()][FullReduction.tableTitles.length];
			for(int i=0;i<fulls.size();i++){
				for(int j=0;j<FullReduction.tableTitles.length;j++) {
					tblData[i][j] = fulls.get(i).getCell(j); 
				}
			}
			tablmod.setDataVector(tblData, FullReduction.tableTitles);
			this.comTable.validate();
			this.comTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmFullReductionManager(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
//		this.a = frmMain;
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
//		toolBar.add(btnReset);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadFullTable();
		this.getContentPane().add(new JScrollPane(this.comTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.validate();
		
		this.btnAdd.addActionListener(this);
		this.btnDelete.addActionListener(this);
//		this.btnReset.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnAdd){
			FrmFullReductionManager_Add dlg=new FrmFullReductionManager_Add(this,"添加满减活动",true);
			dlg.setVisible(true);
			if(dlg.getFullReduction()!=null){//刷新表格
				this.reloadFullTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.comTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择满减活动","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该满减活动吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String reductid=this.tblData[i][0].toString();
				try {
					(new FullReductionManager()).deleteFullReduction(reductid);
					this.reloadFullTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
		
		
	

}
	
