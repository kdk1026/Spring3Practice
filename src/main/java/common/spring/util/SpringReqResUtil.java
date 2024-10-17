package common.spring.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SpringReqResUtil {

	private SpringReqResUtil() {
		super();
	}

	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest();
	}
	
}
