package nhjkpt.configmanage.service.schoolmonthitemize;

import java.util.Calendar;
import java.util.List;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 学校用电分类分月统计表
 * @author qf
 * @date 2013-08-02 01:03:41
 * @version V1.0   
 *
 */
public interface SchoolMonthItemizeServiceI extends CommonService{

	List<Highchart> queryHighchart(String itemizeid,String type,String startDate,String endDate);

	void exportSchooldayItemize(Calendar calendar);
}
