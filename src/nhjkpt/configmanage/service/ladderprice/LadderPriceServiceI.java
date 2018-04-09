package nhjkpt.configmanage.service.ladderprice;

import java.util.List;

import org.framework.core.common.service.CommonService;

import nhjkpt.configmanage.entity.ladderprice.LadderPriceEntity;

public interface LadderPriceServiceI extends CommonService{
	
	List<LadderPriceEntity> getEntityByType(Integer priceType); 
}
