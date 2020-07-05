package takeout.model;

import java.util.Date;

public class ComTitle {
	public static final String[] tableTitles = {"��Ʒ���","��Ʒ����","���ͱ��","��Ʒ�ϼ�ʱ��","��Ʒ�¼�ʱ��"};

	private String comId;
	private String comName;
	private String categoryId;
	private Date createtime;
	private Date endtime;
	
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
//		else if(col==1) return "ʾ���ƻ�";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.comId;
		else if(col==1)
			return ""+this.comName;
		else if(col==2)
			return ""+this.categoryId;
		else if(col==3)
			return ""+this.createtime;
		else if(col==4)
			return ""+this.endtime;
		else 
			return "";
	}
	
}
