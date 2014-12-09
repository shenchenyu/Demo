package com.leo.demo.engine;

import java.util.List;

import com.leo.demo.bean.Bill;
import com.leo.demo.bean.User;

public interface BillEngine {
	/**
	 * 获取当前用户的bill信息
	 * @param user
	 * @return
	 */
	List<Bill> getBills(User user);
}
