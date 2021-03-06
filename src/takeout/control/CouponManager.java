package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.itf.ICouponManager;
import takeout.model.Business;
import takeout.model.Coupon;
import takeout.model.User;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class CouponManager implements ICouponManager {

	public void modifyCcoupon(Coupon coupon)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "update ownedcoupons set ineffect_time = ? where user_Id = ? and coupon_Id = ? and ownorder = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setTimestamp(1, new java.sql.Timestamp(coupon.getIneffectDate().getTime()));
			pst.setString(2, coupon.getUserId());
			pst.setString(3, coupon.getCouponId());
			pst.setInt(4, coupon.getOwnOrder());
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
	public void modifyBcoupon(Coupon coupon, int origindays)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql = "update coupon set discount_money = ?, need_orders = ?, start_time = ?,\r\n" + 
					" end_time = ?, effect_days = ? where coupon_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setFloat(1, coupon.getDiscountMoney());
			pst.setInt(2, coupon.getNeedOrders());
			pst.setTimestamp(3, new java.sql.Timestamp(coupon.getStartTime().getTime()));
			pst.setTimestamp(4, new java.sql.Timestamp(coupon.getEndTime().getTime()));
			pst.setInt(5, coupon.getEffectDays());
			pst.setString(6, coupon.getCouponId());
			pst.execute();
			pst.close();
			System.out.println(origindays+"   "+coupon.getEffectDays());
			if(origindays != coupon.getEffectDays()) {//更新用户所拥有的优惠券
				sql = "update ownedcoupons set ineffect_time = DATE_ADD(ineffect_time,INTERVAL ? day) where coupon_Id = ? and removetime is null\r\n" + 
						"";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, coupon.getEffectDays() - origindays);
				pst.setString(2, coupon.getCouponId());
				System.out.println(coupon.getBusinessId());

				System.out.println(coupon.getCouponId());
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

	public List<Coupon> loadAllBCoupons(Business business, boolean withDeleted)throws BaseException{
		Connection conn = null;
		String sql = null;
		List<Coupon> coupons = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select coupon_Id, discount_money, need_orders, start_time, end_time, effect_days\r\n" + 
					"from coupon where business_Id = ?";
			if(withDeleted == true) {
				sql += " and end_time > now()";
			}
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, business.getBusinessId());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Coupon c = new Coupon();
				c.setCouponId(rs.getString(1));
				c.setDiscountMoney(rs.getFloat(2));
				c.setNeedOrders(rs.getInt(3));
				c.setStartTime(rs.getTimestamp(4));
				c.setEndTime(rs.getTimestamp(5));
				c.setEffectDays(rs.getInt(6));
				coupons.add(c);
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
		return coupons;
	}
	public List<Coupon> loadAllCollects(String userid)throws BaseException{
		Connection conn = null;
		String sql = null;
		List<Coupon> coupons = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();//视图
			sql = "select business_Id, business_name, coupon_Id, end_time, need_orders, alreadycounts,\r\n" + 
					" discount_money, effect_days from userownedcollects where end_time>now() and user_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Coupon c = new Coupon();
				c.setBusinessId(rs.getString(1));
				c.setBusinessName(rs.getString(2));
				c.setCouponId(rs.getString(3));
				c.setEndTime(rs.getTimestamp(4));
				c.setNeedOrders(rs.getInt(5));
				c.setAlreadyCounts(rs.getInt(6));
				c.setDiscountMoney(rs.getFloat(7));
				c.setEffectDays(rs.getInt(8));
				coupons.add(c);
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
		return coupons;
		
	}

	public List<Coupon> loadAllUCoupons(String userid)throws BaseException{
		Connection conn = null;
		String sql = null;
		List<Coupon> coupons = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "\r\n" + 
					"select c.business_Id, b.business_name, oc.coupon_Id, c.discount_money, oc.ineffect_time from business b, ownedcoupons oc,coupon c\r\n" + 
					"where user_Id = ? and oc.coupon_Id = c.coupon_Id and c.business_Id = b.business_Id and ineffect_time>now() order by ineffect_time\r\n" + 
					"\r\n" + 
					"";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setBusinessId(rs.getString(1));
				coupon.setBusinessName(rs.getString(2));
				coupon.setCouponId(rs.getString(3));
				coupon.setDiscountMoney(rs.getFloat(4));
				coupon.setIneffectDate(rs.getTimestamp(5));
				coupons.add(coupon);
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
		return coupons;
		
	}
	public List<Coupon> loadAllCCoupons(User user)throws BaseException{
		Connection conn = null;
		String sql = null;
		List<Coupon> coupons = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select b.business_Id, b.business_name, oc.coupon_Id, oc.ineffect_time, oc.ownorder \r\n" + 
					"from business b, ownedcoupons oc, coupon c\r\n" + 
					"where oc.coupon_Id = c.coupon_Id and c.business_Id = b.business_Id and user_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserId());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Coupon c = new Coupon();
				c.setBusinessId(rs.getString(1));
				c.setBusinessName(rs.getString(2));
				c.setCouponId(rs.getString(3));
				c.setIneffectDate(rs.getTimestamp(4));
				c.setOwnOrder(rs.getInt(5));
				coupons.add(c);
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
		return coupons;
	}


	public void addCCoupon(Coupon coupon)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn =DBUtil.getConnection();
