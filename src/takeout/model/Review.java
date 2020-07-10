package takeout.model;

import java.util.Date;

public class Review {
	public static final String[] tableTitles = {"用户编号","评论时间","星级","评论内容"};
	private String businessid;
	private String comid;
	private String userid;
	private String comname;
	private String content;
	private float stars;
	private Date reviewtime;
	private String orderid;
	
	public String getOrderId() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}

	public String getComid() {
		return comid;
	}

	public void setComid(String comid) {
		this.comid = comid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getComname() {
		return comname;
	}

	public void setComname(String comname) {
		this.comname = comname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public float getStars() {
		return stars;
	}

	public void setStars(float stars) {
		this.stars = stars;
	}

	public Date getReviewtime() {
		return reviewtime;
	}

	public void setReviewtime(Date reviewtime) {
		this.reviewtime = reviewtime;
	}

	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.userid;
		else if(col==1)
			return ""+this.reviewtime;
		else if(col==2)
			return ""+this.stars;
		else if(col==3)
			return ""+this.content;
		else 
			return "";
	}

}
