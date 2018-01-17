package nhjkpt.configmanage.service.schoolinfo;

import org.framework.core.common.service.CommonService;

public interface SchoolinfoServiceI extends CommonService{
	//获取学校今年用电量  查询年表当前月前几个月总用电量+本月小时表用电量
		public Double getUseNhThisYear(String funcid);
}
