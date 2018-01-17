package nhjkpt.configmanage.service.buildingunit;

import java.io.OutputStream;
import java.util.List;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 建筑单位用能统计计算表
 * @author qf
 * @date 2013-08-02 01:18:03
 * @version V1.0   
 *
 */
public interface BuildingUnitServiceI extends CommonService{
	/**
	 * 建筑物单位用能统计曲线显示数据
	 * @param buildingid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Highchart> queryHighchart(String unitid,String buildingid, String type,String startDate, String endDate);
	/**
	 * 学校主页面中建筑物单位用能分类统计曲线数据
	 * @param buildingid
	 * @param itemizeid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Highchart> queryHighchartItemize(String unitid,String buildingid, String itemizeid,String type, String startDate, String endDate);
	/**
	 * 导出excel
	 * @param buildingid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param out
	 */
	void exportExcel(String buildingid, String type, String startDate,String endDate, OutputStream out);

}
