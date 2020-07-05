package takeout.itf;

import java.util.List;

import takeout.model.Business;
import takeout.util.BaseException;

public interface IBusiness {
	
	public void addBusiness(Business business) throws BaseException;
	public void deleteBusiness(String businessId) throws BaseException;
	public List<Business> loadAllBusiness(boolean withDeletedBusiness) throws BaseException;
	
}
