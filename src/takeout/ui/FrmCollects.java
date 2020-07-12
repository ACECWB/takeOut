package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import takeout.control.CartManager;
import takeout.control.CouponManager;
import takeout.control.UserManager;
import takeout.model.Coupon;
import takeout.model.User;
import takeout.util.BaseException;


public class FrmCollects extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnExchange = new Button("换取");

	
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable couponTable=new JTable(tablmod);
	
	
//	private Object tblData1[][];
//	DefaultTableModel tabModel1=new DefaultTableModel();
//	public JTable dataTable1=new JTable(tabModel1);
	
	List<Coupon> allCollects = null;
	
	private void reloadCollectInfoTable(){
		try {
			allCollects=(new CouponManager()).loadAllCollects(User.currentLoginUser.getUserId());
			tblData = new Object[allCollects.size()][Coupon.OtableTitles.length];
			for(int i=0;i<allCollects.size();i++){
				for(int j=0;j<Coupon.OtableTitles.length;j++) {
					tblData[i][j] = allCollects.get(i).getOCell(j); 
				}
			}
			tablmod.setDataVector(tblData, Coupon.OtableTitles);
			this.couponTable.validate();
			this.couponTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmCollects(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnExchange);
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);

		//提取现有数据
		this.reloadCollectInfoTable();
		this.getContentPane().add(new JScrollPane(this.couponTable), BorderLayout.CENTER);
		this.btnExchange.addActionListener(this);
		// 屏幕居中显示
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.validate();

}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnExchange) {
			
			int i=this.couponTable.getSelectedRow();

			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择优惠券","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定兑换该优惠券吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String businessid=this.tblData[i][0].toString();
				String couponid = this.tblData[i][2].toString();
				int days = Integer.parseInt(this.tblData[i][7].toString());
				try {
					(new UserManager()).exchangeCoupon(businessid,couponid, days);
					this.reloadCollectInfoTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
}
	
