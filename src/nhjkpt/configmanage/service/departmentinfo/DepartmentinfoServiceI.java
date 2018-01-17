package nhjkpt.configmanage.service.departmentinfo;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 系基本信息
 * @author qf
 * @date 2014-11-18 23:36:22
 * @version V1.0   
 *
 */
public interface DepartmentinfoServiceI extends CommonService{
	public Double getUseNhThisYear(String departmentid,String funcid);
}
