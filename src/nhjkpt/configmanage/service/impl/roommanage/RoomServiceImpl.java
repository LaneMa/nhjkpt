package nhjkpt.configmanage.service.impl.roommanage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.service.roommanage.RoomServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("roomService")
@Transactional
public class RoomServiceImpl extends CommonServiceImpl implements RoomServiceI {
	
}