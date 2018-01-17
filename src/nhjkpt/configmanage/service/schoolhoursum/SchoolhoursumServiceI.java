package nhjkpt.configmanage.service.schoolhoursum;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 学校用电总量分时统计表
 * @author qf
 * @date 2013-08-15 00:37:58
 * @version V1.0   
 *
 */
public interface SchoolhoursumServiceI extends CommonService{

	/**
	 * 计算学校每小时总和
	 */
	void exportHourdata(Calendar calendar);
	/**
	 * 按天查询出所有数据
	 * @param calendar
	 * @return
	 */
	Double querySchoolhoursum(String funcid,Calendar calendar);
	/**
	 * 分组查询功能
	 * @param calendar
	 * @return
	 */
	List<Map<String,Object>> querySchoolhoursum(Calendar calendar);
	/**
	 * 获取右上本日用水柱状图
	 * @return
	 */
	public List<Highchart> queryEnergySortNEBar();
	/**
	 * 获取左上日用电柱状图
	 * @return
	 */
	public List<Highchart> queryEnergySortNCBar();
}
