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

import takeout.control.LocationManager;
import takeout.model.Location;
import takeout.model.User;
import takeout.util.BaseException;


public class FrmLocationManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("��ӵ�ַ��Ϣ");
	private Button btnDelete = new Button("ɾ����ַ��Ϣ");
	private Button btnModify = new Button("�޸ĵ�ַ��Ϣ");

	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable locationTable=new JTable(tablmod);
	List<Location> locations;
	private void reloadLocationTable(){
		try {
			
			if(User.currentLoginUser==null)
				locations=(new LocationManager()).loadAllLocations();
			else
				locations=(new LocationManager()).loadAllLocations(User.currentLoginUser.getUserId());

			tblData = new Object[locations.size()][Location.UtableTitles.length];
			for(int i=0;i<locations.size();i++){
				for(int j=0;j<Location.UtableTitles.length;j++) {
					tblData[i][j] = locations.get(i).getUCell(j); 
				}
			}
			tablmod.setDataVector(tblData, Location.UtableTitles);
			this.locationTable.validate();
			this.locationTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmLocationManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
		toolBar.add(btnModify);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������
		this.reloadLocationTable();
		this.getContentPane().add(new JScrollPane(this.locationTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		this.validate();
		
		this.btnAdd.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnAdd){
			FrmLocationManager_AddLocation dlg=new FrmLocationManager_AddLocation(this,"��ӵ�ַ��Ϣ",true);
			dlg.setVisible(true);
			if(dlg.getLocation1()!=null){//ˢ�±��
				this.reloadLocationTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.locationTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���ַ��Ϣ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ���õ�ַ��Ϣ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String locaid=this.tblData[i][0].toString();
				try {
					(new LocationManager()).deleteLocation(Integer.parseInt(locaid));
					this.reloadLocationTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource()==this.btnModify){
			int i=this.locationTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���ַ��Ϣ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			Location location=this.locations.get(i);
			
			FrmLocationManager_Modify dlg=new FrmLocationManager_Modify(this,"�޸ĵ�ַ��Ϣ",true,location);
			dlg.setVisible(true);
			if(dlg.getLocation2()!=null){//ˢ�±��
				this.reloadLocationTable();
			}
		}
	}
		
		
	

}
	
