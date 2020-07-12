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

import takeout.control.CouponManager;
import takeout.control.CouponManager;
import takeout.model.Business;
import takeout.model.Coupon;
import takeout.model.Coupon;
import takeout.util.BaseException;
import takeout.util.BusinessException;


public class FrmCoupon extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("������Ż�ȯ");
	private Button btnDelete = new Button("ɾ���Ż�ȯ");

	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	public JTable couponTable=new JTable(tablmod);
	List<Coupon> coupons = null;
	
	private void reloadBCouponTable(){
		Business bus = new Business();
		bus.setBusinessId(Business.currentLoginBusiness.getBusinessId().toString());

		try {
			coupons=(new CouponManager()).loadAllBCoupons(bus, true);
			tblData = new Object[coupons.size()][Coupon.BtableTitles.length];
			System.out.println(coupons.size());
			for(int i=0;i<coupons.size();i++){
				for(int j=0;j<Coupon.BtableTitles.length;j++) {
					tblData[i][j] = coupons.get(i).getBCell(j); 
				}
			}
			tablmod.setDataVector(tblData, Coupon.BtableTitles);
			this.couponTable.validate();
			this.couponTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmCoupon(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������
		this.reloadBCouponTable();
		this.getContentPane().add(new JScrollPane(this.couponTable), BorderLayout.CENTER);

		this.couponTable.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int i = couponTable.getSelectedRow();
				int c = e.getColumn();
				int r = e.getFirstRow();
				if(c>=0 && r>=0) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					int origindays = Integer.parseInt(tblData[r][5].toString());
					int nowdays = 0;
					try {
						if(c == 1) {
							if(couponTable.getValueAt(r, c).toString() == null || "".equals(couponTable.getValueAt(r, c).toString())) {
								throw new BusinessException("�Żݽ���Ϊ�գ�����");
							}
							if(Float.parseFloat(couponTable.getValueAt(r, c).toString())<0) {
								throw new BusinessException("�Żݽ���Ϊ����������");
							}
							
						}else if(c == 2) {
							if(couponTable.getValueAt(r, c).toString() == null || "".equals(couponTable.getValueAt(r, c).toString())) {
								throw new BusinessException("����������Ϊ�գ�����");
							}
							if(Integer.parseInt(couponTable.getValueAt(r, c).toString())<0) {
								throw new BusinessException("����������Ϊ����������");
							}
						}else if(c == 3) {
							if(couponTable.getValueAt(r, c).toString() == null || "".equals(couponTable.getValueAt(r, c).toString())) {
								throw new BusinessException("���ʼʱ�䲻��Ϊ�գ�����");
							}
							if(sdf.parse(couponTable.getValueAt(r, c).toString()).after(sdf.parse(couponTable.getValueAt(r, c+1).toString()))) {
								throw new BusinessException("���ʼʱ�䲻�����ڽ���ʱ�䣡����");
							}
						}else if(c == 4) {
							if(couponTable.getValueAt(r, c).toString() == null || "".equals(couponTable.getValueAt(r, c).toString())) {
								throw new BusinessException("�����ʱ�䲻��Ϊ�գ�����");
							}
							if(sdf.parse(couponTable.getValueAt(r, c-1).toString()).after(sdf.parse(couponTable.getValueAt(r, c).toString()))) {
								throw new BusinessException("�����ʱ�䲻�����ڿ�ʼʱ�䣡����");
							}
						}else if(c == 5) {
							if(couponTable.getValueAt(r, c).toString() == null || "".equals(couponTable.getValueAt(r, c).toString())) {
								throw new BusinessException("��Ч��������Ϊ�գ�����");
							}
							if(Float.parseFloat(couponTable.getValueAt(r, c).toString())<0) {
								throw new BusinessException("��Ч��������Ϊ����������");
							}
						}else {
							JOptionPane.showMessageDialog(null,  "�����޸ĸ�����ֵ������","��ʾ",JOptionPane.ERROR_MESSAGE);
							tablmod.setDataVector(tblData, Coupon.BtableTitles);
							couponTable.validate();
							couponTable.repaint();
							return;
						}
						
						tblData[r][c] = couponTable.getValueAt(r, c);

					}catch(Exception e3) {
						System.out.println("543");
						JOptionPane.showMessageDialog(null,  "��������ȷֵ������","��ʾ",JOptionPane.ERROR_MESSAGE);
						tablmod.setDataVector(tblData, Coupon.BtableTitles);
						couponTable.validate();
						couponTable.repaint();
						FrmCoupon.this.reloadBCouponTable();
						return;
					}
					
					Coupon bc = new Coupon();
					bc.setBusinessId(Business.currentLoginBusiness.getBusinessId());
					bc.setCouponId(tblData[i][0].toString());
					bc.setDiscountMoney(Float.parseFloat(tblData[i][1].toString()));
					bc.setNeedOrders(Integer.parseInt(tblData[i][2].toString()));
					try {
						bc.setStartTime(sdf.parse(tblData[i][3].toString()));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						bc.setEndTime(sdf.parse(tblData[i][4].toString()));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					bc.setEffectDays(Integer.parseInt(tblData[i][5].toString()));
					try {
						System.out.println(bc.getEffectDays()+"  "+origindays);
						(new CouponManager()).modifyBcoupon(bc, origindays);
					} catch (BaseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
				}
				
			}
			
		});

		
		// ��Ļ������ʾ
		this.setSize(500, 500);
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
			FrmAddCoupon dlg=new FrmAddCoupon(this,"����Ż�ȯ",true);
			dlg.setVisible(true);
			if(dlg.getCoupon()!=null){//ˢ�±��
				this.reloadBCouponTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.couponTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���Ż�ȯ������","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ�����Ż�ȯ��(������ɾ�����и��Ż�ȯ��Ϣ��)","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String couponid=this.tblData[i][0].toString();
				try {
					(new CouponManager()).deleteBCoupon(couponid);
					this.reloadBCouponTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
		
		
	

}
	
