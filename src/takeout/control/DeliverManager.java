package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.itf.IDeliverManager;
import takeout.model.Deliver;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
public class DeliverManager implements IDeliverManager {

	@Override
	public void addDeliver(Deliver deliver) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		if(deliver.getDeliverId()==null || "".equals(deliver.getDeliverId()))
			throw new BusinessException("骑手编号不可为空！！！");
		if(deliver.getDeliverName()==null || "".equals(deliver.getDeliverName()))
			throw new BusinessException("骑手姓名不可为空！！！");
		if(deliver.getEmployTime()==null || "".equals(deliver.getEmployTime()))
			throw new BusinessException("骑手就职时间格式不正确或为空！！！");
		if(deliver.getIdentity()==null || "".equals(deliver.getIdentity()))
			throw new BusinessException("骑手身份不可为空！！！");
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

		try {
			conn = DBUtil.getConnection();
			sql = "select * from deliver where deliver_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, deliver.getDeliverId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("该骑手编号已存在!!!");
			rs.close();
			pst.close();
			
			sql = "insert into deliver(deliver_Id, deliver_name, employ_time, identity) values (?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, deliver.getDeliverId());
			pst.setString(2, deliver.getDeliverName());
			try {
				pst.setTimestamp(3, new java.sql.Timestamp(sdf.parse(deliver.getEmployTime()).getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pst.setString(4, deliver.getIdentity());
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

	@Override//现只删除deliver表中的信息
	public void deleteDeliver(String deliverId) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "select * from orders where deliver_Id = ? and status = '配送中'";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, deliverId);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("该骑手还在配送订单！暂时无法删除！！！");
			rs.close();
			pst.close();
			
			sql = "delete from deliver where deliver_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, deliverId);
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
	public List<Deliver> loadAllDelivers() throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		List<Deliver> delivers = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select deliver_Id, deliver_name, employ_time, quit_time, identity from deliver";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Deliver d = new Deliver();
				d.setDeliverId(rs.getString(1));
				d.setDeliverName(rs.getString(2));
				d.setEmployTime(sdf.format(rs.getTimestamp(3)));
				if(rs.getTimestamp(4)!=null)
					d.setQuitTime(sdf.format(rs.getTimestamp(4)));
				d.setIdentity(rs.getString(5));
				delivers.add(d);
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
		return delivers;
	}

}
