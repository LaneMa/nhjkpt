package nhjkpt.configmanage.service.schooldaysum;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 学校用电总量分天统计表
 * @author qf
 * @date 2013-08-16 10:05:32
 * @version V1.0   
 *
 */
public interface SchooldaysumServiceI extends CommonService{

	/**
	 * 将学校分时统计表中的数据加到分日统计表中
	 */
	void exportSchoolhoursum(Calendar calendar);

	/**
	 * 获取分月电量总和
	 * @param funcid
	 * @param calendar
	 * @return
	 */
	Double querySchooldaysum(String funcid, Calendar calendar);
	/**
	 * 按照功能id进行分组求和
	 * @param calendar
	 * @return
	 */
	List<Map<String,Object>> querySchooldaysum(Calendar calendar);
}
