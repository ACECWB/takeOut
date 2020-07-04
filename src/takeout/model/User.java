package takeout.model;

import java.util.Date;

public class User {
	
	public static User currentLoginUser=null;
	
	public static final String[] tableTitles = {"用户编号","用户名","性别","密码","绑定手机号码","绑定邮箱","城市","创建时间","删除时间","vip创建时间","vip结束时间"};
	private String userId;
	private String userName;
	private String sex;
	private String pwd;
	private String phone;
	private String email;
	private String city;
	private Date createTime;
	private Date removeTime;
	private int isVip;
	private Date vipStartTime;
	private Date vipEndTime;
	
	public int getIsVip() {
		return isVip;
	}
	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}
	public Date getRemoveTime() {
		return removeTime;
	}
	public void setRemoveTime(Date removetime) {
		this.removeTime = removetime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int isVip() {
		return isVip;
	}
	public void setVip(int isVip) {
		this.isVip = isVip;
	}
	public Date getVipStartTime() {
		return vipStartTime;
	}
	public void setVipStartTime(Date vipStartTime) {
		this.vipStartTime = vipStartTime;
	}
	public Date getVipEndTime() {
		return vipEndTime;
	}
	public void setVipEndTime(Date vipEndTime) {
		this.vipEndTime = vipEndTime;
	}
	
	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.userId;
		else if(col==1)
			return ""+this.userName;
		else if(col==2)
			return ""+this.sex;
		else if(col==3)
			return ""+this.pwd;
		else if(col==4)
			return ""+this.phone;
		else if(col==5)
			return ""+this.email;
		else if(col==6)
			return ""+this.city;
		else if(col==7)
			return ""+this.createTime;
		else if(col==8)
			return ""+this.removeTime;
		else if(col==9)
			return ""+this.vipStartTime;
		else if(col==10)
			return ""+this.vipEndTime;
		else 
			return "";
	}
	
}
