package nhjkpt.configmanage.service.impl.lighting;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.lighting.LightingEntity;
import nhjkpt.configmanage.entity.metershowhistory.MeterShowHistoryEntity;
import nhjkpt.configmanage.service.lighting.LightingServiceI;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("lightingService")
@Transactional
public class LightingServiceImpl extends CommonServiceImpl implements LightingServiceI {

	@Override
	public void splitShowStr(String str) {
		if(!CommonUtil.isNull(str)){
			String[] code=str.split(" ");
			LightingEntity lighting=new LightingEntity();
			lighting.setBuildingid(code[0]);
			lighting.setFloorid(code[1]);
			lighting.setRoomid(code[2]);
			lighting.setControlType(code[3]);
			lighting.setControlData(code[4]);
			lighting.setLightFalls(code[5]);
			lighting.setUserNum(Integer.parseInt(code[6]));
			lighting.setLightNum(Integer.parseInt(code[7]));
			lighting.setLightType(code[8]);
			lighting.setRetain1(code[9]);
			lighting.setRetain2(code[10]);
		}
	}
	
}