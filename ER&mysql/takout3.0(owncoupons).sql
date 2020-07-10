/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/3 18:30:54                            */
/*==============================================================*/


drop table if exists admin;

drop table if exists business;

drop table if exists collectOrders;

drop table if exists commodity;

drop table if exists commodityCategory;

drop table if exists coupon;

drop table if exists deliver;

drop table if exists deliver_income;

drop table if exists fullReduction;

drop table if exists location;

drop table if exists orderInfo;

drop table if exists orders;

drop table if exists ownedcoupons;

drop table if exists review;

drop table if exists user;

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   admin_Id             varchar(20),
   admin_name           varchar(20) not null,
   admin_pwd            varchar(20) not null,
   primary key (admin_Id)
);

/*==============================================================*/
/* Table: business                                              */
/*==============================================================*/
create table business
(
   business_Id          varchar(20),
   business_name        varchar(20) not null,
   stars                int not null,
   avg_consume          float not null,
   sales_volume         int not null,
   primary key (business_Id)
);

/*==============================================================*/
/* Table: collectOrders                                         */
/*==============================================================*/
create table collectOrders
(
   user_Id              varchar(20) not null,
   business_Id         varchar(20) not null,
   alreadycounts        varchar(20) not null,
   primary key (user_Id, business_Id)
);

/*==============================================================*/
/* Table: commodity                                             */
/*==============================================================*/
create table commodity
(
   com_Id               varchar(20),
   category_Id          varchar(20) not null,
   com_name             varchar(20) not null,
   primary key (com_Id)
);

/*==============================================================*/
/* Table: commodityCategory                                     */
/*==============================================================*/
create table commodityCategory
(
   category_Id          varchar(20),
   category_name        varchar(20) not null,
   category_amount      int not null,
   primary key (category_Id)
);

/*==============================================================*/
/* Table: coupon                                                */
/*==============================================================*/
create table coupon
(
   coupon_Id           varchar(20),
   business_Id          varchar(20),
   discount_money       float not null,
   need_orders          int not null,
   start_time           date not null,
   end_time             date not null,
   effect_days         int not null,
   primary key (coupon_Id)
);

/*==============================================================*/
/* Table: deliver                                               */
/*==============================================================*/
create table deliver
(
   deliver_Id           varchar(20),
   deliver_name         varchar(20) not null,
   employ_time          date not null,
   identity             varchar(20) not null,
   primary key (deliver_Id)
);

/*==============================================================*/
/* Table: deliver_income                                        */
/*==============================================================*/
create table deliver_income
(
   deliver_Id           varchar(20),
   order_Id             varchar(20),
   time                 date not null,
   review               varchar(30) not null,
   each_income          float not null
);

/*==============================================================*/
/* Table: fullReduction                                         */
/*==============================================================*/
create table fullReduction
(
   reduct_Id            varchar(20),
   require_amount       float not null,
   discount_amount      float not null,
   with_coupon          bool not null,
   primary key (reduct_Id)
);

/*==============================================================*/
/* Table: location                                              */
/*==============================================================*/
create table location
(
   loca_Id             int not null auto_increment,
   user_Id              varchar(20),
   loca                 varchar(20) not null,
   phone_number         varchar(20) not null,
   conn_user            varchar(10) not null,
   primary key (loca_Id)
);

/*==============================================================*/
/* Table: orderInfo                                             */
/*==============================================================*/
create table orderInfo
(
   order_Id             varchar(20) not null,
   com_Id               varchar(20) not null,
   count                int not null,
   price                float not null,
   primary key (order_Id, com_Id)
);

/*==============================================================*/
/* Table: orders                                                */
/*==============================================================*/
create table orders
(
   order_Id             varchar(20) not null ,
   user_Id              varchar(20),
   loca_Id              int not null ,
   coupon_Id            varchar(20),
   deliver_Id          varchar(20),
   business_Id          varchar(20),
   origin_amount        float not null,
   final_amount         float not null,
   order_time           date not null,
   req_time             date not null,
   status               varchar(10) not null,
   primary key (order_Id)
);

/*==============================================================*/
/* Table: ownedcoupons                                          */
/*==============================================================*/
create table ownedcoupons
(
   user_Id              varchar(20) not null,
   business_Id          varchar(20) not null,
   coupon_Id            varchar(20) not null,
   ineffect_time        datetime not null,
   primary key (user_Id, business_Id, coupon_Id)
);

