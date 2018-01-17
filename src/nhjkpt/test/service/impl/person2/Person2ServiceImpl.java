package nhjkpt.test.service.impl.person2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.test.service.person2.Person2ServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("person2Service")
@Transactional
public class Person2ServiceImpl extends CommonServiceImpl implements Person2ServiceI {
	
}