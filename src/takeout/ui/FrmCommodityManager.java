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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import takeout.control.CartManager;
import takeout.control.CommodityManager;
import takeout.model.Commodity;
import takeout.model.User;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;


public class FrmCommodityManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加商品信息");
	private Button btnCom = new Button("管理商品");
	private Button btnCate = new Button("管理商品类别");
	private Button btnDelete = new Button("删除商品信息");
	private JTextField edtKeyword = new JTextField(15);

	private JComboBox cmbCate;
	private Button btnSearch = new Button("查询");

	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable commodityTable=new JTable(tablmod);
	
	List<Commodity> coms = null;

	public void reloadTable(){
		try {
			coms=(new CommodityManager()).searchCommoditys(this.edtKeyword.getText(), cmbCate.getSelectedItem().toString());
			tblData =new Object[coms.size()][Commodity.tableTitles_3.length];
			for(int i=0;i<coms.size();i++){
				for(int j=0;j<Commodity.tableTitles_3.length;j++) {
					tblData[i][j] = coms.get(i).getSCell(j);
					
				}
			}
			tablmod.setDataVector(tblData,Commodity.tableTitles_3);
			this.commodityTable.validate();
			this.commodityTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
		
		String[] comcates = null;
		try {
			comcates = (new CartManager()).loadAllCates();
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cmbCate = new JComboBox(comcates);
		
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnCom);
		toolBar.add(btnCate);
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
		toolBar.add(cmbCate);
		toolBar.add(edtKeyword);

		toolBar.add(btnSearch);

		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.commodityTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		this.validate();
		
		this.btnAdd.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnCate.addActionListener(this);
		this.btnCom.addActionListener(this);
		this.btnSearch.addActionListener(this);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
		this.commodityTable.getModel().addTableModelListener(new TableModelListener() {
			
			public void tableChanged(TableModelEvent e) {
				
				int i = commodityTable.getSelectedRow();
				int c = e.getColumn();
				int r = e.getFirstRow();
				if(c>=0 && r>=0) {
					try {
						if(c == 6) {
							
							if(commodityTable.getValueAt(r, c).toString() == null || "".equals(commodityTable.getValueAt(r, c).toString())) {
								throw new BusinessException("数量不可为空！！！");
							}
							if(Integer.parseInt(commodityTable.getValueAt(r, c).toString())<0)
								throw new BusinessException("数量不可为负数！！！");

						}else if(c == 7) {
							if(commodityTable.getValueAt(r, c).toString() == null || "".equals(commodityTable.getValueAt(r, c).toString())) {
								throw new BusinessException("单价不可为空！！！");
							}
							if(Float.parseFloat(commodityTable.getValueAt(r, c).toString())<0) {
								throw new BusinessException("单价不可为负数！！！");
							}
		
						}else if(c == 8) {
							
							if(commodityTable.getValueAt(r, c).toString() == null || "".equals(commodityTable.getValueAt(r, c).toString())) {
								throw new BusinessException("会员价不可为空！！！");
							}
							if(Float.parseFloat(commodityTable.getValueAt(r, c).toString())<0) {
								throw new BusinessException("会员价不可为负数！！！");
							}
							
						}else {
							JOptionPane.showMessageDialog(null,  "不可修改该属性值！！！","提示",JOptionPane.ERROR_MESSAGE);
							tablmod.setDataVector(tblData, Commodity.tableTitles);
							commodityTable.validate();
							commodityTable.repaint();
							FrmCommodityManager.this.reloadTable();

							return;

						}
						System.out.println(1);
						System.out.println(tblData[r][c].toString()+"  "+commodityTable.getValueAt(r, c));

						tblData[r][c] = commodityTable.getValueAt(r, c);

					}catch(Exception e1) {
						JOptionPane.showMessageDialog(null,  "请输入正确值！！！","提示",JOptionPane.ERROR_MESSAGE);
						tablmod.setDataVector(tblData, Commodity.tableTitles);
						commodityTable.validate();
						commodityTable.repaint();
						FrmCommodityManager.this.reloadTable();
						return;
						
					}
					
					Commodity com = new Commodity();
					
					com.setComId(tblData[i][0].toString());
					com.setComName(tblData[i][1].toString());
					com.setCategoryId(tblData[i][2].toString());
					com.setCategoryName(tblData[i][3].toString());
					com.setBusinessId(tblData[i][4].toString());
					com.setCounts(Integer.parseInt(tblData[i][6].toString()));
					com.setEachPrice(Float.parseFloat(tblData[i][7].toString()));
					com.setVipprice(Float.parseFloat(tblData[i][8].toString()));
					System.out.println(tblData[r][c].toString()+"  "+commodityTable.getValueAt(r, c));

					try {
						(new CommodityManager()).modifyCom2bus(com);
					} catch (BaseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					FrmCommodityManager.this.reloadTable();
					
					
				}
				
			}
			
		});

		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnAdd){
			
			FrmCommodityManager_AddCommodity dlg=new FrmCommodityManager_AddCommodity(this,"添加商品信息",true);
			dlg.setVisible(true);
			if(dlg.getCommodity()!=null){//刷新表格
				this.reloadTable();
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
					this.reloadTable();
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
		}else if(e.getSource()==this.btnSearch) {
			this.reloadTable();

		}
	}


}
	
