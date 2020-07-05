package takeout.itf;

import java.util.List;

import takeout.model.ComCate;
import takeout.model.ComTitle;
import takeout.model.Commodity;
import takeout.util.*;

public interface ICommodityManager {
	
	public List<Commodity> loadAllCommoditys()throws BaseException;
	public void addCommodity(Commodity commodity)throws BaseException;
	public void deleteCommodity(String comId, String businessId)throws BaseException;
	
	public List<ComTitle> loadAllComTitles()throws BaseException;
	public void addComTitle(ComTitle comtitle)throws BaseException;
	public void deleteComTitle(String comId)throws BaseException;
	
	public List<ComCate> loadAllComCates()throws BaseException;
	public void addComCate(ComCate comcate)throws BaseException;
	public void deleteComCate(String comcateid)throws BaseException;
	
}
