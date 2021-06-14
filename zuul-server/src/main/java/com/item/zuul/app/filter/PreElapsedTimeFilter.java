package com.item.zuul.app.filter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

//@Component
public class PreElapsedTimeFilter extends ZuulFilter{
	
	private static Logger log = org.slf4j.LoggerFactory.getLogger(PreElapsedTimeFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest rq = ctx.getRequest();
		
		log.info(String.format("%s request enrutado a %s", rq.getMethod(), rq.getRequestURL().toString()));
		
		Long startTime = System.currentTimeMillis();
		rq.setAttribute("startTime", startTime);
		
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
