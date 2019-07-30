/*
SQLyog Ultimate v8.32 
MySQL - 5.1.32-community-log : Database - mydb1
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `book` */

CREATE TABLE `book` (
  `bid` char(32) NOT NULL,
  `bname` varchar(100) NOT NULL,
  `price` decimal(5,1) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `image` varchar(200) DEFAULT NULL,
  `cid` char(32) DEFAULT NULL,
  PRIMARY KEY (`bid`),
  KEY `cid` (`cid`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `category` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `book` */

insert  into `book`(`bid`,`bname`,`price`,`author`,`image`,`cid`) values ('1','Java编程思想（第4版）','75.6','qdmmy6','book_img/9317290-1_l.jpg','1');
insert  into `book`(`bid`,`bname`,`price`,`author`,`image`,`cid`) values ('2','Java核心技术卷1','68.5','qdmmy6','book_img/20285763-1_l.jpg','1');
insert  into `book`(`bid`,`bname`,`price`,`author`,`image`,`cid`) values ('3','Java就业培训教程','39.9','张孝祥','book_img/8758723-1_l.jpg','1');
insert  into `book`(`bid`,`bname`,`price`,`author`,`image`,`cid`) values ('4','Head First java','47.5','（美）塞若','book_img/9265169-1_l.jpg','1');
insert  into `book`(`bid`,`bname`,`price`,`author`,`image`,`cid`) values ('5','JavaWeb开发详解','83.3','孙鑫','book_img/22788412-1_l.jpg','2');
insert  into `book`(`bid`,`bname`,`price`,`author`,`image`,`cid`) values ('6','Struts2深入详解','63.2','孙鑫','book_img/20385925-1_l.jpg','2');
insert  into `book`(`bid`,`bname`,`price`,`author`,`image`,`cid`) values ('7','精通Hibernate','30.0','孙卫琴','book_img/8991366-1_l.jpg','2');
insert  into `book`(`bid`,`bname`,`price`,`author`,`image`,`cid`) values ('8','精通Spring2.x','63.2','陈华雄','book_img/20029394-1_l.jpg','2');
insert  into `book`(`bid`,`bname`,`price`,`author`,`image`,`cid`) values ('9','Javascript权威指南','93.6','（美）弗兰纳根','book_img/22722790-1_l.jpg','3');

/*Table structure for table `category` */

CREATE TABLE `category` (
  `cid` char(32) NOT NULL,
  `cname` varchar(100) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`cid`,`cname`) values ('1','JavaSE');
insert  into `category`(`cid`,`cname`) values ('2','JavaEE');
insert  into `category`(`cid`,`cname`) values ('3','Javascript');

/*Table structure for table `orderitem` */

CREATE TABLE `orderitem` (
  `iid` char(32) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `subtotal` decimal(10,0) DEFAULT NULL,
  `oid` char(32) DEFAULT NULL,
  `bid` char(32) DEFAULT NULL,
  PRIMARY KEY (`iid`),
  KEY `FK_orderitem` (`oid`),
  KEY `FK_orderitem1` (`bid`),
  CONSTRAINT `FK_orderitem` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`),
  CONSTRAINT `FK_orderitem1` FOREIGN KEY (`bid`) REFERENCES `book` (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orderitem` */

insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('0e9c433b6423486cb9bf323155bb30e4',1,'76','a97ecfe54f3042dca88731975951c967','1');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('12b43d22f5e44ccc867add1436ac36b8',5,'150','01b579ef220741088038b3f02dac9320','7');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('12b4fc4d23ff46e688ff64b8fe8de04b',2,'126','5fdafc583c2149fda4a0fe919d3c4673','8');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('1735c39c59c74df09e3a4c55f8e05a90',3,'206','5fdafc583c2149fda4a0fe919d3c4673','2');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('22389bb4b3344af0943b5784d542dd53',3,'190','35c7fb07aadf4a5da0912af663db951e','8');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('26ec67670a304a01badd28c9e55e6928',1,'69','b7658ecdf821410d99102043936763b8','2');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('2f2f83870726417ebb3f8421a289f1af',4,'160','0b687b56ef3a43aabc1ba6b2da3372ed','3');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('3b7b059a8bf744eea7f977ba5b9ca00a',2,'80','a3ee7264d669432e819d69c043dcaa39','3');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('3efb94b511bb4045bb300391f5c90142',2,'95','8371119c9cd0439c8e6a4144a6d0c944','4');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('47c49f8399a24a82aab27fa8c1d9211f',2,'95','01b579ef220741088038b3f02dac9320','4');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('499825009c154868a1e7b85ef1e120e0',11,'832','9c844d1cd6c04fc0ac6fe010c84fd73d','1');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('70810f7bcd3d4cff9a0f7fbc653ec78a',2,'60','35c7fb07aadf4a5da0912af663db951e','7');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('7365cbf881a6485a877159f6e6b3faf3',5,'417','a3ee7264d669432e819d69c043dcaa39','5');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('750b2c1eeaf0451081d1000fc0b229d7',1,'76','b41f1dd16240459ea0af02be92b574a3','1');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('75fdb2c7c28e49dfb8ccf310d3281883',3,'206','0b687b56ef3a43aabc1ba6b2da3372ed','2');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('7690d9c809024879bc1c6aeaf30f01c5',3,'206','67f89f6773ac4e5a890316060fc41182','2');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('77a32769da09408b809a5a484d4993bd',1,'76','e53bfd65160240aea30569df1ebf7730','1');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('7ab6dd63529243ab93e2d75fac8161de',1,'76','2fc63159606f46e392aae11c7ffa5d97','1');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('7fd9247b5946438288521a7974bf201a',1,'76','42edafb7f098454da63639206d141642','1');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('8a815ebc046247d99a578afda60c9902',1,'83','4b265423a0744da49b85e1e82f52de22','5');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('a9e30fc115844932be418bb4390eb8ae',3,'206','8371119c9cd0439c8e6a4144a6d0c944','2');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('de44497be3ec46cfb69a2f9f626bc5dc',2,'151','67f89f6773ac4e5a890316060fc41182','1');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('e59b2178b7634d62b3ed859b448e3d8e',1,'76','ff28a711b20d48f892a145e6efb6efdc','1');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('e6d8df5c77e5403aac16e6c078ca164a',3,'90','8620d74eed234738bb6540e7fd810cd8','7');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('fbb0420cbcab47cabe6dd5a89dc82725',1,'40','167c135512c64d149e33f8da53a96ef3','3');
insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('fedc0770d18d40e7ade2708acab8a193',2,'187','a3ee7264d669432e819d69c043dcaa39','9');

/*Table structure for table `orders` */

CREATE TABLE `orders` (
  `oid` char(32) NOT NULL,
  `ordertime` datetime DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `state` smallint(1) DEFAULT NULL,
  `uid` char(32) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `FK_orders` (`uid`),
  CONSTRAINT `FK_orders` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('01b579ef220741088038b3f02dac9320','2013-05-30 00:33:50','245',2,'a50c92f282274e4fab7ad5b5e754043f','北京市海淀区金燕龙大厦2楼216室无敌收');
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('0b687b56ef3a43aabc1ba6b2da3372ed','2013-05-31 00:05:04','365',1,'a50c92f282274e4fab7ad5b5e754043f','北京市海淀区金燕龙大厦2楼216室无敌收');
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('167c135512c64d149e33f8da53a96ef3','2013-05-31 15:53:50','40',1,'a50c92f282274e4fab7ad5b5e754043f',NULL);
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('2fc63159606f46e392aae11c7ffa5d97','2013-05-31 15:20:09','76',1,'a50c92f282274e4fab7ad5b5e754043f','北京市海淀区金燕龙大厦2楼216室无敌收');
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('35c7fb07aadf4a5da0912af663db951e','2013-05-30 00:21:28','280',1,'a50c92f282274e4fab7ad5b5e754043f',NULL);
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('42edafb7f098454da63639206d141642','2013-05-30 23:40:29','76',1,'a50c92f282274e4fab7ad5b5e754043f',NULL);
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('4b265423a0744da49b85e1e82f52de22','2013-05-31 00:21:22','83',1,'a50c92f282274e4fab7ad5b5e754043f',NULL);
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('5fdafc583c2149fda4a0fe919d3c4673','2013-05-31 00:06:03','332',1,'a50c92f282274e4fab7ad5b5e754043f',NULL);
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('67f89f6773ac4e5a890316060fc41182','2013-05-29 23:40:37','357',1,'a50c92f282274e4fab7ad5b5e754043f','北京市海淀区金燕龙大厦2楼216室无敌收');
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('8371119c9cd0439c8e6a4144a6d0c944','2013-06-03 18:03:23','301',2,'a50c92f282274e4fab7ad5b5e754043f','北京市海淀区金燕龙大厦2楼216室无敌收');
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('8620d74eed234738bb6540e7fd810cd8','2013-05-30 00:39:10','90',1,'a50c92f282274e4fab7ad5b5e754043f',NULL);
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('9c844d1cd6c04fc0ac6fe010c84fd73d','2013-05-30 22:21:04','832',1,'a50c92f282274e4fab7ad5b5e754043f',NULL);
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('a3ee7264d669432e819d69c043dcaa39','2013-05-29 23:44:10','684',1,'a50c92f282274e4fab7ad5b5e754043f',NULL);
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('a97ecfe54f3042dca88731975951c967','2013-05-31 00:18:25','76',1,'a50c92f282274e4fab7ad5b5e754043f',NULL);
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('b41f1dd16240459ea0af02be92b574a3','2013-05-31 00:12:25','76',1,'a50c92f282274e4fab7ad5b5e754043f',NULL);
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('b7658ecdf821410d99102043936763b8','2013-05-30 22:21:30','69',1,'a50c92f282274e4fab7ad5b5e754043f',NULL);
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('e53bfd65160240aea30569df1ebf7730','2013-05-31 00:18:46','76',1,'a50c92f282274e4fab7ad5b5e754043f',NULL);
insert  into `orders`(`oid`,`ordertime`,`price`,`state`,`uid`,`address`) values ('ff28a711b20d48f892a145e6efb6efdc','2013-05-31 00:21:10','76',1,'a50c92f282274e4fab7ad5b5e754043f',NULL);

/*Table structure for table `user` */

CREATE TABLE `user` (
  `uid` char(32) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`uid`,`username`,`password`) values ('038668dea0e14edabe9ba3d93f326a0a','hhh','hhh');
insert  into `user`(`uid`,`username`,`password`) values ('1','zhangSan','123');
insert  into `user`(`uid`,`username`,`password`) values ('191cc9cfa9c54892b3540d39d0f60114','eee','eee');
insert  into `user`(`uid`,`username`,`password`) values ('2','liSi','123');
insert  into `user`(`uid`,`username`,`password`) values ('3','wangWu','123');
insert  into `user`(`uid`,`username`,`password`) values ('8eda5655666d4af795281463d6eb0498','fff','fff');
insert  into `user`(`uid`,`username`,`password`) values ('a50c92f282274e4fab7ad5b5e754043f','aaa','aaa');
insert  into `user`(`uid`,`username`,`password`) values ('c97da3a32dbd41feb725b5c2230480f3','ccc','ccc');
insert  into `user`(`uid`,`username`,`password`) values ('d49c6fb6a0b743bebc613e77e45fc74b','ddd','ddd');
insert  into `user`(`uid`,`username`,`password`) values ('e73af51517fc4d13999e2fbc7e883e12','xxx','xxx');
insert  into `user`(`uid`,`username`,`password`) values ('eda83213a18345a08271f31d8b901f0f','bbb','bbb');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
