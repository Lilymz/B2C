package zjmx.ssm.o2o.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 	因为不可能只有一个页面有验证码功能，而又这些验证码功能的页面必定会检验验证码，	
 * 	故该工具类提供检验验证码 
 * @author jj
 *
 */
public class CodeCheckUtil {
	
	/**首先先获取显示页面上的实际验证码,在获取前端传来的验证码
	 * @return
	 */
	public static Boolean checkCode(HttpServletRequest request) {
		String codeExpected=(String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String codeActual=HttpServletRequestUtil.getString(request, "code");
		if (!codeExpected.equals(codeActual)||codeActual==null) {
			return false;
		}
		return true;
	}
}
