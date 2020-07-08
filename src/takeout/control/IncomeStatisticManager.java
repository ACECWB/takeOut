package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.itf.IIncomeStatisticManager;
import takeout.model.IncomeStatistic;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class IncomeStatisticManager implements IIncomeStatisticManager {

	@Override
	public List<IncomeStatistic> loadAllIncomeStatistics(String deliverid) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		List<IncomeStatistic> is = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select DATE_FORMAT(time,'%Y-%m') t ,year(time) `year`, month(time) `month`, count(*) counts, \r\n" + 
					"sum(if(each_bonus>0, each_bonus,0)) exincome, sum(if(each_bonus<0, each_bonus, 0)) excut\r\n" + 
					" from deliver_income \r\n" + 
					"where deliver_Id = ?\r\n" + 
					"group by t";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, deliverid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				IncomeStatistic s = new IncomeStatistic();
				s.setYear(rs.getString(2));
				s.setMonth(rs.getString(3));
				s.setCounts(rs.getInt(4));
				s.setBonus(rs.getFloat(5));
				s.setCut(rs.getFloat(6));
				double totalincome = 0;
				
				if(s.getCounts()<100)
					totalincome = s.getCounts()*2;
				else if(s.getCounts()<300)
					totalincome = 99*2 + (s.getCounts() - 99)*3;
				else if(s.getCounts()<450)
					totalincome = 99*2 + 200*3 + (s.getCounts() - 299)*5;
				else if(s.getCounts()<550)
					totalincome = 99*2 + 200*3 + 100*5 + (s.getCounts() - 449)*6;
				else if(s.getCounts()<650)
					totalincome = 99*2 + 200*3 + 100*5 + 100*6 + (s.getCounts() - 549)*7;
				else if(s.getCounts()>650)
					totalincome = 99*2 + 200*3 + 100*5 + 100*6 + 100*7 +(s.getCounts()-649)*8;
				
				s.setTotalincome((float)(totalincome + s.getCut() + s.getBonus()));
				is.add(s);
				
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
		return is;
	}

}
