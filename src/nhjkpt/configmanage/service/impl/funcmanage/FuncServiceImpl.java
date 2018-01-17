package nhjkpt.configmanage.service.impl.funcmanage;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.service.funcmanage.FuncServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("funcService")
@Transactional
public class FuncServiceImpl extends CommonServiceImpl implements FuncServiceI {

	@Override
	public FuncEntity queryIdByFuncid(Integer funcid) {
		List<FuncEntity> funcs=this.findByProperty( FuncEntity.class ,"funcid",funcid);
		if(funcs!=null){
			if(funcs.size()!=0){
				return funcs.get(0);
			}else{
				FuncEntity func=new FuncEntity();
				func.setFuncid(funcid);
				func.setFuncname("功能"+funcid);
				this.save(func);
				return null;
			}
		}else{
			FuncEntity func=new FuncEntity();
			func.setFuncid(funcid);
			func.setFuncname("功能"+funcid);
			this.save(func);
			return null;
		}
	}
	
}