package takeout.itf;

import java.util.List;

import takeout.model.Location;
import takeout.util.*;
public interface ILocationManager {
	public void addLocation(Location loc)throws BaseException;
	public List<Location> loadAllLocations()throws BaseException;
	public void deleteLocation(int locaId)throws BaseException;
	
}
