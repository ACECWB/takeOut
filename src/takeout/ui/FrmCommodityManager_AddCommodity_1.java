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
import takeout.control.CommodityManager;
import takeout.model.ComTitle;

import java.text.SimpleDateFormat;

public class FrmCommodityManager_AddCommodity_1 extends JDialog implements ActionListener{
	private ComTitle comtitle = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelCommodityId = new JLabel("商品编号：");
	private JLabel labelCommodityName = new JLabel("商品名称：");
	private JLabel labelCommodityCate = new JLabel("类别编号：");

	
	private JTextField edtCommodityId = new JTextField(20);
	private JTextField edtCommodityName = new JTextField(20);
	private JTextField edtCommodityCate = new JTextField(20);

	public FrmCommodityManager_AddCommodity_1(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelCommodityId); workPane.add(edtCommodityId);
		workPane.add(labelCommodityName); workPane.add(edtCommodityName);
		workPane.add(labelCommodityCate); workPane.add(edtCommodityCate);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(500, 180);
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
			
			String comid = this.edtCommodityId.getText();
			String comname = this.edtCommodityName.getText();
			String comcate = this.edtCommodityCate.getText();
			
			comtitle=new ComTitle();
			comtitle.setComName(comname);
			comtitle.setComId(comid);
			comtitle.setCategoryId(comcate);
			
			try {
				(new CommodityManager()).addComTitle(comtitle);
				this.setVisible(false);
				
			} catch (BaseException e1) {
				this.comtitle=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	public ComTitle getComTitle() {
		return comtitle;
	}

	
	
	
}
