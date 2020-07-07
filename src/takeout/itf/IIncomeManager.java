package takeout.itf;

import java.util.List;

import takeout.control.*;
import takeout.util.*;
import takeout.model.*;

public interface IIncomeManager {
	public List<Income> loadAllIncomes(String deliverid)throws BaseException;
	
	
}
