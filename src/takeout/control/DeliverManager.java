package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.itf.IDeliverManager;
import takeout.model.Business;
import takeout.model.Deliver;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
public class DeliverManager implements IDeliverManager {

	public Deliver login(String userid, String pwd)throws BaseException{
		Connection conn = null;
		String sql = null;
		Deliver d = new Deliver();
		d.setDeliverId(userid);
		d.setPwd(pwd);
		
		if(d.getDeliverId() == null || "".equals(d.getDeliverId()))
			throw new BusinessException("���ֱ�Ų���Ϊ�գ�����");
		if(d.getPwd() == null || "".equals(d.getPwd()))
			throw new BusinessException("���벻��Ϊ�գ�����");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			conn = DBUtil.getConnection();
			sql = "select deliver_name, employ_time, identity, quit_time, status, pwd from deliver where deliver_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				if(rs.getTimestamp(4) != null) {
					rs.close();
					pst.close();
					throw new BusinessException("���˺Ų����ڣ�����");
				}
				if(!rs.getString(6).equals(pwd)) {
					rs.close();
					pst.close();
					
					throw new BusinessException("������󣡣���");

				}
				
				d.setDeliverName(rs.getString(1));
				d.setEmployTime(sdf.format(rs.getTimestamp(2)));
				d.setIdentity(rs.getString(3));
				d.setStatus(rs.getString(5));
				
			}else {
				throw new BusinessException("���˺Ų����ڣ�����");
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
		return d;
		
		
	}
	
	public Deliver reg(Deliver d) throws BaseException{
		Connection conn = null;
		String sql = null;
		if(d.getDeliverName() == null || "".equals(d.getDeliverName()))
			throw new BusinessException("������������Ϊ�գ�����");
		if(d.getDeliverId() == null || "".equals(d.getDeliverId()))
			throw new BusinessException("���ֱ�Ų���Ϊ�գ�����");
		if(d.getPwd() == null || "".equals(d.getPwd()))
			throw new BusinessException("���벻��Ϊ�գ�����");

		try {
			conn = DBUtil.getConnection();
			sql = "select * from deliver where deliver_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, d.getDeliverId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("�ñ���ѱ�ע��!!!");
			rs.close();
			pst.close();
			
			sql = "insert into deliver(deliver_Id, deliver_name, employ_time, "
					+ "identity, status, pwd) values (?,?,now(),'����','����',?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, d.getDeliverId());
			pst.setString(2, d.getDeliverName());
			pst.setString(3, d.getPwd());
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
		return d;
		
	}
	
	public void modifyDeliver(Deliver deliver)throws BaseException{
		Connection conn = null;
		String sql = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			conn = DBUtil.getConnection();
			sql = "update deliver set deliver_name = ?, employ_time = ?, quit_time = ?, identity = ? where deliver_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, deliver.getDeliverName());
			try {
				pst.setTimestamp(2, new java.sql.Timestamp(sdf.parse(deliver.getEmployTime()).getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(deliver.getQuitTime() == null || "null".equals(deliver.getQuitTime()))
				pst.setNull(3, java.sql.Types.DATE);
			else {
				System.out.println("get:"+deliver.getQuitTime());
				try {
					pst.setTimestamp(3, new java.sql.Timestamp(sdf.parse(deliver.getQuitTime()).getTime()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			pst.setString(4, deliver.getIdentity());
			pst.setString(5, deliver.getDeliverId());
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
	public void addDeliver(Deliver deliver) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		if(deliver.getDeliverId()==null || "".equals(deliver.getDeliverId()))
			throw new BusinessException("���ֱ�Ų���Ϊ�գ�����");
		if(deliver.getDeliverName()==null || "".equals(deliver.getDeliverName()))
			throw new BusinessException("������������Ϊ�գ�����");
		if(deliver.getEmployTime()==null || "".equals(deliver.getEmployTime()))
			throw new BusinessException("���־�ְʱ���ʽ����ȷ��Ϊ�գ�����");
		if(deliver.getIdentity()==null || "".equals(deliver.getIdentity()))
			throw new BusinessException("������ݲ���Ϊ�գ�����");
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

		try {
			conn = DBUtil.getConnection();
			sql = "select * from deliver where deliver_Id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, deliver.getDeliverId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("�����ֱ���Ѵ���!!!");
			rs.close();
			pst.close();
			
			sql = "insert into deliver(deliver_Id, deliver_name, employ_time, identity, status,pwd) values (?,?,?,?, '����',1)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, deliver.getDeliverId());
			pst.setString(2, deliver.getDeliverName());
			try {
				pst.setTimestamp(3, new java.sql.Timestamp(sdf.parse(deliver.getEmployTime()).getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pst.setString(4, deliver.getIdentity());
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

	@Override//��ֻɾ��deliver���е���Ϣ
	public void deleteDeliver(String deliverId) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "select * from orders where deliver_Id = ? and status = '������'";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, deliverId);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("�����ֻ������Ͷ�������ʱ�޷�ɾ��������");
			rs.close();
			pst.close();
			
			sql = "select quit_time from deliver where deliver_Id = ? and quit_time is not null";
			pst = conn.prepareStatement(sql);
			pst.setString(1, deliverId);
			rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("�������Ѿ�ɾ��������");
			rs.close();
			pst.close();
			
			sql = "update deliver set quit_time = now() where deliver_Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, deliverId);
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
	public List<Deliver> loadAllDelivers() throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = null;
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		List<Deliver> delivers = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			sql = "select deliver_Id, deliver_name, employ_time, quit_time, identity, status from deliver";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Deliver d = new Deliver();
				d.setDeliverId(rs.getString(1));
				d.setDeliverName(rs.getString(2));
				d.setEmployTime(sdf.format(rs.getTimestamp(3)));
				if(rs.getTimestamp(4)!=null)
					d.setQuitTime(sdf.format(rs.getTimestamp(4)));
				d.setIdentity(rs.getString(5));
				d.setStatus(rs.getString(6));
				delivers.add(d);
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
		return delivers;
	}

}
