package takeout.itf;

import java.util.List;

import takeout.model.Order;
import takeout.util.BaseException;

public interface IOrderManager {
	public List<Order> loadAllOrders(String userid)throws BaseException;
	public void deleteOrder(String userid, String orderid)throws BaseException;
}
