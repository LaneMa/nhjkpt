package nhjkpt.configmanage.service.funcmanage;

import nhjkpt.configmanage.entity.funcmanage.FuncEntity;

import org.framework.core.common.service.CommonService;

public interface FuncServiceI extends CommonService{

	FuncEntity queryIdByFuncid(Integer funcid);
}
