create table ladderprice(
	`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模块id',
	`name` varchar(32) default NULL COMMENT '阶梯价格名称',
	`price_type` tinyint default 1 COMMENT '阶梯价格类型 1水 2电',
	`price` decimal(10,2) default 0 COMMENT '价格（单价）'
	 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='阶梯价格表';