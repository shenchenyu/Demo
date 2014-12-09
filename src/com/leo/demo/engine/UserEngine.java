package com.leo.demo.engine;

import com.leo.demo.bean.User;

public  interface UserEngine {
	 /**
	  * 校验当前用户Token是否过期
	  * @param user
	  * @return
	  */
	 User checkToken(User user);
	 /**
	  * 校验登录是否成功
	  * @param user
	  * @return
	  */
	 User checkLogin(User user);
}	
