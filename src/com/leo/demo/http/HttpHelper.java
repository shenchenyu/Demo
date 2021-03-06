package com.leo.demo.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;
import org.apache.http.util.EntityUtils;
import com.leo.demo.utils.ContentValue;
import com.leo.demo.utils.IOUtils;
import com.leo.demo.utils.LogUtils;
import com.leo.demo.utils.StringUtils;

/**
 * @author Scleo
 */
public class HttpHelper {

	/**
	 * @param url
	 *            访问get请求地址
	 * @param type
	 *            访问http头信息Content-type
	 * @return
	 */
	public static HttpResult get(String url, String type) {
		HttpGet httpGet = new HttpGet(url);
		return execute(url, httpGet, type);
	}

	/** post请求，获取返回字符串内容 */
	public static HttpResult post(String url, String json, String type) {
		HttpPost httpPost = new HttpPost(url);
		StringEntity strEntity;
		try {
			if (!StringUtils.isEmpty(json)) {
				strEntity = new StringEntity(json, ContentValue.ENCODING);
				httpPost.setEntity(strEntity);
			}
			return execute(url, httpPost, type);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 下载 */
	public static HttpResult download(String url, String type) {
		HttpGet httpGet = new HttpGet(url);
		return execute(url, httpGet, type);
	}

	/** 执行网络访问 */
	private static HttpResult execute(String url, HttpRequestBase requestBase,
			String contentType) {
		boolean isHttps = url.startsWith("https://");// 判断是否需要采用https
		AbstractHttpClient httpClient = HttpClientFactory.create(isHttps);
		HttpContext httpContext = new SyncBasicHttpContext(
				new BasicHttpContext());
		HttpRequestRetryHandler retryHandler = httpClient
				.getHttpRequestRetryHandler();// 获取重试机制
		requestBase.setHeader(ContentValue.CONTENT_TYPE, contentType);
		requestBase.setHeader(ContentValue.ACCEPT_TYPE,
				ContentValue.APPLICATION_JSON);
		int retryCount = 0;
		boolean retry = true;
		while (retry) {
			try {
				HttpResponse response = httpClient.execute(requestBase,
						httpContext);// 访问网络
				if (response != null) {
					return new HttpResult(response, httpClient, requestBase);
				}
			} catch (Exception e) {
				IOException ioException = new IOException(e.getMessage());
				retry = retryHandler.retryRequest(ioException, ++retryCount,
						httpContext);// 把错误异常交给重试机制，以判断是否需要采取从事
				LogUtils.e(e.getMessage());
			}
		}
		return null;
	}

	/** http的返回结果的封装，可以直接从中获取返回的字符串或者流 */
	public static class HttpResult {
		private HttpResponse mResponse;
		private InputStream mIn;
		private String mStr;
		private HttpClient mHttpClient;
		private HttpRequestBase mRequestBase;

		public HttpResult(HttpResponse response, HttpClient httpClient,
				HttpRequestBase requestBase) {
			mResponse = response;
			mHttpClient = httpClient;
			mRequestBase = requestBase;
		}

		public int getCode() {
			StatusLine status = mResponse.getStatusLine();
			return status.getStatusCode();
		}

		/** 从结果中获取字符串，一旦获取，会自动关流，并且把字符串保存，方便下次获取 */
		public String getString() {
			if (!StringUtils.isEmpty(mStr)) {
				return mStr;
			}
			HttpEntity entity = mResponse.getEntity();
			try {
				mStr = EntityUtils.toString(entity, ContentValue.ENCODING);
			} catch (ParseException e) {
				LogUtils.e(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return mStr;
		}

		/** 获取流，需要使用完毕后调用close方法关闭网络连接 */
		public InputStream getInputStream() {
			if (mIn == null && getCode() < 300) {
				HttpEntity entity = mResponse.getEntity();
				try {
					mIn = entity.getContent();
				} catch (Exception e) {
					LogUtils.e(e.getMessage());
				}
			}
			return mIn;
		}

		/** 关闭网络连接 */
		public void close() {
			if (mRequestBase != null) {
				mRequestBase.abort();
			}
			IOUtils.close(mIn);
			if (mHttpClient != null) {
				mHttpClient.getConnectionManager().closeExpiredConnections();
			}
		}
	}
}
