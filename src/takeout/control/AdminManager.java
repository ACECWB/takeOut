package takeout.control;

import java.sql.SQLException;
import java.util.Date;
import java.sql.Connection;

import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;
import takeout.itf.IAdminManager;
import takeout.util.BaseException;
import takeout.model.Admin;

public class AdminManager implements IAdminManager {

	@Override
	public Admin reg(String userid, String username, String pwd, String pwd2) throws BaseException {
		// TODO Auto-generated method stub
		Admin ad = new Admin();
		
		if(userid==null || userid.equals("")) {
			throw new BusinessException("�û�������Ϊ��");
		}
		if(pwd==null || pwd2==null||pwd.equals("")||pwd2.equals("")) {
			throw new BusinessException("���벻��Ϊ��");
		}
		if(!pwd.equals(pwd2)) {
			throw new BusinessException("���벻һ�£�");
		}
		String sql = "select admin_Id from admin where admin_Id = ?";
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				throw new BusinessException("���û����Ѵ��ڣ�����");
			}
			pst.close();
			rs.close();
			sql = "insert into admin(admin_Id, admin_name, admin_pwd) VALUES(?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userid.replaceAll("'", "''"));
			pst.setString(2, username.replaceAll("'", "''"));
			pst.setString(3, pwd.replaceAll("'", "''"));
			pst.execute();
			ad.setPwd(pwd);
			ad.setUserid(userid);
			pst.close();
			conn.close();
			System.out.println("�����û��ɹ�");
		}catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}finally {
			if(conn!=null)
				try {
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
		}
		return ad;
		
	}

	@Override
	public Admin login(String userid, String pwd) throws BaseException {
		// TODO Auto-generated method stub
		Admin ad = new Admin();
		ad.setPwd(pwd);
		ad.setName(userid);
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "select * from admin where admin_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid.replaceAll("'", "''"));
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) {
				throw new BusinessException("���û������ڣ�");
			}
			if(rs.getString(3).equals(pwd)) {
				return ad;
			}else {
				throw new BusinessException("������󣡣���");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}finally {
			
			if(conn!=null)
				try {
					conn.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public void changePwd(Admin admin, String oldPwd, String newPwd, String newPwd2)throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "select * from admin where admin_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, admin.getName().replaceAll("'", "''"));
			java.sql.ResultSet rs = pst.executeQuery();
			rs.next();
			if(!rs.getString(3).equals(oldPwd)) {
				throw new BusinessException("������󣡣���");
			}
			if(!newPwd.equals(newPwd2)) {
				throw new BusinessException("���벻һ�£�����");
			}
			pst.close();
			rs.close();
			sql = "update admin set admin_pwd = ? where admin_Id = ? ";
			pst = conn.prepareStatement(sql);
			pst.setString(1,newPwd.replaceAll("'", "''"));
			pst.setString(2, admin.getName().replaceAll("'", "''"));
			pst.execute();
			pst.close();
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}finally {
			if(conn!=null)
				try {
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
		}
		
		
	}

	}

