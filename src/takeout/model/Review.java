package takeout.model;

import java.util.Date;

public class Review {
	public static final String[] tableTitles = {"用户编号","商品编号","商品名称","评论时间","星级","评论内容"};
	private String businessid;
	private String comid;
	private String userid;
	private String comname;
	private String content;
	private int stars;
	private Date reviewtime;
	
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

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
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
			return ""+this.comid;
		else if(col==2)
			return ""+this.comname;
		else if(col==3)
			return ""+this.reviewtime;
		else if(col==4)
			return ""+this.stars;
		else if(col==5)
			return ""+this.content;
		else 
			return "";
	}

}
