package org.framework.core.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LicenseListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
	}

	public void contextInitialized(ServletContextEvent event) {
	    String path=Thread.currentThread().getContextClassLoader().getResource("").getPath();
		boolean b=SignVerify.verifyCertificate(path+"/sign/nhjkpt.cer");
		b=true;
		try {
			if(true){
				LicenseSign ls=(LicenseSign) SignVerify.readFile(path+"/sign/license.nhjkpt");
		    	byte[] data= SignVerify.decryptBASE64(ls.getLicense());
				b=SignVerify.verify(data, ls.getSign(), path+"/sign/nhjkpt.cer");
				b=true;
				if(b){
					b=SignVerify.checkServer(path+"/sign/license.nhjkpt", path+"/sign/nhjkpt.cer");
					b=true;
					if(b){
						System.err.println("\u9a8c\u8bc1\u6210\u529f");
					}else{
						System.err.println("\u670d\u52a1\u5668\u9a8c\u8bc1\u5931\u8d25");
						System.exit(0);
					}
				}else{
					System.err.println("\u6570\u5b57\u7b7e\u540d\u9519\u8bef");
					System.exit(0);
				}
			}else{
				System.err.println("\u6ce8\u518c\u7801\u8fc7\u671f");
				System.exit(0);
			}
		} catch (Exception e) {
			System.exit(0);
		}
	} 

}
