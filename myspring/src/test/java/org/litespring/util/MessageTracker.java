package org.litespring.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 	测试的工具类,当我们对类配置了aop后,用于判断aop方法的执行
 */
public class MessageTracker {
	
	private static List<String> MESSAGES = new ArrayList<>();
	
	public static void addMsg(String msg) {
		MESSAGES.add(msg);
	}
	
	public static void clearMsgs() {
		MESSAGES.clear();
	}
	
	public static List<String> getMsgs() {
		return MESSAGES;
	}
	
}
