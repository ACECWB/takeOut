package takeout.itf;

import java.util.List;

import takeout.control.*;
import takeout.model.*;
import takeout.util.*;
public interface IFullReductionManager {
	public List<FullReduction> loadAllFullReductions(boolean withDeleted)throws BaseException;
	public void addFullReduction(FullReduction fullreduction)throws BaseException;
	public void deleteFullReduction(String reductId)throws BaseException;
	public List<FullReduction> loadAllFullReductions(String businessid, boolean withDeleted) throws BaseException;

	public void modifyReduction(FullReduction full)throws BaseException;
}
