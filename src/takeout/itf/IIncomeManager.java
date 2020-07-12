package takeout.itf;

import java.util.List;

import takeout.control.*;
import takeout.util.*;
import takeout.model.*;

public interface IIncomeManager {
	public List<Income> loadAllIncomes(String deliverid)throws BaseException;
	
	public void addIncome(Income income)throws BaseException;
	
	
	public List<Income> loadPartIncomes(String deliverid, String year, String month) throws BaseException;
}
