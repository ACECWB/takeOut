package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.itf.IOrderManager;
import takeout.model.Order;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class OrderManager implements IOrderManager {
	public void deleteOrder(String userid, String orderid, String businessid)throws BaseException{
		Connection conn = null;
		String sql = null;
		String couponid = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql = "select status,coupon_Id from orders where order_Id = ? and user_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, orderid);
			pst.setString(2, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			rs.next();
			couponid = rs.getString(2);
			if(!"等待商家处理".equals(rs.getString(1)))
				throw new BusinessException("已不可退该订单！！！");
			rs.close();
			pst.close();
			
			sql = "update collectorders set alreadycounts = alreadycounts - 1 where user_Id = ? and business_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, businessid);
			pst.execute();
			pst.close();
			
			sql = "update orders set status = '取消订单'  where order_Id = ? and user_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, orderid);
			pst.setString(2, userid);
			pst.execute();
			pst.close();
			
			sql = "update com2bus INNER JOIN(select * from orderinfo where order_Id = ?)a \r\n" + 
					"on a.com_Id = com2bus.com_Id and com2bus.business_Id = ?\r\n" + 
					"set com2bus.counts = com2bus.counts + a.count";
			pst = conn.prepareStatement(sql);
			pst.setString(1, orderid);
			pst.setString(2, businessid);
			pst.execute();
			pst.close();
			
			if(couponid != null && !"".equals(couponid)) {
				sql = "update ownedcoupons set removetime = null \r\n" + 
						"where ownorder = (\r\n" + 
						"select couponorder from orders where order_Id = ?\r\n" + 
						")";
				pst = conn.prepareStatement(sql);
				pst.setString(1, orderid);
				pst.execute();
				pst.close();
			}
			
			
			conn.commit();
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			}catch(Exception e1) {
				e1.printStackTrace();
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

	@Override
	public List<Order> loadAllOrders(String userid) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		List<Order> orders = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select o.order_Id, business_Id, deliver_Id, com_Id, count, price, coupon_Id,\r\n" + 
					"origin_amount, final_amount, order_time, req_time, receive_time, `status` \r\n" + 
					"from orders o, orderinfo oi \r\n" + 
					"where user_Id = ? and o.order_Id = oi.order_Id ";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Order o = new Order();
				o.setOrderid(rs.getString(1));
				o.setBusinessid(rs.getString(2));
				o.setDeliverid(rs.getString(3));
				o.setComid(rs.getString(4));
				o.setCounts(rs.getInt(5));
				o.setPrice(rs.getFloat(6));
				o.setCouponid(rs.getString(7));
				o.setOriginamount(rs.getFloat(8));
				o.setFinalamount(rs.getFloat(9));
				o.setOrderTime(rs.getTimestamp(10));
				o.setReqtime(rs.getTimestamp(11));
				o.setReceiveTime(rs.getTimestamp(12));
				o.setStatus(rs.getString(13));
				orders.add(o);
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
		return orders;
	}

}
