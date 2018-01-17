package nhjkpt.configmanage.service.impl.metershowhistory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import nhjkpt.configmanage.service.metershowhistory.MeterShowHistoryServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

import nhjkpt.configmanage.entity.metershowhistory.MeterShowHistoryEntity;
import nhjkpt.configmanage.entity.meterfuncshowhistory.MeterFuncShowHistoryEntity;
@Service("meterShowHistoryService")
@Transactional
public class MeterShowHistoryServiceImpl extends CommonServiceImpl implements MeterShowHistoryServiceI {

	@Override
	public void addMain(MeterShowHistoryEntity meterShowHistory,
	        List<MeterFuncShowHistoryEntity> meterFuncShowHistoryList){
			//保存主信息
			this.save(meterShowHistory);
		
			/**保存-瞬时基本信息*/
			for(MeterFuncShowHistoryEntity meterFuncShowHistory:meterFuncShowHistoryList){
				//外键设置
				meterFuncShowHistory.setMeterhistoryid(meterShowHistory.getId());
				this.save(meterFuncShowHistory);
			}
	}

	@Override
	public void updateMain(MeterShowHistoryEntity meterShowHistory,
	        List<MeterFuncShowHistoryEntity> meterFuncShowHistoryList) {
		//保存订单主信息
		this.saveOrUpdate(meterShowHistory);
		
		
		//===================================================================================
		//获取参数
		Object id0 = meterShowHistory.getId();
		//===================================================================================
		//删除-瞬时基本信息
	    String hql0 = "from MeterFuncShowHistoryEntity where 1 = 1 AND meterhistoryid = ? ";
	    List<MeterFuncShowHistoryEntity> meterFuncShowHistoryOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(meterFuncShowHistoryOldList);
		//保存-瞬时基本信息
		for(MeterFuncShowHistoryEntity meterFuncShowHistory:meterFuncShowHistoryList){
			//外键设置
			meterFuncShowHistory.setMeterhistoryid(meterShowHistory.getId());
			this.save(meterFuncShowHistory);
		}
		
	}

	@Override
	public void delMain(MeterShowHistoryEntity meterShowHistory) {
		//删除主表信息
		this.delete(meterShowHistory);
		
		//===================================================================================
		//获取参数
		Object id0 = meterShowHistory.getId();
		//===================================================================================
		//删除-瞬时基本信息
	    String hql0 = "from MeterFuncShowHistoryEntity where 1 = 1 AND meterhistoryid = ? ";
	    List<MeterFuncShowHistoryEntity> meterFuncShowHistoryOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(meterFuncShowHistoryOldList);
	}
	
}