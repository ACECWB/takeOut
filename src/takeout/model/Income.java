package takeout.model;

import java.util.Date;

public class Income {
	public static final String[] tableTitles = {"订单编号","订单要求时间","实际送达时间","客户评价时间","客户评价","额外收入"};
	
	private String deliverid;
	private String orderid;
	private float bonus;
	private Date reviewtime;//评价时间
	private String review;
	private Date reqtime;
	private Date receivetime;
	
	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.orderid;
		else if(col==1)
			return ""+this.reqtime;
		else if(col==2)
			return ""+this.receivetime;
		else if(col==3)
			return ""+this.reviewtime;
		else if(col==4)
			return ""+this.review;
		else if(col==5)
			return ""+this.bonus;
		else 
			return "";
	}

	public String getDeliverid() {
		return deliverid;
	}

	public void setDeliverid(String deliverid) {
		this.deliverid = deliverid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public float getBonus() {
		return bonus;
	}

	public void setBonus(float bonus) {
		this.bonus = bonus;
	}

	public Date getReviewtime() {
		return reviewtime;
	}

	public void setReviewtime(Date reviewtime) {
		this.reviewtime = reviewtime;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Date getReqtime() {
		return reqtime;
	}

	public void setReqtime(Date reqtime) {
		this.reqtime = reqtime;
	}

	public Date getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}
	
	
	
}
