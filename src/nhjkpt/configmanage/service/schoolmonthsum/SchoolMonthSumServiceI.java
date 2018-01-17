package nhjkpt.configmanage.service.schoolmonthsum;

import java.util.Calendar;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 学校用电总量分月统计表
 * @author qf
 * @date 2013-08-02 00:49:01
 * @version V1.0   
 *
 */
public interface SchoolMonthSumServiceI extends CommonService{

	void exportSchooldaySum(Calendar calendar);
}
