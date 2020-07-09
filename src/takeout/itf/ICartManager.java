package takeout.itf;

import java.util.List;

import takeout.model.Cart;
import takeout.model.Order;
import takeout.util.BaseException;

public interface ICartManager {
	public List<Cart> loadAllCarts(String userid)throws BaseException;
	public void deleteCart(String userid, String businessid, String comid)throws BaseException;
	public void addCart(Cart cart)throws BaseException;
	
	public void makeSure(String userid)throws BaseException;
	public List<Cart> loadAllCarts(String userid, String businessid)throws BaseException;
	
	public String getOrigin(String userid, String businessid)throws BaseException;
	public String getAfterFull(String userid, String businessid)throws BaseException;
	public String[] getCoupons(String userid, String businessid)throws BaseException;
	public String getAfterCoupon(String couponid, String afterFull)throws BaseException;
	public void purchase(Order order)throws BaseException;
}
