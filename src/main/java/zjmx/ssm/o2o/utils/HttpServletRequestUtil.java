package zjmx.ssm.o2o.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
	public static int getInt(HttpServletRequest request,String key) {
		try {
			return Integer.decode((String) request.getSession().getAttribute(key));
		} catch (Exception e) {
			return -1;
		}
	}
	public static long getLong(HttpServletRequest request,String key) {
		try {
			return Long.valueOf(request.getParameter(key).trim());
		} catch (Exception e) {
			return -1;
		}
	}
	public static double getDouble(HttpServletRequest request,String key) {
		try {
			return Double.valueOf((String) request.getSession().getAttribute(key));
		} catch (Exception e) {
			return -1;
		}
	}
	public static Boolean getBoolean(HttpServletRequest request,String key) {
		try {
			return Boolean.valueOf((String) request.getSession().getAttribute(key));
		} catch (Exception e) {
			return false;
		}
	}
	public static String getString(HttpServletRequest request,String key) {
		try {
			String res=(String) request.getSession().getAttribute(key);
			if (res!=null) {
				res=res.trim();
			}
			if ("".equals(res)) {
				res=null;
				
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
