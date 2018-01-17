package nhjkpt.configmanage.service.departmentitemize;

import java.io.OutputStream;
import java.util.List;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 系分类用能计算用表
 * @author qf
 * @date 2014-11-18 23:40:21
 * @version V1.0   
 *
 */
public interface DepartmentItemizeServiceI extends CommonService{

	List<Highchart> queryHighchart(String departmentid,String itemizeid, String type,String startDate, String endDate);
	
	List<Highchart> queryHighchartItemize(String departmentid,String itemizeid, String type,String startDate, String endDate);
	
	List<Highchart> queryHighchartDepartment(String departmentid,String itemizeid, String type,String startDate, String endDate);
	
	List<Highchart> queryHighchartDate(String departmentid,String itemizeid, String type,String[] startDate, String[] endDate);

	List<Highchart> queryHighchartpie(String departmentid, String itemizeid,String type, String startDate, String endDate);
	/**
	 * 按照分类为组进行饼状图显示
	 * @param departmentid
	 * @param itemizeid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Highchart> queryHighchartItemizePie(String departmentid,String itemizeid, String type, String startDate, String endDate);
	/**
	 * 导出数据到excel
	 * @param departmentid
	 * @param itemizeid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param out
	 */
	void exportExcel(String departmentid, String itemizeid, String type,String startDate, String endDate, OutputStream out);
}
