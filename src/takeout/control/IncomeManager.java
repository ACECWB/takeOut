package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.itf.IIncomeManager;
import takeout.model.Income;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class IncomeManager implements IIncomeManager {

	public List<Income> loadAllIncomes(String deliverid) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		List<Income> incomes = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select di.order_Id, req_time, receive_time, time, review, each_bonus\r\n" + 
					"from orders o, deliver_income di\r\n" + 
					"where di.order_Id = o.order_Id and di.deliver_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, deliverid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Income i = new Income();
				i.setOrderid(rs.getString(1));
				i.setReqtime(rs.getTimestamp(2));
				i.setReceivetime(rs.getTimestamp(3));
				i.setReviewtime(rs.getTimestamp(4));
				i.setReview(rs.getString(5));
				i.setBonus(rs.getFloat(6));
				incomes.add(i);
				
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
		return incomes;
	}

}
