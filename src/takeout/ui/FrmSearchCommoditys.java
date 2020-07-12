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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import takeout.control.CartManager;
import takeout.control.CommodityManager;
import takeout.model.Commodity;
import takeout.model.User;
import takeout.util.BaseException;



public class FrmSearchCommoditys extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加");

	private JComboBox cmbCate;
	private JTextField edtKeyword = new JTextField(15);
	private Button btnSearch = new Button("查询");
	private Object tblData[][];
	List<Commodity> coms = null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	
	private void reloadTable(){
		try {
			coms=(new CommodityManager()).searchCommoditys(this.edtKeyword.getText(), cmbCate.getSelectedItem().toString());
			tblData =new Object[coms.size()][Commodity.tableTitles_3.length];
			for(int i=0;i<coms.size();i++){
				for(int j=0;j<Commodity.tableTitles_3.length;j++) {
					tblData[i][j] = coms.get(i).getSCell(j);
					
				}
			}
			tablmod.setDataVector(tblData,Commodity.tableTitles_3);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmSearchCommoditys(Frame f, String s, boolean b) {
		super(f, s, b);
		String[] comcates = null;
		try {
			comcates = (new CartManager()).loadAllCates();
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cmbCate = new JComboBox(comcates);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(cmbCate);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		toolBar.add(btnAdd);
		this.btnSearch.setSize(50, 50);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
//		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//		this.setLocation((int) (width - this.getWidth()) / 2,
//				(int) (height - this.getHeight()) / 2);

		this.setLocationRelativeTo(null);
		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnSearch.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd){
			int i=this.dataTable.getSelectedRow();
			
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择一个商品！！！","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			FrmCartManager_Add dlg = new FrmCartManager_Add(this,"添加购物车",true);
			
			dlg.comid = tblData[i][0].toString();
			dlg.comname = tblData[i][1].toString();
			dlg.comcateid = tblData[i][2].toString();
			dlg.comcatename = tblData[i][3].toString();
			dlg.businessid = tblData[i][4].toString();
			dlg.businessname = tblData[i][5].toString();
			dlg.counts = Integer.parseInt(tblData[i][6].toString());
			if(User.currentLoginUser.getVipEndTime()==null || User.currentLoginUser.getVipEndTime().before(new Date()))
				dlg.price = Float.parseFloat(tblData[i][7].toString());
			else
				dlg.price = Float.parseFloat(tblData[i][8].toString());			
			dlg.setVisible(true);

		}else if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
		
	}
}
