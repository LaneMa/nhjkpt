package nhjkpt.system.service;

import java.util.List;

import nhjkpt.system.pojo.base.TSAttachment;

import org.framework.core.common.service.CommonService;


public interface DeclareService extends CommonService{
	
	public List<TSAttachment> getAttachmentsByCode(String businessKey,String description);
	
}
