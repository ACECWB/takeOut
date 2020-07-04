package takeout.model;

public class Business {
	public static final String[] tableTitles = {"商家编号","商家名称","星级","平均消费","总销量"};
	private String businessId;
	private String businessName;
	private int stars;
	private float avg_consume;
	private int sales_volume;
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	public float getAvg_consume() {
		return avg_consume;
	}
	public void setAvg_consume(float avg_consume) {
		this.avg_consume = avg_consume;
	}
	public int getSales_volume() {
		return sales_volume;
	}
	public void setSales_volume(int sales_volume) {
		this.sales_volume = sales_volume;
	}
	
	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.businessId;
		else if(col==1)
			return ""+this.businessName;
		else if(col==2)
			return ""+this.stars;
		else if(col==3)
			return ""+this.avg_consume;
		else if(col==4)
			return ""+this.sales_volume;
		else 
			return "";
	}

}
