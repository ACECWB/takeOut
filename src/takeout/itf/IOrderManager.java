package takeout.itf;

import java.util.List;

import takeout.model.Order;
import takeout.util.BaseException;

public interface IOrderManager {
	public List<Order> loadAllOrders(String userid, String orderid,int model)throws BaseException;
	public List<Order> loadAllOrders(String userid)throws BaseException;

	public void deleteOrder(String userid, String orderid, String businessid)throws BaseException;
}
