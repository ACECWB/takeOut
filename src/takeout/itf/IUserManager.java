package takeout.itf;


import java.util.List;

import takeout.model.Admin;
import takeout.model.User;
import takeout.util.*;

public interface IUserManager {

	public void createUser(User user)throws BaseException;
	public void changeUserPwd(String userid,String oldPwd,String newPwd1, String newPwd2)throws BaseException;
	public void deleteUser(String userid)throws BaseException;
	public User login(String userid, String pwd)throws BaseException;
	public List<User> loadAllUsers(boolean withDeletedUser)throws BaseException;
//	public boolean modifyUserName(String userid, String updateName) throws BusinessException;
	public void resetUserPwd(String userid)throws BaseException;
	
	public void modifyUser(User user)throws BaseException;
	public void reg(User user) throws BaseException;
	public void vip(int month)throws BaseException;
	public void exchangeCoupon(String businessid, String couponid, int days)throws BaseException;

}
