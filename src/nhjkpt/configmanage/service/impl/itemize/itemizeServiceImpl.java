package nhjkpt.configmanage.service.impl.itemize;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.service.itemize.itemizeServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("itemizeService")
@Transactional
public class itemizeServiceImpl extends CommonServiceImpl implements itemizeServiceI {
	
}