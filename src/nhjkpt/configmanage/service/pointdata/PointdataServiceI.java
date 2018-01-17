package nhjkpt.configmanage.service.pointdata;

import java.util.Calendar;

import nhjkpt.configmanage.entity.hourdata.HourdataEntity;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 历史数据
 * @author qf
 * @date 2013-08-11 00:21:48
 * @version V1.0   
 *
 */
public interface PointdataServiceI extends CommonService{

	/**
	 * 历史数据的整点数据调取计算
	 */
	void exportIntegralPointData(Calendar calendar);
	/**
	 * 按月将输入插入到数据库中
	 */
	void addIntegralPointData(Calendar calendar);
	/**
	 * 
	 * @param calendar
	 * @return
	 */
	HourdataEntity queryHourData(String meterid,Calendar calendar);
	void importPointData(String startDate, String endDate);
	/**
	 * 检查是否断线
	 */
	void breakLineCheck();
}
