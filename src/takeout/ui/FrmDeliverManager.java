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


import takeout.control.DeliverManager;
import takeout.model.Deliver;
import takeout.util.BaseException;


public class FrmDeliverManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("�������");
	private Button btnDelete = new Button("ɾ������");
	
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable deliverTable=new JTable(tablmod);
	
	private void reloadDeliverTable(){
		try {
			List<Deliver> delivers=(new DeliverManager()).loadAllDelivers();
			tblData = new Object[delivers.size()][Deliver.tableTitles.length];
			for(int i=0;i<delivers.size();i++){
				for(int j=0;j<Deliver.tableTitles.length;j++) {
					tblData[i][j] = delivers.get(i).getCell(j); 
				}
			}
			tablmod.setDataVector(tblData, Deliver.tableTitles);
			this.deliverTable.validate();
			this.deliverTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmDeliverManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������
		this.reloadDeliverTable();
		this.getContentPane().add(new JScrollPane(this.deliverTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		this.validate();
		
		this.btnAdd.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnAdd){
			FrmDeliverManager_AddDeliver dlg=new FrmDeliverManager_AddDeliver(this,"�������",true);
			dlg.setVisible(true);
			if(dlg.getDeliver()!=null){//ˢ�±��
				this.reloadDeliverTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.deliverTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ������","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ����������Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String deliverid=this.tblData[i][0].toString();
				try {
					(new DeliverManager()).deleteDeliver(deliverid);
					this.reloadDeliverTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
		
		
	

}
	
