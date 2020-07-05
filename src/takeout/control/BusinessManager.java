package takeout.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;

import takeout.itf.IBusiness;
import takeout.util.*;
import takeout.model.Business;
import java.text.SimpleDateFormat;
public class BusinessManager implements IBusiness {

	@Override
	public void addBusiness(Business business) throws BaseException {
		// TODO Auto-generated method stub
		if(business.getBusinessId()==null || "".equals(business.getBusinessId()))
			throw new BusinessException("�̼ұ�Ų���Ϊ�գ�����");
		if(business.getBusinessName()==null || "".equals(business.getBusinessName()))
			throw new BusinessException("�̼����Ʋ���Ϊ�գ�����");
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "select * from business where business_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, business.getBusinessId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("���̼ұ���Ѵ��ڣ�����");
			rs.close();
			pst.close();
			
			sql = "insert into business(business_Id, business_name, stars, avg_consume, sales_volume,createtime) values (?,?,0,0,0,now())";
			pst = conn.prepareStatement(sql);
			pst.setString(1, business.getBusinessId());
			pst.setString(2, business.getBusinessName());
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
	public void deleteBusiness(String businessId) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "select * from orders where business_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, businessId);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("�����������еĶ�������޷�ɾ��������");
			rs.close();
			pst.close();
			
			conn.setAutoCommit(false);
			sql = "delete from collectorders where business_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, businessId);
			pst.execute();
			pst.close();
			
			sql = "delete from coupon where business_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, businessId);
			pst.execute();
			pst.close();
			
			sql = "delete from ownedcoupons where business_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, businessId);
			pst.execute();
			pst.close();
			
			sql = "delete from com2bus where business_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, businessId);
			pst.execute();
			pst.close();
			
			sql = "update business set removetime = now() where business_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, businessId);
			pst.execute();
			pst.close();
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
			if(conn!=null) {
				try {
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}

	@Override
	public List<Business> loadAllBusiness(boolean withDeletedBusiness) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		List<Business> businesss = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			conn = DBUtil.getConnection();
			sql = "select business_Id, business_name, stars, avg_consume, sales_volume, createtime from business";
			if(!withDeletedBusiness)
				sql += " where removetime is null";
			
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Business b = new Business();
				b.setBusinessId(rs.getString(1));
				b.setBusinessName(rs.getString(2));
				b.setStars(rs.getInt(3));
				b.setAvg_consume(rs.getFloat(4));
				b.setSales_volume(rs.getInt(5));
				b.setCreateTime(sdf.format(rs.getTimestamp(6)));
				businesss.add(b);
			}
			rs.close();
			st.close();
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
		return businesss;
		
	}

}