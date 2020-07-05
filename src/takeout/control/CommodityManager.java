package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.itf.ICommodityManager;
import takeout.model.ComCate;
import takeout.model.ComTitle;
import takeout.model.Commodity;
import takeout.util.*;

public class CommodityManager implements ICommodityManager {
	public List<ComCate> loadAllComCates()throws BaseException{
		Connection conn = null;
		String sql = null;
		List<ComCate> ccs = new ArrayList<>();
		try{
			conn = DBUtil.getConnection();
			sql = "select category_Id, category_name, createtime, removetime from commoditycategory";
			
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				ComCate cc = new ComCate();
				cc.setCategoryId(rs.getString(1));
				cc.setCategoryName(rs.getString(2));
				cc.setCreatetime(rs.getTimestamp(3));
				cc.setEndtime(rs.getTimestamp(4));
				ccs.add(cc);
			}
			rs.close();
			st.close();
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
		
		return ccs;
	}
	
	
	public void addComCate(ComCate comcate)throws BaseException{
		Connection conn = null;
		String sql = null;
		if(comcate.getCategoryId()==null || "".equals(comcate.getCategoryId()))
			throw new BusinessException("类型编号不可为空！！！");
		if(comcate.getCategoryName()==null || "".equals(comcate.getCategoryName()))
			throw new BusinessException("类型名称不可为空！！！");
		
		try{
			conn = DBUtil.getConnection();
			sql = "select * from commoditycategory where category_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, comcate.getCategoryId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("该商品类别已存在！！！");
			rs.close();
			pst.close();
			
			sql = "insert into commoditycategory(category_Id, category_name,createtime) values (?,?,now())";
			pst = conn.prepareStatement(sql);
			pst.setString(1, comcate.getCategoryId());
			pst.setString(2, comcate.getCategoryName());
			pst.execute();
			pst.close();
			
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
	
	public void deleteComCate(String comcateid)throws BaseException{
		Connection conn = null;
		String sql = null;
		try{
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql = "update commodity set removetime = now() where category_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, comcateid);
			pst.execute();
			pst.close();
			
			sql = "update commoditycategory set removetime = now() where category_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, comcateid);
			pst.execute();
			pst.close();
			
			sql = "delete com2bus from commodity ,commoditycategory,com2bus\r\n" + 
					"where commodity.category_Id = ? and commodity.category_Id = commoditycategory.category_Id \r\n" + 
					"and commodity.com_Id = com2bus.com_Id";
			pst = conn.prepareStatement(sql);
			pst.setString(1, comcateid);
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
	
	
	public List<ComTitle> loadAllComTitles()throws BaseException{
		Connection conn = null;
		String sql = null;
		List<ComTitle> cts = new ArrayList<>();
		try{
			conn = DBUtil.getConnection();
			sql = "select com_Id, category_Id, com_name,createtime,removetime from commodity";
			
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				ComTitle ct = new ComTitle();
				ct.setComId(rs.getString(1));
				ct.setCategoryId(rs.getString(2));
				ct.setComName(rs.getString(3));
				ct.setCreatetime(rs.getTimestamp(4));
				ct.setEndtime(rs.getTimestamp(5));
				cts.add(ct);
			}
			rs.close();
			st.close();
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
		
		return cts;
	}
	public void addComTitle(ComTitle comtitle)throws BaseException{
		Connection conn = null;
		String sql = null;
		
		if(comtitle.getComId()==null || "".equals(comtitle.getComId()))
			throw new BusinessException("商品编号不可为空！！！");
		if(comtitle.getComName()==null || "".equals(comtitle.getComName()))
			throw new BusinessException("商品名称不可为空！！！");
		
		try{
			conn = DBUtil.getConnection();
			sql = "select * from commodity where com_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, comtitle.getComId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("该商品编号已存在！！！");
			rs.close();
			pst.close();
			
			sql = "select * from commodity where category_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, comtitle.getCategoryId());
			rs = pst.executeQuery();
			if(!rs.next())
				throw new BusinessException("该类别不存在！！！");
			rs.close();
			pst.close();
			
			sql = "insert into commodity(com_Id, category_Id, com_name,createtime) values (?,?,?,now())";
			pst = conn.prepareStatement(sql);
			pst.setString(1, comtitle.getComId());
			pst.setString(2, comtitle.getCategoryId());
			pst.setString(3, comtitle.getComName());
			pst.execute();
			pst.close();
			
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
	public void deleteComTitle(String comId)throws BaseException{
		Connection conn = null;
		String sql = null;
		try{
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql = "update commodity set removetime = now() where com_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, comId);
			pst.execute();
			pst.close();
			
			sql = "delete from com2bus where com_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, comId);
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
	public List<Commodity> loadAllCommoditys() throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		List<Commodity> commoditys = new ArrayList<>();
		try{
			conn = DBUtil.getConnection();
			sql = "select cb.com_Id, c.com_name, cc.category_Id, cc.category_name, cb.business_Id, counts, each_price\r\n" + 
					"from com2bus cb, commodity c,commoditycategory cc\r\n" + 
					"where cb.com_Id = c.com_Id and cc.category_Id = c.category_Id";
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Commodity c = new Commodity();
				c.setComId(rs.getString(1));
				c.setComName(rs.getString(2));
				c.setCategoryId(rs.getString(3));
				c.setCategoryName(rs.getString(4));
				c.setBusinessId(rs.getString(5));
				c.setCounts(rs.getInt(6));
				c.setEachPrice(rs.getFloat(7));
				commoditys.add(c);
			}
			rs.close();
			st.close();
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
		
		
		return commoditys;
	}

	@Override
	public void addCommodity(Commodity commodity) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		if(commodity.getBusinessId()==null || "".equals(commodity.getBusinessId()))
			throw new BusinessException("商家编号不可为空！！！");
		if(commodity.getComId()==null || "".equals(commodity.getComId()))
			throw new BusinessException("商品编号不可为空！！！");
		
		try{
			conn = DBUtil.getConnection();
			sql = "select * from commodity where com_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, commodity.getComId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next())
				throw new BusinessException("不存在该商品编号！！！");
			rs.close();
			pst.close();
			
			sql = "select * from business where business_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, commodity.getBusinessId());
			rs = pst.executeQuery();
			if(!rs.next())
				throw new BusinessException("不存在该商家编号！！！");
			rs.close();
			pst.close();
			
			sql = "insert into com2bus (com_Id, business_Id, counts, each_price) values (?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, commodity.getComId());
			pst.setString(2, commodity.getBusinessId());
			pst.setInt(3, commodity.getCounts());
			pst.setFloat(4, commodity.getEachPrice());
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
	public void deleteCommodity(String comId, String businessId) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		try{
			conn = DBUtil.getConnection();
			sql = "delete from com2bus where com_Id = ? and business_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, comId);
			pst.setString(2, businessId);
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

}
