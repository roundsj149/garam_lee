package com.explorer.routemap.member.controller;

import java.io.*; 
import java.util.*; 
import javax.servlet.http.*;

public class SessionChecker {
	
	public static int nowUser = 0; 

    public static void setSession(HttpSession session){ 
    	// 리스너 객체를 생성해서 이것도 세션에 같이 담는다.  리스너 라는 이름으로... 
        session.setAttribute("listener", new CustomBindingListener()); 
    } 

    public static void setNowUser(int x){ 
    	nowUser += x; 
    } 

    public static int getNowUser(){ 
    	return nowUser;
    }
    
}

class CustomBindingListener implements HttpSessionBindingListener {
	
	public void valueBound(HttpSessionBindingEvent event) { 
	//세션이 생겼을 때 할 내용 
		
		SessionChecker.setNowUser(1);

	} 
	
	public void valueUnbound(HttpSessionBindingEvent event) { 
	//세션이 종료되었을때 
		SessionChecker.setNowUser(-1);
	} 
} 