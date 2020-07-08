package takeout.itf;

import java.util.List;

import takeout.model.Cart;
import takeout.util.BaseException;

public interface ICartManager {
	public List<Cart> loadAllCarts(String userid)throws BaseException;
	public void deleteCart(String userid, String businessid, String comid)throws BaseException;
	public void addCart(Cart cart)throws BaseException;
	
}
