drop table if exists ladderprice;
create table ladderprice(
	`id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键id',
	`name` varchar(32) default NULL COMMENT '阶梯价格名称',
	`price_type` tinyint default 1 COMMENT '阶梯价格类型 1水 2电',
	`price_type_name` varchar(32) default null COMMENT '阶梯价格类型名称',
	`price` decimal(10,2) default 0 COMMENT '价格（单价）',
	 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='阶梯价格表';