package takeout.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FrmChoice extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	
	private JButton btnAdminLogin = new JButton("管理员身份登陆");
	private JButton btnUserLogin = new JButton("用户身份登陆");
	private JButton btnCancel = new JButton("退出");
	

	public FrmChoice() {
		
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdminLogin);
		toolBar.add(btnUserLogin);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.CENTER);
		this.setSize(400,100);
		this.setLocationRelativeTo(null);
		
		btnAdminLogin.addActionListener(this);
		btnUserLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnAdminLogin) {
//			FrmMain fr = new FrmMain();
//			fr.setVisible(true);
//			FrmLogin dlg = new FrmLogin(this,"管理员登陆",true);
//			dlg.setVisible(true);
			this.setVisible(true);
			
		}else if(e.getSource()==this.btnUserLogin) {
			System.exit(0);
		}else if(e.getSource()==this.btnCancel) {
			System.exit(0);
		}
	}
}
