package takeout.itf;

import java.util.List;

import takeout.model.Order;
import takeout.util.BaseException;

public interface IOrderManager {
	public List<Order> loadAllOrders(String userid, String orderid,int model)throws BaseException;
	public List<Order> loadAllOrders(String userid)throws BaseException;

	public List<Order> loadAllBOrders()throws BaseException;
	public List<Order> loadAllDOrders()throws BaseException;
	public List<Order>loadAllOrderInfo(Order order)throws BaseException;
	public void changeBStatus(String orderid)throws BaseException;
	public void quitBStatus(String orderid)throws BaseException;
	
	public void getOrder(String orderid)throws BaseException;
	public void FinishOrder(String orderid)throws BaseException;
	public List<Order> loadAllBOrders1(int model)throws BaseException;
	
	public void deleteOrder(String userid, String orderid, String businessid)throws BaseException;
}
