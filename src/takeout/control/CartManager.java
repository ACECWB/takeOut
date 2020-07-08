package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.itf.ICartManager;
import takeout.model.Cart;
import takeout.model.User;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class CartManager implements ICartManager {

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
				
				sql = "insert into cart(user_Id, com_Id, com_name, business_Id, counts, price, business_name) values (?,?,?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, cart.getUserid());
				pst.setString(2, cart.getComid());
				pst.setString(3, cart.getComname());
				pst.setString(4, cart.getBusinessid());
				pst.setInt(5, cart.getCounts());
				pst.setFloat(6, cart.getPrice());
				pst.setString(7, cart.getBusinessname());
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
