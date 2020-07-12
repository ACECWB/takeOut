package takeout.model;

import java.util.Date;

public class Review {
	public static final String[] tableTitles = {"用户编号","评论时间","星级","评论内容"};
	public static final String[] UtableTitles = {"订单编号","商家编号","商家名称","星级","评论内容","骑手编号","骑手名称","骑手评价","评价日期"};
	public static final String[] BtableTitles = {"订单编号","星级","评论内容","骑手编号","骑手名称","骑手评价","评价日期"};

	
	private String businessid;
	private String comid;
	private String userid;
	private String comname;
	private String content;
	private float stars;
	private Date reviewtime;
	private String orderid;
	
	private String businessname;
	private String deliverid;
	private String delivername;
	private String deliverreview;
	
	public String getBCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.orderid;
		else if(col==1)
			return ""+this.stars;
		else if(col==2)
			return ""+this.content;
		else if(col==3)
			return ""+this.deliverid;
		else if(col==4)
			return ""+this.delivername;
		else if(col==5)
			return ""+this.deliverreview;
		else if(col==6)
			return ""+this.reviewtime;
		else 
			return "";
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

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getBusinessname() {
		return businessname;
	}

	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}

	public String getDeliverid() {
		return deliverid;
	}

	public void setDeliverid(String deliverid) {
		this.deliverid = deliverid;
	}

	public String getDelivername() {
		return delivername;
	}

	public void setDelivername(String delivername) {
		this.delivername = delivername;
	}

	public String getDeliverreview() {
		return deliverreview;
	}

	public void setDeliverreview(String deliverreview) {
		this.deliverreview = deliverreview;
	}

	public static String[] getUtabletitles() {
		return UtableTitles;
	}

	public String getUCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.orderid;
		else if(col==1)
			return ""+this.businessid;
		else if(col==2)
			return ""+this.businessname;
		else if(col==3)
			return ""+this.stars;
		else if(col==4)
			return ""+this.content;
		else if(col==5)
			return ""+this.deliverid;
		else if(col==6)
			return ""+this.delivername;
		else if(col==7)
			return ""+this.deliverreview;
		else if(col==8)
			return ""+this.reviewtime;
		else 
			return "";
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
