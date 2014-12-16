package com.leo.demo.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class MD5Utils {
	/**
	 *  对密码进行MD5加密
	 * @param password
	 * @return
	 */
	public static String digest(String password){
		// 参数 加密方式
		try {
			MessageDigest digest=MessageDigest.getInstance("MD5");
			StringBuffer  sb=new StringBuffer();
			byte[] result = digest.digest(password.getBytes());
			for(byte b:result){
				int num=b&0xff;  // 提升到了int 类型
				// 把数转换成16进制的字符串
				String hex=Integer.toHexString(num);  
				if(hex.length()<2){
					sb.append("0");
				}
				sb.append(hex);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
}
