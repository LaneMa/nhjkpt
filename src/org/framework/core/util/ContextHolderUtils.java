package org.framework.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
* @ClassName: ContextHolderUtils 
* @Description: TODO(上下文工具类) 
* @author qf 
* @date 2012-12-15 下午11:27:39 
*
 */
public class ContextHolderUtils {
	/**
	 * SpringMvc下获取request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		if(RequestContextHolder.getRequestAttributes()!=null){
			return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}else{
			return null;
		}
	}
	/**
	 * SpringMvc下获取session
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		if(getRequest()!=null){
			return getRequest().getSession();
		}else{
			return null;
		}
	}

}
