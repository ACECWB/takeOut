package StartTakeout;

import takeout.control.AdminManager;
import takeout.control.BusinessManager;
import takeout.control.DeliverManager;
import takeout.control.UserManager;
import takeout.itf.IAdminManager;
import takeout.itf.IUserManager;
import takeout.itf.IBusiness;
import takeout.itf.IDeliverManager;
public class takeOutUtil {
	public static final IAdminManager adminManager = new AdminManager();
	public static final IUserManager userManager = new UserManager();
	public static final IBusiness businessManager = new BusinessManager();
	public static final IDeliverManager deliverManager = new DeliverManager();

}
