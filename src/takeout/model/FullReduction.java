package takeout.model;

import java.util.Date;

public class FullReduction {
	public static final String[] tableTitles = {"满减编号","商家编号","满减金额","优惠金额","是否可与优惠券叠加","活动结束时间"};
	private String reductId;
	private float requireAmount;
	private float discountAmount;
	private String withCoupon;
	private Date endTime;
	private String businessid;
	
	public String getBusinessId() {
		return businessid;
	}


	public void setBusinessId(String businessid) {
		this.businessid = businessid;
	}
	public String getReductId() {
		return reductId;
	}


	public void setReductId(String reductId) {
		this.reductId = reductId;
	}


	public float getRequireAmount() {
		return requireAmount;
	}


	public void setRequireAmount(float requireAmount) {
		this.requireAmount = requireAmount;
	}


	public float getDiscountAmount() {
		return discountAmount;
	}


	public void setDiscountAmount(float discountAmount) {
		this.discountAmount = discountAmount;
	}


	public String getWithCoupon() {
		return withCoupon;
	}


	public void setWithCoupon(String withCoupon) {
		this.withCoupon = withCoupon;
	}

	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.reductId;
		else if(col == 1)
			return ""+this.businessid;
		else if(col==2)
			return ""+this.requireAmount;
		else if(col==3)
			return ""+this.discountAmount;
		else if(col==4)
			return ""+this.withCoupon;
		else if(col==5)
			return ""+this.endTime;
		else 
			return "";
	}

}
