package takeout.model;

public class IncomeStatistic {
	public static final String[] tableTitles = {"时间","总订单数","总额外收入","总扣除金额","总收入"};
	private String deliverid;
	private String year;
	private String month;
	private int counts;
	private float bonus;
	private float cut;
	private float totalincome;
	
	
	public String getDeliverid() {
		return deliverid;
	}


	public void setDeliverid(String deliverid) {
		this.deliverid = deliverid;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public int getCounts() {
		return counts;
	}


	public void setCounts(int counts) {
		this.counts = counts;
	}


	public float getBonus() {
		return bonus;
	}


	public void setBonus(float bonus) {
		this.bonus = bonus;
	}


	public float getCut() {
		return cut;
	}


	public void setCut(float cut) {
		this.cut = cut;
	}


	public float getTotalincome() {
		return totalincome;
	}


	public void setTotalincome(float totalincome) {
		this.totalincome = totalincome;
	}


	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.year + "-" + this.month;
		else if(col==1)
			return ""+this.counts;
		else if(col==2)
			return ""+this.bonus;
		else if(col==3)
			return ""+this.cut;
		else if(col==4)
			return ""+this.totalincome;
		else 
			return "";
	}
	
}
