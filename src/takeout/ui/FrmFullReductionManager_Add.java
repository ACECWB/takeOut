package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import takeout.util.*;
import takeout.control.FullReductionManager;
import takeout.model.FullReduction;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FrmFullReductionManager_Add extends JDialog implements ActionListener{
	private FullReduction reduct = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	
	private JLabel labelReductId = new JLabel("������ţ�");
	private JLabel labelBusinessId = new JLabel("�̼ұ�ţ�");
	private JLabel labelRequire = new JLabel("���Ҫ��");
	private JLabel labelDiscount = new JLabel("�����");
	private JLabel labelWith = new JLabel("�Ƿ�����Ż�ȯһ��ʹ�ã�");
//	private JLabel labelStart = new JLabel("���ʼʱ�䣺");
//	private JLabel labelEnd = new JLabel("�����ʱ�䣺");

	private JComboBox cmbWith= new JComboBox(new String[] { "��", "��"});

	private JTextField edtReductId = new JTextField(20);
	private JTextField edtBusinessId = new JTextField(20);
	private JTextField edtRequire = new JTextField(20);
	private JTextField edtDiscount = new JTextField(20);
//	private JTextField edtStart = new JTextField(20);
//	private JTextField edtEnd = new JTextField(20);
		
	public FrmFullReductionManager_Add(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelReductId); workPane.add(edtReductId);		
		workPane.add(labelBusinessId); workPane.add(edtBusinessId);
		workPane.add(labelRequire); workPane.add(edtRequire);
		workPane.add(labelDiscount); workPane.add(edtDiscount);
		workPane.add(labelWith); workPane.add(cmbWith);
//		workPane.add(labelStart); workPane.add(edtStart);
//		workPane.add(labelEnd); workPane.add(edtEnd);

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 250);
		this.setLocationRelativeTo(null);
		
		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			reduct = new FullReduction();
			
			try {
				reduct.setDiscountAmount(Float.parseFloat(this.edtDiscount.getText().toString()));
				reduct.setReductId(this.edtReductId.getText().toString());
				reduct.setBusinessId(this.edtBusinessId.getText().toString());
				reduct.setRequireAmount(Integer.parseInt(this.edtRequire.getText().toString()));
				reduct.setWithCoupon(this.cmbWith.getSelectedItem().toString());
				System.out.println(reduct.getBusinessId() + "  "+reduct.getReductId());

			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "������������Ϣ������","����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
//			try {
//				reduct.setEndTime(sdf.parse(this.edtEnd.getText()));
//			} catch (ParseException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
			
			try {
				System.out.println(reduct.getBusinessId() + "  "+reduct.getReductId());

				(new FullReductionManager()).addFullReduction(reduct);
				this.setVisible(false);
				
			} catch (BaseException e1) {
				this.reduct=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	public FullReduction getFullReduction() {
		return reduct;
	}

	
	
	
}
