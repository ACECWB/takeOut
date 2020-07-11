package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import takeout.itf.IUserManager;
import takeout.model.User;
import takeout.util.*;

public class UserManager implements IUserManager {
	public void vip(int month)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();//判断该用户是否已经为VIP-有结果则为老VIP
			conn.setAutoCommit(false);
			sql = "select vip_end_time from user where user_Id = ? and vip_end_time is not null and vip_end_time>now()";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, User.currentLoginUser.getUserId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) {//该用户为新VIP
				rs.close();
				pst.close();
				
				sql = "update user set isvip = 1, vip_start_time = now(), vip_end_time = ? where user_Id = ?";
				pst = conn.prepareStatement(sql);
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.MONTH, month);
				pst.setTimestamp(1, new java.sql.Timestamp(cal.getTime().getTime()));
				pst.setString(2, User.currentLoginUser.getUserId());
				User.currentLoginUser.setIsVip(1);
				User.currentLoginUser.setVipStartTime(new Date());
				User.currentLoginUser.setVipEndTime(cal.getTime());
				pst.execute();
				pst.close();
				
			}else {//该用户为续费VIP
				Calendar cal = Calendar.getInstance();
				cal.setTime(rs.getTimestamp(1));
				cal.add(Calendar.MONTH, month);
				rs.close();
				pst.close();
				System.out.println("after" + cal.getTime());
				sql = "update user set isvip = 1, vip_end_time = ? where user_Id = ?";
				pst = conn.prepareStatement(sql);
				pst.setTimestamp(1, new java.sql.Timestamp(cal.getTime().getTime()));
				pst.setString(2, User.currentLoginUser.getUserId());
				User.currentLoginUser.setIsVip(1);
				User.currentLoginUser.setVipEndTime(cal.getTime());
				pst.execute();
				pst.close();
				
				
			}
			conn.commit();
			conn.close();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
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

	public void reg(User user) throws BaseException{
		Connection conn = null;
		String sql = null;
		if(user.getUserId() == null || "".equals(user.getUserId()))
			throw new BusinessException("用户账号不可为空！！！");
		if(user.getPwd() == null || "".equals(user.getPwd()))
			throw new BusinessException("用户密码不可为空！！！");
		if(user.getUserName() == null || "".equals(user.getUserName()))
			throw new BusinessException("用户名不可为空！！！");
		if(user.getPhone() == null || "".equals(user.getPhone()))
			throw new BusinessException("绑定手机号不可为空！！！");
		if(user.getCity() == null || "".equals(user.getCity()))
			throw new BusinessException("用户所在城市不可为空！！！");

		try {
			conn = DBUtil.getConnection();
			sql = "insert into `user`(user_Id, user_name, sex, pwd, phone, email, city, isvip, createtime)\r\n" + 
					"values (?,?,?,?,?,?,?,0,now())";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
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
	public User login(String userid, String pwd)throws BaseException{
		Connection conn = null;
		String sql = null;
		User user = new User();
		user.setUserId(userid);
		user.setPwd(pwd);
		
		try {
			conn = DBUtil.getConnection();
			sql = "select pwd ,user_name,sex,phone,email,city,isvip,vip_start_time, vip_end_time from user where user_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next())
				throw new BusinessException("不存在该用户！！！");
			if(!rs.getString(1).equals(pwd))
				throw new BusinessException("密码错误！！！");
			user.setUserName(rs.getString(2));
			user.setSex(rs.getString(3));
			user.setPhone(rs.getString(4));
			user.setEmail(rs.getString(5));
			user.setCity(rs.getString(6));
			user.setIsVip(rs.getInt(7));
			user.setVipStartTime(rs.getTimestamp(8));
			user.setVipEndTime(rs.getTimestamp(9));
			
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
		return user;
	}
	
	
	public void modifyUser(User user)throws BaseException{
		Connection conn = null;
		String sql = null;
		if(user.getUserName() == null || "".equals(user.getUserName()))
			throw new BusinessException("用户名不可为空！！！");
		if(user.getPhone() == null || "".equals(user.getPhone()))
			throw new BusinessException("绑定手机号码不可为空！！！");
		if(user.getCity() == null || "".equals(user.getCity()))
			throw new BusinessException("用户所在城市不可为空！！！");
		try {
			conn = DBUtil.getConnection();
			sql = "update `user` set user_name = ?, sex = ?, pwd = ?, phone = ?, email = ?, city = ? where user_Id = ? ";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserName());
			pst.setString(2, user.getSex());
			pst.setString(3, user.getPwd());
			pst.setString(4, user.getPhone());
			pst.setString(5, user.getEmail());
			pst.setString(6, user.getCity());
			pst.setString(7, user.getUserId());
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
	
	public void resetComTitle(String comId)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "update commodity set removetime = null where com_Id = ?" ;
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, comId);
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
	public void changeUserPwd(String userid, String oldPwd, String newPwd1, String newPwd2) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		if(newPwd1 == null || "".equals(newPwd1))
			throw new BusinessException("新密码不可为空！！！");
		if(newPwd2 == null || "".equals(newPwd2))
			throw new BusinessException("新密码不可为空！！！");
		if(!newPwd1.equals(newPwd2))
			throw new BusinessException("新密码不一致！！！");
		
		
		try {
			conn = DBUtil.getConnection();
			sql = "select pwd from user where user_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			rs.next();
			if(!rs.getString(1).equals(oldPwd)) {
				throw new BusinessException("密码错误！！！");
			}
			
			pst.close();
			rs.close();
			sql = "update user set pwd = ? where user_Id = ? ";
			pst = conn.prepareStatement(sql);
			pst.setString(1,newPwd1);
			pst.setString(2, userid);
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
