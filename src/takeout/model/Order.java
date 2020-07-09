package takeout.model;

import java.util.Date;

public class Order {
	public static final String[] tableTitles = {"订单编号","商家编号","骑手编号","商品编号","数量","价格","优惠券编号","原总价","现总价","下单时间","送达要求","送达时间","状态"};
	
	private String orderid;
	private String couponid;
	private String deliverid;
	private float originamount;
	private float finalamount;
	private Date reqtime;
	private String status;
	private String comid;
	private int counts;
	private float price;
	private Date ordertime;
	private Date receivetime;
	
	private String userid;
	private String businessid;
	private String locaid;
	private int ownorder;
	
	public int getOwnOrder() {
		return ownorder;
	}
	
	public void setOwnOrder(int ownorder) {
		this.ownorder = ownorder;
	}
	
	public String getLocaid() {
		return locaid;
	}

	public void setLocaid(String locaid) {
		this.locaid = locaid;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}
	
	public void setReceiveTime(Date time) {
		this.receivetime = time;
	}
	public Date getReceiveTime() {
		return this.receivetime;
	}
	
	public String getCell(int col){
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
			return ""+this.deliverid;
		else if(col==3)
			return ""+this.comid;
		else if(col==4)
			return ""+this.counts;
		else if(col==5)
			return ""+this.price;
		else if(col==6)
			return ""+this.couponid;
		else if(col==7)
			return ""+this.originamount;
		else if(col==8)
			return ""+this.finalamount;
		else if(col==9)
			return ""+this.ordertime;
		else if(col==10)
			return ""+this.reqtime;
		else if(col==11)
			return ""+this.receivetime;
		else if(col==12)
			return ""+this.status;
		else 
			return "";
	}

	public Date getOrderTime() {
		return ordertime;
	}
	public void setOrderTime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getCouponid() {
		return couponid;
	}

	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}

	public String getDeliverid() {
		return deliverid;
	}

	public void setDeliverid(String deliverid) {
		this.deliverid = deliverid;
	}

	public float getOriginamount() {
		return originamount;
	}

	public void setOriginamount(float originamount) {
		this.originamount = originamount;
	}

	public float getFinalamount() {
		return finalamount;
	}

	public void setFinalamount(float finalamount) {
		this.finalamount = finalamount;
	}

	public Date getReqtime() {
		return reqtime;
	}

	public void setReqtime(Date reqtime) {
		this.reqtime = reqtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComid() {
		return comid;
	}

	public void setComid(String comid) {
		this.comid = comid;
	}

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
}
