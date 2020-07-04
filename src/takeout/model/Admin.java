package takeout.model;


public class Admin {
	public static Admin currentLoginUser=null;
	private String userid;
	private String name;
	private String pwd;
	
	
	public String getUserid() {
		return userid;
	}
	public String getPwd() {
		return pwd;
	}
	public String getName() {
		return name;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public void setName(String name) {
		this.name = name;
	}
}
