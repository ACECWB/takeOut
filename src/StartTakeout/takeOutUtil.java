package StartTakeout;

import takeout.control.AdminManager;
import takeout.control.UserManager;
import takeout.itf.IAdminManager;
import takeout.itf.IUserManager;

public class takeOutUtil {
	public static final IAdminManager adminManager = new AdminManager();
	public static final IUserManager userManager = new UserManager();
	
}
