package takeout.model;

public class Location {
	public static final String[] tableTitles = {"地址编号","用户编号","具体地址","手机号码","联系人"};
	public static final String[] UtableTitles = {"地址编号","具体地址","手机号码","联系人"};

	private int locaId;
	private String userId;
	private String loca;
	private String phone;
	private String connUser;
	public int getLocaId() {
		return locaId;
	}
	public void setLocaId(int locaId) {
		this.locaId = locaId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLoca() {
		return loca;
	}
	public void setLoca(String loca) {
		this.loca = loca;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getConnUser() {
		return connUser;
	}
	public void setConnUser(String connUser) {
		this.connUser = connUser;
	}
	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.locaId;
		else if(col==1)
			return ""+this.userId;
		else if(col==2)
			return ""+this.loca;
		else if(col==3)
			return ""+this.phone;
		else if(col==4)
			return ""+this.connUser;
		else 
			return "";
	}
	public String getUCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.locaId;
		else if(col==1)
			return ""+this.loca;
		else if(col==2)
			return ""+this.phone;
		else if(col==3)
			return ""+this.connUser;
		else 
			return "";
	}

}
