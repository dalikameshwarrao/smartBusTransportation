package com.smartbustransport.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Order(1)
public class CORSFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CORSFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String origin = req.getHeader("Origin");
		if (origin != null && !origin.isEmpty()) {
			resp.setHeader("Access-Control-Allow-Origin", origin);
			resp.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,PUT,OPTIONS");
			resp.setHeader("Access-Control-Max-Age", "3600");
			resp.setHeader("Access-Control-Allow-Credentials", "true");
			resp.setHeader("Access-Control-Allow-Headers",
					"Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Access-Control-Allow-Credentials,Access-Control-Allow-Origin,Authorization,*");
			if (RequestMethod.OPTIONS.name().equals(req.getMethod())) {
				resp.setStatus(HttpServletResponse.SC_OK);
				return;
			}
		}
		chain.doFilter(request, response);
	}
	
	

}
