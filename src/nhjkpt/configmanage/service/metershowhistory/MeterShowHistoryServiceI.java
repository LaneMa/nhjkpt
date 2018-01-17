package nhjkpt.configmanage.service.metershowhistory;

import java.util.List;
import org.framework.core.common.service.CommonService;
import nhjkpt.configmanage.entity.metershowhistory.MeterShowHistoryEntity;
import nhjkpt.configmanage.entity.meterfuncshowhistory.MeterFuncShowHistoryEntity;

public interface MeterShowHistoryServiceI extends CommonService{

	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(MeterShowHistoryEntity meterShowHistory,
	        List<MeterFuncShowHistoryEntity> meterFuncShowHistoryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(MeterShowHistoryEntity meterShowHistory,
	        List<MeterFuncShowHistoryEntity> meterFuncShowHistoryList);
	public void delMain (MeterShowHistoryEntity meterShowHistory);
}
