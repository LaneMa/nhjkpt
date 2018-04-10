package nhjkpt.configmanage.service.buildinginfo;

import java.util.List;

import org.framework.core.common.service.CommonService;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;

public interface BuildinginfoServiceI extends CommonService{

	String queryIdByBuildingid(String buildingid);
	public Double getUseNhThisYear(String buildingid,String funcid);
	List<BuildinginfoEntity> queryListByName(String buildName);
}
