package com.shirley.aTest.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shirley.aTest.entity.ResponseContent;

/**
 * @Description: TODO(获取各类值方法)
 * @author 371683941@qq.com
 * @date 2019年7月19日 下午1:17:34
 */
public class GetValue {
	private Map<String, String> bindVariables;
	private Map<String, String> variables;
	private ResponseContent responseContent;

	/**
	 * 获取值初始化
	 */
	public GetValue(Map<String, String> bindVariables, Map<String, String> variables, ResponseContent responseContent) {
		this.bindVariables = bindVariables;
		this.variables = variables;
		this.responseContent = responseContent;
	}

	/**
	 * 获取各种类型的值
	 */
	public String getValue(String value) {
		String addValues[] = null;
		String valueStr = "";
		String splitStr = "&" + "+" + "&";
		// 判断是否有拼接值
		if (value.contains("${") && value.contains(splitStr)) {
			addValues = value.split("&\\+&");
		}
		if (null != addValues && addValues.length > 0) {
			for (String addValue : addValues) {
				valueStr += getKeyValue(addValue);
			}
		} else {
			valueStr = getKeyValue(value);
		}
		return valueStr;
	}

	public String getKeyValue(String str) {
		String value = "";
		String function[] = str.split("&_&");

		// 根据函数名获取对应值
		for (int i = 0; i < function.length; i++) {
			if (i == 0) {
				String type = null;
				if (function[i].contains("${") && function[i].contains("}")
						&& function[i].indexOf("}") > function[i].indexOf("${")) {
					type = function[i].substring(function[i].indexOf("${") + 2, function[i].lastIndexOf("}"));
				}
				if (null != type) {
					if (null != responseContent && "StatusCode".equals(type)) {
						value = responseContent.getResponseCode();
					} else if (null != responseContent && "Content:all".equals(type)) {
						value = responseContent.getContent();
					} else if (null != responseContent && !"Content:all".equals(type) && type.contains("Content:")) {
						value = getJsonValue(responseContent.getContent(), type.substring(type.indexOf(":") + 1));
					} else if (null != responseContent && "Header:all".equals(type)) {
						for (Header header : responseContent.getHeaders()) {
							value += header.getName() + header.getValue();
						}
					} else if (null != responseContent && !"Header:all".equals(type) && type.contains("Header:")) {
						value = getHeaderValue(responseContent.getHeaders(), type.substring(type.indexOf(":") + 1));
					} else if (type.contains("Custom_") && type.contains("(") && type.contains(")")
							&& type.indexOf(")") > type.indexOf("(")) {
						String method = type.substring(type.indexOf("_"), type.indexOf("("));
						Map<String, String> paramValue = new HashMap<String, String>();
						String[] paramValues = type.substring(type.indexOf("(") + 1, type.lastIndexOf(")")).split(",");
						for (int j = 0; j < paramValues.length; j++) {
							paramValue.put("param" + j, getVariableValue(paramValues[j]));
						}
						CustomFunction customFunction = new CustomFunction();
						try {
							value = (String) customFunction.getClass().getMethod(method, new Class[] { Map.class })
									.invoke(customFunction, new Object[] { paramValue });
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							value = "method not found!";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							value="method error!";
						}
					} else if (null != bindVariables && type.contains("Bind:")) {
						value = bindVariables.get(type.substring(type.indexOf(":") + 1));
					} else if (null != variables && type.contains("Variable:")) {
						value = variables.get(type.substring(type.indexOf(":") + 1));
					} else if (type.contains("_JS(") && type.contains(")")) {
						JavaScriptFunction javaScriptFunction = new JavaScriptFunction();
						value = javaScriptFunction.jsFunction(
								getVariableValue(type.substring(type.indexOf("_JS(") + 4, type.lastIndexOf(")"))));
					} else if (type.contains("_JSFile(") && type.contains(")")) {
						String[] jSFileStr = type.substring(type.indexOf("_JSFile(") + 8, type.lastIndexOf(")"))
								.split(",");
						JavaScriptFunction javaScriptFunction = new JavaScriptFunction();
						if (jSFileStr.length == 3) {
							value = javaScriptFunction.jsScriptFunction(jSFileStr[0], jSFileStr[1],
									getVariableValue(jSFileStr[2]));
						}
					}
				} else {
					value = function[i];
				}
			} else {
				value = functions(value, function[i]);// 执行组合函数
			}
		}
		return value;
	}

