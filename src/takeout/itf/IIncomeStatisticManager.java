package takeout.itf;


import java.util.List;

import takeout.control.*;
import takeout.model.*;
import takeout.util.*;

public interface IIncomeStatisticManager {
	public List<IncomeStatistic> loadAllIncomeStatistics(String deliverid)throws BaseException;
	
}
