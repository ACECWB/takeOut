package takeout.model;

public class FullReduction {
	public static final String[] tableTitles = {"满减编号","满减金额","优惠金额","是否可与优惠券叠加"};
	private String reductId;
	private float requireAmount;
	private float discountAmount;
	private int withCoupon;
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
	public int getWithCoupon() {
		return withCoupon;
	}
	public void setWithCoupon(int withCoupon) {
		this.withCoupon = withCoupon;
	}
	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.reductId;
		else if(col==1)
			return ""+this.requireAmount;
		else if(col==2)
			return ""+this.discountAmount;
		else if(col==3)
			return ""+this.withCoupon;
		else 
			return "";
	}

}
