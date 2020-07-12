package takeout.itf;

import java.util.List;

import takeout.model.Business;
import takeout.model.Order;
import takeout.util.BaseException;

public interface IBusiness {
	
	public void addBusiness(Business business) throws BaseException;
	public void deleteBusiness(String businessId) throws BaseException;
	public List<Business> loadAllBusiness(boolean withDeletedBusiness) throws BaseException;
	public List<Business> loadAllBusiness(String userid) throws BaseException;

	public void modifyBusinessName(String businessname, String businessid)throws BaseException;
	
	
	public Business login(String userid, String pwd)throws BaseException;
	public Business reg(Business bus) throws BaseException;
	
}
