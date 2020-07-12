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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import takeout.control.FullReductionManager;
import takeout.control.FullReductionManager;
import takeout.model.FullReduction;
import takeout.model.FullReduction;
import takeout.util.BaseException;
import takeout.util.BusinessException;


public class FrmFullReductionManager extends JDialog implements ActionListener{
	private FrmCommodityManager a = null;
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("����������");
	private Button btnDelete = new Button("ɾ�������");
//	private Button btnReset = new Button("�ϼ���Ʒ");

	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable comTable=new JTable(tablmod);
	
	private void reloadFullTable(){
		try {
			List<FullReduction> fulls=(new FullReductionManager()).loadAllFullReductions(true);
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
		//��ȡ��������
		this.reloadFullTable();
		this.comTable.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int i = comTable.getSelectedRow();
				int c = e.getColumn();
				int r = e.getFirstRow();
				if(c>=0 && r>=0) {
					FullReduction full = new FullReduction();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

					if(c == 1) {
						try {
							if(comTable.getValueAt(r, c).toString() == null || "".equals(comTable.getValueAt(r, c).toString())) {
								throw new BusinessException("�̼ұ�Ų���Ϊ�գ�����");
							}

						}catch(Exception e4) {
							JOptionPane.showMessageDialog(null,  "��������ȷ��Ϣ������","��ʾ",JOptionPane.ERROR_MESSAGE);
							tablmod.setDataVector(tblData, FullReduction.tableTitles);
							comTable.validate();
							comTable.repaint();
							return;
						}
					}else if(c == 2) {
						try {
							if(comTable.getValueAt(r, c).toString() == null || "".equals(comTable.getValueAt(r, c).toString())) {
								throw new BusinessException("��������Ϊ�գ�����");
							}

						}catch(Exception e4) {
							JOptionPane.showMessageDialog(null,  "��������ȷ��Ϣ������","��ʾ",JOptionPane.ERROR_MESSAGE);
							tablmod.setDataVector(tblData, FullReduction.tableTitles);
							comTable.validate();
							comTable.repaint();
							return;
						}
					}else if(c == 3) {
						try {
							if(comTable.getValueAt(r, c).toString() == null || "".equals(comTable.getValueAt(r, c).toString())) {
								throw new BusinessException("�Żݽ���Ϊ�գ�����");
							}
							
							
						}catch(Exception e4) {
							JOptionPane.showMessageDialog(null,  "��������ȷ��Ϣ������","��ʾ",JOptionPane.ERROR_MESSAGE);
							tablmod.setDataVector(tblData, FullReduction.tableTitles);
							comTable.validate();
							comTable.repaint();
							return;
						}
					}else if(c == 4) {
						try {
							if(comTable.getValueAt(r, c).toString() == null || "".equals(comTable.getValueAt(r, c).toString())) {
								throw new BusinessException("�Ƿ�����Ż�ȯ���Ӳ���Ϊ�գ�����");
							}
							if(!comTable.getValueAt(r, c).toString().equals("��") && !comTable.getValueAt(r, c).toString().equals("��"))
								throw new BusinessException("���ݱ���Ϊ�ǻ�񣡣���");
							
						}catch(Exception e4) {
							JOptionPane.showMessageDialog(null,  "��������ȷ��Ϣ������","��ʾ",JOptionPane.ERROR_MESSAGE);
							tablmod.setDataVector(tblData, FullReduction.tableTitles);
							comTable.validate();
							comTable.repaint();
							return;
						}
					}else if(c == 5) {
						try {
							if(comTable.getValueAt(r, c).toString() == null || "".equals(comTable.getValueAt(r, c).toString())) {
								throw new BusinessException("�������ֹ�ڲ���Ϊ�գ�����");
							}
							full.setEndTime(sdf.parse(comTable.getValueAt(r, c).toString()));
						}catch(Exception e4) {
							JOptionPane.showMessageDialog(null,  "��������ȷ��Ϣ������","��ʾ",JOptionPane.ERROR_MESSAGE);
							tablmod.setDataVector(tblData, FullReduction.tableTitles);
							comTable.validate();
							comTable.repaint();
							return;
						}
					}else {
						JOptionPane.showMessageDialog(null,  "�����޸ĸ�����ֵ������","��ʾ",JOptionPane.ERROR_MESSAGE);
						tablmod.setDataVector(tblData, FullReduction.tableTitles);
						comTable.validate();
						comTable.repaint();
						return;
					}
					tblData[r][c] = comTable.getValueAt(r, c);
					
					full.setReductId(tblData[i][0].toString());
					full.setBusinessId(tblData[i][1].toString());
					full.setRequireAmount(Float.parseFloat(tblData[i][2].toString()));
					full.setDiscountAmount(Float.parseFloat(tblData[i][3].toString()));
					full.setWithCoupon(tblData[i][4].toString());
					try {
						full.setEndTime(sdf.parse(tblData[i][5].toString()));
					} catch (ParseException e2) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null,  "��������ȷʱ���ʽ������","��ʾ",JOptionPane.ERROR_MESSAGE);
						e2.printStackTrace();
						tablmod.setDataVector(tblData, FullReduction.tableTitles);
						comTable.validate();
						comTable.repaint();
						return;
					}
					try {
						(new FullReductionManager()).modifyReduction(full);
					} catch (BaseException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null,  "��Ϣ���󣡣���","��ʾ",JOptionPane.ERROR_MESSAGE);

						e1.printStackTrace();
					}
					
					FrmFullReductionManager.this.reloadFullTable();
					
					
					
				}
				
			}
			
		});

		this.getContentPane().add(new JScrollPane(this.comTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
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
			FrmFullReductionManager_Add dlg=new FrmFullReductionManager_Add(this,"��������",true, 0);
			dlg.setVisible(true);
			if(dlg.getFullReduction()!=null){//ˢ�±��
				this.reloadFullTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.comTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ�������","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ�����������","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String reductid=this.tblData[i][0].toString();
				try {
					(new FullReductionManager()).deleteFullReduction(reductid);
					this.reloadFullTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
		
		
	

}
	
