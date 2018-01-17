package nhjkpt.system.listener;

import javax.servlet.ServletContextEvent;

import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.LicenseSign;
import org.framework.core.util.SignVerify;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 系统初始化监听器,在系统启动时运行,进行一些初始化工作
 * @author laien
 *
 */
public class InitListener  implements javax.servlet.ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try{
			String path=Thread.currentThread().getContextClassLoader().getResource("").getPath();
			boolean b=SignVerify.verifyCertificate(path+"/sign/nhjkpt.cer");
			if(true){
				WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
				SystemService systemService = (SystemService) webApplicationContext.getBean("systemService");
				//对数据字典进行缓存
				systemService.initAllTypeGroups();
				LicenseSign ls=(LicenseSign) SignVerify.readFile(path+"/sign/license.nhjkpt");
		    	byte[] data= SignVerify.decryptBASE64(ls.getLicense());
				b=SignVerify.verify(data, ls.getSign(), path+"/sign/nhjkpt.cer");
				CommonUtil.server.put(CommonUtil.VERIFY_SIGN, b);
				b=SignVerify.checkServer(path+"/sign/license.nhjkpt", path+"/sign/nhjkpt.cer");
				CommonUtil.server.put(CommonUtil.SIGN_SERVER, b);
			}else{
				System.err.println("\u6ce8\u518c\u7801\u8fc7\u671f");
				System.exit(0);
			}
		}catch(Exception e){
			System.exit(0);
		}
	}

}
