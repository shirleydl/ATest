package com.shirley.aTestActuator.method;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Description: TODO(自定义函数)
 * @author 371683941@qq.com
 * @date 2019年8月5日 上午11:27:54
 */
public class CustomFunction {

	/**
	 * param0=rule param1=time
	 */
	public String _getTime(Map<String, String> object) {
		try {
			if (object.size() == 2) {
				SimpleDateFormat format = new SimpleDateFormat(object.get("param0"));
				return format.format(new Date(Long.valueOf(object.get("param1"))));
			}
			if (object.get("param0").equals("now")) {
				return new Date().getTime() / 1000 + "";
			}
			return new Date().getTime() + "";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * param0=value param1=start_rule param2=end_rule
	 */
	public String _getSubstring(Map<String, String> object) {
		if (object.size() == 3) {
			String value = object.get("param0");
			try {
				String[] startIndex = object.get("param1").split(":");
				String[] endIndex = object.get("param2").split(":");
				if (startIndex.length == 2 && endIndex.length == 2)
					return value.substring(value.indexOf(startIndex[0]) + Integer.parseInt(startIndex[1]),
							value.indexOf(endIndex[0]) + Integer.parseInt(endIndex[1]));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return object.get("param0");
	}

	/**
	 * param0=value param1=format
	 */
	public String _getMd5(Map<String, String> object) {
		Md5 md5 = new Md5();
		if (object.get("param1").equals("Encryption32"))
			return md5.encryption32(object.get("param0"));
		return object.get("param0");
	}

	/**
	 * param0=value
	 */
	public String _toUpperCase(Map<String, String> object) {
		return object.get("param0").toUpperCase();
	}
	
	/**
	 * param0=value
	 */
	public String _toEncodeURI(Map<String, String> object) {
		try {
			return URLEncoder.encode(object.get("param0"), "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	/**
	 * param0=value
	 * param1=old char
	 */
	public String _toReplaceEnter(Map<String, String> object) {
		try {
			return object.get("param0").replace(object.get("param1"), "\r\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
