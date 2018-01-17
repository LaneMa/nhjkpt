package nhjkpt.configmanage.service.roomlight;

import nhjkpt.configmanage.entity.roomlight.RoomlightEntity;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 教室照明管理
 * @author qf
 * @date 2015-01-03 19:47:13
 * @version V1.0   
 *
 */
public interface RoomlightServiceI extends CommonService{
	public void ReceiveUdp(String msg);
	public void SendUdp(RoomlightEntity roomlight);
}
