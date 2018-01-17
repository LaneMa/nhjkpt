package nhjkpt.configmanage.service.impl.systemlink;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.service.systemlink.SystemLinkServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("systemLinkService")
@Transactional
public class SystemLinkServiceImpl extends CommonServiceImpl implements SystemLinkServiceI {
	
}