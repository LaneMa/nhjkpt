package nhjkpt.configmanage.service.impl.originadata;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.service.originadata.OriginadataServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("originadataService")
@Transactional
public class OriginadataServiceImpl extends CommonServiceImpl implements OriginadataServiceI {
	
}