//			if(coupon.getBusinessId() == null || "".equals(coupon.getBusinessId()))
//				throw new BusinessException("商家编号不可为空！！！");
			if(coupon.getCouponId() == null || "".equals(coupon.getCouponId()))
				throw new BusinessException("优惠券编号不可为空！！！");
			if(coupon.getIneffectDate() == null || "".equals(coupon.getIneffectDate().toString()))
				throw new BusinessException("有效截止期不可为空！！！");
			
			sql = "select * from coupon where coupon_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, coupon.getCouponId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next())
				throw new BusinessException("不存在该优惠券！！！");
			rs.close();
			pst.close();
			
			sql = "insert into ownedcoupons(user_Id, coupon_Id, ineffect_time)\r\n" + 
					"values (?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, coupon.getUserId());
			pst.setString(2, coupon.getCouponId());
			pst.setTimestamp(3, new java.sql.Timestamp(coupon.getIneffectDate().getTime()));
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
	public void addBCoupon(Coupon coupon) throws BaseException {
		// TODO Auto-generated method stub
		if(coupon.getCouponId() == null || "".equals(coupon.getCouponId()))
			throw new BusinessException("优惠券编号不可为空！！！");
		if(coupon.getBusinessId() == null || "".equals(coupon.getBusinessId()))
			throw new BusinessException("商家编号不可为空！！！");
		if(coupon.getDiscountMoney()<=0)
			throw new BusinessException("优惠金额不可<=0");
		if(coupon.getStartTime() == null || "".equals(coupon.getStartTime().toString()))
			throw new BusinessException("活动开始时间不可为空！！！");
		if(coupon.getEndTime() == null || "".equals(coupon.getEndTime().toString()))
			throw new BusinessException("活动结束时间不可为空！！！");
		if(coupon.getEffectDays()<=0)
			throw new BusinessException("优惠券有效时间不可<=0");
		if(coupon.getStartTime().after(coupon.getEndTime()))
			throw new BusinessException("结束时间不可早于开始时间！！！");
		
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "select * from coupon where coupon_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, coupon.getCouponId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("该优惠券编码已存在！！！");
			rs.close();
			pst.close();
			
			sql = "insert into coupon(coupon_Id, business_Id, discount_money, need_orders, start_time, end_time, effect_days)\r\n" + 
					"VALUES (?,?,?,?,?,?,?)";
			pst =conn.prepareStatement(sql);
			pst.setString(1, coupon.getCouponId());
			pst.setString(2, coupon.getBusinessId());
			pst.setFloat(3, coupon.getDiscountMoney());
			pst.setInt(4, coupon.getNeedOrders());
			pst.setTimestamp(5, new java.sql.Timestamp(coupon.getStartTime().getTime()));
			pst.setTimestamp(6, new java.sql.Timestamp(coupon.getEndTime().getTime()));
			pst.setInt(7, coupon.getEffectDays());
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
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				
		}

	
	}
	public void deleteCCoupon(int order)throws BaseException{
		Connection conn = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "delete from ownedcoupons where ownorder = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, order);
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
	public void deleteBCoupon(String couponid) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "select end_time from coupon where coupon_Id = ? and end_time>now()";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, couponid);
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) {
				throw new BusinessException("该优惠券已被删除！！！");
			}
			rs.close();
			pst.close();
			
			conn.setAutoCommit(false);
			sql = "update coupon set end_time = now() where coupon_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, couponid);
			pst.execute();
			pst.close();
			
			sql = "delete from ownedcoupons where coupon_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, couponid);
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
			if(conn!=null)
				try {
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
		}
	}

}
