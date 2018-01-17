package nhjkpt.configmanage.service.impl.buildingshowfunc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.service.buildingshowfunc.BuildingShowFuncServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("buildingShowFuncService")
@Transactional
public class BuildingShowFuncServiceImpl extends CommonServiceImpl implements BuildingShowFuncServiceI {
	
}