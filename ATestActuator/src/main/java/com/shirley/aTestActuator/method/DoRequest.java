package com.shirley.aTestActuator.method;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.config.RequestConfig;

import com.shirley.aTestActuator.entity.AssertResult;
import com.shirley.aTestActuator.entity.Asserts;
import com.shirley.aTestActuator.entity.Request;
import com.shirley.aTestActuator.entity.ResponseContent;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年7月8日 下午2:16:43
 */
public class DoRequest {
	private Request request;
	private int taskId;
	private Map<String, String> bindVariables;
	private Map<String, String> variables = new HashMap<String, String>();

	/**
	 * 请求初始化，把请求的值转换成对应真实的值。包括请求头、请求参数、测试集绑定变量、用例变量
	 */
	public DoRequest(int taskId, Request request, Map<String, String> bindVariables) {
		this.taskId = taskId;
		this.bindVariables = bindVariables;
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> paramMap = new LinkedHashMap<String, String>();

		// 获取用例变量值（不包含返回值）
		if (null != request.getVariables() && request.getVariables().size() > 0) {
			for (Entry<String, String> e : request.getVariables().entrySet()) {
				if (!e.getValue().contains("${StatusCode}") && !e.getValue().contains("${Content:")
						&& !e.getValue().contains("${Header:")) {
					variables.put(e.getKey(), getValue(e.getValue(), null));
				}
			}
		}

		// 获取请求头值
		if (null != request.getHeaders() && request.getHeaders().size() > 0) {
			for (Entry<String, String> e : request.getHeaders().entrySet()) {
				e.setValue(getValue(e.getValue(), null));
				headers.put(e.getKey(), e.getValue());
			}
			request.setHeaders(headers);
		}

		// 获取请求参数值（map）
		if (null != request.getParamMap() && request.getParamMap().size() > 0) {
			for (Entry<String, String> e : request.getParamMap().entrySet()) {
				e.setValue(getValue(e.getValue(), null));
				paramMap.put(e.getKey(), e.getValue());
			}
			request.setParamMap(paramMap);
		}

		// 获取请求头值（raw）
		if (null != request.getParamStr() && !request.getParamStr().equals("")) {
			request.setParamStr(getValue(request.getParamStr(), null));
		}
		this.request = request;

	}

	/**
	 * 执行请求
	 */
	public ResponseContent toRequest() {
		ResponseContent responseContent = new ResponseContent();
		RequestConfig requestConfig = null;
		// 设定超时时间
		if (0 != request.getTimeout()) {
			requestConfig = RequestConfig.custom().setSocketTimeout(request.getTimeout())
					.setConnectTimeout(request.getTimeout()).setConnectionRequestTimeout(request.getTimeout()).build();
		}

		// 判断method调用请求方法
		if ("get".equals(request.getMethod())) {
			StringBuffer sbParams = new StringBuffer();
			if (null != request.getParamMap() && request.getParamMap().size() > 0) {
				for (Entry<String, String> e : request.getParamMap().entrySet()) {
					sbParams.append(null!=e.getKey()?e.getKey():"");
					sbParams.append("=");
					try {
						sbParams.append(null!=e.getValue()?URLEncoder.encode(e.getValue(), "utf-8"):"");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						sbParams.append("");
					}
					sbParams.append("&");
				}
				responseContent = HttpClientUtil.getInstance().sendHttpGet(
						request.getUrl() + request.getApi() + "?" + sbParams.substring(0, sbParams.length() - 1),
						request.getHeaders(), requestConfig, request.getRetry(), request.getInterval());

			} else
				responseContent = HttpClientUtil.getInstance().sendHttpGet(request.getUrl() + request.getApi(),
						request.getHeaders(), requestConfig, request.getRetry(), request.getInterval());

		}
		if ("form".equals(request.getMethod())) {
			responseContent = HttpClientUtil.getInstance().sendHttpPost(request.getUrl() + request.getApi(),
					request.getParamMap(), request.getHeaders(), requestConfig, request.getRetry(),
					request.getInterval());

		}
		if ("raw".equals(request.getMethod())) {
			responseContent = HttpClientUtil.getInstance().sendHttpPost(request.getUrl() + request.getApi(),
					request.getParamStr(), request.getHeaders(), requestConfig, request.getRetry(),
					request.getInterval());

		}

		return responseContent;
	}

