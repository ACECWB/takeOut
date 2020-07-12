package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import takeout.control.IncomeStatisticManager;
import takeout.control.IncomeManager;
import takeout.control.IncomeManager;
import takeout.control.IncomeManager;
import takeout.control.IncomeManager;
import takeout.model.IncomeStatistic;
import takeout.model.Income;
import takeout.model.ComTitle;
import takeout.model.Commodity;
import takeout.model.Deliver;
import takeout.model.Income;
import takeout.model.IncomeStatistic;
import takeout.model.Income;
import takeout.model.User;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;


public class FrmCheckIncome extends JDialog implements ActionListener{
	public FrmMain fm = null;
	private JPanel toolBar = new JPanel();
	
	private JPanel westBar = new JPanel();
	private JPanel centerBar = new JPanel();
	
	private Object tblData1[][];
	DefaultTableModel tabModel1=new DefaultTableModel();
	public JTable dataTable1=new JTable(tabModel1);
	
	private Object tblData2[][];
	DefaultTableModel tabModel2=new DefaultTableModel();
	public JTable dataTable2=new JTable(tabModel2);
	
	private List<Income> allIncome;
	private List<IncomeStatistic> allStatistic;
	private IncomeStatistic curSta;

	public void reloadIncomesInfo(int statisticidx){
		if(statisticidx<0) return;
			curSta = allStatistic.get(statisticidx);
		try {
			allIncome = new IncomeManager().loadPartIncomes(Deliver.currentLoginDeliver.getDeliverId(),curSta.getYear(),curSta.getMonth());
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblData1 =  new Object[allIncome.size()][Income.tableTitles.length];
		for(int i=0;i<allIncome.size();i++){
			for(int j=0;j<Income.tableTitles.length;j++)
				tblData1[i][j]=allIncome.get(i).getCell(j);
		}
		tabModel1.setDataVector(tblData1,Income.tableTitles);
		this.dataTable1.validate();
		this.dataTable1.repaint();
	}
	
	public void reloadStatisticTable(){//这是测试数据，需要用实际数替换
		try {
			allStatistic = new IncomeStatisticManager().loadAllIncomeStatistics(Deliver.currentLoginDeliver.getDeliverId());
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblData2 =  new Object[allStatistic.size()][IncomeStatistic.tableTitles.length];
		for(int i=0;i<allStatistic.size();i++){
			for(int j=0;j<IncomeStatistic.tableTitles.length;j++)
				tblData2[i][j]=allStatistic.get(i).getCell(j);
		}
		tabModel2.setDataVector(tblData2,IncomeStatistic.tableTitles);
		this.dataTable2.validate();
		this.dataTable2.repaint();
		tabModel1.setRowCount(0);
	}
	
	
	public FrmCheckIncome(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
//		this.a = frmMain;
		this.setSize(750, 480);
		this.setLocationRelativeTo(null);
		this.validate();
		this.fm = frmMain;
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		
//		toolBar.add(btnReset);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		
		JScrollPane jsp2 = new JScrollPane(this.dataTable2);
		jsp2.setPreferredSize(new Dimension(300,400));
		westBar.add(jsp2);
		
		JScrollPane jsp1 = new JScrollPane(this.dataTable1);
		jsp1.setPreferredSize(new Dimension(400,400));
	    centerBar.add(jsp1);
	    this.add(westBar,BorderLayout.WEST);
	    this.add(centerBar,BorderLayout.CENTER);
	    
//		this.getContentPane().add(new JScrollPane(this.dataTable2), BorderLayout.WEST);
		this.dataTable2.addMouseListener(new MouseAdapter (){

			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmCheckIncome.this.dataTable2.getSelectedRow();
				if(i<0) {
					return;
				}
				FrmCheckIncome.this.reloadIncomesInfo(i);
			}
	    	
	    });
		
		
		
//	    this.getContentPane().add(new JScrollPane(this.dataTable1), BorderLayout.CENTER);
		this.reloadStatisticTable();

		// 屏幕居中显示
		
		
	
//		this.btnReset.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}


}
	
