package nhjkpt.configmanage.service.buildingitemize;

import java.io.OutputStream;
import java.util.List;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 建筑分类用能计算用表
 * @author qf
 * @date 2013-08-02 01:08:19
 * @version V1.0   
 *
 */
public interface BuildingItemizeServiceI extends CommonService{

	List<Highchart> queryHighchart(String buildingid,String itemizeid, String type,String startDate, String endDate);
	
	List<Highchart> queryHighchartItemize(String buildingid,String itemizeid, String type,String startDate, String endDate);
	
	List<Highchart> queryHighchartBuilding(String buildingid,String itemizeid, String type,String startDate, String endDate);
	
	List<Highchart> queryHighchartBuildingsum(String buildingid, String type,String startDate, String endDate);
	
	List<Highchart> queryHighchartDate(String buildingid,String itemizeid, String type,String[] startDate, String[] endDate);

	List<Highchart> queryHighchartpie(String buildingid, String itemizeid,String type, String startDate, String endDate);
	/**
	 * 按照分类为组进行饼状图显示
	 * @param buildingid
	 * @param itemizeid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Highchart> queryHighchartItemizePie(String buildingid,String itemizeid, String type, String startDate, String endDate);
	/**
	 * 导出数据到excel
	 * @param buildingid
	 * @param itemizeid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param out
	 */
	void exportExcel(String buildingid, String itemizeid, String type,String startDate, String endDate, OutputStream out);
}
