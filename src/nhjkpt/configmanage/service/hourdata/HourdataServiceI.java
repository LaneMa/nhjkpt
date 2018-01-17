package nhjkpt.configmanage.service.hourdata;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import nhjkpt.configmanage.entity.hourdata.HourdataEntity;
import nhjkpt.configmanage.entity.schoolsum.SchoolSumEntity;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 历史数据的小时用电量表
 * @author qf
 * @date 2013-08-13 15:25:25
 * @version V1.0   
 *
 */
public interface HourdataServiceI extends CommonService{
	/**
	 * 历史数据按照小时进行统计
	 */
	void exportPointDataHour(Calendar calendar);
	/**
	 * 获取当前时间点的数据
	 * @param calendar
	 * @return
	 */
	List<HourdataEntity> queryHourdataByHour(String funcid,String meterid,Calendar calendar);
	
	List<Map<String,Object>> queryHourdata(Calendar calendar);
	List<Map<String, Object>> queryHourdataByMeterid(Calendar calendar);
	List<HourdataEntity> queryHourdataByHourByMeterid(String funcid,String meterid,Calendar calendar);
}
