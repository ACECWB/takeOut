package takeout.model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Coupon {
	public static final String[] BtableTitles = {"优惠券编号","优惠金额","需要订单数","活动开始时间","活动结束时间","有效天数","删除时间"};
	public static final String[] CtableTitles = {"商家编号","商家名称","优惠券编号","有效截止日期"};
	public static final String[] CtableTitles1 = {"商家编号","商家名称","优惠券编号","有效截止日期","序号"};
	public static final String[] UtableTitles = {"商家编号","商家名称","优惠券编号","有效截止日期"};
	public static final String[] OtableTitles = {"商家编号","商家名称","优惠券编号","活动截止日期","要求单数","已集单数","优惠金额","有效天数"};

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private String couponId;
	private String businessId;
	private String businessName;
	private float discountMoney;
	private int needOrders;
	private Date startTime;
	private Date endTime;
	private int effectDays;
	private Date removetime;
	
	private int ownorder;
	private String userid;
	private Date ineffectDate;
	private int alreadycounts;
	
	
	public int getAlreadyCounts() {
		return alreadycounts;
	}
	public void setAlreadyCounts(int alreadycounts) {
		this.alreadycounts = alreadycounts;
	}
	
	public String getOCell(int col){
		if(col==0)
			return ""+this.businessId;
		else if(col==1)
			return ""+this.businessName;
		else if(col==2)
			return ""+this.couponId;
		else if(col==3)
			return ""+this.endTime;
		else if(col==4)
			return ""+this.needOrders;
		else if(col==5)
			return ""+this.alreadycounts;
		else if(col==6)
			return ""+this.discountMoney;
		else if(col==7)
			return ""+this.effectDays;
		else 
			return "";
	}
	
	public String getUCell(int col){
		if(col==0)
			return ""+this.businessId;
		else if(col==1)
			return ""+this.businessName;
		else if(col==2)
			return ""+this.couponId;
		else if(col==3)
			return ""+this.ineffectDate;
		else 
			return "";
	}
	
	public void setOwnOrder(int ownorder) {
		this.ownorder = ownorder;
	}
	public int getOwnOrder() {
		return this.ownorder;
	}
	
	public void setUserId(String userid) {
		this.userid = userid;
	}
	public String getUserId() {
		return userid;
	}
	public void setRemoveTime(Date removetime) {
		this.removetime = removetime;
	}
	public Date getRemoveTime() {
		return removetime;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessName() {
		return businessName;
	}
	
	public String getCCell(int col){
		if(col==0)
			return ""+this.businessId;
		else if(col==1)
			return ""+this.businessName;
		else if(col==2)
			return ""+this.couponId;
		else if(col==3)
			return ""+this.ineffectDate;
		else if(col==4)
			return ""+this.ownorder;
		else 
			return "";
	}
	
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

	public Date getIneffectDate() {
		
		return ineffectDate;
	}

	public void setIneffectDate(Date ineffectDate) {
		this.ineffectDate = ineffectDate;
	}

	public static String[] getCtabletitles() {
		return CtableTitles;
	}

	public String getBCell(int col){
		if(col==0)
			return ""+this.couponId;
		else if(col==1)
			return ""+this.discountMoney;
		else if(col==2)
			return ""+this.needOrders;
		else if(col==3)
			return ""+this.startTime;
		else if(col==4)
			return ""+this.endTime;
		else if(col==5)
			return ""+this.effectDays;
		else if(col==6)
			return ""+this.removetime;
		else 
			return "";
	}
	
}