	/**
	 * 组合函数方法
	 */
	public String functions(String value, String functionStr) {
		if (null != value && null != functionStr) {
			String type = null;
			if (functionStr.contains("${") && functionStr.contains("}")
					&& functionStr.indexOf("}") > functionStr.indexOf("${")) {
				type = functionStr.substring(functionStr.indexOf("${") + 2, functionStr.indexOf("}"));
			}
			if (null != type) {
				if (type.contains("Custom_") && type.contains("(") && type.contains(")")
						&& type.indexOf(")") > type.indexOf("(")) {
					String method = type.substring(type.indexOf("_"), type.indexOf("("));
					Map<String, String> paramValue = new HashMap<String, String>();
					String[] paramValues = type.substring(type.indexOf("(") + 1, type.lastIndexOf(")")).split(",");
					paramValue.put("param0", value);
					for (int j = 0; j < paramValues.length; j++) {
						paramValue.put("param" + (j + 1), getVariableValue(paramValues[j]));
					}
					CustomFunction customFunction = new CustomFunction();
					try {
						value = (String) customFunction.getClass().getMethod(method, new Class[] { Map.class })
								.invoke(customFunction, new Object[] { paramValue });
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						value = "method not found!";
					} catch (Exception e) {
						// TODO Auto-generated catch block
						value="method error!";
					}
				}
			}
		}
		return value;
	}

	/**
	 * 获取json值
	 */
	public String getJsonValue(String str, String key) {
		try {
			if (null != str) {
				JsonParser parser = new JsonParser();
				JsonElement jsonObject = parser.parse(str);
				String[] keyArray = key.split("\\.");
				for (String keyObject : keyArray) {
					if (null != jsonObject && !jsonObject.isJsonNull()) {
						Integer keyObjectNum = null;
						if (keyObject.contains("[")) {
							keyObjectNum = Integer
									.valueOf(keyObject.substring(keyObject.indexOf("[") + 1, keyObject.indexOf("]")));
						}
						if (null != keyObjectNum) {
							String keyObjectStr = keyObject.substring(0, keyObject.indexOf("["));
							if ("$array".equals(keyObjectStr)) {
								jsonObject = jsonObject.getAsJsonArray().get(keyObjectNum);
							} else if (str.contains(keyObjectStr + "\":[")) {
								jsonObject = jsonObject.getAsJsonObject().getAsJsonArray(keyObjectStr)
										.get(keyObjectNum);
							}
						} else if (jsonObject instanceof JsonObject) {
							if (jsonObject.getAsJsonObject().get(keyObject) instanceof JsonObject) {
								jsonObject = jsonObject.getAsJsonObject().get(keyObject).getAsJsonObject();
							} else
								jsonObject = jsonObject.getAsJsonObject().get(keyObject);
						}
					}
				}
				if (null != jsonObject && !jsonObject.isJsonNull()) {
					return jsonObject.getAsString();
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取请求头值
	 */
	public String getHeaderValue(Header[] headers, String name) {
		if (null != headers && headers.length > 0) {
			String headerStr = "";
			for (Header header : headers) {
				if (header.getName().equals(name)) {
					headerStr += header.getValue() + ";";
				}
			}
			return headerStr.substring(0, headerStr.length());
		}
		return null;
	}

	/**
	 * 获取变量值
	 */
	public String getVariableValue(String str) {
		if (str.contains("${VAR(") && str.contains("}") && null != variables) {
			return variables.get(str.substring(str.indexOf("${VAR(") + 6, str.indexOf(")}")));
		}
		if (null != bindVariables && str.contains("${Bind:")) {
			return bindVariables.get(str.substring(str.indexOf("${Bind:") + 7, str.indexOf("}")));
		}
		return str;
	}
}
