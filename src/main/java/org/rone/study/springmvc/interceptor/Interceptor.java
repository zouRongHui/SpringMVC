package org.rone.study.springmvc.interceptor;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class Interceptor implements HandlerInterceptor {

	/**
	 * 在调用业务方法或者下一个拦截器之前执行
	 * return true 时会继续执行业务方法或者下一个拦截器方法
	 * return false 时则不会继续执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		System.out.println("在调用业务方法或者下一个拦截器之前执行..." + arg0.getRequestURL());
		Integer seconds = Calendar.getInstance().get(Calendar.SECOND);
		if (seconds % 2 == 0) {
			return true;
		} else {
			arg1.sendRedirect("/rone/error");
			return false;
		}
	}

	/**
	 * 在业务方法之后，渲染视图之前执行
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("在业务方法之后，渲染视图之前执行");
	}

	/**
	 * 渲染视图之后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("渲染视图之后执行");
	}

}
