package nhjkpt.configmanage.service.lighting;

import nhjkpt.configmanage.entity.metershowhistory.MeterShowHistoryEntity;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 教室照明
 * @author qf
 * @date 2014-11-17 22:28:39
 * @version V1.0   
 *
 */
public interface LightingServiceI extends CommonService{

	/**
	 * 拆分udp读取过来的数据
	 * @param str
	 * @return
	 */
	void splitShowStr(String str);
}
