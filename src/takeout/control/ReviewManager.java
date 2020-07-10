package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import takeout.itf.IReviewManager;
import takeout.model.IncomeStatistic;
import takeout.model.Review;
import takeout.model.User;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class ReviewManager implements IReviewManager {

	public void addReview(Review review)throws BaseException{
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();//插入评论
			conn.setAutoCommit(false);
			sql = "insert into review(order_Id, content, review_date, stars) values (?,?,?,?)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, review.getOrderid());
			pst.setString(2, review.getContent());
			pst.setTimestamp(3, new java.sql.Timestamp(review.getReviewtime().getTime()));
			pst.setFloat(4, review.getStars());
			pst.execute();
			pst.close();
			//确认送达后更新商家星级,其余平均消费、总订单数由骑手系统确认送达时更新
			sql = "update business set stars = (\r\n" + 
					"	select avg(stars) from review where business_Id = ?\r\n" + 
					")\r\n" + 
					"where business_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, review.getBusinessid());
			pst.setString(2, review.getBusinessid());
			pst.execute();
			pst.close();
			
			sql = "update orders set isreviewed = 1 where order_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, review.getOrderid());
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
	
	public List<Review> loadAllUReviews(String userid)throws BaseException{
		Connection conn = null;
		String sql = null;
		List<Review> reviews = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select r.order_Id, b.business_Id, b.business_name, r.stars, r.content, d.deliver_Id,\r\n" + 
					"d.deliver_name, di.review, r.review_date\r\n" + 
					"from orders o, business b, review r, deliver d, deliver_income di\r\n" + 
					"where o.user_Id = ? and o.order_Id = r.order_Id and di.order_Id = o.order_Id and \r\n" + 
					"o.deliver_Id = di.deliver_Id and o.business_Id = b.business_Id and d.deliver_Id = di.deliver_Id";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Review review = new Review();
				review.setOrderid(rs.getString(1));
				review.setBusinessid(rs.getString(2));
				review.setBusinessname(rs.getString(3));
				review.setStars(rs.getFloat(4));
				review.setContent(rs.getString(5));
				review.setDeliverid(rs.getString(6));
				review.setDelivername(rs.getString(7));
				review.setDeliverreview(rs.getString(8));
				review.setReviewtime(rs.getTimestamp(9));
				reviews.add(review);
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
		return reviews;
	}
	@Override
	public List<Review> loadAllBReviews(String businessid) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		List<Review> reviews = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select o.user_Id, content, stars, review_date from review r, orders o\r\n" + 
					"where o.order_Id = r.order_Id and o.business_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, businessid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Review r= new Review();
				r.setUserid(rs.getString(1));
				r.setContent(rs.getString(2));
				r.setStars(rs.getInt(3));
				r.setReviewtime(rs.getTimestamp(4));
				r.setBusinessid(businessid);
				reviews.add(r);
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
		return reviews;
	}

}
