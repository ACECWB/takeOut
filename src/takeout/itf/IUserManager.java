package takeout.itf;


import java.util.List;


import takeout.model.User;
import takeout.util.*;

public interface IUserManager {

	public void createUser(User user)throws BaseException;
	public void changeUserPwd(String userid,String oldPwd,String newPwd)throws BaseException;
	public void deleteUser(String userid)throws BaseException;
//	public User loadUsers(String userid)throws BaseException;
	public List<User> loadAllUsers(boolean withDeletedUser)throws BaseException;
//	public boolean modifyUserName(String userid, String updateName) throws BusinessException;
	public void resetUserPwd(String userid)throws BaseException;
	
}