/*==============================================================*/
/* Table: review                                                */
/*==============================================================*/
create table review
(
   user_Id              varchar(20),
   com_Id               varchar(20) not null,
   business_Id          varchar(20) not null,
   content              varchar(30),
   review_date          date not null,
   stars                int not null,
   pictures             longblob,
   primary key (user_Id, com_Id, business_Id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   user_Id              varchar(20),
   user_name            varchar(20) not null,
   sex                  varchar(3),
   pwd                  varchar(20) not null,
   phone                varchar(20) not null,
   email                varchar(20),
   city                 varchar(10) not null,
   isvip                bool not null,
   vip_start_time       date,
   vip_end_time         date,
   primary key (user_Id)
);

alter table collectOrders add constraint FK_collectOrders foreign key (user_Id)
      references user (user_Id) on delete restrict on update restrict;

alter table collectOrders add constraint FK_collectOrders2 foreign key (business_Id)
      references business (business_Id) on delete restrict on update restrict;

alter table commodity add constraint FK_belong_to foreign key (category_Id)
      references commodityCategory (category_Id) on delete restrict on update restrict;

alter table coupon add constraint FK_own1 foreign key (business_Id)
      references business (business_Id) on delete restrict on update restrict;

alter table deliver_income add constraint FK_Reference_24 foreign key (order_Id)
      references orders (order_Id) on delete restrict on update restrict;

alter table deliver_income add constraint FK_Relationship_13 foreign key (deliver_Id)
      references deliver (deliver_Id) on delete restrict on update restrict;

alter table location add constraint FK_live foreign key (user_Id)
      references user (user_Id) on delete restrict on update restrict;

alter table orderInfo add constraint FK_orderInfo foreign key (order_Id)
      references orders (order_Id) on delete restrict on update restrict;

alter table orderInfo add constraint FK_orderInfo2 foreign key (com_Id)
      references commodity (com_Id) on delete restrict on update restrict;

alter table orders add constraint FK_Relationship_10 foreign key (deliver_Id)
      references deliver (deliver_Id) on delete restrict on update restrict;

alter table orders add constraint FK_businessId foreign key (business_Id)
      references business (business_Id) on delete restrict on update restrict;

alter table orders add constraint FK_couponId foreign key (coupon_Id)
      references coupon (coupon_Id) on delete restrict on update restrict;

alter table orders add constraint FK_locaId foreign key (loca_Id)
      references location (loca_Id) on delete restrict on update restrict;

alter table orders add constraint FK_userId foreign key (user_Id)
      references user (user_Id) on delete restrict on update restrict;

alter table ownedcoupons add constraint FK_ownedcoupons foreign key (user_Id)
      references user (user_Id) on delete restrict on update restrict;

alter table ownedcoupons add constraint FK_ownedcoupons2 foreign key (business_Id)
      references business (business_Id) on delete restrict on update restrict;

alter table ownedcoupons add constraint FK_ownedcoupons3 foreign key (coupon_Id)
      references coupon (coupon_Id) on delete restrict on update restrict;

alter table review add constraint FK_review foreign key (user_Id)
      references user (user_Id) on delete restrict on update restrict;

alter table review add constraint FK_review2 foreign key (com_Id)
      references commodity (com_Id) on delete restrict on update restrict;

alter table review add constraint FK_review3 foreign key (business_Id)
      references business (business_Id) on delete restrict on update restrict;

alter table user add createtime datetime not NULL;

alter table user add removetime datetime default null;



alter table deliver change employ_time employ_time datetime not null


create table com2bus(
com_Id varchar(20) not null,
business_Id varchar(20) not null,
counts int not null,
each_price float not null,
FOREIGN key (com_Id) REFERENCES commodity(com_Id),
FOREIGN key (business_Id) REFERENCES business(business_Id)
)

alter table deliver add quit_time datetime

alter table business add createtime datetime

alter table business add removetime datetime


alter table business change createtime createtime datetime not null
alter table commoditycategory drop category_amount



alter table commodity add createtime datetime not NULL
alter table commodity add removetime datetime 




alter table coupon change start_time start_time datetime not null

alter table coupon change end_time end_time datetime not null

alter table coupon add removetime datetime 


drop table ownedcoupons

CREATE TABLE `ownedcoupons` (
  `user_Id` varchar(20) NOT NULL,
  `business_Id` varchar(20) NOT NULL,
  `coupon_Id` varchar(20) NOT NULL,
  `ineffect_time` datetime NOT NULL
)
alter table ownedcoupons add ownorder int not null



alter table ownedcoupons add  PRIMARY KEY (ownorder)
alter table ownedcoupons change ownorder ownorder int auto_increment 

alter table fullreduction add starttime datetime not NULL
alter table fullreduction add removetime datetime 


alter table fullreduction change with_coupon with_coupon varchar(3) not null
alter table fullreduction drop starttime
alter table orders drop FOREIGN KEY FK_couponId

alter table orders change order_time order_time datetime not null
alter table orders change req_time req_time datetime not null

alter table deliver_income change time time datetime
alter table deliver_income change each_income each_bonus float not null
alter table orders add receive_time datetime 

create table cart(
user_Id varchar(30) not null,
com_Id varchar(30) not null,
com_name varchar(30) not null,
business_Id varchar(30) not null,
counts int not null,
price float not null
)

alter table cart add business_name varchar(30) not null
alter table cart drop com_name
alter table cart drop business_name

alter table fullreduction add business_Id varchar(30) not null

alter table deliver add `status` varchar(10) not null
alter table ownedcoupons add removetime datetime
alter table orders add couponorder int not null

drop table review

create table review(
	`user_Id` varchar(20) NOT NULL DEFAULT '',
  `business_Id` varchar(20) NOT NULL,
  `content` varchar(30) DEFAULT NULL,
  `review_date` datetime NOT NULL,
  `stars` int(11) NOT NULL,
  `pictures` longblob
)


alter table review drop user_Id
alter table review drop business_Id

alter table review add order_Id varchar(20) not null


create view userownedcollects as(
select user_Id, c.business_Id, b.business_name, c.coupon_Id, c.end_time, c.need_orders, co.alreadycounts,
c.discount_money, effect_days from coupon c, collectorders co, business b
where  co.business_Id = b.business_Id and co.business_Id = c.business_Id

)
alter table user change vip_start_time  vip_start_time  datetime
alter table user change vip_end_time  vip_end_time  datetime
