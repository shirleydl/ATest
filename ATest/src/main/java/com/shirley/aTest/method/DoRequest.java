package com.shirley.aTest.method;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.config.RequestConfig;

import com.shirley.aTest.entity.AssertResult;
import com.shirley.aTest.entity.Asserts;
import com.shirley.aTest.entity.Request;
import com.shirley.aTest.entity.ResponseContent;
import com.shirley.aTest.util.AssertMehtod;
import com.shirley.aTest.util.GetValue;
import com.shirley.aTest.util.HttpClientUtil;

/**
 * @Description: TODO(执行请求和断言)
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
		Map<String, String> paramMap = new HashMap<String, String>();
		GetValue getValue = new GetValue(bindVariables, variables, null);

		// 获取用例变量值（不包含返回值）
		if (null != request.getVariables() && request.getVariables().size() > 0) {
			for (Entry<String, String> e : request.getVariables().entrySet()) {
				if (!e.getValue().contains("${StatusCode}") && !e.getValue().contains("${Content:")
						&& !e.getValue().contains("${Header:")) {
					variables.put(e.getKey(), getValue.getValue(e.getValue()));
				}
			}
		}
		
		// 获取url值
		request.setUrl(getValue.getValue(request.getUrl()));
				
		// 获取请求头值
		if (null != request.getHeaders() && request.getHeaders().size() > 0) {
			for (Entry<String, String> e : request.getHeaders().entrySet()) {
				e.setValue(getValue.getValue(e.getValue()));
				headers.put(e.getKey(), e.getValue());
			}
			request.setHeaders(headers);
		}

		// 获取请求参数值（map）
		if (null != request.getParamMap() && request.getParamMap().size() > 0) {
			for (Entry<String, String> e : request.getParamMap().entrySet()) {
				e.setValue(getValue.getValue(e.getValue()));
				paramMap.put(e.getKey(), e.getValue());
			}
			request.setParamMap(paramMap);
		}

		// 获取请求头值（raw）
		if (null != request.getParamStr() && !request.getParamStr().equals("")) {
			request.setParamStr(getValue.getValue(request.getParamStr()));
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
		if (0 != request.getTimeout())
			requestConfig = RequestConfig.custom().setSocketTimeout(request.getTimeout())
					.setConnectTimeout(request.getTimeout()).setConnectionRequestTimeout(request.getTimeout())
					.setRedirectsEnabled(request.getRedirect() == 0 ? false : true).build();
		else
			requestConfig = RequestConfig.custom().setSocketTimeout(8000).setConnectTimeout(8000)
					.setConnectionRequestTimeout(8000).setRedirectsEnabled(request.getRedirect() == 0 ? false : true)
					.build();

		// 判断method调用请求方法
		if ("get".equals(request.getMethod())) {
			StringBuffer sbParams = new StringBuffer();
			if (null != request.getParamMap() && request.getParamMap().size() > 0) {
				for (Entry<String, String> e : request.getParamMap().entrySet()) {
					sbParams.append(null != e.getKey() ? e.getKey() : "");
					sbParams.append("=");
					try {
						sbParams.append(null != e.getValue() ? URLEncoder.encode(e.getValue(), "utf-8") : "");
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

		}else if ("form".equals(request.getMethod())) {
			responseContent = HttpClientUtil.getInstance().sendHttpPost(request.getUrl() + request.getApi(),
					request.getParamMap(), request.getHeaders(), requestConfig, request.getRetry(),
					request.getInterval());

		}else if ("raw".equals(request.getMethod())) {
			responseContent = HttpClientUtil.getInstance().sendHttpPost(request.getUrl() + request.getApi(),
					request.getParamStr(), request.getHeaders(), requestConfig, request.getRetry(),
					request.getInterval());

		}else if ("get_url".equals(request.getMethod())) {
			responseContent = HttpClientUtil.getInstance().sendHttpGet(request.getUrl() + request.getApi()+
					request.getParamStr(), request.getHeaders(), requestConfig, request.getRetry(),
					request.getInterval());
		}

		return responseContent;
	}

	/**
	 * 更新取返回值的用例变量
	 */
	public void toUpdateVariables(ResponseContent responseContent) {
		GetValue getValue = new GetValue(bindVariables, variables, responseContent);
		if (null != request.getVariables() && request.getVariables().size() > 0) {
			for (Entry<String, String> e : request.getVariables().entrySet()) {
				if (e.getValue().contains("${StatusCode}") || e.getValue().contains("${Content:")
						|| e.getValue().contains("${Header:")) {
					variables.put(e.getKey(), getValue.getValue(e.getValue()));
				}
			}
		}
	}

	/**
	 * 通过返回值绑定测试集变量
	 */
	public Map<String, String> toBind(ResponseContent responseContent) {
		GetValue getValue = new GetValue(bindVariables, variables, responseContent);
		Map<String, String> binds = request.getBindVariables();
		Map<String, String> bindMap = new HashMap<String, String>();
		if (null != binds && binds.size() > 0) {
			for (Map.Entry<String, String> bind : binds.entrySet()) {
				bindMap.put(bind.getKey(), getValue.getValue(bind.getValue()));
			}
		}
		return bindMap;
	}

	/**
	 * 执行断言
	 */
	public AssertResult toAssert(ResponseContent responseContent) {
		GetValue getValue = new GetValue(bindVariables, variables, responseContent);
		List<Asserts> assertList = request.getAsserts();
		Map<String, String> assertRestultsMap = new HashMap<String, String>();
		String allStatus = "success";
		AssertResult assertResult = new AssertResult();
		assertResult.setTaskId(taskId);
		assertResult.setCaseId(request.getCaseId());
		assertResult.setUrl(request.getUrl() + request.getApi());
		assertResult.setRequestContent(request.toString());
		assertResult.setResponseContent(responseContent.toString());
		AssertMehtod assertMehtod = new AssertMehtod();
		for (Asserts asserts : assertList) {
			String status = "fail";
			status = assertMehtod.doAssert(getValue.getValue(asserts.getKey()), getValue.getValue(asserts.getValue()),
					asserts.getMethod()) ? "success" : "fail";
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
}
