package takeout.model;

public class CommodityCategory {
	public static final String[] tableTitles = {"���ͱ��","������"};
	private String categoryId;
	private String categoryName;
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
	
	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "ʾ���ƻ�";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.categoryId;
		else if(col==1)
			return ""+this.categoryName;
		else 
			return "";
	}
}
