package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.itf.IReviewManager;
import takeout.model.IncomeStatistic;
import takeout.model.Review;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class ReviewManager implements IReviewManager {

	@Override
	public List<Review> loadAllReviews(String businessid) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		List<Review> reviews = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select user_Id, r.com_Id, c.com_name,  review_date, stars, content\r\n" + 
					"from review r, commodity c\r\n" + 
					"where r.com_Id = c.com_Id and business_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, businessid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Review r= new Review();
				r.setUserid(rs.getString(1));
				r.setComid(rs.getString(2));
				r.setComname(rs.getString(3));
				r.setReviewtime(rs.getTimestamp(4));
				r.setStars(rs.getInt(5));
				r.setContent(rs.getString(6));
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
