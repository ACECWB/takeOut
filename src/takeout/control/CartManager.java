package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import takeout.itf.ICartManager;
import takeout.model.Cart;
import takeout.model.Order;
import takeout.model.User;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class CartManager implements ICartManager {
	public void purchase(Order order)throws BaseException{
		Connection conn = null;
		String sql = null;
		int counts = 0;
		if(order.getLocaid() == null || "".equals(order.getLocaid()))
			throw new BusinessException("地址编号不可为空！！！");
		if(order.getOrderTime() == null || "".equals(order.getOrderTime().toString()))
			throw new BusinessException("要求送达时间不可为空！！！");
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql = "select * from location where loca_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, order.getLocaid());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next())
				throw new BusinessException("不存在该地址联系信息！！！");
			rs.close();
			pst.close();
			
			
			sql = "update com2bus INNER JOIN(select * from cart where user_Id = ? and business_Id = ?)a on a.com_Id = com2bus.com_Id and a.business_Id = com2bus.business_Id\r\n" + 
					"set com2bus.counts = com2bus.counts - a.counts";
			pst = conn.prepareStatement(sql);
			pst.setString(1, order.getUserid());
			pst.setString(2, order.getBusinessid());
			pst.execute();
			pst.close();
			
			order.setOwnOrder(0);
			if(order.getCouponid() != null && !"".equals(order.getCouponid())){//删除同优惠券编号中最快过期的优惠券
				sql = "select ownorder from ownedcoupons where ineffect_time =(\r\n" + 
						"select min(ineffect_time) from ownedcoupons where removetime is null AND\r\n" + 
						"user_Id = ? and business_Id = ? and coupon_Id = ? \r\n" + 
						")";
				pst = conn.prepareStatement(sql);
				pst.setString(1, order.getUserid());
				pst.setString(2, order.getBusinessid());
				pst.setString(3, order.getCouponid());
				rs = pst.executeQuery();
				rs.next();
				System.out.print(order.getCouponid());
				order.setOwnOrder(rs.getInt(1));
				rs.close();
				pst.close();
				
				sql = "update ownedcoupons set removetime = now() where ownorder = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, order.getOwnOrder());
				pst.execute();
				pst.close();
				
			}
			
			sql = "insert into orders(order_Id, user_Id, loca_Id, coupon_Id, business_Id, origin_amount\r\n" + 
					", final_amount, order_time, req_time, `status`,couponorder,isreviewed)\r\n" + 
					"values (?,?,?,?,?,?,?,?,?,?,?,0)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, order.getOrderid());
			pst.setString(2, order.getUserid());
			pst.setString(3, order.getLocaid());
			pst.setString(4, order.getCouponid());
			pst.setString(5, order.getBusinessid());
			pst.setFloat(6, order.getOriginamount());
			pst.setFloat(7, order.getFinalamount());
			pst.setTimestamp(8, new java.sql.Timestamp(order.getOrderTime().getTime()));
			pst.setTimestamp(9, new java.sql.Timestamp(order.getReqtime().getTime()));
			pst.setString(10, order.getStatus());
			pst.setInt(11, order.getOwnOrder());
			pst.execute();
			pst.close();
			
		
			//将购买的详细信息插入info中
			sql = "insert into orderinfo  \r\n" + 
					"SELECT ?, com_Id, counts, price from cart where user_Id = ? and business_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, order.getOrderid());
			pst.setString(2, order.getUserid());
			pst.setString(3, order.getBusinessid());
			pst.execute();
			pst.close();
			//删除购物车中信息
			sql = "delete from cart where user_Id = ? and business_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, order.getUserid());
			pst.setString(2, order.getBusinessid());
			pst.execute();
			pst.close();
			
			sql = "select alreadycounts from collectorders where user_Id = ? and business_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, order.getUserid());
			pst.setString(2, order.getBusinessid());
			rs = pst.executeQuery();
			if(!rs.next()) {
				counts = 1;
				rs.close();
				pst.close();
				sql = "insert into collectorders(user_Id, business_Id, alreadycounts) values (?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, order.getUserid());
				pst.setString(2, order.getBusinessid());
				pst.setInt(3, 1);
				pst.execute();
				pst.close();
				
			}else {
				counts = rs.getInt(1) + 1;
				rs.close();
				pst.close();
				sql = "update collectorders set alreadycounts = alreadycounts + 1 where user_Id = ? and business_Id = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, order.getUserid());
				pst.setString(2, order.getBusinessid());
				pst.execute();
				pst.close();
				
			}
			int require = 0;
			int days = 0;
			String couponid = null;
			sql = "select coupon_Id, need_orders, effect_days from coupon where business_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, order.getBusinessid());
			rs = pst.executeQuery();
			System.out.println(123);
			if(!rs.next()) {
				;
			}else {
				
				System.out.println("counts: "+counts+"   require: "+rs.getInt(1));

				couponid = rs.getString(1);
				require = rs.getInt(2);
				days = rs.getInt(3);
				if(require > counts)
					;
				else {
					counts = counts - require;
					rs.close();
					pst.close();
					
					sql = "update collectorders set alreadycounts = ? where user_Id = ? and business_Id = ?";
					pst = conn.prepareStatement(sql);
					pst.setInt(1, counts);
					pst.setString(2, order.getUserid());
					pst.setString(3, order.getBusinessid());
					pst.execute();
					pst.close();
					
					sql = "insert into ownedcoupons(user_Id, business_Id, coupon_Id, ineffect_time) values (?,?,?,?)";
					pst = conn.prepareStatement(sql);
					pst.setString(1, order.getUserid());
					pst.setString(2, order.getBusinessid());
					pst.setString(3, couponid);
					pst.setTimestamp(4, new java.sql.Timestamp(order.getOrderTime().getTime() + days*24*60*60*1000L));
					pst.execute();
					pst.close();
					
				}
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
	public String getAfterCoupon(String couponid, String afterFull)throws BaseException{
		Connection conn = null;
		String sql = null;
		String afterCoupon = null;
		if(couponid == null || "".equals(couponid))
			return afterFull;
		
		try {
			conn = DBUtil.getConnection();
			sql = "select with_coupon, c.business_Id from fullreduction fr, coupon c where c.coupon_Id = ? and c.business_Id = fr.business_Id "; 
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, couponid);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) {//判断满减是否可与优惠券公用,若有优惠券无满减活动则可以靠第二个sql来正常执行
				if(rs.getString(1).equals("否")) {
					afterFull = this.getOrigin(User.currentLoginUser.getUserId(), rs.getString(2));//若不允许同时使用则满减后的钱重置为原始价格，再通过下面代码来实现优惠券的减免
					JOptionPane.showMessageDialog(null, "优惠券与满减活动不可叠加！现显示价格为原始-优惠券优惠金额！","提示",JOptionPane.INFORMATION_MESSAGE);

				}
			}
			rs.close();
			pst.close();
			
			sql = "select discount_money from coupon where coupon_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, couponid);
			rs = pst.executeQuery();
			System.out.println(afterFull + "   "+ couponid);
			if(rs.next()) {
				afterCoupon = ""+(Float.parseFloat(afterFull)-rs.getFloat(1));
				if(Float.parseFloat(afterCoupon) < 0) {
					afterCoupon = ""+0;
				}
			}else {
				rs.close();
				pst.close();
				conn.close();
				return afterFull;
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
		return afterCoupon;
	}
	
	public String[] getCoupons(String userid, String businessid)throws BaseException{
		Connection conn = null;
		String sql = null;
		String[] coupons = null;
		int size = 0;
		int i = 1;
		try {
			conn = DBUtil.getConnection();
//			sql = "select DISTINCT(with_coupon) from fullreduction where business_Id = ?";
//			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
//			pst.setString(1, businessid);
//			java.sql.ResultSet rs = pst.executeQuery();
//			if(rs.next()) {
//				if(rs.getString(1).equals("否")) {
//					coupons = new String[1];
//					coupons[0] = "";
//					rs.close();
//					pst.close();
//					conn.close();
//					return coupons;
//				}
//			}
//			rs.close();
//			pst.close();
			
			sql = "select count(DISTINCT(coupon_Id)) count from ownedcoupons where user_Id = ? and business_Id = ? and removetime is null"; 		
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, businessid);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
			coupons = new String[size + 1];
			coupons[0] = "";
			rs.close();
			pst.close();
			
			sql = "select DISTINCT(coupon_Id) from ownedcoupons where user_Id = ? and business_Id = ? and removetime is null";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, businessid);
			rs = pst.executeQuery();
			while(rs.next()) {
				coupons[i] = rs.getString(1);
				i++;
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

	public String getAfterFull(String userid, String businessid)throws BaseException{
		Connection conn = null;
		String sql = null;
		String Afterprice = null;
		String price = this.getOrigin(userid, businessid);
		
		try {
			conn = DBUtil.getConnection();
			sql = "select discount_amount, if(?-require_amount>=0,?-require_amount,?) m from fullreduction \r\n" + 
					"where business_Id = ? and removetime > now()\r\n" + 
					"order by m limit 1";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setFloat(1, Float.parseFloat(price));
			pst.setFloat(2, Float.parseFloat(price));
			pst.setFloat(3, Float.parseFloat(price));
			pst.setString(4, businessid);
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) {//不存在满减活动
				rs.close();
				pst.close();
				conn.close();
				return price;
			}
			if(rs.getFloat(2) == Float.parseFloat(price)) {//未到达最低满减要求金额
				rs.close();
				pst.close();
				conn.close();
				return price;
			}
			Float discount = rs.getFloat(1);
			Afterprice = ""+(Float.parseFloat(price)-discount);
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
		return Afterprice;
	}
	
	
	public String getOrigin(String userid, String businessid)throws BaseException{
		Connection conn = null;
		String sql = null;
		String price = null;
		try {
			conn = DBUtil.getConnection();
			sql = "select sum(price) from cart where user_Id = ? and business_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, businessid);
			java.sql.ResultSet rs = pst.executeQuery();
			rs.next();
			price = rs.getString(1);
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
		return price;
	}

	public List<Cart> loadAllCarts(String userid, String businessid)throws BaseException{
		Connection conn = null;
		String sql = null;
		List<Cart> carts = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select c.com_Id, cd.com_name, counts, price from cart c, commodity cd\r\n" + 
					"where user_Id = ? and business_Id = ? and c.com_Id = cd.com_Id";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, businessid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Cart c = new Cart();
				c.setComid(rs.getString(1));
				c.setComname(rs.getString(2));
				c.setCounts(rs.getInt(3));
				c.setPrice(rs.getFloat(4));
				carts.add(c);
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
		return carts;
	}
	public void makeSure(String userid)throws BaseException{
		Connection conn = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
//			rs.close();
//			pst.close();
//			conn.close();
			
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
	public void addCart(Cart cart)throws BaseException{
		Connection conn = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "select * from cart where user_Id = ? and com_Id = ? and business_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, User.currentLoginUser.getUserId());
			pst.setString(2, cart.getComid());
			pst.setString(3, cart.getBusinessid());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				rs.close();
				pst.close();
				
				sql = "update cart set counts = counts + ? , price = price + ? where user_Id = ? and com_Id = ? and business_Id = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, cart.getCounts());
				pst.setFloat(2, cart.getPrice());
				pst.setString(3, cart.getUserid());
				pst.setString(4, cart.getComid());
				pst.setString(5, cart.getBusinessid());
				pst.execute();
				
			}else {
				rs.close();
				pst.close();
				
				sql = "insert into cart(user_Id, com_Id, business_Id, counts, price) values (?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, cart.getUserid());
				pst.setString(2, cart.getComid());
				pst.setString(3, cart.getBusinessid());
				pst.setInt(4, cart.getCounts());
				pst.setFloat(5, cart.getPrice());
				pst.execute();
			
			}
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
	
	public void deleteCart(String userid, String businessid, String comid)throws BaseException{
		Connection conn = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "delete from cart where user_Id = ? and business_Id = ? and com_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, businessid);
			pst.setString(3, comid);
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
	public List<Cart> loadAllCarts(String userid) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		List<Cart> carts = new ArrayList<>();
		
		try {
			conn = DBUtil.getConnection();
			sql = "select com_Id, com_name, business_Id, business_name, counts, price from cart where user_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Cart c = new Cart();
				c.setUserid(userid);
				c.setComid(rs.getString(1));
				c.setComname(rs.getString(2));
				c.setBusinessid(rs.getString(3));
				c.setBusinessname(rs.getString(4));
				c.setCounts(rs.getInt(5));
				c.setPrice(rs.getFloat(6));
				carts.add(c);
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
		
		return carts;
	}

}
