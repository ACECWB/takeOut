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
			pst.setString(1, review.getOrderId());
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
			pst.setString(1, review.getOrderId());
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
	@Override
	public List<Review> loadAllReviews(String businessid) throws BaseException {
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
