package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import takeout.itf.IUserManager;
import takeout.model.User;
import takeout.util.*;

public class UserManager implements IUserManager {
	
	@Override
	public void createUser(User user) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		if(user.getUserId()==null || "".equals(user.getUserId()))
			throw new BusinessException("用户编码不可为空！！！");
		if(user.getUserName()==null || "".equals(user.getUserName()))
			throw new BusinessException("用户名不可为空！！！");
		if(user.getPhone()==null || "".equals(user.getPhone()))
			throw new BusinessException("用户电话不可为空！！！");
		if(user.getPwd()==null || "".equals(user.getPwd()))
			throw new BusinessException("用户密码不可为空！！！");
		if(user.getCity()==null || "".equals(user.getCity()))
			throw new BusinessException("用户所在城市不可为空！！！");
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "select * from user where user_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("该用户Id已存在！！！");
			pst.close();
			rs.close();
			sql = "insert into user(user_Id, user_name, sex, pwd, phone, email, city, createtime, isvip)\r\n" + 
					" values (?,?,?,?,?,?,?,NOW(),0)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserId());
			pst.setString(2, user.getUserName());
			pst.setString(3, user.getSex());
			pst.setString(4, user.getPwd());
			pst.setString(5, user.getPhone());
			pst.setString(6, user.getEmail());
			pst.setString(7, user.getCity());
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

	@Override
	public void changeUserPwd(String userid, String oldPwd, String newPwd) throws BaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(String userid) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "update user set removetime = now() where user_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
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

//	@Override
//	public User loadUsers(String userid) throws BaseException {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	public List<User> loadAllUsers(boolean withDeletedUser)throws BaseException{
		Connection conn = null;
		String sql = null;
		List<User> users = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select user_Id, user_name, sex, pwd, phone, email, city, createtime, removetime, isvip, vip_start_time, vip_end_time\r\n" + 
					"from user ";
			if(!withDeletedUser)
				sql += "where removetime is null ";
			sql += " order by user_Id";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setSex(rs.getString(3));
				user.setPwd(rs.getString(4));
				user.setPhone(rs.getString(5));
				user.setEmail(rs.getString(6));
				user.setCity(rs.getString(7));
				user.setCreateTime(rs.getTimestamp(8));
				user.setRemoveTime(rs.getTimestamp(9));
				user.setIsVip(rs.getInt(10));
				user.setVipStartTime(rs.getTimestamp(11));
				user.setVipEndTime(rs.getTimestamp(12));
				users.add(user);
			}
			rs.close();
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
		
		return users;
	}
	public void resetUserPwd(String userid)throws BaseException{
		Connection conn = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "update user set pwd = '123456' where user_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
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
