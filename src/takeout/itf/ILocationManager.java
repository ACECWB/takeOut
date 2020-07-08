package takeout.itf;

import java.util.List;

import takeout.model.Location;
import takeout.util.*;
public interface ILocationManager {
	public void addLocation(Location loc)throws BaseException;
	public List<Location> loadAllLocations()throws BaseException;
	public void deleteLocation(int locaId)throws BaseException;
	
	public List<Location> loadAllLocations(String userid)throws BaseException;
	public void modifyLoca(int locaid, String loca)throws BaseException;
	public void modifyPhone(int locaid, String phone)throws BaseException;
	public void modifyCon(int locaid, String con)throws BaseException;
	
}