	/**
	 * 更新取返回值的用例变量
	 */
	public void toUpdateVariables(ResponseContent responseContent) {
		if (null != request.getVariables() && request.getVariables().size() > 0) {
			for (Entry<String, String> e : request.getVariables().entrySet()) {
				if (e.getValue().contains("${StatusCode}") || e.getValue().contains("${Content:")
						|| e.getValue().contains("${Header:")) {
					variables.put(e.getKey(), getValue(e.getValue(), responseContent));
				}
			}
		}
	}

	/**
	 * 通过返回值绑定测试集变量
	 */
	public Map<String, String> toBind(ResponseContent responseContent) {
		Map<String, String> binds = request.getBindVariables();
		Map<String, String> bindMap = new HashMap<String, String>();
		if (null != binds && binds.size() > 0) {
			for (Map.Entry<String, String> bind : binds.entrySet()) {
				bindMap.put(bind.getKey(), getValue(bind.getValue(), responseContent));
			}
		}

		return bindMap;
	}

	/**
	 * 执行断言
	 */
	public AssertResult toAssert(ResponseContent responseContent) {
		List<Asserts> assertList = request.getAsserts();
		Map<String, String> assertRestultsMap = new HashMap<String, String>();
		String allStatus = "success";
		AssertResult assertResult = new AssertResult();
		assertResult.setTaskId(taskId);
		assertResult.setUrl(request.getUrl() + request.getApi());
		assertResult.setRequestContent(request.toString());
		assertResult.setResponseContent(responseContent.toString());

		for (Asserts asserts : assertList) {
			String status = "fail";
			status = assertMehtod(getValue(asserts.getKey(), responseContent),
					getValue(asserts.getValue(), responseContent), asserts.getMethod()) ? "success" : "fail";
			assertRestultsMap.put("\n" + asserts.getKey() + "-" + asserts.getMethod() + "-" + asserts.getValue(),
					status);
			if (status != "success") {
				allStatus = "fail";
			}
		}
		assertResult.setAssertResult(assertRestultsMap.toString());
		assertResult.setStatus(allStatus);
		return assertResult;
	}

	/**
	 * 根据判断assert。method进行断言
	 */
	public Boolean assertMehtod(String key, String value, String method) {
		try {
			if (null != key && null != value) {
				if ("equal".equals(method)) {
					if (key.equals(value))
						return true;
					else
						return false;

				}
				if ("nEqual".equals(method)) {
					if (key.equals(value))
						return false;
					else
						return true;
				}
				if ("contain".equals(method)) {
					if (key.contains(value))
						return true;
					else
						return false;
				}
				if ("nContain".equals(method)) {
					if (key.contains(value))
						return false;
					else
						return true;
				}
				if ("regular".equals(method)) {
					Pattern pattern = Pattern.compile(value);
					Matcher matcher = pattern.matcher(key);
					return matcher.matches();
				}
				if ("startwith".equals(method)) {
					return key.startsWith(value);
				}
				if ("endwith".equals(method)) {
					return key.endsWith(value);
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取各种类型的值
	 */
	public String getValue(String value, ResponseContent responseContent) {
		String addValues[] = null;
		GetValue getValue = new GetValue(bindVariables, variables, responseContent);
		String valueStr = "";
		String splitStr = "&" + "+" + "&";
		// 判断是否有拼接值
		if (value.contains("${") && value.contains(splitStr)) {
			addValues = value.split("&\\+&");
		}
		if (null != addValues && addValues.length > 0) {
			for (String addValue : addValues) {
				valueStr += getValue.getValue(addValue);
			}
		} else {
			valueStr = getValue.getValue(value);
		}
		return valueStr;
	}
}
