package nhjkpt.configmanage.service.impl.udprecord;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.service.udprecord.UdpRecordServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("udpRecordService")
@Transactional
public class UdpRecordServiceImpl extends CommonServiceImpl implements UdpRecordServiceI {
	
}