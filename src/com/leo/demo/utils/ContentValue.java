package com.leo.demo.utils;
/**
 * 
 * @author Scleo
 *
 */
public abstract class ContentValue {
	public static final int SOCKET_TIMEOUT = 10000;
	//Sp文件中记录是否已登录
	public static final String SPFILE_TOKEN = "auth_token";
	public static String[] testValue = new String[]{
			"2014-01-31	记-1	库存现金 付款￥555.00",
			"2014-01-31	记-1	库存现金 付款￥666.00",
			"2014-01-31	记-1	库存现金 收入￥777.00",
			"2014-01-31	记-1	库存现金 收入￥888.00",
	};
	public static String ENCODING = "utf-8";
	/**
	 * 服务器地址
	 */
	public static String SERVER_URL = "http://concordyaapi.chinacloudsites.cn/api";
	public static String SERVER_URI = "http://concordyaapi.chinacloudsites.cn/api";
	public static String REGIST_URI = "account/register";
	public static String VERICAL_REQUEST = "verifycode";
	public static String VERICAL_PHONE = "Phone";
	public static String BILL_GETALL = "bill";
	public static String LOGIN_URI = "token";
	/*****************http参数***********************/
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String ACCEPT_TYPE = "Accept";
	public static final String APPLICATION_JSON = "application/json";
	public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
	public static final String APPLICATION_XML = "application/xml";
	/*****************服务器返回状态************************/
	public static final String ERROR_MSG = "error";
	/***************临时测试的部分***************/
	public  static String VERICAL_CODE = "55554";
	/***************网络状态***********/
	public static int NO_NETWORK = 0;
	public static int WIFI_STATE = 1;
	public static int CNWAP_STATE = 2;
	public static int CMNET_STATE = 0;
	/***************账单状态***********/
	/************测试数据**************/
	/***短信运营商号码中心***/
	public static final String SERVER_PHONE = "106550771016990848";//联通
	public static final String SERVER_PHONE_DX = "10690660990849";//电信
	public static final String SERVER_PHONE_YD = "10690262999";//移动
}
