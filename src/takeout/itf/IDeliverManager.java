package takeout.itf;

import takeout.util.*;

import java.util.List;

import takeout.model.Deliver;
public interface IDeliverManager {
	public void addDeliver(Deliver deliver)throws BaseException;
	public void deleteDeliver(String deliverId)throws BaseException;
	public List<Deliver> loadAllDelivers()throws BaseException;
	
	public void modifyDeliver(Deliver deliver)throws BaseException;
	
	public Deliver reg(Deliver d) throws BaseException;
	public Deliver login(String userid, String pwd)throws BaseException;
	
	
}
