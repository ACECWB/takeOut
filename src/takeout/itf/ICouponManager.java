package takeout.itf;

import takeout.util.*;

import java.util.List;

import takeout.model.*;

public interface ICouponManager {
	public List<Coupon> loadAllBCoupons(Business business)throws BaseException;
	public List<Coupon> loadAllCCoupons(User user)throws BaseException;
	
	public void addBCoupon(Coupon coupon)throws BaseException;
	public void addCCoupon(Coupon coupon)throws BaseException;

	public void deleteBCoupon(String couponid)throws BaseException;
	public void deleteCCoupon(int order)throws BaseException;

}
