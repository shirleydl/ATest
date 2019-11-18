package com.shirley.aTest.util;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.shirley.aTest.entity.ResponseContent;

public class HttpClientUtil {
	private static HttpClientUtil instance = null;

	private HttpClientUtil() {
	}

	public static HttpClientUtil getInstance() {
		if (null == instance) {
			instance = new HttpClientUtil();
		}
		return instance;
	}

	public static CloseableHttpClient getHttpClient(final int executionCount, int retryInterval) {
		ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy = new MyServiceUnavailableRetryStrategy.Builder()
				.executionCount(executionCount).retryInterval(retryInterval).build();
		return HttpClientBuilder.create().setRetryHandler(new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException e, int count, HttpContext contr) {
				if (count >= executionCount) {
					// Do not retry if over max retry count
					return false;
				}
				if (e instanceof InterruptedIOException) {
					// Timeout
					return true;
				}

				return true;
			}
		}).setServiceUnavailableRetryStrategy(serviceUnavailableRetryStrategy)
				.setConnectionManager(new PoolingHttpClientConnectionManager()).build();
	}

	/**
	 * 发送 post请求
	 * 
	 * @param httpUrl
	 *            地址
	 */
	public ResponseContent sendHttpPost(String httpUrl, Map<String, String> headers, RequestConfig requestConfig,
			int executionCount, int retryInterval) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		return sendHttpPost(httpPost, headers, requestConfig, executionCount, retryInterval);
	}

	/**
	 * 发送 post请求
	 * 
	 * @param httpUrl
	 *            地址
	 * @param params
	 *            参数(格式:key1=value1&key2=value2)
	 */
	public ResponseContent sendHttpPost(String httpUrl, String params, Map<String, String> headers,
			RequestConfig requestConfig, int executionCount, int retryInterval) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		try {
			// 设置参数
			StringEntity stringEntity = new StringEntity(params, "UTF-8");
			httpPost.setEntity(stringEntity);
			return sendHttpPost(httpPost, headers, requestConfig, executionCount, retryInterval);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 发送 post请求
	 * 
	 * @param httpUrl
	 *            地址
	 * @param maps
	 *            参数
	 */
	public ResponseContent sendHttpPost(String httpUrl, Map<String, String> maps, Map<String, String> headers,
			RequestConfig requestConfig, int executionCount, int retryInterval) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		// 创建参数队列
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if (null != maps && maps.size() > 0) {
			for (String key : maps.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
			}
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			return sendHttpPost(httpPost, headers, requestConfig, executionCount, retryInterval);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 发送 post请求（带文件）
	 * 
	 * @param httpUrl
	 *            地址
	 * @param maps
	 *            参数
	 * @param fileLists
	 *            附件
	 */
	public ResponseContent sendHttpPost(String httpUrl, Map<String, String> maps, List<File> fileLists,
			Map<String, String> headers, RequestConfig requestConfig, int executionCount, int retryInterval) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
		if (null != maps && maps.size() > 0) {
			for (String key : maps.keySet()) {
				meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
			}
		}
		for (File file : fileLists) {
			FileBody fileBody = new FileBody(file);
			meBuilder.addPart("files", fileBody);
		}
		HttpEntity reqEntity = meBuilder.build();
		httpPost.setEntity(reqEntity);
		return sendHttpPost(httpPost, headers, requestConfig, executionCount, retryInterval);
	}

	/**
	 * 发送Post请求
	 * 
	 * @param httpPost
	 * @return
	 */
	private ResponseContent sendHttpPost(HttpPost httpPost, Map<String, String> headers, RequestConfig requestConfig,
			int executionCount, int retryInterval) {
		CloseableHttpClient httpClient = getHttpClient(executionCount, retryInterval);
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		ResponseContent responseContent = new ResponseContent();
		try {
			if (null == httpClient)
				// 创建默认的httpClient实例.
				httpClient = HttpClients.createDefault();
			httpPost.setConfig(requestConfig);
			if (null != headers && headers.size() > 0) {
				for (String key : headers.keySet()) {
					httpPost.addHeader(key, headers.get(key));
				}
			}

			// 执行请求
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			responseContent.setHeaders(response.getAllHeaders());
			responseContent.setResponseCode(response.getStatusLine().getStatusCode() + "");
			responseContent.setContent(EntityUtils.toString(entity, "UTF-8"));
		} catch (InterruptedIOException e) {
			responseContent.setContent("请求超时！");
		} catch (Exception e) {
			responseContent.setContent("请求报错！");
		} finally {
			try {
				// 关闭连接,释放资源
				if (null != response) {
					response.close();
				}
				if (null != httpClient) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	/**
	 * 发送 get请求
	 * 
	 * @param httpUrl
	 */
	public ResponseContent sendHttpGet(String httpUrl, Map<String, String> headers, RequestConfig requestConfig,
			int executionCount, int retryInterval) {
		HttpGet httpGet = new HttpGet(httpUrl);
		return sendHttpGet(httpGet, headers, requestConfig, executionCount, retryInterval);

	}

	/**
	 * 发送 get请求Https
	 * 
	 * @param httpUrl
	 */
	public ResponseContent sendHttpsGet(String httpUrl, Map<String, String> headers, RequestConfig requestConfig,
			int executionCount, int retryInterval) {
		HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
		return sendHttpsGet(httpGet, headers, requestConfig, executionCount, retryInterval);
	}

	/**
	 * 发送Get请求
	 * 
	 * @param httpPost
	 * @return
	 */

	private ResponseContent sendHttpGet(HttpGet httpGet, Map<String, String> headers, RequestConfig requestConfig,
			int executionCount, int retryInterval) {
		CloseableHttpClient httpClient = getHttpClient(executionCount, retryInterval);
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		ResponseContent responseContent = new ResponseContent();
		try {
			if (null == httpClient)
				// 创建默认的httpClient实例.
				httpClient = HttpClients.createDefault();
			httpGet.setConfig(requestConfig);
			if (null != headers && headers.size() > 0) {
				for (String key : headers.keySet()) {
					httpGet.addHeader(key, headers.get(key));
				}
			}
			// 执行请求
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			responseContent.setHeaders(response.getAllHeaders());
			responseContent.setResponseCode(response.getStatusLine().getStatusCode() + "");
			responseContent.setContent(EntityUtils.toString(entity, "UTF-8"));
		} catch (InterruptedIOException e) {
			responseContent.setContent("请求超时！");
		} catch (Exception e) {
			responseContent.setContent("请求报错！");
		} finally {
			try {
				// 关闭连接,释放资源
				if (null != response) {
					response.close();
				}
				if (null != httpClient) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	/**
	 * 发送Get请求Https
	 * 
	 * @param httpPost
	 * @return
	 */
	private ResponseContent sendHttpsGet(HttpGet httpGet, Map<String, String> headers, RequestConfig requestConfig,
			int executionCount, int retryInterval) {
		CloseableHttpClient httpClient = getHttpClient(executionCount, retryInterval);
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		ResponseContent responseContent = new ResponseContent();
		try {
			// 创建默认的httpClient实例.
			PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader
					.load(new URL(httpGet.getURI().toString()));
			DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
			httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
			httpGet.setConfig(requestConfig);
			if (null != headers && headers.size() > 0) {
				for (String key : headers.keySet()) {
					httpGet.addHeader(key, headers.get(key));
				}
			}
			// 执行请求
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			responseContent.setHeaders(response.getAllHeaders());
			responseContent.setResponseCode(response.getStatusLine().getStatusCode() + "");
			responseContent.setContent(EntityUtils.toString(entity, "UTF-8"));
		} catch (InterruptedIOException e) {
			responseContent.setContent("请求超时！");
		} catch (Exception e) {
			responseContent.setContent("请求报错！");
		} finally {
			try {
				// 关闭连接,释放资源
				if (null != response) {
					response.close();
				}
				if (null != httpClient) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}
}