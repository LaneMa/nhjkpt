package nhjkpt.system.service;

import org.framework.core.common.service.CommonService;

import nhjkpt.system.pojo.base.TSUser;

public interface UserService extends CommonService{

	public TSUser checkUserExits(TSUser user);
	public String getUserRole(TSUser user);
	public void pwdInit(TSUser user, String newPwd);
}
