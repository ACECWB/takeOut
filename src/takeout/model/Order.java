package takeout.model;

import java.util.Date;

public class Order {
	public static final String[] tableTitles = {"订单编号","商家编号","骑手编号","商品编号","数量","价格","优惠券编号","原总价","现总价","下单时间","送达要求","送达时间","状态"};
	public static final String[] UtableTitles = {"订单编号","商家编号","商家名称","骑手编号","优惠券编号","原总价","现总价","下单时间","送达要求","送达时间","状态","是否已评论"};
	public static final String[] ItableTitles = {"商品编号","商品名称","商品数量","商品总价格"};
	public static final String[] BtableTitles = {"订单编号","用户编号","地址信息","优惠券编号","骑手编号","原总价","最终价格","下单时间","要求送达时间","状态","接收时间","是否已评论"};
	public static final String[] BstableTitles = {"订单编号","用户编号","地址信息","优惠券编号","骑手编号","原总价","最终价格","下单时间","要求送达时间","状态"};
	public static final String[] DstableTitles = {"订单编号","商家名称","商家地址","用户联系人","用户地址","用户手机号码","下单时间","要求送达时间","状态"};

	
	private String orderid;
	private String couponid;
	private String deliverid;
	private float originamount;
	private float finalamount;
	private Date reqtime;
	private String status;
	private String comid;
	private int counts;
	private float price;
	private Date ordertime;
	private Date receivetime;
	
	private String userid;
	private String businessid;
	private String businessname;
	private String locaid;
	private int ownorder;
	private String comname;
	
	private int isreviewed;
	private String userloca;
	private String busloca;
	private String connname;
	private String phone;
	
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone = phone; 
	}
	
	public String getBusLoca() {
		return this.busloca;
	}
	public void setBusLoca(String busloca) {
		this.busloca = busloca;
	}
	public String getConnname() {
		return this.connname;
	}
	public void setConnname(String name) {
		this.connname = name;
	}
	public String getDCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.orderid;
		else if(col==1)
			return ""+this.businessname;
		else if(col==2)
			return ""+this.busloca;
		else if(col==3)
			return ""+this.connname;
		else if(col==4)
			return ""+this.userloca;
		else if(col==5)
			return ""+this.phone;
		else if(col==6)
			return ""+this.ordertime;
		else if(col==7)
			return ""+this.reqtime;
		else if(col==8)
			return ""+this.status;
		else 
			return "";
	}
	public String getBCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.orderid;
		else if(col==1)
			return ""+this.userid;
		else if(col==2)
			return ""+this.userloca;
		else if(col==3)
			return ""+this.couponid;
		else if(col==4)
			return ""+this.deliverid;
		else if(col==5)
			return ""+this.originamount;
		else if(col==6)
			return ""+this.finalamount;
		else if(col==7)
			return ""+this.ordertime;
		else if(col==8)
			return ""+this.reqtime;
		else if(col==9)
			return ""+this.status;
		else if(col==10)
			return ""+this.receivetime;
		else if(col==11)
			return ""+this.isreviewed;
		else 
			return "";
	}
	public String getUserLoca() {
		return this.userloca;
	}
	public void setLoca(String userloca) {
		this.userloca = userloca;
	}
	public String getBusinessName() {
		return businessname;
	}
	public void setBusinessName(String businessname) {
		this.businessname = businessname;
	}
	
	public int getIsReviewed() {
		return isreviewed;
	}
	public void setIsReviewed(int isreviewed) {
		this.isreviewed = isreviewed;
	}
	
	public void setComName(String comname) {
		this.comname = comname;
	}
	public String getComName() {
		return comname;
	}
	
	
	
	public String getICell(int col){
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
	
	public String getUCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.orderid;
		else if(col==1)
			return ""+this.businessid;
		else if(col==2)
			return ""+this.businessname;
		else if(col==3)
			return ""+this.deliverid;
		else if(col==4)
			return ""+this.couponid;
		else if(col==5)
			return ""+this.originamount;
		else if(col==6)
			return ""+this.finalamount;
		else if(col==7)
			return ""+this.ordertime;
		else if(col==8)
			return ""+this.reqtime;
		else if(col==9)
			return ""+this.receivetime;
		else if(col==10)
			return ""+this.status;
		else if(col==11)
			return ""+this.isreviewed;
		else 
			return "";
	}
	
	public int getOwnOrder() {
		return ownorder;
	}
	
	public void setOwnOrder(int ownorder) {
		this.ownorder = ownorder;
	}
	
	public String getLocaid() {
		return locaid;
	}

	public void setLocaid(String locaid) {
		this.locaid = locaid;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}
	
	public void setReceiveTime(Date time) {
		this.receivetime = time;
	}
	public Date getReceiveTime() {
		return this.receivetime;
	}
	
	public String getCell(int col){
//		if(col==0) return "1";
//		else if(col==1) return "示例计划";
//		else if(col==2) return "2";
//		else if(col==3) return "1";
//		else return "";
		if(col==0)
			return ""+this.orderid;
		else if(col==1)
			return ""+this.businessid;
		else if(col==2)
			return ""+this.deliverid;
		else if(col==3)
			return ""+this.comid;
		else if(col==4)
			return ""+this.counts;
		else if(col==5)
			return ""+this.price;
		else if(col==6)
			return ""+this.couponid;
		else if(col==7)
			return ""+this.originamount;
		else if(col==8)
			return ""+this.finalamount;
		else if(col==9)
			return ""+this.ordertime;
		else if(col==10)
			return ""+this.reqtime;
		else if(col==11)
			return ""+this.receivetime;
		else if(col==12)
			return ""+this.status;
		else 
			return "";
	}

	public Date getOrderTime() {
		return ordertime;
	}
	public void setOrderTime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getCouponid() {
		return couponid;
	}

	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}

	public String getDeliverid() {
		return deliverid;
	}

	public void setDeliverid(String deliverid) {
		this.deliverid = deliverid;
	}

	public float getOriginamount() {
		return originamount;
	}

	public void setOriginamount(float originamount) {
		this.originamount = originamount;
	}

	public float getFinalamount() {
		return finalamount;
	}

	public void setFinalamount(float finalamount) {
		this.finalamount = finalamount;
	}

	public Date getReqtime() {
		return reqtime;
	}

	public void setReqtime(Date reqtime) {
		this.reqtime = reqtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComid() {
		return comid;
	}

	public void setComid(String comid) {
		this.comid = comid;
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
