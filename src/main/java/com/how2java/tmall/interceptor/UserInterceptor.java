package com.how2java.tmall.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
*@author Yi
*@data 2017年10月31日 下午9:29:11
*
*/
public class UserInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		//
		String[] URL = new String[]{"forelogout", "forebuyone","foreaddCart","forecart","foredeleteOrderItem","forechangeOrderItem","forebuy",
		"forecreateOrder","forepayed","forebought","foredeleteOrder","forealipay","foreconfirmPay","foreorderConfirmed","forereview","foredoreview"};
		String url = httpServletRequest.getRequestURI();
		url = url.substring(11);
		//System.out.println(url + Arrays.asList(URL).contains(url));
		if (Arrays.asList(URL).contains(url)){
			HttpSession httpSession = httpServletRequest.getSession();
			if(httpSession.getAttribute("user") != null){
				return true;
			}
			httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/fore/login.jsp").forward(httpServletRequest,httpServletResponse);
			return false;
		}
		return true;
	}
	//controller 方法已经执行
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}
	//modelandview 方法已经返回
	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}

	/**
	 * 
	 */


}
