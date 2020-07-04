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
   price                float not null,
   discount_price       float not null,
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
   loca_Id              varchar(20),
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
   loca_Id              varchar(20),
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
