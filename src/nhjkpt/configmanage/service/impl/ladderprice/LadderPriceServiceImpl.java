package nhjkpt.configmanage.service.impl.ladderprice;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.service.ladderprice.LadderPriceServiceI;

@Service("ladderPriceService")
@Transactional
public class LadderPriceServiceImpl extends CommonServiceImpl implements LadderPriceServiceI {
	
}