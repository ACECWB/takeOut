package takeout.model;

public class Cart {
	public static final String[] tableTitles = {"商品编号","商品名称","数量","价格"};
	private String userid;
	private String comid;
	private String comname;
	private String businessid;
	private String businessname;
	private int counts;
	private float price;
	
	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.comid;
		else if(col==1)
			return ""+this.comname;
		else if(col==2)
			return ""+this.counts;
		else if(col==3)
			return ""+this.price;
		else 
			return "";
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getComid() {
		return comid;
	}

	public void setComid(String comid) {
		this.comid = comid;
	}

	public String getComname() {
		return comname;
	}

	public void setComname(String comname) {
		this.comname = comname;
	}

	public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}

	public String getBusinessname() {
		return businessname;
	}

	public void setBusinessname(String businessname) {
		this.businessname = businessname;
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
