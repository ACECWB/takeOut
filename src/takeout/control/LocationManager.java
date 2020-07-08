package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.itf.ILocationManager;
import takeout.model.Location;
import takeout.util.*;


public class LocationManager implements ILocationManager {
	public void modifyLoca(int locaid, String loca)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "update location set loca = ? where loca_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, loca);
			pst.setInt(2, locaid);
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
	public void modifyPhone(int locaid, String phone)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "update location set phone_number = ? where loca_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, phone);
			pst.setInt(2, locaid);
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
	public void modifyCon(int locaid, String con)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "update location set conn_user = ? where loca_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, con);
			pst.setInt(2, locaid);
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
	public List<Location> loadAllLocations(String userid)throws BaseException{
		Connection conn = null;
		String sql = null;
		List<Location> locs = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select loca_Id, loca, phone_number, conn_user from location where user_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Location l = new Location();
				l.setLocaId(rs.getInt(1));
				l.setUserId(userid);
				l.setLoca(rs.getString(2));
				l.setPhone(rs.getString(3));
				l.setConnUser(rs.getString(4));
				locs.add(l);
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
		return locs;
		
	}

	@Override
	public void addLocation(Location loc) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		
		if(loc.getLoca()==null || "".equals(loc.getLoca()))
			throw new BusinessException("具体地址信息不可为空！！！");
		if(loc.getPhone()==null || "".equals(loc.getPhone()))
			throw new BusinessException("联系电话不可为空！！！");
		if(loc.getConnUser()==null || "".equals(loc.getConnUser()))
			throw new BusinessException("联系人不可为空！！！");
		
		try {
			conn = DBUtil.getConnection();
			sql = "select * from user where user_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, loc.getUserId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next())
				throw new BusinessException("不存在该用户编号!!!");
			rs.close();
			pst.close();
			
			sql = "insert into location(user_Id, loca, phone_number, conn_user) values (?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, loc.getUserId());
			pst.setString(2, loc.getLoca());
			pst.setString(3, loc.getPhone());
			pst.setString(4, loc.getConnUser());
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
	public List<Location> loadAllLocations() throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		List<Location> locations = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select loca_Id, user_Id, loca, phone_number, conn_user from location";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Location l = new Location();
				l.setLocaId(rs.getInt(1));
				l.setUserId(rs.getString(2));
				l.setLoca(rs.getString(3));
				l.setPhone(rs.getString(4));
				l.setConnUser(rs.getString(5));
				locations.add(l);
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
		return locations;
	}

	
	public void deleteLocation(int locaId) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "select * from orders where loca_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, locaId);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("订单信息中使用了该地址信息！故不能删除！！！");
			rs.close();
			pst.close();
			
			sql = "delete from location where loca_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, locaId);
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
