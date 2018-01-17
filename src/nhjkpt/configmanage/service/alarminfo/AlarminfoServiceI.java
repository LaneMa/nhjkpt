package nhjkpt.configmanage.service.alarminfo;

import org.framework.core.common.service.CommonService;

public interface AlarminfoServiceI extends CommonService{
	//判断此表具是否已存有断线记录
		public boolean existDxAlarm (String  meterid) ;
}
