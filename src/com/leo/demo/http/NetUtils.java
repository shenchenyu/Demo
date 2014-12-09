package com.leo.demo.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.leo.demo.utils.ContentValue;
import com.lidroid.xutils.util.LogUtils;
/**
 * 
 * @author Scleo 二〇一四年十二月八日 17:03:53
 *
 */
public class NetUtils {

	private static HttpGet get;
	private static HttpPost post;

	/**
	 * 执行post请求, 访问账户验证
	 * @param username
	 * @param password
	 * @return
	 */
	public static String doPostOfHttpClient(String url, String json) {
		// 定义一个客户端
		HttpClient client = new DefaultHttpClient();
		post = new HttpPost(url);
		// 设置请求的参数
		HttpParams params = post.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 10000);
		HttpConnectionParams.setSoTimeout(params, 10000);
		//  设置请求头消息 
		//	post.setHeader(name, value)
		post.setHeader("Content-Type","application/json");
		post.setHeader("Accept","application/json");
		try {
			StringEntity entity = new StringEntity(json, ContentValue.ENCODING); // 创建一个UrlEncodedFormEntity对象
			post.setEntity(entity); // 设置请求的实体参数
			//  执行请求方法
			HttpResponse response = client.execute(post);
			//  获取服务器返回的状态码
			StatusLine statusLine = response.getStatusLine(); // 获取状态行对象, 其中状态码就在里边
			int statusCode = statusLine.getStatusCode(); // 从状态行中获取状态码
			LogUtils.d("访问地址："+url);
			LogUtils.d("参数："+json);
			LogUtils.d("响应码："+statusCode);
			if(statusCode == 200) {
				// 访问成功, 获取服务器返回的数据
				LogUtils.d("Post_Entity:"+EntityUtils.toString(entity, ContentValue.ENCODING));
				return EntityUtils.toString(entity,ContentValue.ENCODING);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(client != null) {
				//  获得连接管理器, 关闭连接
				client.getConnectionManager().shutdown();
			}
		}
		return null;
	}
	
	/**
	 * 拿着姓名和密码验证.
	 * @param username
	 * @param password
	 * @return 成功: success, 失败: failed
	 * 		   访问失败时, 返回null
	 */
	public static String doGetOfHttpClient(String url) {
		// URLEncoder.encode(username) url编码
		//  定义一个客户端(HttpClient)
		HttpClient client = new DefaultHttpClient();
		//url = "http://concordapi.chinacloudsites.cn/api/verifycode?Phone=1&verifycode=1";
		get = new HttpGet(url);
		//  设置一些请求参数
		HttpParams params = get.getParams();
		// 设置请求超时时间
		HttpConnectionParams.setConnectionTimeout(params, 10000);
		// 设置读取超时时间
		HttpConnectionParams.setSoTimeout(params, 10000);
		//  有时候需要设置一些请求头消息, 可选
		//	get.setHeader("use-agent", "asdfasdf");
		get.setHeader("Content-Type", "application/json");
		get.setHeader("Accept", "application/json");
		try {
			//  开始执行get请求方法, 并且返回给我们一个HttpResponse的响应对象
			HttpResponse response = client.execute(get);
			//  获取服务器返回的状态码
			StatusLine statusLine = response.getStatusLine(); // 获取状态行对象, 其中状态码就在里边
			int statusCode = statusLine.getStatusCode(); // 从状态行中获取状态码
			LogUtils.d("访问地址："+url);
			//LogUtils.d("参数："+json.toString());
			LogUtils.d("响应码："+statusCode);
			if(statusCode == 200) {
				//  访问成功, 获取服务器返回的数据
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity, ContentValue.ENCODING);
				LogUtils.d("GET_Entity:"+result);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(client != null) {
				// 8. 获取连接管理器, 断开链接
				client.getConnectionManager().shutdown();
			}
		}
		return null;
	}
}
