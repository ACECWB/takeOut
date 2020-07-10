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

import takeout.control.ReviewManager;
import takeout.model.Review;
import takeout.model.User;
import takeout.util.BaseException;


public class FrmLookReviews extends JDialog {

	private Object tblData1[][];
	DefaultTableModel tabModel1=new DefaultTableModel();
	public JTable dataTable1=new JTable(tabModel1);
	
	List<Review> allReviews = null;
	
	
	public void reloadReviewsTable(){
		try {
			allReviews=(new ReviewManager()).loadAllUReviews(User.currentLoginUser.getUserId());
			tblData1 = new Object[allReviews.size()][Review.UtableTitles.length];
			for(int i=0;i<allReviews.size();i++){
				for(int j=0;j<Review.UtableTitles.length;j++) {
					tblData1[i][j] = allReviews.get(i).getUCell(j); 
				}
			}
			tabModel1.setDataVector(tblData1, Review.UtableTitles);
			this.dataTable1.validate();
			this.dataTable1.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmLookReviews(FrmOrderManager frmOrderManager, String s, boolean b) {
		super(frmOrderManager, s, b);
		
		//提取现有数据
		this.reloadReviewsTable();
		this.getContentPane().add(new JScrollPane(this.dataTable1), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		this.validate();
			
		
	}
		

}
	
