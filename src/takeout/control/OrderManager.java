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
	public void deleteOrder(String userid, String orderid)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql = "select receive_time from orders where order_Id = ? and user_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, orderid);
			pst.setString(2, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			rs.next();
			if(rs.getTimestamp(1)!=null)
				throw new BusinessException("不可退已签收的订单！！！");
			rs.close();
			pst.close();
			
			sql = "delete from orderinfo where order_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, orderid);
			pst.execute();
			pst.close();
			
			sql = "delete from orders where order_Id = ? and user_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, orderid);
			pst.setString(2, userid);
			pst.execute();
			pst.close();
			
			
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
			sql = "select o.order_Id, deliver_Id, com_Id, count, price, coupon_Id,\r\n" + 
					"origin_amount, final_amount, order_time, req_time, receive_time, `status` \r\n" + 
					"from orders o, orderinfo oi \r\n" + 
					"where user_Id = ? and o.order_Id = oi.order_Id ";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Order o = new Order();
				o.setOrderid(rs.getString(1));
				o.setDeliverid(rs.getString(2));
				o.setComid(rs.getString(3));
				o.setCounts(rs.getInt(4));
				o.setPrice(rs.getFloat(5));
				o.setCouponid(rs.getString(6));
				o.setOriginamount(rs.getFloat(7));
				o.setFinalamount(rs.getFloat(8));
				o.setOrderTime(rs.getTimestamp(9));
				o.setReqtime(rs.getTimestamp(10));
				o.setReceiveTime(rs.getTimestamp(11));
				o.setStatus(rs.getString(12));
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
