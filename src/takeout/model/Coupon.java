package takeout.model;

import java.util.Date;

public class Coupon {
	public static final String[] tableTitles = {"优惠券编号","商家编号","优惠金额","需要订单数","活动开始时间","活动结束时间","有效天数"};
	
	private String couponId;
	private String businessId;
	private float discountMoney;
	private int needOrders;
	private Date startTime;
	private Date endTime;
	private int effectDays;
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public float getDiscountMoney() {
		return discountMoney;
	}
	public void setDiscountMoney(float discountMoney) {
		this.discountMoney = discountMoney;
	}
	public int getNeedOrders() {
		return needOrders;
	}
	public void setNeedOrders(int needOrders) {
		this.needOrders = needOrders;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getEffectDays() {
		return effectDays;
	}
	public void setEffectDays(int effectDays) {
		this.effectDays = effectDays;
	}
	
	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.couponId;
		else if(col==1)
			return ""+this.businessId;
		else if(col==2)
			return ""+this.discountMoney;
		else if(col==3)
			return ""+this.needOrders;
		else if(col==4)
			return ""+this.startTime;
		else if(col==5)
			return ""+this.endTime;
		else if(col==6)
			return ""+this.effectDays;
		else 
			return "";
	}
	
}
