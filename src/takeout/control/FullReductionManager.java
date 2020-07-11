package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.itf.IFullReductionManager;
import takeout.model.FullReduction;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class FullReductionManager implements IFullReductionManager {
	public void modifyReduction(FullReduction full)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "update fullreduction set business_Id = ?, require_amount = ? , discount_amount = ?, with_coupon = ? , removetime = ? where reduct_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, full.getBusinessId());
			pst.setFloat(2, full.getRequireAmount());
			pst.setFloat(3, full.getDiscountAmount());
			pst.setString(4, full.getWithCoupon());
			pst.setTimestamp(5, new java.sql.Timestamp(full.getEndTime().getTime()));
			pst.setString(6, full.getReductId());
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
	public List<FullReduction> loadAllFullReductions() throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		List<FullReduction> reducts = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select reduct_Id, require_amount, discount_amount, with_coupon, removetime, business_Id from fullreduction";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				FullReduction r = new FullReduction();
				r.setReductId(rs.getString(1));
				r.setRequireAmount(rs.getFloat(2));
				r.setDiscountAmount(rs.getFloat(3));
				r.setWithCoupon(rs.getString(4));
//				r.setStartTime(rs.getTimestamp(5));
				r.setEndTime(rs.getTimestamp(5));
				r.setBusinessId(rs.getString(6));
				reducts.add(r);
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
		return reducts;
	}

	@Override
	public void addFullReduction(FullReduction fullreduction)throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		System.out.println(fullreduction.getBusinessId() + "  "+fullreduction.getReductId());

		if(fullreduction.getReductId() == null || "".equals(fullreduction.getReductId()))
			throw new BusinessException("满减活动编号不可为空！！！");
		if(fullreduction.getDiscountAmount()<=0)
			throw new BusinessException("减免金额不可<=0！！！");
		if(fullreduction.getRequireAmount()<=0)
			throw new BusinessException("要求金额不可<=0！！！");
		
		try {
			conn =DBUtil.getConnection();
			sql = "select * from fullreduction where reduct_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, fullreduction.getReductId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("已存在该满减活动编号！！！");
			rs.close();
			pst.close();
			
			sql = "select * from business where business_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, fullreduction.getBusinessId());
			rs = pst.executeQuery();
			if(!rs.next())
				throw new BusinessException("不存在该商家！！！");
			
			sql = "insert into fullreduction(reduct_Id, require_amount, discount_amount, with_coupon, business_Id)\r\n" + 
					"VALUES (?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, fullreduction.getReductId());
			pst.setFloat(2, fullreduction.getRequireAmount());
			pst.setFloat(3, fullreduction.getDiscountAmount());
			pst.setString(4, fullreduction.getWithCoupon());
			pst.setString(5, fullreduction.getBusinessId());
//			pst.setTimestamp(5, new java.sql.Timestamp(fullreduction.getEndTime().getTime()));
//			pst.setTimestamp(6, new java.sql.Timestamp(fullreduction.getStartTime().getTime()));
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

	
	public void deleteFullReduction(String reductId)throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "update fullreduction set removetime = now() where reduct_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, reductId);
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
