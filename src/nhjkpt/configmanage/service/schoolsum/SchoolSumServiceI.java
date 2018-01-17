package nhjkpt.configmanage.service.schoolsum;

import java.util.Date;
import java.util.List;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 学校水电气总量计算表
 * @author qf
 * @date 2013-07-31 19:22:49
 * @version V1.0   
 *
 */
public interface SchoolSumServiceI extends CommonService{

	List<Highchart> queryHighchart(String funcid,String type,String startDate,String endDate,String pictype);
	
	List<Highchart> queryEnergySortCCBar();
}
