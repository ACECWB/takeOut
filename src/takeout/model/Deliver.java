package takeout.model;

import java.util.Date;

public class Deliver {
	
	public static Deliver currentLoginDeliver=null;
	
	public static final String[] tableTitles = {"骑手编号","骑手姓名","就职时间","辞职时间","身份","状态"};
	private String deliverId;
	private String deliverName;
	private String employTime;
	private String identity;
	private String quitTime;
	private String status;
	private String pwd;
	
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getQuitTime() {
		return quitTime;
	}
	public void setQuitTime(String quitTime) {
		this.quitTime = quitTime;
	}
	
	public String getDeliverId() {
		return deliverId;
	}
	public void setDeliverId(String deliverId) {
		this.deliverId = deliverId;
	}
	public String getDeliverName() {
		return deliverName;
	}
	public void setDeliverName(String deliverName) {
		this.deliverName = deliverName;
	}
	public String getEmployTime() {
		return employTime;
	}
	public void setEmployTime(String employTime) {
		this.employTime = employTime;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.deliverId;
		else if(col==1)
			return ""+this.deliverName;
		else if(col==2) 
			return this.employTime;
		else if(col==3)
			return ""+this.quitTime;
		else if(col==4)
			return ""+this.identity;
		else if(col==5)
			return ""+this.status;
		else 
			return "";
	}

}
