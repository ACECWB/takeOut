package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.itf.IOrderManager;
import takeout.model.Business;
import takeout.model.Deliver;
import takeout.model.Order;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class OrderManager implements IOrderManager {
	
	public void FinishOrder(String orderid)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql = "update orders set status = '已送达', receive_time = now() where order_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, orderid);
			pst.execute();
			pst.close();
			
			sql = "select * from deliverget where order_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, orderid);
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) {
				rs.close();
				pst.close();
				
				sql = "update deliver set status = '空闲' where deliver_Id = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, Deliver.currentLoginDeliver.getDeliverId());
				pst.execute();
				
			}else
				rs.close();
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

	public void getOrder(String orderid)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql = "update orders set deliver_Id = ?, status = '正在配送中' where order_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, Deliver.currentLoginDeliver.getDeliverId());
			pst.setString(2, orderid);
			pst.execute();
			pst.close();
			
			sql = "update deliver set status = '配送中' where deliver_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, Deliver.currentLoginDeliver.getDeliverId());
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
	
	public List<Order> loadAllDOrders()throws BaseException{
		Connection conn = null;
		String sql = null;
		List<Order> orders = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select order_Id, business_name, location, conn_user, loca, phone_number,\r\n" + 
					"order_time, req_time, status from deliverget";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Order o = new Order();
				o.setOrderid(rs.getString(1));
				o.setBusinessName(rs.getString(2));
				o.setBusLoca(rs.getString(3));
				o.setConnname(rs.getString(4));
				o.setLoca(rs.getString(5));
				o.setPhone(rs.getString(6));
				o.setOrderTime(rs.getTimestamp(7));
				o.setReqtime(rs.getTimestamp(8));
				o.setStatus(rs.getString(9));
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
	public void quitBStatus(String orderid)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "update orders set status = '取消订单' where order_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, orderid);
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
	public void changeBStatus(String orderid)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "update orders set status = '等待配送' where order_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, orderid);
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
	public List<Order>loadAllOrderInfo(Order order)throws BaseException{
		Connection conn = null;
		String sql = null;
		List<Order> orders = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select oi.com_Id, com_name, count, price from orderinfo oi, commodity c where oi.com_Id = c.com_Id and order_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, order.getOrderid());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Order o = new Order();
				o.setComid(rs.getString(1));
				o.setComName(rs.getString(2));
				o.setCounts(rs.getInt(3));
				o.setPrice(rs.getFloat(4));
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
	public List<Order> loadAllBOrders()throws BaseException{
		Connection conn = null;
		String sql = null;
		List<Order> orders = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select order_Id, o.user_Id, loca, coupon_Id, deliver_Id, origin_amount,\r\n" + 
					"final_amount, order_time, req_time, status, receive_time, isreviewed from \r\n" + 
					"orders o, location l where business_Id = ? and o.loca_Id = l.loca_Id";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, Business.currentLoginBusiness.getBusinessId());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Order o = new Order();
				o.setOrderid(rs.getString(1));
				o.setUserid(rs.getString(2));
				o.setLoca(rs.getString(3));
				o.setCouponid(rs.getString(4));
				o.setDeliverid(rs.getString(5));
				o.setOriginamount(rs.getFloat(6));
				o.setFinalamount(rs.getFloat(7));
				o.setOrderTime(rs.getTimestamp(8));
				o.setReqtime(rs.getTimestamp(9));
				o.setStatus(rs.getString(10));
				o.setReceiveTime(rs.getTimestamp(11));
				o.setIsReviewed(rs.getInt(12));
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

	public List<Order> loadAllOrders(String userid)throws BaseException{
		Connection conn = null;
		String sql = null;
		List<Order> orders = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select o.order_Id, business_Id, deliver_Id, oi.com_Id, count, price, coupon_Id,\r\n" + 
					"origin_amount, final_amount, order_time, req_time, receive_time, `status` \r\n" + 
					"from orders o, orderinfo oi, commodity c\r\n" + 
					"where user_Id = ? and o.order_Id = oi.order_Id and c.com_Id = oi.com_Id";
		
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
	@Override
	public List<Order> loadAllOrders(String userid, String orderid,int model) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		List<Order> orders = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			if(model == 2) {//查看订单详细信息
				sql = "select c.com_Id, com_name, count, price from orderinfo oi, commodity c\r\n" + 
						"where c.com_Id = oi.com_Id and order_Id = ?";
				
			}else if(model == 1) {
				sql = "select order_Id, o.business_Id, business_name, deliver_Id, coupon_Id, origin_amount, final_amount, order_time, req_time\r\n" + 
						", receive_time, status, isreviewed from orders o, business b where user_Id = ? and o.business_Id = b.business_Id";
				
			}
		
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			if(model == 1) {
				pst.setString(1, userid);
			}else if(model == 2) {
				pst.setString(1, orderid);
			}
			java.sql.ResultSet rs = pst.executeQuery();
			if(model == 2) {
				while(rs.next()) {
					Order o = new Order();
					o.setComid(rs.getString(1));
					o.setComName(rs.getString(2));
					o.setCounts(rs.getInt(3));
					o.setPrice(rs.getFloat(4));
					orders.add(o);
				}
			}else if(model == 1) {
				while(rs.next()) {
					Order o = new Order();
					o.setOrderid(rs.getString(1));
					o.setBusinessid(rs.getString(2));
					o.setBusinessName(rs.getString(3));
					o.setDeliverid(rs.getString(4));
					o.setCouponid(rs.getString(5));
					o.setOriginamount(rs.getFloat(6));
					o.setFinalamount(rs.getFloat(7));
					o.setOrderTime(rs.getTimestamp(8));
					o.setReqtime(rs.getTimestamp(9));
					o.setReceiveTime(rs.getTimestamp(10));
					o.setStatus(rs.getString(11));
					o.setIsReviewed(rs.getInt(12));
					orders.add(o);
				}
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
