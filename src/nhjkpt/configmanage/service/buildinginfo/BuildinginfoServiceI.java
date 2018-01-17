package nhjkpt.configmanage.service.buildinginfo;

import org.framework.core.common.service.CommonService;

public interface BuildinginfoServiceI extends CommonService{

	String queryIdByBuildingid(String buildingid);
	public Double getUseNhThisYear(String buildingid,String funcid);

}
