package takeout.model;

import java.util.Date;

public class ComCate {
	public static final String[] tableTitles = {"类型编号","分类名","创建时间","删除时间"};

	private String categoryName;
	private String categoryId;
	private Date createtime;
	private Date endtime;
	

	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}


	public Date getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public Date getEndtime() {
		return endtime;
	}


	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}


	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.categoryId;
		else if(col==1)
			return ""+this.categoryName;
		else if(col==2)
			return ""+this.createtime;
		else if(col==3)
			return ""+this.endtime;
		else 
			return "";
	}
	
}
