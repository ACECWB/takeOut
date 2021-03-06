package takeout.model;

public class Commodity extends ComTitle{
	public static final String[] tableTitles = {"商品编号","商品名称","类型编号","类型名","商家编号","数量","单价","会员价"};
	public static final String[] tableTitles_1 = {"商品编号","商品名称"};
	public static final String[] tableTitles_2 = {"类型编号","类型名"};
	public static final String[] tableTitles_3 = {"商品编号","商品名称","类型编号","类型名","商家编号","商家名称","数量","单价","会员价"};
	public static final String[] BtableTitles = {"商品编号","商品名称","类型编号","类型名","数量","单价","会员价"};
	public static final String[] RtableTitles = {"商家编号","商家名称","商品编号","商品名称","销量","库存","单价","会员价"};


	private String comId;
	private String comName;
	private String categoryId;
	private String categoryName;
	private String businessId;
	private int counts;
	private int sales;
	private String businessname;
	private float eachPrice;
	private float vipprice;
	
	public String getRCell(int col){

		if(col==0)
			return ""+this.businessId;
		else if(col==1)
			return ""+this.businessname;
		else if(col==2)
			return ""+this.comId;
		else if(col==3)
			return ""+this.comName;
		else if(col==4)
			return ""+this.sales;
		else if(col==5)
			return ""+this.counts;
		else if(col==6)
			return ""+this.eachPrice;
		else if(col==7)
			return ""+this.vipprice;
		else 
			return "";
	}
	
	public void setSales(int sales) {
		this.sales = sales;
	}
	public int getSales() {
		return this.sales;
	}
	public String getBCell(int col){

		if(col==0)
			return ""+this.comId;
		else if(col==1)
			return ""+this.comName;
		else if(col==2)
			return ""+this.categoryId;
		else if(col==3)
			return ""+this.categoryName;
		else if(col==4)
			return ""+this.counts;
		else if(col==5)
			return ""+this.eachPrice;
		else if(col==6)
			return ""+this.vipprice;
		else 
			return "";
	}
	public String getBusinessname() {
		return this.businessname;
	}
	public void setBusinessname(String name) {
		this.businessname = name;
	}
	
	public float getVipprice() {
		return this.vipprice;
	}
	public void setVipprice(float vipprice) {
		this.vipprice = vipprice;
	}
	
	public String getComId() {
		return comId;
	}
	public void setComId(String comId) {
		this.comId = comId;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public float getEachPrice() {
		return eachPrice;
	}
	public void setEachPrice(float eachPrice) {
		this.eachPrice = eachPrice;
	}
	public String getCell(int col){

		if(col==0)
			return ""+this.comId;
		else if(col==1)
			return ""+this.comName;
		else if(col==2)
			return ""+this.categoryId;
		else if(col==3)
			return ""+this.categoryName;
		else if(col==4)
			return ""+this.businessId;
		else if(col==5)
			return ""+this.counts;
		else if(col==6)
			return ""+this.eachPrice;
		else if(col==7)
			return ""+this.vipprice;
		else 
			return "";
	}
	public String getSCell(int col){

		if(col==0)
			return ""+this.comId;
		else if(col==1)
			return ""+this.comName;
		else if(col==2)
			return ""+this.categoryId;
		else if(col==3)
			return ""+this.categoryName;
		else if(col==4)
			return ""+this.businessId;
		else if(col==5)
			return ""+this.businessname;
		else if(col==6)
			return ""+this.counts;
		else if(col==7)
			return ""+this.eachPrice;
		else if(col==8)
			return ""+this.vipprice;
		else 
			return "";
	}
	
}
